package com.gamesdk.sdkbilling.V;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.utils.DisplayUtils;

/**
 * Created by Amuro on 2016/5/12.
 */
public abstract class BillingBaseView extends BaseView
{
    public static final int id_ll_change_pay_way = 0x00000001;
    public static final int id_bt_pay = 0x00000002;
    public static final int id_tv_title = 0x00000003;
    public static final int id_tv_policy = 0x00000004;

    public BillingBaseView(Context context)
    {
        super(context);
    }

    @Override
    protected void initSelfView()
    {
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setGravity(Gravity.CENTER);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        LinearLayout contentView = getContentLinearLayout();

        contentView.addView(getTitle());
        contentView.addView(getCenterView());
        contentView.addView(getButtonPay());

        rootView.addView(contentView);
        this.addView(rootView);
    }

    private LinearLayout getContentLinearLayout()
    {
        int contentViewWidth =
                (int)(Math.min(DisplayUtils.getScreenWidth(getContext()),
                        DisplayUtils.getScreenHeight(getContext())) * 90 / 100);

        LinearLayout contentView = new LinearLayout(getContext());
        LayoutParams lParams = new LayoutParams(
                contentViewWidth, LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(lParams);
        contentView.setPadding(20, 20, 20, 20);
        contentView.setBackgroundColor(Color.WHITE);
        contentView.setOrientation(LinearLayout.VERTICAL);

        return contentView;
    }

    protected View getTitle()
    {
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        LayoutParams rParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        relativeLayout.setPadding(
                0, DisplayUtils.dip2px(getContext(), 5), 0, DisplayUtils.dip2px(getContext(), 5));

        TextView textViewTitle = new TextView(getContext());
        textViewTitle.setId(id_tv_title);
        RelativeLayout.LayoutParams tParams1 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tParams1.addRule(RelativeLayout.CENTER_VERTICAL | RelativeLayout.ALIGN_PARENT_LEFT);
        textViewTitle.setLayoutParams(tParams1);
        textViewTitle.setText("Marketing page");
        textViewTitle.setTextColor(Color.DKGRAY);
        textViewTitle.setTextSize(20);
        relativeLayout.addView(textViewTitle);

        return relativeLayout;
    }

    protected View getCenterView()
    {
        LinearLayout linearLayoutCenter = new LinearLayout(getContext());
        linearLayoutCenter.setId(id_ll_change_pay_way);
        LayoutParams lcParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(getContext(), 50));
        linearLayoutCenter.setLayoutParams(lcParams);
        linearLayoutCenter.setOrientation(LinearLayout.VERTICAL);
        linearLayoutCenter.setGravity(Gravity.CENTER_VERTICAL);

        TextView textViewPayway = new TextView(getContext());
        LayoutParams tvParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.leftMargin = DisplayUtils.dip2px(getContext(), 16);
        textViewPayway.setLayoutParams(tvParams);
        textViewPayway.setText("切换支付方式");
        textViewPayway.setTextColor(Color.DKGRAY);
        textViewPayway.setTextSize(15);

        linearLayoutCenter.addView(textViewPayway);

        return linearLayoutCenter;
    }

    protected View getButtonPay()
    {
        Button buttonPay = new Button(getContext());
        buttonPay.setId(id_bt_pay);
        LayoutParams bParams1 = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        bParams1.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        bParams1.bottomMargin = 20;
        buttonPay.setLayoutParams(bParams1);
        buttonPay.setText("确认付费");

        return buttonPay;
    }

}
