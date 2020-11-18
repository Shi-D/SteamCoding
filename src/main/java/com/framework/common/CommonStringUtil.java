package com.framework.common;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * 
 * @author {In-Death}
 * 
 */
public class CommonStringUtil
{

	public static final char COMMA = ',';

	/**
	 * 如果最后面一个字符是逗号则删除
	 * 
	 * @param sb
	 */
	public static void deleteLastComma(StringBuffer sb)
	{
		if (sb != null)
		{
			char lastChar = sb.charAt(sb.length() - 1);
			if (lastChar == COMMA)
			{
				sb.deleteCharAt(sb.length() - 1);
			}
		}
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNumeric(String string)
	{
		if (StringUtils.isNotBlank(string) && StringUtils.isNumeric(string))
		{
			return true;
		}
		return false;
	}

	/**
	 * 将集合变成逗号分割字符串
	 * 
	 * @param collection
	 * @return
	 */
	public static String collectionToStr(Collection<?> collection)
	{
		StringBuffer stringBuffer = new StringBuffer();
		if (collection != null && collection.size() > 0)
		{
			for (Object object : collection)
			{
				stringBuffer.append(object.toString() + COMMA);
			}
			CommonStringUtil.deleteLastComma(stringBuffer);
		}

		return stringBuffer.toString();
	}

	/**
	 * 删除字符串中的所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteWhitespace(String str)
	{
		return StringUtils.deleteWhitespace(str);
	}
}
