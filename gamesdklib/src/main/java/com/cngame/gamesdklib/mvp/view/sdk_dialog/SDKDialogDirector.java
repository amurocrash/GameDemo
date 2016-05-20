package com.cngame.gamesdklib.mvp.view.sdk_dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.cngame.gamesdklib.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Amuro on 2016/5/17.
 */
public class SDKDialogDirector
{
    private Context context;
    private Stack<SDKBaseDialog> dialogStack;

    public SDKDialogDirector(Context context)
    {
        this.context = context;
        dialogStack = new Stack<>();
    }

    public void startDialog(DialogIntent intent)
    {
        startDialogForResult(intent, -1);
    }

    public void startDialogForResult(DialogIntent intent, final int requestCode)
    {
        try
        {
            final SDKBaseDialog fromDialog = intent.getFromDialog();
            Class<? extends SDKBaseDialog> toDialogClass = intent.getToDialogClass();

            if (toDialogClass != null)
            {
                final SDKBaseDialog toDialog
                        = toDialogClass.getConstructor(Context.class).newInstance(context);

                toDialog.setDialogDirector(this);
                toDialog.onIntent(intent);

                toDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialog)
                    {
                        if (fromDialog != null)
                        {
                            fromDialog.onDialogResult(
                                    requestCode,
                                    toDialog.getResultCode(),
                                    toDialog.getResultData());
                        }
                    }
                });
                toDialog.show();

                int action = intent.getDialogAction();
                if(action == DialogIntent.ACTION_CLEAR_STACK)
                {
                    clearStack();
                }

                push(toDialog);


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void push(SDKBaseDialog dialog)
    {
        this.dialogStack.push(dialog);
    }

    private void clearStack()
    {
        while(!dialogStack.empty())
        {
            pop();
        }
    }

    public void pop()
    {
        LogUtils.e("stack size -> " + dialogStack.size());
        if(!dialogStack.empty())
        {
            this.dialogStack.pop();
        }
    }

    private void remove(SDKBaseDialog dialog)
    {
        this.dialogStack.remove(dialog);
    }
}
