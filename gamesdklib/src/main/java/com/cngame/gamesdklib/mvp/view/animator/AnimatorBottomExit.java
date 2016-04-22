package com.cngame.gamesdklib.mvp.view.animator;

import com.cngame.gamesdklib.utils.DisplayUtils;
import com.nineoldandroids.animation.ObjectAnimator;

import android.view.View;

public class AnimatorBottomExit extends BaseAnimatorSet 
{
	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(
				
				ObjectAnimator.ofFloat(
						view, "translationY", 0, 250 * DisplayUtils.getDensity(view.getContext())),
				
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}