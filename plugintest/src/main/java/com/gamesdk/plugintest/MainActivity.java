package com.gamesdk.plugintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cngame.gamesdklib.plugin.IPlugin;
import com.gamesdk.sdkbilling.SDKBillingInterface;
import com.gamesdk.sdkmarketing.SDKMarketingInterface;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_billing).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doBilling();
            }
        });

        findViewById(R.id.bt_marketing).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IPlugin p = new SDKMarketingInterface();
                p.invoke(MainActivity.this, null, null);
            }
        });
    }

    private void doBilling()
    {
        Map<String, String> params = new HashMap<String, String>();

        IPlugin p = new SDKBillingInterface();
        p.invoke(MainActivity.this, params, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
