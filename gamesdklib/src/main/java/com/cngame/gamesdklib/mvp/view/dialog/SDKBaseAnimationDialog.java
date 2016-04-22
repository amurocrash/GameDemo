package com.cngame.gamesdklib.mvp.view.dialog;

import android.content.Context;

import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;

public abstract class SDKBaseAnimationDialog extends SDKBaseDialog
{

	public SDKBaseAnimationDialog(Context context)
	{
		super(context);
	}
	
	@Override
	protected void initParams()
	{
		setShowAnim(getShowAnim());
		setDismissAnim(getDismissAnim());
	}

	protected abstract BaseAnimatorSet getShowAnim();
	protected abstract BaseAnimatorSet getDismissAnim();
}
