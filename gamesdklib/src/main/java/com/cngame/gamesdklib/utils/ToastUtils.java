package com.cngame.gamesdklib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Amuro on 2016/3/8.
 */
public class ToastUtils
{
    public static void show(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
