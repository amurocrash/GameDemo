package com.cngame.gamesdklib.http;

import android.os.Handler;
import android.os.Looper;

import com.cngame.gamesdklib.http.core.HttpConstants;
import com.cngame.gamesdklib.http.core.HttpError;
import com.cngame.gamesdklib.http.core.HttpRequestFactory;
import com.cngame.gamesdklib.http.mock.IMockManager;
import com.cngame.gamesdklib.http.urlParser.URLData;
import com.cngame.gamesdklib.http.urlParser.URLDataInitiator;
import com.cngame.gamesdklib.utils.DefaultThreadPool;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/22.
 */
public class HttpAsyncHelper<T>
{
    public interface OnResponseListener<T>
    {
        void onSuccess(T obj);
        void onFailed(HttpError error);
    }

    private HttpSyncHelper<T> syncHelper;
    private Handler hanlder;

    public HttpAsyncHelper()
    {
        this.syncHelper = new HttpSyncHelper<>();
        this.hanlder = new Handler(Looper.getMainLooper());
    }

    public void invoke(
            String urlKey, final Map<String, String> paramMap, final OnResponseListener<T> listener)
    {
        final URLData urlData = URLDataInitiator.findURL(urlKey);
        if(urlData == null)
        {
            if(listener != null)
            {
                listener.onFailed(
                        new HttpError(HttpConstants.ERROR_CODE_LOCAL, "no data found with this urlKey"));
            }

            return;
        }

        if(doMock(urlData, listener))
        {
            return;
        }

        DefaultThreadPool.getInstance().execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final T obj = syncHelper.invoke(
                            urlData.getMethod(),
                            urlData.getUrl(),
                            paramMap,
                            urlData.getResponseType(), (Class<T>)Class.forName(urlData.getResponseClass()));

                    if(obj != null)
                    {
                        if(listener != null)
                        {
                            hanlder.post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    listener.onSuccess(obj);
                                }
                            });
                        }
                    }
                    else
                    {
                        if(listener != null)
                        {
                            hanlder.post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    listener.onFailed(syncHelper.getHttpError());
                                }
                            });
                        }
                    }
                }
                catch (final Exception e)
                {
                    if(listener != null)
                    {
                        hanlder.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                listener.onFailed(new HttpError(HttpConstants.ERROR_CODE_LOCAL, e.getMessage()));
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean doMock(URLData urlData, final OnResponseListener<T> listener)
    {
        if(urlData.isMockable())
        {
            try
            {
                final IMockManager<T> manager =
                        (IMockManager) Class.forName(urlData.getMockClass()).newInstance();

                if (manager != null)
                {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (listener != null)
                            {
                                listener.onSuccess(manager.getResponseObject());
                            }
                        }
                    }, manager.getResponseTime() * 1000);

                }
                else
                {
                    if(listener != null)
                    {
                        listener.onFailed(new HttpError(HttpConstants.ERROR_CODE_LOCAL, "mock failed"));
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                if(listener != null)
                {
                    listener.onFailed(new HttpError(HttpConstants.ERROR_CODE_LOCAL, "mock failed"));
                }
            }
        }

        return false;


    }
}
