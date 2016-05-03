package com.gamesdk.sdkmarketing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cngame.gamesdklib.mvp.view.animator.AnimatorBottomExit;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorTopEnter;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.mvp.view.dialog.SDKBaseAnimationDialog;

/**
 * Created by Amuro on 2016/5/3.
 */
public class MarketingDialog extends SDKBaseAnimationDialog
{
    public MarketingDialog(Context context)
    {
        super(context);
    }

    @Override
    protected BaseAnimatorSet getShowAnim()
    {
        return new AnimatorTopEnter();
    }

    @Override
    protected BaseAnimatorSet getDismissAnim()
    {
        return new AnimatorBottomExit();
    }

    @Override
    protected View onCreateContentView()
    {
        return LayoutInflater.from(context).inflate(
                context.getResources().getIdentifier(
                        "dialog_marketing_layout", "layout", context.getPackageName()), null);
    }
}
