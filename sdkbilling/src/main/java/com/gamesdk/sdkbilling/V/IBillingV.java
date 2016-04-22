package com.gamesdk.sdkbilling.V;

import com.cngame.gamesdklib.mvp.view.IV;

/**
 * Created by Amuro on 2016/4/20.
 */
public interface IBillingV extends IV
{
    void onBillingSuccess();
    void onBillingFailed();
    void onBillingCanceled();
}
