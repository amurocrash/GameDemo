package com.cngame.gamesdklib.mvp.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.utils.DisplayUtils;
import com.nineoldandroids.animation.Animator;

public abstract class SDKBaseDialog extends Dialog
{
    protected Context context;

    private int screenWidth;
    private int screenHeight;
    private int contentWidth;
    private int contentHeight;
    private int dialogMaxHeight;
    private float widthScale;
    private float heightScale;

    private LinearLayout linearLayoutRoot;
    private LinearLayout linearLayoutContent;

    private BaseAnimatorSet showAnimation;
    private BaseAnimatorSet dismissAnimation;
    private boolean isShowAnimPlaying = false;
    private boolean isDimissAnimPlaying = false;
    private boolean isOutsideTouchDismiss = true;

    private int resultCode = -1;
    private Bundle resultData;

    public SDKBaseDialog(Context context)
    {
        super(context);
        this.context = context;

        initData();
        setDialogTheme();
        initParams();
    }

    private void initData()
    {
        screenWidth = DisplayUtils.getScreenWidth(context);
        screenHeight = DisplayUtils.getScreenHeight(context);
        dialogMaxHeight = screenHeight - DisplayUtils.getStatusBar(context);
        widthScale = 0.88f;
        heightScale = 0.5f;
    }

    private void setDialogTheme()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
    }

    protected abstract void initParams();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        linearLayoutRoot = new LinearLayout(context);
        linearLayoutRoot.setGravity(Gravity.CENTER);

        linearLayoutContent = new LinearLayout(context);
        linearLayoutContent.setOrientation(LinearLayout.VERTICAL);
        linearLayoutContent.setBackgroundColor(Color.WHITE);
        linearLayoutContent.addView(onCreateContentView());

        linearLayoutRoot.addView(linearLayoutContent);

        setContentView(linearLayoutRoot,
                new ViewGroup.LayoutParams(screenWidth, dialogMaxHeight));

    }

    protected abstract View onCreateContentView();

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        initContentView();
        initShowAnimation();

    }

    private void initContentView()
    {
        if (widthScale == 0)
        {
            contentWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        else if (widthScale == 1)
        {
            contentWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        else
        {
            contentWidth = (int) (screenWidth * widthScale);
        }

        if (heightScale == 0)
        {
            contentHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        else if (heightScale == 1)
        {
            contentHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        else
        {
            contentHeight = (int) (dialogMaxHeight * heightScale);
        }

        linearLayoutContent.setLayoutParams(new LinearLayout.LayoutParams(contentWidth, contentHeight));

        linearLayoutRoot.setOnTouchListener(new OnTouchListener()
        {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (isOutsideTouchDismiss)
                {
                    int action = event.getAction();

                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        if (!inRangeOfView(linearLayoutContent, event))
                        {
                            dismiss();
                        }
                    }
                }

                return false;
            }
        });
    }

    private void initShowAnimation()
    {
        if (showAnimation != null)
        {
            showAnimation.listener(new BaseAnimatorSet.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animator)
                {
                    isShowAnimPlaying = true;
                }

                @Override
                public void onAnimationRepeat(Animator animator)
                {
                }

                @Override
                public void onAnimationEnd(Animator animator)
                {
                    isShowAnimPlaying = false;
                }

                @Override
                public void onAnimationCancel(Animator animator)
                {
                    isShowAnimPlaying = false;
                }
            }).playOn(linearLayoutContent);
        }
        else
        {
            BaseAnimatorSet.reset(linearLayoutContent);
        }
    }

    private boolean inRangeOfView(View view, MotionEvent ev)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y
                || ev.getY() > (y + view.getHeight()))
        {
            return false;
        }
        return true;
    }

    @Override
    public void dismiss()
    {
        if (dismissAnimation != null)
        {
            dismissAnimation.listener(new BaseAnimatorSet.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animator)
                {
                    isDimissAnimPlaying = true;
                }

                @Override
                public void onAnimationRepeat(Animator animator)
                {
                }

                @Override
                public void onAnimationEnd(Animator animator)
                {
                    isDimissAnimPlaying = false;
                    superDismiss();
                }

                @Override
                public void onAnimationCancel(Animator animator)
                {
                    isDimissAnimPlaying = false;
                    superDismiss();
                }
            }).playOn(linearLayoutContent);
        }
        else
        {
            superDismiss();
        }
    }

    private void superDismiss()
    {
        super.dismiss();
    }

    @Override
    public void onBackPressed()
    {
        if (isShowAnimPlaying || isDimissAnimPlaying)
        {
            return;
        }
        super.onBackPressed();
    }

    public SDKBaseDialog setOutsideTouchDismiss(boolean isOutsideTouchDismiss)
    {
        this.isOutsideTouchDismiss = isOutsideTouchDismiss;
        return this;
    }

    /**
     * set window dim or not(设置背景是否昏暗)
     */
    public SDKBaseDialog setDimEnabled(boolean isDimEnabled)
    {
        if (isDimEnabled)
        {
            getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);
        }
        else
        {
            getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
        }
        return this;
    }

    /**
     * set dialog width scale:0-1(设置对话框宽度,占屏幕宽的比例0-1)
     */
    public SDKBaseDialog setWidthScale(float widthScale)
    {
        this.widthScale = widthScale;
        return this;
    }

    /**
     * set dialog height scale:0-1(设置对话框高度,占屏幕宽的比例0-1)
     */
    public SDKBaseDialog setHeightScale(float heightScale)
    {
        this.heightScale = heightScale;
        return this;
    }

    /**
     * set show anim(设置显示的动画)
     */
    public SDKBaseDialog setShowAnim(BaseAnimatorSet showAnim)
    {
        this.showAnimation = showAnim;
        return this;
    }

    /**
     * set dismiss anim(设置隐藏的动画)
     */
    public SDKBaseDialog setDismissAnim(BaseAnimatorSet dismissAnim)
    {
        this.dismissAnimation = dismissAnim;
        return this;
    }

}






























