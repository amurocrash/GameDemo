package com.cngame.gamesdkcore;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/12.
 */
public class GameSDK
{
    public static void init(Context context)
    {
        GlobalManager.getInstance().init(context);
    }

    public static void init(Context context, IGameInterface initListener)
    {
        GlobalManager.getInstance().init(context, initListener);
    }

    public static void invokeModule(String moduleName, Context context) throws Exception
    {
        GlobalManager.getInstance().invokeModule(moduleName, context, null, null);
    }

    public static void invokeModule(
            String moduleName, Context context, Map<String, String> paramMap) throws Exception
    {
        GlobalManager.getInstance().invokeModule(moduleName, context, paramMap, null);
    }

    public static void invokeModule(
            String moduleName, Context context, Map<String, String> paramMap, IGameInterface resultListener) throws Exception
    {
        GlobalManager.getInstance().invokeModule(moduleName, context, paramMap, resultListener);
    }
}
