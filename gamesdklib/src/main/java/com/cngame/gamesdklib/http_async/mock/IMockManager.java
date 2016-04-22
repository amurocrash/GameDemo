package com.cngame.gamesdklib.http_async.mock;

/**
 * Created by Amuro on 2016/3/29.
 */
public interface IMockManager<T>
{
    T getResponseObject();
    int getResponseTime();
}
