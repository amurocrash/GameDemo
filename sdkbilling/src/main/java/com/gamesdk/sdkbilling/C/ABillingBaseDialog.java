package com.gamesdk.sdkbilling.C;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cngame.gamesdklib.mvp.view.animator.AnimatorBottomExit;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorTopEnter;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.DialogIntent;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.SDKBaseAnimationDialog;
import com.cngame.gamesdklib.utils.ToastUtils;
import com.gamesdk.sdkbilling.M.ChargePoint;
import com.gamesdk.sdkbilling.V.BillingBaseView;

/**
 * Created by Amuro on 2016/5/12.
 */
public abstract class ABillingBaseDialog extends SDKBaseAnimationDialog
{
    public ABillingBaseDialog(Context context)
    {
        super(context);
    }

    @Override
    protected BaseAnimatorSet getShowAnim()
    {
        return new AnimatorTopEnter();
    }

    @Override
    protected BaseAnimatorSet getDismissAnim()
    {
        return new AnimatorBottomExit();
    }

    protected ChargePoint chargePoint;
    private LinearLayout linearLayoutChangePayway;
    private Button buttonPay;

    @Override
    public void onIntent(DialogIntent intent)
    {
        chargePoint = (ChargePoint) intent.getData("chargePoint");
        ToastUtils.show(context, chargePoint.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        linearLayoutChangePayway = (LinearLayout)getViewById(BillingBaseView.id_ll_change_pay_way);
        buttonPay = (Button)getViewById(BillingBaseView.id_bt_pay);

        initView();
    }

    private void initView()
    {
        linearLayoutChangePayway.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changePayWay();
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmPay();
            }
        });
    }

    protected void changePayWay()
    {

    }

    protected void confirmPay()
    {

    }
}
