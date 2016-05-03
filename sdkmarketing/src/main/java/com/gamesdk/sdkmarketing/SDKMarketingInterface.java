package com.gamesdk.sdkmarketing;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.uniform_interface.IUniformInterface;
import com.cngame.gamesdklib.utils.ToastUtils;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/20.
 */
public class SDKMarketingInterface implements IUniformInterface
{
    public void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        ToastUtils.show(context, "营销模块");
    }
}
