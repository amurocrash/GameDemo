package com.cngame;

import android.content.Context;

import com.cngame.gamesdkcore.GameSDK;
import com.cngame.gamesdkcore.GlobalManager;
import com.cngame.gamesdklib.IGameInterface;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/12.
 */
public class GameSDKCore
{
    public static void init(Context context)
    {
        GameSDK.init(context);
    }

    public static void init(Context context, IGameInterface initListener)
    {
        GameSDK.init(context, initListener);
    }

    public static void invokeModule(String moduleName, Context context) throws Exception
    {
        GameSDK.invokeModule(moduleName, context, null, null);
    }

    public static void invokeModule(
            String moduleName, Context context, Map<String, String> paramMap) throws Exception
    {
        GameSDK.invokeModule(moduleName, context, paramMap, null);
    }

    public static void invokeModule(
            String moduleName, Context context, Map<String, String> paramMap, IGameInterface resultListener) throws Exception
    {
        GameSDK.invokeModule(moduleName, context, paramMap, resultListener);
    }
}
