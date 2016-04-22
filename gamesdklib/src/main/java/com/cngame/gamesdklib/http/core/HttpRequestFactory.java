package com.cngame.gamesdklib.http.core;

import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpRequestFactory
{
    public static HttpRequest getRequest(String baseUrl, Map<String, String> paramMap)
    {
        return getRequest(HttpConstants.GET, baseUrl, paramMap, HttpConstants.JSON);
    }

    public static HttpRequest getRequest(
            int method, String baseUrl, Map<String, String> paramMap, int responseType)
    {
        if(method == HttpConstants.GET)
        {
            return new HttpRequestGet(baseUrl, paramMap, responseType);
        }
        else if(method == HttpConstants.POST)
        {
            return new HttpRequestPost(baseUrl, paramMap, responseType);
        }
        else
        {
            return null;
        }

    }
}
