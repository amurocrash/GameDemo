package com.gamesdk.sdkbilling.C.normal;

import android.content.Context;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.utils.ToastUtils;
import com.gamesdk.sdkbilling.V.normal.BillingSMSNormalView;
import com.gamesdk.sdkbilling.constants.NormalBillingType;

/**
 * Created by Amuro on 2016/5/17.
 */
public class BillingSMSNormalDialog extends ABillingNormalDialog
{
    public BillingSMSNormalDialog(Context context)
    {
        super(context);
    }

    @Override
    protected int getPolicyText()
    {
        return NormalBillingType.SMS_NORMAL;
    }

    @Override
    protected BaseView onCreateContentView()
    {
        return new BillingSMSNormalView(context);
    }

    @Override
    protected void confirmPay()
    {
        ToastUtils.show(context, "normal");
    }
}
