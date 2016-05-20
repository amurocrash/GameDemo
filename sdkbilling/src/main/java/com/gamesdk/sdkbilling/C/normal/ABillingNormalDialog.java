package com.gamesdk.sdkbilling.C.normal;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.sdk_dialog.DialogIntent;
import com.gamesdk.sdkbilling.C.ABillingBaseDialog;
import com.gamesdk.sdkbilling.C.BillingDialogFactory;
import com.gamesdk.sdkbilling.C.payway_change.PayWayChangeDialog;
import com.gamesdk.sdkbilling.V.BillingBaseView;

/**
 * Created by Amuro on 2016/5/16.
 */
public abstract class ABillingNormalDialog extends ABillingBaseDialog
{
    private TextView textViewPolicy;

    public ABillingNormalDialog(Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        textViewPolicy = (TextView) findViewById(BillingBaseView.id_tv_policy);
        textViewPolicy.setText(getPolicyText() + "");
    }

    protected abstract int getPolicyText();

    @Override
    protected void changePayWay()
    {
        DialogIntent intent = new DialogIntent(this, PayWayChangeDialog.class);
        intent.putData("chargePoint", chargePoint);
        startDialog(intent);
    }

    @Override
    public void onDialogResult(int requestCode, int resultCode, Bundle data)
    {
        if(resultCode != -1)
        {
            int currentPolicy = chargePoint.getCurrentPolicy();
            if (currentPolicy != getPolicyText())
            {
                finish();
                DialogIntent intent = new DialogIntent(
                        this, BillingDialogFactory.getBillingDialogClass(currentPolicy));
                intent.putData("chargePoint", chargePoint);
                startDialog(intent);
            }
        }
    }
}
