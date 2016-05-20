package com.cngame.gamesdklib.mvp.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cngame.gamesdklib.utils.DisplayUtils;
import com.cngame.gamesdklib.utils.LogUtils;

/**
 * Created by Amuro on 2016/4/15.
 */
public abstract class BaseView extends LinearLayout
{
    protected boolean isLandscape;

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
        isLandscape = DisplayUtils.getConfiguration(getContext()).orientation ==
                    Configuration.ORIENTATION_LANDSCAPE;

        initSelfView();
    }

    protected abstract void initSelfView();

}
