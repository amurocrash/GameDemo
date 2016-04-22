package com.cngame.gamesdklib.http_async.core;

import com.cngame.gamesdklib.http_async.urlParser.URLData;

import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpRequestFactory
{
    public static HttpRequest getRequest(
            URLData urlData, Map<String, String> paramMap, HttpRequest.OnHttpResponseListener httpListener)
    {
        int method = urlData.getMethod();

        if(method == URLData.GET)
        {
            return new HttpRequestGet(urlData, paramMap, httpListener);
        }
        else if(method == URLData.POST)
        {
            return new HttpRequestPost(urlData, paramMap, httpListener);
        }
        else
        {
            return null;
        }

    }
}
