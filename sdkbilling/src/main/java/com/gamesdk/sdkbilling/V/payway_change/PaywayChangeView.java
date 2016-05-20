package com.gamesdk.sdkbilling.V.payway_change;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.utils.DisplayUtils;

/**
 * Created by Amuro on 2016/5/17.
 */
public class PayWayChangeView extends BaseView
{
    public static final int id_lv = 0x00000001;

    public PayWayChangeView(Context context)
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

        contentView.addView(getTitleView());
        contentView.addView(getCenterView());

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

    private View getTitleView()
    {
        TextView textView = new TextView(getContext());
        LayoutParams tParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tParams.topMargin = DisplayUtils.dip2px(getContext(), 10);
        tParams.bottomMargin = DisplayUtils.dip2px(getContext(), 10);
        textView.setLayoutParams(tParams);
        textView.setText("切换支付方式");
        textView.setTextSize(18);
        textView.setTextColor(Color.DKGRAY);

        return textView;
    }

    private View getCenterView()
    {
        ListView listView = new ListView(getContext());
        listView.setId(id_lv);
        LayoutParams lparams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                DisplayUtils.getScreenHeight(getContext()) / 2);
        listView.setLayoutParams(lparams);

        return listView;
    }
}
