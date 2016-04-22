package com.cngame.gamesdklib.http.response_parser;

import com.cngame.gamesdklib.http.core.HttpConstants;

public class ResponseParserFactory<T>
{

	public static <T> IResponseParser<T> getResponseParser(int responseType)
	{
		switch(responseType)
		{
		case HttpConstants.JSON:
			return new JsonParser<T>();
			
		default :
			return null;
		}
		
	}
}
