package com.gamesdk.sdkbilling;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.gamesdk.sdkbilling.V.BillingV;

import java.util.Map;


/**
 * Created by Amuro on 2016/4/14.
 */
public class SDKBillingInterface
{
    public static void invoke(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        SDKBillingManager billingManager = new SDKBillingManager(context, paramMap, resultListener);
        billingManager.doBilling();
    }
}
