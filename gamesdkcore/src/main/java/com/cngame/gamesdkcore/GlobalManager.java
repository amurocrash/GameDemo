package com.cngame.gamesdkcore;

import android.content.Context;

import com.cngame.gamesdkcore.init.InitManager;
import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.class_loader.SDKPluginManager;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/12.
 */
public class GlobalManager
{
    private static GlobalManager instance;

    public static GlobalManager getInstance()
    {
        if(instance == null)
        {
            synchronized (GlobalManager.class)
            {
                if(instance == null)
                {
                    instance = new GlobalManager();
                }
            }
        }

        return instance;
    }

    public void init(Context context)
    {
        init(context, null);
    }

    public void init(Context context, final IGameInterface initListener)
    {
        InitManager.init(context, initListener);
    }

    public void invokeModule(
            String moduleName,
            Context context,
            Map<String, String> paramMap,
            IGameInterface resultListener) throws Exception
    {
        SDKPluginManager.getInstance().loadPlugin(moduleName, context, paramMap, resultListener);
    }
}
