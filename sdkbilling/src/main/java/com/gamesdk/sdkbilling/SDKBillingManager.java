package com.gamesdk.sdkbilling;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.gamesdk.sdkbilling.P.BillingMainPresenter;
import com.gamesdk.sdkbilling.V.BillingV;
import com.gamesdk.sdkbilling.V.IBillingV;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/20.
 */
public class SDKBillingManager
{
    private static final String SHOW_BILLING_PAGE = "show_billing_page";

    private Context context;
    private Map<String, String> paramMap;
    private IGameInterface resultListener;

    public SDKBillingManager(Context context, Map<String, String> paramMap, IGameInterface resultListener)
    {
        this.context = context;
        this.paramMap = paramMap;
        this.resultListener = resultListener;
    }

    public void doBilling()
    {
        String showBillingPage = paramMap.get(SHOW_BILLING_PAGE);

        if("1".equals(showBillingPage))
        {
            BillingV v = new BillingV(context, resultListener);
            v.show();
        }
        else
        {
            BillingMainPresenter.getInstance().addView(new IBillingV()
            {
                @Override
                public void onBillingSuccess()
                {
                    resultListener.onResult(0, "success");
                }

                @Override
                public void onBillingFailed()
                {
                    resultListener.onResult(1, "failed");
                }

                @Override
                public void onBillingCanceled()
                {

                }

                @Override
                public void show()
                {

                }
            });
            BillingMainPresenter.getInstance().doBilling();
        }

    }


}
