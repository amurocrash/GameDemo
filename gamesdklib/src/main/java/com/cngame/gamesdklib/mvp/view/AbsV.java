package com.cngame.gamesdklib.mvp.view;

import android.content.Context;

/**
 * Created by Amuro on 2016/4/14.
 */
public abstract class AbsV implements IV
{
    protected OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener)
    {
        this.onDismissListener = onDismissListener;
    }

    protected void notifyDismiss(Object... args)
    {
        if(onDismissListener != null)
        {
            onDismissListener.onDismiss(args);
        }
    }

}
