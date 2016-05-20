package com.cngame.gamesdklib.mvp.view.sdk_dialog;

import android.content.Context;

import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;

/**
 * Created by Amuro on 2016/5/13.
 */
public abstract class SDKBaseAnimationDialog extends SDKBaseDialog
{
    public SDKBaseAnimationDialog(Context context)
    {
        super(context);
        initAnim();
    }

    public SDKBaseAnimationDialog(Context context, int gravity, boolean isCancelable, boolean isOutsideCancelable)
    {
        super(context, gravity, isCancelable, isOutsideCancelable);
        initAnim();
    }

    protected void initAnim()
    {
        setShowAnim(getShowAnim());
        setDismissAnim(getDismissAnim());
    }

    protected abstract BaseAnimatorSet getShowAnim();
    protected abstract BaseAnimatorSet getDismissAnim();
}
