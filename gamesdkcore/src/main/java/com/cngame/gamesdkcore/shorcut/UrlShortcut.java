package com.cngame.gamesdkcore.shorcut;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Amuro on 2016/4/18.
 */
public class UrlShortcut extends Shortcut
{
    private String clickUrl;

    public UrlShortcut(Context context, String shortcutName, String clickUrl)
    {
        super(context, shortcutName);
        this.clickUrl = clickUrl;
    }

    public UrlShortcut(Context context, String shortcutName, boolean duplicated, String clickUrl)
    {
        super(context, shortcutName, duplicated);
        this.clickUrl = clickUrl;
    }

    @Override
    protected Intent getClickIntent()
    {
        Intent clickIntent = new Intent();
        clickIntent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(clickUrl);
        clickIntent.setData(content_url);
        return clickIntent;
    }

}
