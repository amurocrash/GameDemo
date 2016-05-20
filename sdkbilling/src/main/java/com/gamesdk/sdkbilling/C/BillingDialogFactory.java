package com.gamesdk.sdkbilling.C;

import com.gamesdk.sdkbilling.C.normal.BillingSMSNormalDialog;
import com.gamesdk.sdkbilling.C.normal.BillingSMSOptionalDialog;
import com.gamesdk.sdkbilling.constants.NormalBillingType;

/**
 * Created by Amuro on 2016/5/16.
 */
public class BillingDialogFactory
{
    public static Class<? extends ABillingBaseDialog> getBillingDialogClass(int policy)
    {
        Class<? extends ABillingBaseDialog> dialogClass = null;

        switch (policy)
        {
            case NormalBillingType.SMS_OPTIONAL:
                dialogClass = BillingSMSOptionalDialog.class;
                break;
            case NormalBillingType.SMS_NORMAL:
                dialogClass = BillingSMSNormalDialog.class;
                break;
        }

        return dialogClass;
    }

}
