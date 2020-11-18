package com.framework.cache;

import java.util.List;

/**
 * 缓存接口
 * 
 * @author {In-Death}
 *
 */
public interface DBCacheStore
{
	/**
	 * 初始化缓存
	 */
	void init();
	
	/**
	 * 重新初始化缓存
	 */
	void reinit();

	/**
	 * 清除缓存
	 */
	void clear();
	
	/**
	 * 保存或者新增缓存
	 */
	void save(Object entity);
	
	/**
	 * 删除缓存
	 */
	void delete(Object entity);
	
	/**
	 * 删除缓存
	 */
	void deleteByKey(Object key);

	/**
	 * 获取缓存
	 * 
	 * @param key
	 */
	Object getByKey(Object key);

	/**
	 * Get by property name
	 * 
	 * @param propertyName
	 * @return
	 */
	List<Object> getByPropertyName(String propertyName, Object value);

	/**
	 * 获取所有缓存
	 * 
	 * @return
	 */
	List<Object> getAll();
}
