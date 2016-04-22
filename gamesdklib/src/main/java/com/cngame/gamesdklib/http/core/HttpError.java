package com.cngame.gamesdklib.http.core;

/**
 * Created by Amuro on 2016/3/8.
 */
public class HttpError
{
    private int errorCode;
    private String errorMessage;

    public HttpError()
    {

    }

    public HttpError(int errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString()
    {
        return "HttpError{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
