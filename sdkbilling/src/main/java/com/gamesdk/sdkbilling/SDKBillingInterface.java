package com.gamesdk.sdkbilling;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.plugin.IPlugin;

import java.util.Map;


/**
 * Created by Amuro on 2016/4/14.
 */
public class SDKBillingInterface implements IPlugin
{
    public void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        SDKBillingManager.getInstance().doBilling(context, paramMap, resultListener);
    }
}
