package com.cngame.gamesdklib.http_async.core;

import android.os.Handler;

import com.cngame.gamesdklib.utils.DefaultThreadPool;
import com.cngame.gamesdklib.http_async.constants.HttpConstants;
import com.cngame.gamesdklib.http_async.mock.IMockManager;
import com.cngame.gamesdklib.http_async.response_parser.BaseEntity;
import com.cngame.gamesdklib.http_async.response_parser.IResponseParser;
import com.cngame.gamesdklib.http_async.response_parser.JsonParser;
import com.cngame.gamesdklib.http_async.urlParser.URLData;
import com.cngame.gamesdklib.http_async.urlParser.URLDataInitiator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpHelper<T>
{
    public interface OnResponseListener<T>
    {
        void onSuccess(T obj);
        void onFailed(HttpError error);
    }

    private HttpRequest request;

    public void invokeUrl(String url, OnResponseListener<T> listener, Class<T> clazz)
    {
        URLData urlData = new URLData();
        urlData.setUrl(url);
        urlData.setResponseClass(clazz.getCanonicalName());

        request = HttpRequestFactory.getRequest(
                urlData, null, getOnHttpResponseListener(urlData, listener));
        if (request == null)
        {
            if(listener != null)
            {
                listener.onFailed(
                        new HttpError(HttpConstants.ERROR_CODE_LOCAL, "Only support get and post"));
            }

            return;
        }

        request.run();
    }

    public void invoke(String urlKey)
    {
        invoke(urlKey, null, null);
    }

    public void invoke(String urlKey, OnResponseListener<T> listener)
    {
        invoke(urlKey, null, listener);
    }

    public void invoke(String urlKey, Object bean, OnResponseListener<T> listener)
    {

        Map<String, String> paramMap = null;
        try
        {
            paramMap = convertBeanToMap(bean);
        }
        catch (NoSuchMethodException e)
        {

        }
        catch (Exception e)
        {
            return;
        }

        invoke(urlKey, paramMap, listener);
    }

    private Map<String, String> convertBeanToMap(Object bean) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Field[] fields = bean.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();
        for(Field field : fields)
        {

            String fieldName = field.getName();
            StringBuffer sb = new StringBuffer();
            sb.append("get");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));

            Method method = bean.getClass().getMethod(sb.toString());
            String param = (String) method.invoke(bean, new Object[]{});

            map.put(fieldName, param);

        }

        return map;
    }

    public void invoke(
            String urlKey, Map<String, String> paramMap, OnResponseListener<T> listener)
    {
        URLData urlData = URLDataInitiator.findURL(urlKey);
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

        request = HttpRequestFactory.getRequest(
                        urlData, paramMap, getOnHttpResponseListener(urlData, listener));
        if (request == null)
        {
            if(listener != null)
            {
                listener.onFailed(
                        new HttpError(HttpConstants.ERROR_CODE_LOCAL, "Only support get and post"));
            }

            return;
        }

        DefaultThreadPool.getInstance().execute(request);
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

    public void cancel()
    {
        if(request != null)
        {
            request.abort();
            DefaultThreadPool.getInstance().removeTaskFromQueue(request);
        }
    }

    private HttpRequest.OnHttpResponseListener getOnHttpResponseListener(final URLData urlData, final OnResponseListener<T> listener)
    {
        if(listener == null)
        {
            return null;
        }

        HttpRequest.OnHttpResponseListener httpListener = new HttpRequest.OnHttpResponseListener()
        {
            @Override
            public void onSucceed(BaseEntity baseEntity)
            {
                if(!baseEntity.isSuccess())
                {
                    listener.onFailed(new HttpError(
                            baseEntity.getErrorCode(), baseEntity.getErrorMessage()
                    ));

                    return;
                }

                String json = baseEntity.getResult();
                IResponseParser<T> parser = new JsonParser<T>();
                try
                {
                    Class<T> clazz = (Class<T>) Class.forName(urlData.getResponseClass());
                    T t = parser.parseResponse(json, clazz);
                    listener.onSuccess(t);
                }
                catch (Exception e)
                {
                    listener.onFailed(
                            new HttpError(HttpConstants.ERROR_CODE_LOCAL, "Unable to parse your response class"));
                }

            }

            @Override
            public void onFailed(HttpError error)
            {
                listener.onFailed(error);
            }
        };

        return httpListener;
    }
}



































