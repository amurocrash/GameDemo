package com.gamesdk.sdkmarketing;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.utils.ToastUtils;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/20.
 */
public class SDKMarketingInterface
{
    public static void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        ToastUtils.show(context, "营销模块");
    }
}
