package com.cngame.gamesdkcore.init;

import android.content.Context;
import android.os.Handler;

import com.cngame.gamesdklib.IGameInterface;
import com.cngame.gamesdklib.LibCore;
import com.cngame.gamesdklib.class_loader.SDKPluginManager;

/**
 * Created by Amuro on 2016/4/14.
 */
public final class InitManager
{
    private static final int INIT_SUCCESS = 0;
    private static final int INIT_FAILED = 1;

    public static void init(final Context context, final IGameInterface initListener)
    {
        initSync(context, initListener);
        initAsync(context, initListener);
    }

    private static void initSync(Context context, IGameInterface initListener)
    {
        try
        {
            LibCore.getInstance().init(context);
        }
        catch (Exception e)
        {
            if(initListener != null)
            {
                initListener.onResult(INIT_FAILED, e.getMessage());
            }
        }
    }

    private static void initAsync(final Context context, final IGameInterface initListener)
    {
        final Handler handler = new Handler();

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    SDKPluginManager.getInstance().init(context);

                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(initListener != null)
                            {
                                initListener.onResult(INIT_SUCCESS, "");
                            }
                        }
                    });


                }
                catch (final Exception e)
                {
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(initListener != null)
                            {
                                initListener.onResult(INIT_FAILED, e.getMessage());
                            }
                        }
                    });
                }
            }
        }).start();
    }

}
