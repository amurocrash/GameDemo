package com.cngame.gamesdklib.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by Amuro on 2016/3/9.
 */
public abstract class BaseActivity extends FragmentActivity
{
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        context = this;
        initData();
        initView(savedInstanceState);
    }

    protected abstract void initData();
    protected abstract void initView(Bundle savedInstanceState);

}
