package com.cngame.gamesdklib.http.core;


import android.text.TextUtils;

import com.cngame.gamesdklib.http.response_parser.IResponseParser;
import com.cngame.gamesdklib.http.response_parser.ResponseParserFactory;
import com.cngame.gamesdklib.http.utils.BaseEntityUtils;
import com.cngame.gamesdklib.utils.LogUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public abstract class HttpRequest
{
    protected HttpURLConnection conn;
    protected String baseUrl;
    protected Map<String, String> paramMap;
    protected int responseType;

    public HttpRequest(String baseUrl, Map<String, String> paramMap, int responseType)
    {
        this.baseUrl = baseUrl;
        this.paramMap = paramMap;
        this.responseType = responseType;
    }

    protected abstract String getMethod();
    protected abstract String getUrl();
    protected abstract void sendData() throws Exception;

    public BaseEntity execute()
    {
        try
        {
            initConn();
            sendData();
            return disposeResponse();
        }
        catch (Exception e)
        {
            return BaseEntityUtils.getLoaclFailedBaseEntity("local error: " + e.getClass() + " --- " + e.getMessage());
        }
        finally
        {
            conn.disconnect();
        }
    }

    protected void initConn() throws Exception
    {
        URL url = new URL(getUrl());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(getMethod());
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setReadTimeout(HttpConstants.DEFAULT_READ_TIMEOUT);
        conn.setConnectTimeout(HttpConstants.DEFAULT_CONNECT_TIMEOUT);
    }

    protected String generateParam()
    {
        if(paramMap == null || paramMap.size() == 0)
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : paramMap.entrySet())
        {
            if(!TextUtils.isEmpty(sb))
            {
                sb.append("&");
            }

            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }

        return sb.toString();
    }

    protected BaseEntity disposeResponse() throws Exception
    {
        int responseCode = conn.getResponseCode();
        if (responseCode == 200)
        {
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            int len;
            char[] buffer = new char[1024];
            while ((len = br.read(buffer)) > 0)
            {
                sb.append(buffer, 0, len);
            }

            LogUtils.e("response: " + sb.toString());
            in.close();

            return parseResponse(sb.toString());

        }
        else
        {
            return BaseEntityUtils.getFailedBaseEntity(responseCode, "Server error");
        }
    }

    protected BaseEntity parseResponse(String response) throws Exception
    {
        IResponseParser<BaseEntity> parser =
                ResponseParserFactory.getResponseParser(responseType);
        BaseEntity baseEntity = parser.parseResponse(response, BaseEntity.class);

        if(baseEntity != null)
        {
            return baseEntity;
        }
        else
        {
            return BaseEntityUtils.getLoaclFailedBaseEntity("unable to parse response");
        }
    }

    public void abort()
    {
        if(conn != null)
        {
            conn.disconnect();
        }
    }

}
