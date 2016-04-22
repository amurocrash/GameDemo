package com.cngame.gamesdklib.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Amuro on 2016/3/8.
 */
public class DialogUtils
{
    public static ProgressDialog getProgressDialog(Context context)
    {
        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        pd.setCancelable(true);
        pd.setMessage("please waiting...");

        return pd;
    }
}
