package com.cngame.gamesdklib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * Created by Amuro on 2016/3/25.
 */
public class DisplayUtils
{
    public static int getScreenWidth(Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDensity(Context context)
    {
        return getDisplayMetrics(context).density;
    }

    private static DisplayMetrics getDisplayMetrics(Context context)
    {
        return context.getResources().getDisplayMetrics();
    }

    public static int px2dip(Context context, float pxValue)
    {
        final float scale = getDisplayMetrics(context).density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = getDisplayMetrics(context).density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue)
    {
        final float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getStatusBar(Context context)
    {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            statusBarHeight = context.getResources().getDimensionPixelSize(
                    resourceId);
        }
        if (isFlymeOs4x())
        {
            return 2 * statusBarHeight;
        }

        return statusBarHeight;
    }

    public static boolean isFlymeOs4x()
    {
        String sysVersion = android.os.Build.VERSION.RELEASE;
        if ("4.4.4".equals(sysVersion))
        {
            String sysIncrement = android.os.Build.VERSION.INCREMENTAL;
            String displayId = android.os.Build.DISPLAY;
            if (!TextUtils.isEmpty(sysIncrement))
            {
                return sysIncrement.contains("Flyme_OS_4");
            }
            else
            {
                return displayId.contains("Flyme OS 4");
            }
        }
        return false;
    }
}
