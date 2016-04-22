package com.cngame.gamesdklib.http.urlParser;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

public class URLDataInitiator
{
	private static List<URLData> urlDataList; 

	public static URLData findURL(String findKey)
	{

		for(URLData data : urlDataList)
		{
			if (data.getKey().trim().equals(findKey))
			{
				return data;
			}
		}
		
		return null;
	}

	public static void init(Context context)
	{
		if(urlDataList == null)
		{
			urlDataList = new ArrayList<URLData>();
		}

		getURLDataFromXml(context);
	}
	
	private static void getURLDataFromXml(Context context)
	{
		XmlResourceParser xmlParser = null;//context.getResources().getXml(R.xml.url);

		int eventCode;
		try
		{
			eventCode = xmlParser.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if ("Node".equals(xmlParser.getName()))
					{
						URLData urlData = new URLData();

						//key
						String key = xmlParser.getAttributeValue(null, "Key");
						urlData.setKey(key);

						//baseUrl
						urlData.setUrl(xmlParser.getAttributeValue(null, "Url"));

						//expires time
						String expiresStr = xmlParser
								.getAttributeValue(null, "Expires");
						if(!TextUtils.isEmpty(expiresStr) && TextUtils.isDigitsOnly(expiresStr))
						{
							urlData.setExpires(Long.parseLong(expiresStr));
						}
						else
						{
							urlData.setExpires(0);
						}

						//response class
						String responseClass = xmlParser.getAttributeValue(
								null, "ResponseClass");

						if(TextUtils.isEmpty(responseClass))
						{
							responseClass = "java.lang.Object";
						}

						urlData.setResponseClass(responseClass);

						//Request Method
						String method = xmlParser.getAttributeValue(null,
								"Method");
						if (!TextUtils.isEmpty(method))
						{
							urlData.setMethod(method);
						}

						String responseType = xmlParser.getAttributeValue(null,
								"ResponseType");
						if (!TextUtils.isEmpty(responseType))
						{
							urlData.setResponseType(responseType);
						}
						
						String mockClass = xmlParser.getAttributeValue(null, "Mock");
						urlData.setMockClass(mockClass);
						
						String mockable = xmlParser.getAttributeValue(null, "Mockable");
						if("true".equals(mockable))
						{
							urlData.setMockable(true);
						}
						else
						{
							urlData.setMockable(false);
						}
						
						urlDataList.add(urlData);
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventCode = xmlParser.next();
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			xmlParser.close();
		}
	}
}
