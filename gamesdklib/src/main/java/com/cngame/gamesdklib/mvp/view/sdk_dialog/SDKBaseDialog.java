package com.cngame.gamesdklib.mvp.view.sdk_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.utils.DisplayUtils;
import com.nineoldandroids.animation.Animator;

public abstract class SDKBaseDialog extends Dialog
{
	public SDKBaseDialog(Context context)
	{
		this(context, Gravity.CENTER, false, false);
	}

	public SDKBaseDialog(Context context, int gravity, boolean isCancelable, boolean isOutsideCancelable)
	{
		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		this.context = context;

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = this.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.dimAmount = 0.8f;
		window.setAttributes(lp);

		setCancelable(isCancelable);
        this.gravity = gravity;
		this.isOutsideCancelable = isOutsideCancelable;
	}

	protected Context context;
	protected LinearLayout rootView;
	protected BaseView contentView;

    private int gravity;
	private boolean isOutsideCancelable = true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		rootView = new LinearLayout(context);
		rootView.setOrientation(LinearLayout.VERTICAL);
		rootView.setBackgroundColor(Color.TRANSPARENT);
		rootView.setGravity(gravity);

		contentView = onCreateContentView();
		rootView.addView(contentView);
		setContentView(rootView);
	}

	protected abstract BaseView onCreateContentView();

	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();

		initContentView();
		initShowAnimation();

	}

	private void initContentView()
	{
		rootView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (isOutsideCancelable)
				{
					int action = event.getAction();

					if (action == MotionEvent.ACTION_DOWN)
					{
						if (!DisplayUtils.inRangeOfView(contentView, event))
						{
							dismiss();
						}
					}
				}

				return false;
			}
		});
	}

	private BaseAnimatorSet showAnimation;
	private BaseAnimatorSet dismissAnimation;
	private boolean isShowAnimPlaying = false;
	private boolean isDimissAnimPlaying = false;

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
			}).playOn(contentView);
		}
		else
		{
			BaseAnimatorSet.reset(contentView);
		}
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
			}).playOn(contentView);
		}
		else
		{
			superDismiss();
		}
	}

	public void superDismiss()
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

        onSDKDialogBackPressed();
	}

    public void onSDKDialogBackPressed()
    {
        finish();
    }

	public SDKBaseDialog setOutsideTouchDismiss(boolean isOutsideCancelable)
	{
		this.isOutsideCancelable = isOutsideCancelable;
		return this;
	}

	public SDKBaseDialog setDimEnabled(boolean isDimEnabled)
	{
		if (isDimEnabled)
		{
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		else
		{
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		return this;
	}

	public SDKBaseDialog setShowAnim(BaseAnimatorSet showAnim)
	{
		this.showAnimation = showAnim;
		return this;
	}

	public SDKBaseDialog setDismissAnim(BaseAnimatorSet dismissAnim)
	{
		this.dismissAnimation = dismissAnim;
		return this;
	}

    protected View getViewById(int id)
    {
        return contentView.findViewById(id);
    }

    public void finish()
    {
        onFinish();
        dismiss();
        dialogDirector.pop();
    }

    protected void onFinish()
    {

    }


    /****************Result Control***************/
    private int resultCode = -1;
    private Bundle resultData;
    private SDKDialogDirector dialogDirector;

    protected void setResult(int resultCode)
    {
        setResult(resultCode, null);
    }

    protected void setResult(int resultCode, Bundle data)
    {
        this.resultCode = resultCode;
        this.resultData = data;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public Bundle getResultData()
    {
        return resultData;
    }

    public void onDialogResult(int requestCode, int resultCode, Bundle data)
    {

    }

    protected void startDialog(DialogIntent intent)
    {
        startDialogForResult(intent, -1);
    }

    protected void startDialogForResult(DialogIntent intent, int requestCode)
    {
        if(dialogDirector != null)
        {
            dialogDirector.startDialogForResult(intent, requestCode);
        }
    }

    public void setDialogDirector(SDKDialogDirector dialogDirector)
    {
        this.dialogDirector = dialogDirector;
    }

    public void onIntent(DialogIntent intent)
    {
    }
}
