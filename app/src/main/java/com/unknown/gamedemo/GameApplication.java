package com.unknown.gamedemo;

import android.app.Application;

import com.cngame.GameSDKCore;
import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.utils.LogUtils;

/**
 * Created by Amuro on 2016/4/12.
 */
public class GameApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        GameSDKCore.init(this, new IGameInterface()
        {
            @Override
            public void onResult(int resultCode, String resultMsg, Object... args)
            {
                if(resultCode == 0)
                {
                    LogUtils.e("init success");
                }
                else
                {
                    LogUtils.e("init failed: " + resultMsg);
                }
            }
        });
    }
}
