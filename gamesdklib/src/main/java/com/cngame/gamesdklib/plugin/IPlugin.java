package com.cngame.gamesdklib.plugin;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;

import java.util.Map;

/**
 * Created by liangbin on 2016/5/3.
 */
public interface IPlugin
{
    void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener);
}
