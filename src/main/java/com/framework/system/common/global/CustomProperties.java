package com.framework.system.common.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProperties
{
	private static Logger logger = LoggerFactory.getLogger(CustomProperties.class);

	public static final Properties systemCfg = new Properties();
	public static final Properties smsCfg = new Properties();

	public static String DEFAULT_PASSWORD = "111111";
	public static String JBPM_SERVICE = "http://114.215.196.21:8080/JBPMService/service/workflowService";
	
	public synchronized static void loads()
	{
		logger.info("开始加载.properties属性文件......");

		InputStream is = CustomProperties.class.getResourceAsStream("/systemCfg.properties");
		try
		{
			systemCfg.clear();
			systemCfg.load(is);
			
			DEFAULT_PASSWORD = systemCfg.getProperty("user.default.password");
			JBPM_SERVICE = systemCfg.getProperty("jbpm.service");
			
			logger.info("systemCfg.properties属性文件已加载");
		}
		catch (Exception e)
		{
			logger.info("不能读取属性文件. " + "请确保systemCfg.properties在CLASSPATH指定的路径中");
		}
		finally
		{
			close(is);
		}

		is = CustomProperties.class.getResourceAsStream("/sms.properties");
		try
		{
			smsCfg.clear();
			smsCfg.load(is);
			logger.info("sms.properties属性文件已加载");
		}
		catch (Exception e)
		{
			logger.info("不能读取属性文件. " + "请确保sms.properties在CLASSPATH指定的路径中");
		}
		finally
		{
			close(is);
		}

		logger.info("结束加载.properties属性文件......");
	}

	private static void close(InputStream is)
	{
		try
		{
			if (is != null)
			{
				is.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void clear()
	{
		systemCfg.clear();
		smsCfg.clear();
		
		logger.info("系统自定义.properties属性缓存清空完成！");
	}
}
