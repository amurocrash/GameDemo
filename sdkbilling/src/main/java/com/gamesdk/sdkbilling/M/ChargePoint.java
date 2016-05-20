package com.gamesdk.sdkbilling.M;

import android.text.TextUtils;

import com.gamesdk.sdkbilling.constants.BillingParams;
import com.gamesdk.sdkbilling.constants.NormalBillingType;

import java.util.Map;

/**
 * Created by Amuro on 2016/5/16.
 */
public class ChargePoint
{
    private boolean isShowBillingPage;
    private int originalPolicy;
    private int currentPolicy;

    public ChargePoint(Map<String, String> outParamMap)
    {
        String isShowBillingPageStr = outParamMap.get(BillingParams.IS_SHOW_BILLING_PAGE);

        if(TextUtils.isEmpty(isShowBillingPageStr) || isShowBillingPageStr.equals("1"))
        {
            isShowBillingPage = true;
        }
        else
        {
            isShowBillingPage = false;
        }

        this.originalPolicy = NormalBillingType.SMS_OPTIONAL;
        this.currentPolicy = originalPolicy;

    }

    public boolean isShowBillingPage()
    {
        return isShowBillingPage;
    }

    public void setShowBillingPage(boolean showBillingPage)
    {
        isShowBillingPage = showBillingPage;
    }

    public int getOriginalPolicy()
    {
        return originalPolicy;
    }

    public void setOriginalPolicy(int originalPolicy)
    {
        this.originalPolicy = originalPolicy;
    }

    public int getCurrentPolicy()
    {
        return currentPolicy;
    }

    public void setCurrentPolicy(int currentPolicy)
    {
        this.currentPolicy = currentPolicy;
    }

    @Override
    public String toString()
    {
        return "ChargePoint{" +
                "isShowBillingPage=" + isShowBillingPage +
                ", originalPolicy=" + originalPolicy +
                ", currentPolicy=" + currentPolicy +
                '}';
    }
}
