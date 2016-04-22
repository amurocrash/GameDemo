package com.cngame.gamesdklib.http.core;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpRequestPost extends HttpRequest
{
    public HttpRequestPost(String baseUrl, Map<String, String> paramMap, int responseType)
    {
        super(baseUrl, paramMap, responseType);
    }

    @Override
    protected String getMethod()
    {
        return "POST";
    }

    @Override
    protected String getUrl()
    {
        return baseUrl;
    }

    @Override
    protected void sendData() throws Exception
    {
        String params = generateParam();
        OutputStream out = conn.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        bw.write(params);
        bw.flush();
        bw.close();
    }
}
