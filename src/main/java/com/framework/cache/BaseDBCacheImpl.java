package com.framework.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDBCacheImpl implements DBCacheStore
{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	// key为表主键，value为实体对象
	protected final Map<Object, Object> store = new HashMap<Object, Object>();

	@Override
	public abstract void init();
	
	@Override
	public void reinit()
	{
		clear();
		init();
	}
	
	@Override
	public void clear()
	{
		store.clear();
	}

	@Override
	public abstract void save(Object entity);
	
	@Override
	public abstract void delete(Object entity);
	
	@Override
	public abstract void deleteByKey(Object key);
	
	@Override
	public Object getByKey(Object key)
	{
		return store.get(key);
	}

	@Override
	public List<Object> getByPropertyName(String propertyName, Object value)
	{
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> getAll()
	{
		List<Object> list = new ArrayList<Object>();
		
		for (Object object : store.values())
		{
			list.add(object);
		}
		return list;
	}
}
