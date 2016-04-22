package com.cngame.gamesdklib.mvp.presenter;

import android.os.Handler;

import com.cngame.gamesdklib.mvp.view.IV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amuro on 2016/3/24.
 */
public abstract class AbsPresenter<V extends IV>
{
    public static <P extends AbsPresenter> P getInstance(Class<? extends AbsPresenter> clazz)
    {
        return PresenterManager.getPresenter(clazz);
    }

    protected AbsPresenter()
    {
        vList = new ArrayList<>();
        handler = new Handler();
    }

    protected List<V> vList;
    protected Handler handler;

    public void addView(V view)
    {
        vList.add(view);
    }

    public void removeView(V view)
    {
        vList.remove(view);
    }

}
