package com.cngame.gamesdklib.mvp.presenter;

import com.cngame.gamesdklib.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amuro on 2016/3/24.
 */
public class PresenterManager
{
    private static Map<Class<? extends AbsPresenter>, AbsPresenter> presenterMap = new HashMap<>();

    public static <P extends AbsPresenter> P getPresenter(Class<? extends AbsPresenter> clazz)
    {
        AbsPresenter presenter = presenterMap.get(clazz);
        if(presenter == null)
        {
            try
            {
                Constructor<? extends AbsPresenter> c = clazz.getDeclaredConstructor();
                c.setAccessible(true);

                presenter = c.newInstance();
            }
            catch (Exception e)
            {
                LogUtils.e(e.getMessage());
                e.printStackTrace();
            }

            presenterMap.put(clazz, presenter);
        }

        return (P)presenter;
    }
}
