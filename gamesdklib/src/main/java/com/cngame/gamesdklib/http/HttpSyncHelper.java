package com.cngame.gamesdklib.http;

import com.cngame.gamesdklib.http.core.BaseEntity;
import com.cngame.gamesdklib.http.core.HttpConstants;
import com.cngame.gamesdklib.http.core.HttpRequest;
import com.cngame.gamesdklib.http.core.HttpRequestFactory;
import com.cngame.gamesdklib.http.response_parser.IResponseParser;
import com.cngame.gamesdklib.http.response_parser.ResponseParserFactory;
import com.cngame.gamesdklib.http.utils.BaseEntityUtils;
import com.cngame.gamesdklib.http.core.HttpError;

import java.util.Map;

/**
 * Created by Amuro on 2016/4/22.
 */
public class HttpSyncHelper<T>
{
    private HttpRequest request;
    private HttpError httpError;

    public HttpSyncHelper()
    {

    }

    public HttpError getHttpError()
    {
        return httpError;
    }

    public T invoke(
            String baseUrl, Class<T> clazz)
    {
        return invoke(baseUrl, null, clazz);
    }

    public T invoke(
            String baseUrl, Map<String, String> paramMap, Class<T> clazz)
    {
        return invoke(HttpConstants.GET, baseUrl, paramMap, HttpConstants.JSON, clazz);
    }

    public T invoke(
            int method, String baseUrl, Map<String, String> paramMap, int responseType, Class<T> clazz)
    {
        request = HttpRequestFactory.getRequest(method,
                baseUrl, paramMap, responseType);

        BaseEntity baseEntity;

        if (request == null)
        {
            baseEntity = BaseEntityUtils.getLoaclFailedBaseEntity("Only support get and post");
        }
        else
        {
            baseEntity = request.execute();
        }

        if(baseEntity.isSuccess())
        {
            IResponseParser<T> parser = ResponseParserFactory.getResponseParser(responseType);
            T obj = parser.parseResponse(baseEntity.getResult(), clazz);

            if(obj != null)
            {
                return obj;
            }
            else
            {
                baseEntity =  BaseEntityUtils.getLoaclFailedBaseEntity("unable to parse response");
            }
        }

        generateHttpError(baseEntity);

        return null;
    }

    public void abort()
    {
        if(request != null)
        {
            request.abort();
        }
    }

    private void generateHttpError(BaseEntity baseEntity)
    {
        if(httpError == null)
        {
            httpError = new HttpError();
        }
        httpError.setErrorCode(baseEntity.getErrorCode());
        httpError.setErrorMessage(baseEntity.getErrorMessage());
    }
}
