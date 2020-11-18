package com.framework.authority.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.framework.authority.service.ResourceService;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应权限的定义。
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{

	private static Logger logger = LoggerFactory.getLogger(MyInvocationSecurityMetadataSource.class);

	private ResourceService resourceService;
	private AntPathMatcher urlMatcher;
	// 是否使用Apache Ant的匹配模式，即资源/userManager/**
	// 和/userManager/UserManager_list.action匹配
	private boolean useAntPath = true;
	// 是否在比较URL前将URL都转化成小写，即资源/userManager/** 和/UserManager/××匹配
	private boolean lowercaseComparisons = true;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	/**
	 * 构造函数
	 * 
	 * @param resourceService
	 */
	public MyInvocationSecurityMetadataSource(ResourceService resourceService)
	{
		this.resourceService = resourceService;
		loadResourceDefine();
	}

	public void loadResourceDefine()
	{
		/*
		 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的权限。 一个资源可以由多个权限来访问。
		 */
		if (resourceMap == null)
		{
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		}
		else
		{
			resourceMap.clear();
		}
		
		logger.info("开始加载资源-权限关系......");
		
		// 获取所有资源
		List<String> resourceList = this.resourceService.getResources();

		for (Iterator<String> localIterator = resourceList.iterator(); localIterator.hasNext();)
		{
			String resourceStr = localIterator.next();
			String key = resourceStr;
			if (lowercaseComparisons)
			{
				key = resourceStr.toLowerCase();
			}
			resourceMap.put(key, this.resourceService.getConfigAttributes(resourceStr));
		}

		logger.info("资源-权限关系加载完毕");
	}

	public Collection<ConfigAttribute> getAllConfigAttributes()
	{
		return new ArrayList<ConfigAttribute>();
	}

	public Collection<ConfigAttribute> getAttributes(Object filter) throws IllegalArgumentException
	{
		// 用户请求的URL
		String reqURL = ((FilterInvocation) filter).getRequestUrl();

		if ("/".equals(reqURL))
		{
			return null;
		}

		int firstQuestionMarkIndex = reqURL.indexOf("?");
		// 判断请求是否带有参数 如果有参数就去掉后面的参数(/index.action?dc=_xxx --> /index.action)
		if (firstQuestionMarkIndex != -1)
		{
			reqURL = reqURL.substring(0, firstQuestionMarkIndex);
		}

		// 遍历资源-权限集合Map，取到请求的URL后与上面取出来的资源做比较
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext())
		{
			// 资源URL
			String resURL = (String) ite.next();

			if (lowercaseComparisons)
			{
				reqURL = reqURL.toLowerCase();
				resURL = resURL.toLowerCase();
			}

			// useAntPath如果为true则使用Apache Ant的匹配模式，否则使用String的equals方法对比
			if (useAntPath)
			{
				if (null == urlMatcher)
				{
					urlMatcher = new AntPathMatcher();
				}
				if (urlMatcher.match(resURL, reqURL))
				{
					return resourceMap.get(resURL);
				}
			}
			else
			{
				if (resURL.trim().equals(reqURL.trim()))
				{
					return resourceMap.get(resURL);
				}
			}
		}
		return null;
	}

	public boolean supports(Class<?> arg0)
	{
		return true;
	}

	public boolean isUseAntPath()
	{
		return this.useAntPath;
	}

	public void setUseAntPath(boolean useAntPath)
	{
		this.useAntPath = useAntPath;
	}

	public boolean isLowercaseComparisons()
	{
		return this.lowercaseComparisons;
	}

	public void setLowercaseComparisons(boolean lowercaseComparisons)
	{
		this.lowercaseComparisons = lowercaseComparisons;
	}
}
