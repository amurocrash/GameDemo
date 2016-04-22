package com.cngame.gamesdklib.utils;

import android.util.Log;

/**
 * Created by Amuro on 2016/3/8.
 */
public class LogUtils
{
    public static final String DEFAULT_LOG_TAG = "amuro";

    public static void e(String message)
    {
        Log.e(DEFAULT_LOG_TAG, message);
    }

}
