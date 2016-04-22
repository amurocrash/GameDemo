package com.cngame.gamesdkcore.shorcut.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by Amuro on 2016/4/18.
 */
public class ShortcutActivity extends FragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(new ShortcutView(this));


    }

}
