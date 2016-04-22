package com.cngame.gamesdkcore.shorcut;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Amuro on 2016/4/18.
 */
public class CustomShortcut extends Shortcut
{
    public CustomShortcut(Context context, String shortcutName)
    {
        super(context, shortcutName);
    }

    public CustomShortcut(Context context, String shortcutName, boolean duplicated)
    {
        super(context, shortcutName, duplicated);
    }

    @Override
    protected Intent getClickIntent()
    {
        Intent clickInent = new Intent();
        clickInent.setClassName(context, "com.cngame.gamesdkcore.shorcut.activity.ShortcutActivity");
        clickInent.setAction("android.intent.action.MAIN");
        clickInent.addCategory("com.gamesdk.sdkmarketing.gamesdk.category.shortcut");
        return clickInent;
    }

}
