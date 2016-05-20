package com.gamesdk.sdkadvertisement;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.plugin.IPlugin;
import com.cngame.gamesdklib.utils.ToastUtils;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/21.
 */
public class SDKAdvertisementInterface implements IPlugin
{
    public void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        ToastUtils.show(context, "广告模块");
    }
}
