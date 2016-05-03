package com.cngame.gamesdklib.uniform_interface;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;

import java.util.Map;

/**
 * Created by liangbin on 2016/5/3.
 */
public interface IUniformInterface {
    void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener);
}
