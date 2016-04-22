package com.cngame.gamesdkcore.shorcut.factory;

import android.content.Context;

import com.cngame.gamesdkcore.shorcut.CustomShortcut;
import com.cngame.gamesdkcore.shorcut.Shortcut;
import com.cngame.gamesdkcore.shorcut.UrlShortcut;

/**
 * Created by Amuro on 2016/4/18.
 */
public class ShortcutFactory
{
    public static final int URL = 1;
    public static final int CUSTOM = 2;

    public static Shortcut getShortcut(Context context, String name)
    {
        return getShortcut(CUSTOM, context, name);
    }

    public static Shortcut getShortcut(Context context, String name, String url)
    {
        return getShortcut(URL, context, name, url);
    }

    public static Shortcut getShortcut(int type, Context context, String name, Object... args)
    {
        Shortcut shortcut = null;

        if(type == URL)
        {
            if(args == null || args.length == 0)
            {
                return null;
            }

            shortcut = new UrlShortcut(context, name, (String)args[0]);
        }
        else
        {
            shortcut = new CustomShortcut(context, name);
        }

        return shortcut;
    }
}
