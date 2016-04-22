package com.gamesdk.sdkbilling.P;

import android.os.Handler;

import com.cngame.gamesdklib.mvp.presenter.AbsPresenter;
import com.gamesdk.sdkbilling.V.IBillingV;

/**
 * Created by Amuro on 2016/4/20.
 */
public class BillingMainPresenter extends AbsPresenter<IBillingV>
{
    public static BillingMainPresenter getInstance()
    {
        return getInstance(BillingMainPresenter.class);
    }

    public void doBilling()
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for(IBillingV v : vList)
                        {
                            v.onBillingSuccess();
                        }
                    }
                });

            }

        }).start();
    }
}
