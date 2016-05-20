package com.gamesdk.sdkbilling.C.normal;

import android.content.Context;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.utils.ToastUtils;
import com.gamesdk.sdkbilling.V.normal.BillingSMSOptionalView;
import com.gamesdk.sdkbilling.constants.NormalBillingType;

/**
 * Created by Amuro on 2016/5/16.
 */
public class BillingSMSOptionalDialog extends ABillingNormalDialog
{
    public BillingSMSOptionalDialog(Context context)
    {
        super(context);
    }

    @Override
    protected BaseView onCreateContentView()
    {
        return new BillingSMSOptionalView(context);
    }

    @Override
    protected int getPolicyText()
    {
        return NormalBillingType.SMS_OPTIONAL;
    }

    @Override
    protected void confirmPay()
    {
        ToastUtils.show(context, "optional");
    }
}
