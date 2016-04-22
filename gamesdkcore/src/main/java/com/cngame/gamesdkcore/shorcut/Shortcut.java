package com.cngame.gamesdkcore.shorcut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.cngame.gamesdkcore.shorcut.tool.LauncherUtil;

/**
 * Created by Amuro on 2016/4/18.
 */
public abstract class Shortcut
{
    protected Context context;
    protected String shortcutName;
    protected boolean duplicated;
    protected Bitmap shortcutIcon;

    public Shortcut(Context context, String shortcutName)
    {
        this(context, shortcutName, false);
    }

    public Shortcut(Context context, String shortcutName, boolean duplicated)
    {
        this(context, shortcutName, duplicated, null);
    }

    public Shortcut(Context context, String shortcutName, Bitmap shortcutIcon)
    {
        this(context, shortcutName, false, shortcutIcon);
    }

    public Shortcut(Context context, String shortcutName, boolean duplicated, Bitmap shortcutIcon)
    {
        this.context = context;
        this.shortcutName = shortcutName;
        this.duplicated = duplicated;
        this.shortcutIcon = shortcutIcon;
    }

    public final void createShortcut()
    {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        shortcutIntent.putExtra("duplicate", duplicated);

        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getClickIntent());

        if(shortcutIcon == null)
        {
            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                    context, context.getResources().getIdentifier(
                            "ic_launcher", "mipmap", context.getPackageName()));
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        }
        else
        {
//        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/1.bmp");
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
        }

        context.sendBroadcast(shortcutIntent);
    }

    protected abstract Intent getClickIntent();

    public final void deleteShortcut()
    {
        Intent shortcutIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);

        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getClickIntent());

        context.sendBroadcast(shortcutIntent);
    }

    public final boolean isShortcutExist()
    {
        return LauncherUtil.isShortCutExist(context, shortcutName);
    }

}
