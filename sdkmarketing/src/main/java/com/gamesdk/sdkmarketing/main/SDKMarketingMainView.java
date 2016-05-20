package com.gamesdk.sdkmarketing.main;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.utils.DisplayUtils;

/**
 * Created by Amuro on 2016/5/12.
 */
public class SDKMarketingMainView extends BaseView
{
    public static final int id_wv = 0x00000001;
    public static final int id_tv_title = 0x00000002;
    public static final int id_tv_progress = 0x00000003;

    public SDKMarketingMainView(Context context)
    {
        super(context);
    }

    @Override
    protected void initSelfView()
    {
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setGravity(Gravity.CENTER);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout contentView = getContentLinearLayout();

        contentView.addView(getTitle());
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

    private View getTitle()
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

        TextView textViewProgress = new TextView(getContext());
        textViewProgress.setId(id_tv_progress);
        RelativeLayout.LayoutParams tParams2 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tParams2.addRule(RelativeLayout.ALIGN_BOTTOM, id_tv_title);
        textViewProgress.setLayoutParams(tParams2);
        textViewProgress.setText("0%");
        textViewProgress.setTextColor(Color.DKGRAY);
        textViewProgress.setTextSize(15);
        relativeLayout.addView(textViewProgress);

        return relativeLayout;
    }

    private View getCenterView()
    {
        WebView webView = new WebView(getContext());
        webView.setId(id_wv);
        LayoutParams wParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                (int)(DisplayUtils.getScreenHeight(getContext()) * 0.5));
        wParams.topMargin = DisplayUtils.dip2px(getContext(), 10);
        webView.setLayoutParams(wParams);
        return webView;
    }


}
