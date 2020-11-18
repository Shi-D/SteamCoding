package com.framework.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.cache.impl.ColumnModelCacheImpl;

/**
 * 
 * @author {In-Death}
 * 
 */
public class DefaultDBCache
{
	private static Logger logger = LoggerFactory.getLogger(DefaultDBCache.class);

	// Cache store for read-only
	private static Map<String, DBCacheStore> cacheStore = new HashMap<String, DBCacheStore>();

	/**
	 * 初始化缓存
	 */
	public static void init()
	{
		cacheStore.put(ColumnModelCacheImpl.CACHE_NAME, 			new ColumnModelCacheImpl());
		

		logger.info("开始初始化系统自定义缓存......");
		
		for (DBCacheStore store : cacheStore.values())
		{
			store.init();
		}

		logger.info("系统自定义缓存数据初始化完成！");
	}

	/**
	 * 清空缓存数据
	 */
	public static void clear()
	{
		// Initialization all cache store implements
		for (DBCacheStore store : cacheStore.values())
		{
			store.clear();
		}

		cacheStore.clear();

		logger.info("系统自定义缓存数据已清空！");
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param cacheName
	 *            缓存对象名称
	 * @return
	 */
	public static DBCacheStore getCacheStore(String cacheName)
	{
		return cacheStore.get(cacheName);
	}

	/**
	 * 新增或者更新缓存
	 * 
	 * @param cacheName
	 * @param entity
	 */
	public static void save(String cacheName, Object entity)
	{
		DBCacheStore store = cacheStore.get(cacheName);

		if (store != null)
		{
			store.save(entity);
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 * @param entity
	 */
	public static void delete(String cacheName, Object entity)
	{
		DBCacheStore store = cacheStore.get(cacheName);

		if (store != null)
		{
			store.delete(entity);
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 *            缓存名字
	 * @param id
	 *            实体主键
	 */
	public static void deleteByKey(String cacheName, Object key)
	{
		DBCacheStore store = cacheStore.get(cacheName);

		if (store != null)
		{
			store.deleteByKey(key);
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 *            缓存名字
	 * @param ids
	 *            实体主键数组
	 */
	public static void deleteByKeys(String cacheName, Object[] keys)
	{
		DBCacheStore store = cacheStore.get(cacheName);

		if (store != null)
		{
			for (Object key : keys)
			{
				store.deleteByKey(key);
			}
		}
	}

	/**
	 * 通过唯一编号获取缓存对象，有的缓存是主键id作为唯一编号，有的缓存是指定特殊编号作为唯一编号，获取时请注意。
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param id
	 *            id值
	 * @return
	 */
	public static Object getByKey(String cacheName, Object key)
	{
		if (!cacheStore.containsKey(cacheName))
		{
			logger.warn("未知的缓存名称");
			return null;
		}

		try
		{
			DBCacheStore store = cacheStore.get(cacheName);
			
			return store.getByKey(key);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 通过其他属性名称获取缓存对象集合
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 */
	public static List<?> getByPropertyName(String cacheName, String propertyName, Object value)
	{
		if (!cacheStore.containsKey(cacheName))
		{
			logger.warn("未知的缓存名称");
			return null;
		}

		try
		{
			DBCacheStore store = cacheStore.get(cacheName);
			return store.getByPropertyName(propertyName, value);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 获取所有缓存对象集合
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return
	 */
	public static List<?> getAll(String cacheName)
	{
		if (!cacheStore.containsKey(cacheName))
		{
			logger.warn("未知的缓存名称");
			return null;
		}

		try
		{
			DBCacheStore store = cacheStore.get(cacheName);
			return store.getAll();
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
