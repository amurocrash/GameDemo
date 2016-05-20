package com.gamesdk.sdkmarketing;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.plugin.IPlugin;
import com.gamesdk.sdkmarketing.main.SDKMarketingMainDialog;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/20.
 */
public class SDKMarketingInterface implements IPlugin
{
    public void invoke(final Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        SDKMarketingMainDialog dialog = new SDKMarketingMainDialog(context);
        dialog.show();
    }
}
