package com.unknown.gamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cngame.gamesdkcore.shorcut.Shortcut;
import com.cngame.gamesdkcore.shorcut.factory.ShortcutFactory;
import com.cngame.gamesdklib.utils.ToastUtils;

/**
 * Created by Amuro on 2016/4/18.
 */
public class ShortcutTestActivity extends Activity
{
    Shortcut shortcut;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut_test_layout);

        shortcut = ShortcutFactory.getShortcut(this, "MIGU");

        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shortcut.createShortcut();
            }
        });

        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shortcut.deleteShortcut();
            }
        });

        findViewById(R.id.bt_is_exist).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ToastUtils.show(ShortcutTestActivity.this, shortcut.isShortcutExist() ? "存在" : "不存在");
            }
        });
    }
}
