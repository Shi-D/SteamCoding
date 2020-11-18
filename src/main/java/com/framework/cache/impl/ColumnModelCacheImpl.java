package com.framework.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.framework.cache.BaseDBCacheImpl;
import com.framework.common.SystemContext;
import com.framework.excel.entity.ColumnChange;
import com.framework.excel.entity.ColumnModel;
import com.framework.excel.service.ColumnModelService;

public class ColumnModelCacheImpl extends BaseDBCacheImpl
{
	private ColumnModelService columnModelService = (ColumnModelService) SystemContext.getBean(ColumnModelService.SERVICE_BEAN_NAME);

	public static final String CACHE_NAME = "columnModelCache";

	// key：表或者视图编号，value：columnModel的Map
	private Map<Integer, Map<String, ColumnModel>> store = new HashMap<Integer, Map<String, ColumnModel>>();

	@Override
	public void init()
	{
		
		logger.info("开始初始化Excel列模型缓存......");

		List<ColumnModel> list = columnModelService.loadAll(ColumnModel.class);

		for (ColumnModel model : list)
		{
			Integer tableCode = model.getTableCode();

			if (!store.containsKey(tableCode))
			{
				Map<String, ColumnModel> columnModelMap = new HashMap<String, ColumnModel>();

				store.put(tableCode, columnModelMap);
			}

			Map<String, ColumnModel> columnModelMap = store.get(tableCode);

			columnModelMap.put(model.getPropertyName(), model);

			Set<ColumnChange> columnChanges = model.getColumnChanges();

			if (columnChanges != null && columnChanges.size() > 0)
			{
				Map<String, String> changeMap = new HashMap<String, String>();

				for (ColumnChange change : columnChanges)
				{
					changeMap.put(change.getChangeKey(), change.getChangeValue());
				}

				model.setChange(changeMap);
			}
		}

		logger.info("Excel列模型缓存初始化完毕");
	}

	@Override
	public void save(Object entity)
	{
	}

	@Override
	public void delete(Object entity)
	{
	}

	@Override
	public void deleteByKey(Object key)
	{
	}

	/**
	 * 获取所需列模式集合
	 * 
	 * @param tableCode
	 *            表或者视图编号
	 * @param propertyNames
	 *            属性名字数组
	 * @return
	 */
	public List<ColumnModel> getColumnModelList(Integer tableCode, String[] propertyNames)
	{
		if (!store.containsKey(tableCode))
		{
			return null;
		}

		Map<String, ColumnModel> columnModelMap = store.get(tableCode);

		List<ColumnModel> cmList = new ArrayList<ColumnModel>();

		for (String property : propertyNames)
		{
			if (columnModelMap.containsKey(property))
			{
				cmList.add(columnModelMap.get(property));
			}
		}
		return cmList;
	}
}
