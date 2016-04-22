package com.gamesdk.sdkbilling.V;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.BaseView;

/**
 * Created by Amuro on 2016/4/15.
 */
public class BillingView extends BaseView
{
    public static final int id_bt_pay1 = 0x00000001;

    public BillingView(Context context)
    {
        super(context);
    }

    @Override
    protected View getSelfView()
    {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams lParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lParams);
        linearLayout.setPadding(20, 20, 20, 20);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        TextView textView = new TextView(getContext());
        textView.setLayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("Billing page");
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(20);

        LinearLayout linearLayoutCenter = new LinearLayout(getContext());
        LayoutParams lcParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, 400);
        linearLayoutCenter.setLayoutParams(lcParams);
        linearLayoutCenter.setOrientation(LinearLayout.VERTICAL);

        Button buttonPay1 = new Button(getContext());
        buttonPay1.setId(id_bt_pay1);
        LayoutParams bParams1 = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bParams1.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        bParams1.bottomMargin = 20;
        buttonPay1.setLayoutParams(bParams1);
        buttonPay1.setText("确认付费");

        linearLayout.addView(textView);
        linearLayout.addView(linearLayoutCenter);
        linearLayout.addView(buttonPay1);

        return linearLayout;
    }
}
