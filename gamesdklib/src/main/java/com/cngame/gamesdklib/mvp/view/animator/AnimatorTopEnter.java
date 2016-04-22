package com.cngame.gamesdklib.mvp.view.animator;

import com.cngame.gamesdklib.utils.DisplayUtils;
import com.nineoldandroids.animation.ObjectAnimator;

import android.view.View;

public class AnimatorTopEnter extends BaseAnimatorSet
{
	public AnimatorTopEnter()
	{
		duration = 1000;
	}

	@Override
	public void setAnimation(View view)
	{
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),
				
				ObjectAnimator.ofFloat(
						view, 
						"translationY", 
						-250 * DisplayUtils.getDensity(view.getContext()),
						30, 
						-10, 
						0)
				);
	}

}