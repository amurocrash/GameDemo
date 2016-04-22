package com.cngame.gamesdklib.mvp.model;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amuro on 2016/3/24.
 */
public class ModelManager
{
    private static Map<Class<? extends AbsModel>, AbsModel> modelMap = new HashMap<>();

    public static <M extends AbsModel> M getModel(Class<? extends AbsModel> clazz)
    {
        AbsModel model = modelMap.get(clazz);
        if(model == null)
        {
            try
            {
                Constructor cs = clazz.getDeclaredConstructor(new Class[]{});
                cs.setAccessible(true);
                model = (AbsModel) cs.newInstance(new Object[]{});
                modelMap.put(clazz, model);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return (M)model;
    }
}
