package com.cngame.gamesdklib.mvp.view.sdk_dialog;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amuro on 2016/5/17.
 */
public class DialogIntent extends Intent
{
    public static final int ACTION_NORMAL = 1;
    public static final int ACTION_CLEAR_STACK = 2;

    private int dialogAction;
    private SDKBaseDialog fromDialog;
    private Class<? extends SDKBaseDialog> toDialogClass;
    private Map<String, Object> dataMap;

    public DialogIntent(SDKBaseDialog fromDialog, Class<? extends  SDKBaseDialog> toDialogClass)
    {
        this(fromDialog, toDialogClass, ACTION_NORMAL);
    }

    public DialogIntent(SDKBaseDialog fromDialog, Class<? extends  SDKBaseDialog> toDialogClass, int dialogAction)
    {
        this.fromDialog = fromDialog;
        this.toDialogClass = toDialogClass;
        this.dialogAction = dialogAction;
        this.dataMap = new HashMap<>();
    }

    public SDKBaseDialog getFromDialog()
    {
        return fromDialog;
    }

    public void setFromDialog(SDKBaseDialog fromDialog)
    {
        this.fromDialog = fromDialog;
    }

    public Class<? extends SDKBaseDialog> getToDialogClass()
    {
        return toDialogClass;
    }

    public void setToDialogClass(Class<? extends SDKBaseDialog> toDialogClass)
    {
        this.toDialogClass = toDialogClass;
    }

    public int getDialogAction()
    {
        return dialogAction;
    }

    public void setDialogAction(int dialogAction)
    {
        this.dialogAction = dialogAction;
    }

    public void putData(String key, Object value)
    {
        this.dataMap.put(key, value);
    }

    public Object getData(String key)
    {
        return this.dataMap.get(key);
    }

}
