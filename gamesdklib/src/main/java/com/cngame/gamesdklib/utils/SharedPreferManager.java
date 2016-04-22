package com.cngame.gamesdklib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2016/3/28.
 */
public class SharedPreferManager
{
    public static final String SP_NAME = "myapp_sp";

    private static SharedPreferManager instance = new SharedPreferManager();

    public static SharedPreferManager getInstance()
    {
        return instance;
    }

    private Context context;

    public void init(Context context)
    {
        this.context = context;
    }

    public void saveToSP(String key, String value)
    {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSpValue(String key)
    {
        SharedPreferences sp =
                context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public void removeSpValue(String key)
    {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();

    }
}































