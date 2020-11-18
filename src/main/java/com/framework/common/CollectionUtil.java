package com.framework.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 集合工具类
 * 
 * @author {In-Death}
 * 
 */
public class CollectionUtil
{
	/**
	 * 将List集合转换成有规律的字符串
	 * 
	 * @param list
	 *            集合
	 * @param regex
	 *            规则字符
	 * @return
	 */
	public static String join(Collection<String> list, String regex)
	{
		if (list == null || list.isEmpty())
		{
			return null;
		}

		String result = "";
		
		Iterator<String> iter = list.iterator();
		
		while (iter.hasNext())
		{
			String s = iter.next();
			
			if (iter.hasNext())
			{
				result += s + regex;
			}
			else
			{
				result += s;
			}
		}

		return result;
	}
	
	/**
	 * 判断集合是否为空（包含null的判断）
	 * 
	 * @param e
	 * @return
	 * @author LEVEL
	 * @date 2016年10月31日 下午6:56:02
	 */
	public static boolean isEmpty(Collection<?> e)
	{
		return e == null || e.isEmpty();
	}
	
	/**
	 * 判断集合是否不为空
	 * 
	 * @param e
	 * @return
	 * @author LEVEL
	 * @date 2016年10月31日 下午6:56:34
	 */
	public static boolean isNotEmpty(Collection<?> e)
	{
		return isEmpty(e);
	}
	
	/**
	 * 判断Map是否为空
	 * 
	 * @param e
	 * @return
	 * @author LEVEL
	 * @date 2016年10月31日 下午6:57:10
	 */
	public static boolean isEmpty(Map<?, ?> e)
	{
		return e == null || e.isEmpty();
	}
	
	/**
	 * 判断Map是否不为空
	 * 
	 * @param e
	 * @return
	 * @author LEVEL
	 * @date 2016年10月31日 下午6:57:33
	 */
	public static boolean isNotEmpty(Map<?, ?> e)
	{
		return isEmpty(e);
	}
}
