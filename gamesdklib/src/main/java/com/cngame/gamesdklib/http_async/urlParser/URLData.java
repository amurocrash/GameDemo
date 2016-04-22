package com.cngame.gamesdklib.http_async.urlParser;

public class URLData
{
    public static final int GET = 0x01;
    public static final int POST = 0x02;

    public static final int JSON = 0x01;
    public static final int XML = 0x02;
    public static final int STRING = 0x03;

    private static final int DEFAULT_METHOD = GET;
    private static final int DEFAULT_RESPONSE_TYPE = JSON;

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
            this.method = POST;
            return;
        }

        this.method = GET;
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
            this.responseType = JSON;
        }
        else if ("xml".equals(responseType))
        {
            this.responseType = XML;
        }
        else if ("string".equals(responseType))
        {
            this.responseType = STRING;
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
