package com.framework.system.common.entity;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult
{
	private boolean subOk = true; // 提交是否成功
	private Map<String, Object> errors = new HashMap<String, Object>();// 错误信息
	private Map<String, Object> messages = new HashMap<String, Object>();// 其他信息，可能成功了有成功的一些信息
	private String message;
	private Object object;

	public boolean isSubOk()
	{
		return subOk;
	}

	public void setSubOk(boolean subOk)
	{
		this.subOk = subOk;
	}

	public Map<String, Object> getErrors()
	{
		return errors;
	}

	public void setErrors(Map<String, Object> errors)
	{
		this.errors = errors;
	}

	public Map<String, Object> getMessages()
	{
		return messages;
	}

	public void setMessages(Map<String, Object> messages)
	{
		this.messages = messages;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

}
