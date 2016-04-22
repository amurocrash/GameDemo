package com.cngame.gamesdklib.http.urlParser;

import com.cngame.gamesdklib.http.core.HttpConstants;

public class URLData
{
    private static final int DEFAULT_METHOD = HttpConstants.GET;
    private static final int DEFAULT_RESPONSE_TYPE = HttpConstants.JSON;

    private String key;
    private String url;
    private long expires;
    private String responseClass;
    private String mockClass;
    private boolean isMockable;

    private int method;
    private int responseType;

    public URLData()
    {
        this.method = DEFAULT_METHOD;
        this.responseType = DEFAULT_RESPONSE_TYPE;
        this.isMockable = false;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public long getExpires()
    {
        return expires;
    }

    public void setExpires(long expires)
    {
        this.expires = expires;
    }

    public String getResponseClass()
    {
        return responseClass;
    }

    public void setResponseClass(String responseClass)
    {
        this.responseClass = responseClass;
    }

    public int getMethod()
    {
        return method;
    }

    public void setMethod(int method)
    {
        this.method = method;
    }

    public void setMethod(String method)
    {
        if ("post".equals(method))
        {
            this.method = HttpConstants.POST;
            return;
        }

        this.method = HttpConstants.GET;
    }

    public int getResponseType()
    {
        return responseType;
    }

    public void setResponseType(int responseType)
    {
        this.responseType = responseType;
    }

    public void setResponseType(String responseType)
    {
        if ("json".equals(responseType))
        {
            this.responseType = HttpConstants.JSON;
        }
        else if ("xml".equals(responseType))
        {
            this.responseType = HttpConstants.XML;
        }
        else if ("string".equals(responseType))
        {
            this.responseType = HttpConstants.STRING;
        }
        else
        {
            this.responseType = -1;
        }
    }

    public String getMockClass()
    {
        return mockClass;
    }

    public void setMockClass(String mockClass)
    {
        this.mockClass = mockClass;
    }

    public boolean isMockable()
    {
        return isMockable;
    }

    public void setMockable(boolean isMockable)
    {
        this.isMockable = isMockable;
    }

}
