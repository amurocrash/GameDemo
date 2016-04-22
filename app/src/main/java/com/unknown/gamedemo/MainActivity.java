package com.unknown.gamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cngame.GameSDKCore;
import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Amuro on 2016/4/12.
 */
public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doBilling();
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doMarketing();
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doAdvertisement();
            }
        });

        findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doLogging();
            }
        });
    }

    private void doBilling()
    {
        try
        {
            Map<String, String> map = new HashMap<>();
            map.put("show_billing_page", "1");

            GameSDKCore.invokeModule("SDKBilling", this, map, new IGameInterface()
            {
                @Override
                public void onResult(int resultCode, String resultMsg, Object... args)
                {
                    if(resultCode == 0)
                    {
                        ToastUtils.show(MainActivity.this, "计费成功");
                    }
                    else if(resultCode == -1)
                    {
                        ToastUtils.show(MainActivity.this, "计费取消");
                    }
                    else
                    {
                        ToastUtils.show(MainActivity.this, "计费失败");
                    }
                }
            });
        }
        catch (Exception e)
        {
            ToastUtils.show(MainActivity.this, "模块加载失败\n" + e.getMessage());
        }
    }

    private void doMarketing()
    {
        try
        {
            GameSDKCore.invokeModule("SDKMarketing", this);
        }
        catch (Exception e)
        {
            ToastUtils.show(MainActivity.this, "模块加载失败\n" + e.getMessage());
        }
    }

    private void doAdvertisement()
    {
        try
        {
            GameSDKCore.invokeModule("SDKAdvertisement", this);
        }
        catch (Exception e)
        {
            ToastUtils.show(MainActivity.this, "模块加载失败\n" + e.getMessage());
        }
    }

    private void doLogging()
    {
        try
        {
            GameSDKCore.invokeModule("SDKLogging", this);
        }
        catch (Exception e)
        {
            ToastUtils.show(MainActivity.this, "模块加载失败\n" + e.getMessage());
        }
    }

    @Override
    protected void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

}
