package com.cngame.gamesdkcore.shorcut.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cngame.gamesdklib.utils.DisplayUtils;

/**
 * Created by Amuro on 2016/4/18.
 */
public class ShortcutView extends LinearLayout
{
    public static final int id_bt_start = 0x00000001;
    public static final int id_tv_progress = 0x00000002;
    public static final int id_bt_pause = 0x00000003;

    public ShortcutView(Context context)
    {
        super(context);
        init();
    }

    public ShortcutView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ShortcutView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        this.setOrientation(VERTICAL);

        ScrollView scrollViewBase = new ScrollView(getContext());
        scrollViewBase.setLayoutParams(new LayoutParams(
                (int)(DisplayUtils.getScreenWidth(getContext()) * 0.9),
                (int)(DisplayUtils.getScreenHeight(getContext()) * 0.6)));
        scrollViewBase.setPadding(20, 20, 20, 20);

        scrollViewBase.addView(getContentRootView());
        this.addView(scrollViewBase);
    }

    public View getContentRootView()
    {
        LinearLayout linearLayoutRoot = new LinearLayout(getContext());
        LayoutParams lParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutRoot.setLayoutParams(lParams);
        linearLayoutRoot.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        textView.setText("推荐游戏");

        Button buttonStart = new Button(getContext());
        buttonStart.setId(id_bt_start);
        buttonStart.setText("download");

        Button buttonPause = new Button(getContext());
        buttonPause.setId(id_bt_pause);
        buttonPause.setText("pause");

        TextView textViewProgress = new TextView(getContext());
        textViewProgress.setId(id_tv_progress);

        linearLayoutRoot.addView(textView);
        linearLayoutRoot.addView(buttonStart);
        linearLayoutRoot.addView(buttonPause);
        linearLayoutRoot.addView(textViewProgress);

        return linearLayoutRoot;
    }


}
