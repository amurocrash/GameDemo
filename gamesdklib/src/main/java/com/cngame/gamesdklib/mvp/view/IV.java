package com.cngame.gamesdklib.mvp.view;

import java.util.Objects;

/**
 * Base interface of V in MVP
 * Created by Amuro on 2016/3/24.
 */
public interface IV
{
    public interface OnDismissListener
    {
        void onDismiss(Object... args);
    }

    void show();

}
