package com.framework.common;

import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class GsonUtil
{
	public static Gson gson;
	public static JsonParser jsonParser;

	static
	{
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	/**
	 * 对象转换为JSON格式数据，不包含null值属性
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object)
	{
		return gson.toJson(object);
	}

	/**
	 * 对象转换为JSON格式数据，包含null值属性
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonContainNull(Object object)
	{
		Gson gson = new GsonBuilder().serializeNulls().create();

		return gson.toJson(object);
	}

	/**
	 * 将JSON数据转换为对象
	 * 
	 * @param json
	 *            JSON格式数据
	 * @param classOfT
	 *            对象类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> classOfT)
	{
		return gson.fromJson(json, classOfT);
	}

	public static Object fromJson(String json, Type typeOfT)
	{
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * 是否是格式良好的JSON数据
	 * 
	 * @param json
	 *            JSON数据
	 * @return
	 */
	public static boolean isJson(String json)
	{
		if (StringUtils.isBlank(json))
		{
			return false;
		}

		try
		{
			new JsonParser().parse(json);

			return true;
		}
		catch (JsonParseException e)
		{
			return false;
		}
	}

	/**
	 * 将JSON数据装换成JsonObject对象
	 * 
	 * @param json
	 *            JSON数据
	 * @return
	 */
	public static JsonObject fromJsonObject(String json)
	{
		return jsonParser.parse(json).getAsJsonObject();
	}

	/**
	 * 将Object对象装换成JsonObject对象
	 * 
	 * @param object
	 *            Object对象
	 * @return
	 */
	public static JsonObject fromJsonObject(Object object)
	{
		return jsonParser.parse(GsonUtil.toJson(object)).getAsJsonObject();
	}

	/**
	 * 将JSON数据装换成JsonArray对象
	 * 
	 * @param json
	 *            JSON数据
	 * @return
	 */
	public static JsonArray fromJsonArray(String json)
	{
		return jsonParser.parse(json).getAsJsonArray();
	}
	
	/**
	 * 将Object对象装换成JsonArray对象
	 * 
	 * @param object
	 *            Object对象
	 * @return
	 */
	public static JsonArray fromJsonArray(Object object)
	{
		return jsonParser.parse(GsonUtil.toJson(object)).getAsJsonArray();
	}
}
