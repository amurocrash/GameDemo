package com.gamesdk.sdkbilling.V.normal;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamesdk.sdkbilling.V.BillingBaseView;

/**
 * Created by Amuro on 2016/5/16.
 */
public class ABillingNormalView extends BillingBaseView
{
    public ABillingNormalView(Context context)
    {
        super(context);
    }

    @Override
    protected View getTitle()
    {
        RelativeLayout rl = (RelativeLayout) super.getTitle();

        TextView textViewProgress = new TextView(getContext());
        textViewProgress.setId(id_tv_policy);
        RelativeLayout.LayoutParams tParams2 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tParams2.addRule(RelativeLayout.ALIGN_BOTTOM, id_tv_title);
        textViewProgress.setLayoutParams(tParams2);
        textViewProgress.setText("0");
        textViewProgress.setTextColor(Color.DKGRAY);
        textViewProgress.setTextSize(15);
        rl.addView(textViewProgress);

        return rl;
    }
}
