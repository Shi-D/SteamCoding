package com.framework.common;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * HttpClient工具类
 * 
 * @author {In-Death}
 *
 */
public class HttpClientUtil
{
	private static CloseableHttpClient httpClient;
	
	private HttpClientUtil()
	{
		
	}
	
	public static synchronized CloseableHttpClient getHttpClient()
	{
		if (httpClient == null)
		{
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();  
			  
			httpClient = HttpClients.custom().setConnectionManager(cm).build();
		}
		
		return httpClient;
	}
	
	/**
	 * 释放资源
	 * 
	 * @param response
	 */
	public static void close(CloseableHttpResponse response)
	{
		try
		{
			if (response != null)
			{
				response.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
