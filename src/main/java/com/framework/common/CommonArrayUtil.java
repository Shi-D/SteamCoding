package com.framework.common;


/**
 * 数组工具类
 * 
 * @author {In-Death}
 * 
 */
public class CommonArrayUtil
{
	/**
	 * 将有规律的字符串转换成Integer型数组数据，字符串默认分隔符","<br/>
	 * 例如： <br/>
	 * 1,2,3,4,5<br/>
	 * 1;2;3;4;5<br/>
	 * 
	 * @param a
	 *            有规律的字符串
	 * @return
	 * @throws Exception
	 */
	public static Integer[] asInteger(String a) throws Exception
	{
		return CommonArrayUtil.asInteger(a, ",");
	}
	
	/**
	 * 将有规律的字符串转换成Integer型数组数据<br/>
	 * 例如： <br/>
	 * 1,2,3,4,5<br/>
	 * 1;2;3;4;5<br/>
	 * 
	 * @param a
	 *            有规律的字符串
	 * @param regex
	 *            字符串分隔符
	 * @return
	 * @throws Exception
	 */
	public static Integer[] asInteger(String a, String regex) throws Exception
	{
		if (a == null || a.isEmpty())
		{
			return null;
		}

		String[] as = a.split(regex);

		int size = as.length;

		Integer[] b = new Integer[size];

		for (int i = 0; i < size; i++)
		{
			try
			{
				b[i] = Integer.valueOf(as[i]);
			}
			catch (NumberFormatException e)
			{
				throw new Exception("第" + (i + 1) + "位置的'" + as[i] + "'字符串转换成Integer类型失败");
			}
		}

		return b;
	}

	/**
	 * 将字符串数组转换成Integer型数组数据<br/>
	 * 
	 * @param as
	 *            字符串数组
	 * @return
	 * @throws Exception
	 */
	public static Integer[] asInteger(String[] as) throws Exception
	{
		if (as == null || as.length == 0)
		{
			return null;
		}

		int size = as.length;

		Integer[] b = new Integer[size];

		for (int i = 0; i < size; i++)
		{
			try
			{
				b[i] = Integer.valueOf(as[i]);
			}
			catch (NumberFormatException e)
			{
				throw new Exception("第" + (i + 1) + "位置的'" + as[i] + "'字符串转换成Integer类型失败");
			}
		}

		return b;
	}
	
	/**
	 * 将有规律的字符串转换成Double型数组数据<br/>
	 * 例如： <br/>
	 * 1,2,3,4,5<br/>
	 * 1;2;3;4;5<br/>
	 * 
	 * @param a
	 *            有规律的字符串
	 * @param regex
	 *            字符串分隔符
	 * @return
	 * @throws Exception
	 */
	public static Double[] asDouble(String a, String regex) throws Exception
	{
		if (a == null || a.isEmpty())
		{
			return null;
		}

		String[] as = a.split(regex);

		int size = as.length;

		Double[] b = new Double[size];

		for (int i = 0; i < size; i++)
		{
			try
			{
				b[i] = Double.valueOf(as[i]);
			}
			catch (NumberFormatException e)
			{
				throw new Exception("第" + (i + 1) + "位置的'" + as[i] + "'字符串转换成Double类型失败");
			}
		}

		return b;
	}
	
	/**
	 * 将数组转换成字符串
	 * 
	 * @param arrays	数组
	 * @param regex		分隔符
	 * @return
	 */
	public static String join(String[] arrays, String regex)
	{
		StringBuilder result = new StringBuilder();
		
		int length = arrays.length;
		
		for (int i = 0; i < length - 1; i++)
		{
			result.append(arrays[i]).append(regex);
		}
		
		result.append(arrays[length - 1]);
		
		return result.toString();
	}
	
	/**
	 * 将数组转换成字符串
	 * 
	 * @param arrays	数组
	 * @param regex		分隔符
	 * @return
	 */
	public static String join(byte[] arrays, String regex)
	{
		StringBuilder result = new StringBuilder();
		
		int length = arrays.length;
		
		for (int i = 0; i < length - 1; i++)
		{
			result.append(arrays[i]).append(regex);
		}
		
		result.append(arrays[length - 1]);
		
		return result.toString();
	}

	/**
	 * origin数组表示被填充的数组，fromIndex表示origin数组开始填充的位置，data数组表示填充的数组
	 * 
	 * @param origin
	 *            被填充数组
	 * @param fromIndex
	 *            被填充数组起始位置
	 * @param data
	 *            填充数组
	 */
	public static void fill(byte[] origin, int fromIndex, byte[] data)
	{
		int originLen = origin.length;

		int dataLen = data.length;

		for (int i = 0; i < dataLen; i++)
		{
			int index = i + fromIndex;

			if (index >= originLen)
			{
				break;
			}

			origin[index] = data[i];
		}
	}
}
