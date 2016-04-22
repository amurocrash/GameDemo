package com.gamesdk.sdkbilling.V;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorBottomExit;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorTopEnter;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.mvp.view.dialog.SDKBaseAnimationDialog;
import com.gamesdk.sdkbilling.P.BillingMainPresenter;

/**
 * Created by Amuro on 2016/4/14.
 */
public class BillingV extends SDKBaseAnimationDialog implements IBillingV
{
    private IGameInterface resultListener;

    public BillingV(Context context, IGameInterface resultListener)
    {
        super(context);
        this.resultListener = resultListener;
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

    private BillingMainPresenter presenter = new BillingMainPresenter();
    private ProgressDialog progressDialog;

    @Override
    protected View onCreateContentView()
    {
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        presenter.addView(this);

        BillingView view = new BillingView(context);
        Button buttonPay1 = (Button)view.findViewById(BillingView.id_bt_pay1);

        buttonPay1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressDialog.show();
                presenter.doBilling();
            }
        });

        return view;
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        resultListener.onResult(-1, "canceled");
    }

    @Override
    public void onBillingSuccess()
    {
        progressDialog.dismiss();
        resultListener.onResult(0, "success");
    }

    @Override
    public void onBillingFailed()
    {
        progressDialog.dismiss();
        resultListener.onResult(1, "failed");
    }

    @Override
    public void onBillingCanceled()
    {
        progressDialog.dismiss();
    }
}
