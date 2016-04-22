package com.cngame.gamesdklib;

import android.content.Context;

import com.cngame.gamesdklib.http_async.urlParser.URLDataInitiator;
import com.cngame.gamesdklib.utils.SharedPreferManager;

/**
 * Created by Amuro on 2016/3/8.
 */
public class LibCore
{
    private static LibCore instance = new LibCore();

    private LibCore()
    {

    }

    public static LibCore getInstance()
    {
        return instance;
    }

    public void init(Context context)
    {
//        URLDataInitiator.init(context);
        SharedPreferManager.getInstance().init(context);
    }
}
