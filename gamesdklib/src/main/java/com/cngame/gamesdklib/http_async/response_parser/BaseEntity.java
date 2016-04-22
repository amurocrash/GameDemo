package com.cngame.gamesdklib.http_async.response_parser;

import java.io.Serializable;

public class BaseEntity implements Serializable
{
	private static final long serialVersionUID = -4175435655377312423L;

	private boolean isSuccess;
	/**
	 * 0代表成功
	 */
	private int errorCode;
	private String errorMessage;
	private String result;

	public BaseEntity()
	{
	}

	public BaseEntity(boolean isSuccess, int errorCode, String errorMessage, String result)
	{
		this.isSuccess = isSuccess;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.result = result;
	}

	public boolean isSuccess()
	{
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess)
	{
		this.isSuccess = isSuccess;
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

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("isSuccess: " + isSuccess + "\n");
		sb.append("errorType: " + errorCode + "\n");
		sb.append("errorMessage: " + errorMessage + "\n");
		sb.append("result: " + result);

		return sb.toString();
	}
}