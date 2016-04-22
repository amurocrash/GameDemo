package com.cngame.gamesdklib.http.core;

import android.text.TextUtils;

import com.cngame.gamesdklib.utils.LogUtils;

import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpRequestGet extends HttpRequest
{
    public HttpRequestGet(String baseUrl, Map<String, String> paramMap, int responseType)
    {
        super(baseUrl, paramMap, responseType);
    }

    @Override
    protected String getMethod()
    {
        return "GET";
    }

    @Override
    protected String getUrl()
    {
        String url = baseUrl;

        String params = generateParam();

        if(!TextUtils.isEmpty(params))
        {
            url = url + "?" + params;
        }

        LogUtils.e("url -> " + url);
        return url;
    }

    @Override
    protected void sendData() throws Exception
    {

    }
}
