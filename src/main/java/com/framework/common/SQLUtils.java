package com.framework.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.framework.system.common.base.service.IBaseService;

/**
 * SQL工具类
 * 
 * @author In-Death
 * 
 */
public class SQLUtils
{

	/**
	 * 创建SQL的insert语句(一次性插入多条记录的格式)
	 * 
	 * @param clazz
	 * @param list
	 * @param removeFirst
	 * @return
	 * @throws ParseException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static <T> String createLongInsertSentence(Class<T> clazz, List<T> list, boolean removeFirst) throws ParseException, NoSuchMethodException, SecurityException
	{
		String insertSQL = "";

		int listSize = list.size();

		if (listSize <= 0)
		{
			return insertSQL;
		}

		List<Field> fieldList = SQLUtils.getEntityFields(clazz);
		
		if (removeFirst)
		{
			fieldList.remove(0);
		}
		System.out.println(fieldList);
		
		String s1 = "(";
		int fieldLength = fieldList.size();
		for (int i = 0; i < fieldLength-4; i++)
		{
			Field field = fieldList.get(i);
					
			if (i == 0)
			{
				s1 += getColumnName(clazz, field);
			}
			else
			{
				s1 += "," + getColumnName(clazz, field);
			}
		}
		s1 += ")";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < listSize; i++)
		{
			T object = list.get(i);
			if (i == 0)
			{
				sb.append("(");
			}
			else
			{
				sb.append(",(");
			}
			for (int j = 0; j < fieldLength; j++)
			{
				Field field = fieldList.get(j);
				Object fieldValue = SQLUtils.getter(object, field);
				Object value = null;
				if (fieldValue instanceof Date)
				{
					value = "'" + DateUtil.formatDateToString((Date) fieldValue, "yyyy-MM-dd HH:mm:ss") + "'";
				}
				else if (fieldValue instanceof String)
				{
					value = "'" + fieldValue + "'";
				}
				else if (fieldValue instanceof Boolean)
				{
					value = ((Boolean)fieldValue ? 1 : 0);
				}
				else
				{
					value = fieldValue;
				}

				if (j == 0)
				{
					sb.append(value);
				}
				else
				{
					sb.append("," + value);
				}
			}
			sb.append(")");
		}
		insertSQL = "INSERT INTO " + clazz.getAnnotation(Table.class).name() + s1 + " VALUES" + sb.toString();
		return insertSQL;
	}

	/**
	 * 根据对象集合list循环生成length个对象的insert into语句，再执行插入
	 * 
	 * @param service
	 *            service对象
	 * @param length
	 *            生成insert into语句对象的个数
	 * @param clazz
	 *            对象类
	 * @param list
	 *            对象集合
	 * @param removeFirst
	 *            是否删除对象的第一个属性，一般第一个属性为id
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ParseException
	 */
	public static <T> void insertIntoList(IBaseService<T> service, int length, Class<T> clazz, List<T> list, boolean removeFirst) throws NoSuchMethodException, SecurityException, ParseException
	{
		// 对象集合长度
		int listSize = list.size();
		if (listSize > 0)
		{
			/*
			 * 如果listSize <= length，直接使用对象集合生成insert语句并执行，
			 * 否则循环每length个对象个数生成insert语句并执行
			 */
			if (listSize <= length)
			{
				String sql = SQLUtils.createLongInsertSentence(clazz, list, removeFirst);

				service.executeSQLUpdate(sql);
			}
			else
			{
				List<T> temp = new ArrayList<T>(length);
				for (int i = 0; i < listSize; i++)
				{
					temp.add(list.get(i));
					if ((i + 1) % length == 0 || i == listSize - 1)
					{
						String sql = SQLUtils.createLongInsertSentence(clazz, temp, removeFirst);
						temp.clear();
						service.executeSQLUpdate(sql);
					}
				}
			}
		}
	}

	public static Object getter(Object obj, Field field)
	{
		return SQLUtils.getter(obj, field.getName());
	}

	public static Object getter(Object obj, String fieldName)
	{
		Object object = null;
		String field = capitalize(fieldName);
		try
		{
			Method method = obj.getClass().getMethod("get" + field);
			object = method.invoke(obj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}

	public static <T> String getColumnName(Class<T> clazz, Field field) throws NoSuchMethodException, SecurityException
	{
		Column c = field.getAnnotation(Column.class);

		if (c == null)
		{
			Method m = clazz.getMethod("get" + capitalize(field));

			c = m.getAnnotation(Column.class);
		}

		return c.name();
	}
	
	/**
	 * 获取实体类相关的字段
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> List<Field> getEntityFields(Class<T> clazz)
	{
		List<Field> list = new ArrayList<Field>();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields)
		{
			if (!Modifier.isPrivate(field.getModifiers()))
			{
				continue;
			}
			
			list.add(field);
		}
		
		return list;
	}

	public static String capitalize(Field field)
	{
		return capitalize(field.getName());
	}

	public static String capitalize(String fieldName)
	{
		String field = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return field;
	}
	
	/**
	 * 创建SQL基础查询语句<br/>
	 * 列如：select col1 as col1,col2 as col2..... from EntityClassName<br/>
	 * 
	 * @param entityClassName
	 *            实体类类名称
	 * @param columns
	 *            字段数组
	 * @return
	 */
	public static String createBaseSelectSql(String entityClassName, String[] columns)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		int size = columns.length;
		for (int i = 0; i < size; i++)
		{
			String column = columns[i];
			if (i == size - 1)
			{
				sb.append(column).append(" as ").append(column);
				break;
			}
			sb.append(column).append(" as ").append(column).append(",");
		}
		sb.append(" from ").append(entityClassName);

		return sb.toString();
	}
}
