package com.cngame.gamesdklib.http.utils;

import com.cngame.gamesdklib.http.core.BaseEntity;
import com.cngame.gamesdklib.http.core.HttpConstants;

/**
 * Created by Amuro on 2016/4/22.
 */
public class BaseEntityUtils
{
    public static BaseEntity getLoaclFailedBaseEntity(String errorMsg)
    {
        return getFailedBaseEntity(HttpConstants.ERROR_CODE_LOCAL, errorMsg);
    }

    public static BaseEntity getFailedBaseEntity(int errorCode, String errorMsg)
    {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setSuccess(false);
        baseEntity.setErrorCode(errorCode);
        baseEntity.setErrorMessage(errorMsg);
        baseEntity.setResult("");

        return baseEntity;
    }

    public static BaseEntity getSuccessBaseEntity()
    {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setSuccess(true);
        baseEntity.setErrorCode(0);
        baseEntity.setErrorMessage("success");
        baseEntity.setResult("");

        return baseEntity;
    }
}
