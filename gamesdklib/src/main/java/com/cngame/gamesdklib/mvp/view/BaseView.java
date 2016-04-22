package com.cngame.gamesdklib.mvp.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cngame.gamesdklib.utils.DisplayUtils;

/**
 * Created by Amuro on 2016/4/15.
 */
public abstract class BaseView extends LinearLayout
{
    public BaseView(Context context)
    {
        super(context);
        init();
    }

    public BaseView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init()
    {
        FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(
                (int)(DisplayUtils.getScreenWidth(getContext()) * 0.9),
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.CENTER;
        this.setLayoutParams(lParams);
        this.setBackgroundColor(Color.parseColor("#FFFFFF"));
        this.setPadding(20, 20, 20, 20);
        this.setOrientation(LinearLayout.VERTICAL);

        if(getSelfView() != null)
        {
            this.addView(getSelfView());
        }
    }

    protected abstract View getSelfView();

}
