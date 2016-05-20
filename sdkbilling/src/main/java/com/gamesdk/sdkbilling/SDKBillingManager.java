package com.gamesdk.sdkbilling;

import android.content.Context;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.DialogIntent;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.SDKDialogDirector;
import com.gamesdk.sdkbilling.C.BillingDialogFactory;
import com.gamesdk.sdkbilling.M.ChargePoint;

import java.util.Map;

/**
 * Created by Amuro on 2016/5/16.
 */
public class SDKBillingManager
{
    private SDKBillingManager(){}

    private static SDKBillingManager instance = new SDKBillingManager();

    public static SDKBillingManager getInstance()
    {
        return instance;
    }

    private Context context;
    private Map<String, String> outParamMap;
    private IGameInterface gameInterface;

    private ChargePoint chargePoint;
    private BillingDialogFactory billingDirector;

    public void doBilling(Context context, Map<String, String> outParamMap, IGameInterface gameInterface)
    {
        init(context, outParamMap, gameInterface);
        goBilling();
    }

    private void init(Context context, Map<String, String> outParamMap, IGameInterface gameInterface)
    {
        this.context = context;
        this.outParamMap = outParamMap;
        this.gameInterface = gameInterface;

        if(outParamMap == null)
        {
            throw new RuntimeException("paramsMap can not be null");
        }

        chargePoint = new ChargePoint(outParamMap);
    }

    private void goBilling()
    {
        if(chargePoint.isShowBillingPage())
        {
            SDKDialogDirector dialogDirector = new SDKDialogDirector(context);
            DialogIntent intent = new DialogIntent(
                    null, BillingDialogFactory.getBillingDialogClass(chargePoint.getCurrentPolicy()));
            intent.putData("chargePoint", chargePoint);
            dialogDirector.startDialog(intent);

        }
        else
        {

        }
    }

    public ChargePoint getChargePoint()
    {
        return chargePoint;
    }
}
