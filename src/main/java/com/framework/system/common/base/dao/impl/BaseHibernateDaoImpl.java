package com.framework.system.common.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.framework.system.common.base.dao.IBaseHibernateDao;

public class BaseHibernateDaoImpl<T> implements IBaseHibernateDao<T>
{
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate()
	{
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void merge(T entity)
	{
		getHibernateTemplate().merge(entity);
	}

	@Override
	public void flush()
	{
		getHibernateTemplate().flush();
	}

	@Override
	public void clear()
	{
		getHibernateTemplate().clear();
	}

	@Override
	public void evict(T entity)
	{
		getHibernateTemplate().evict(entity);
	}

	@Override
	public void save(T entity)
	{
		getHibernateTemplate().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity)
	{
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<T> entities)
	{
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	@Override
	public void update(T entity)
	{
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity)
	{
		getHibernateTemplate().delete(entity);
	}

	@Override
	public T get(Class<T> clazz, Serializable ID)
	{
		return getHibernateTemplate().get(clazz, ID);
	}

	@Override
	public T load(Class<T> clazz, Serializable id)
	{
		return getHibernateTemplate().load(clazz, id);
	}

	@Override
	public List<T> loadAll(Class<T> clazz)
	{
		return getHibernateTemplate().loadAll(clazz);
	}

	@Override
	public Integer getTotalCountByHQL(String hql, Object... params)
	{
		String newHQL = changeHSQL(hql);
		Object totalCount = uniqueResultByHQL(newHQL, params);
		return changeObjectToInteger(totalCount);
	}

	@Override
	public Integer getTotalCountByHQL(String hql, Map<Object, Object> params)
	{
		String newHQL = changeHSQL(hql);
		Object totalCount = uniqueResultByHQL(newHQL, params);
		return changeObjectToInteger(totalCount);
	}

	@Override
	public Integer getTotalCountBySQL(String sql, Object... params)
	{
		String newHQL = changeHSQL(sql);
		Object totalCount = uniqueResultBySQL(newHQL, params);
		return changeObjectToInteger(totalCount);
	}

	@Override
	public Integer getTotalCountBySQL(String sql, Map<Object, Object> params)
	{
		String newHQL = changeHSQL(sql);
		Object totalCount = uniqueResultBySQL(newHQL, params);
		return changeObjectToInteger(totalCount);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int executeHQLUpdate(final String hql, final Object... params)
	{
		return (int) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int executeHQLUpdate(final String hql, final Map<Object, Object> params)
	{
		return (int) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> executeHQLQuery(final String hql, final Object... params)
	{
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.list();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> executeHQLQuery(final String hql, final Map<Object, Object> params)
	{
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.list();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int executeSQLUpdate(final String sql, final Object... params)
	{
		return (Integer) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int executeSQLUpdateByNamed(final String sql, final Map<Object, Object> params)
	{
		return (Integer) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> executeSQLQuery(final String sql, final Object... params)
	{
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.list();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> executeSQLQueryByNamed(final String sql, final Map<Object, Object> params)
	{
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(final String hql, final Object... params)
	{
		return (List<T>) getHibernateTemplate().find(hql, params);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Integer firstResult, Integer maxResult, Object... params)
	{
		Query query = getHQLQuery(hql, params);
		if (firstResult != null)
			query.setFirstResult(firstResult);
		if (maxResult != null)
			query.setMaxResults(maxResult);
		return query.list();
	}

	@Override
	public Object findUnique(String hql, Object... params)
	{
		return getHQLQuery(hql, params).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria)
	{
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
	{
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedParam(String hql, String paramName, Object value)
	{
		return (List<T>) getHibernateTemplate().findByNamedParam(hql, paramName, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedParam(String hql, String[] paramNames, Object[] values)
	{
		return (List<T>) getHibernateTemplate().findByNamedParam(hql, paramNames, values);
	}

	@Override
	public List<T> findByNamedParam(final String hql, final Map<Object, Object> params)
	{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置Query参数
				setQueryParameter(query, params);
				return query.list();
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName)
	{
		return (List<T>) getHibernateTemplate().findByNamedQuery(queryName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object value)
	{
		return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values)
	{
		return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
	{
		return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, paramName, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
	{
		return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, paramNames, values);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query getHQLQuery(final String hql, final Object... params)
	{
		return (Query) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置参数
				setQueryParameter(query, params);
				return query;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query getHQLQuery(final String hql, final Map<Object, Object> params)
	{
		return (Query) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				// 设置参数
				setQueryParameter(query, params);
				return query;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SQLQuery getSQLQuery(final String sql, final Object... params)
	{
		return (SQLQuery) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置参数
				setQueryParameter(query, params);
				return query;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SQLQuery getSQLQuery(final String sql, final Map<Object, Object> params)
	{
		return (SQLQuery) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				// 设置参数
				setQueryParameter(query, params);
				return query;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getResultByHQL(String hql, Object... params)
	{
		return (List<Map<String, Object>>) getHQLQuery(hql, params).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getResultByHQL(String hql, Map<Object, Object> params)
	{
		return (List<Map<String, Object>>) getHQLQuery(hql, params).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getResultBySQL(final String sql, final Object... params)
	{
		return (List<Map<String, Object>>) getSQLQuery(sql, params).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getResultBySQL(String sql, Map<Object, Object> params)
	{
		return (List<Map<String, Object>>) getSQLQuery(sql, params).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public Object uniqueResultByHQL(String hql, Object... params)
	{
		return getHQLQuery(hql, params).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	@Override
	public Object uniqueResultByHQL(String hql, Map<Object, Object> params)
	{
		return getHQLQuery(hql, params).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	@Override
	public Object uniqueResultBySQL(String sql, Object... params)
	{
		return getSQLQuery(sql, params).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	@Override
	public Object uniqueResultBySQL(String sql, Map<Object, Object> params)
	{
		return getSQLQuery(sql, params).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	@Override
	public Filter enableFilter(String filterName)
	{
		return this.getHibernateTemplate().enableFilter(filterName);
	}

	@Override
	public Query getNamedQuery(String queryName)
	{
		Query query = this.hibernateTemplate.getSessionFactory().openSession().getNamedQuery(queryName);
		return query;
	}

	/**
	 * 判断数据库中是否存在指定的表，仅用于SQL SERVER数据库
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean existsTable(String tableName) throws SQLException
	{
		Connection conn = hibernateTemplate.getSessionFactory().openSession().connection();
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet rs = meta.getTables(conn.getCatalog(), "DBO", tableName, new String[] { "TABLE" });

		if (rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 设置Query参数
	 * 
	 * @param query
	 * @param params
	 */
	private void setQueryParameter(Query query, Map<Object, Object> params)
	{
		if (params != null && !params.isEmpty())
		{
			Iterator<Entry<Object, Object>> iter = params.entrySet().iterator();

			while (iter.hasNext())
			{
				Entry<Object, Object> entry = iter.next();
				Object paramName = entry.getKey();
				Object value = entry.getValue();
				/*
				 * 如果key的类型为整型，则query中的语句占位符为?；如果key类型为字符串，则query中的语句占位符为字符串性占位符
				 */
				if (paramName instanceof Integer)
				{
					query.setParameter((Integer) paramName, value);
				}
				else if (paramName instanceof String)
				{
					if (value instanceof Collection<?>)
					{
						query.setParameterList((String) paramName, (Collection<?>) value);
					}
					else if (value instanceof Object[])
					{
						query.setParameterList((String) paramName, (Object[]) value);
					}
					else
					{
						query.setParameter((String) paramName, value);
					}
				}
			}
		}
	}

	/**
	 * 设置Query参数
	 * 
	 * @param query
	 * @param params
	 */
	private void setQueryParameter(Query query, Object... params)
	{
		if (params != null && params.length > 0)
		{
			for (int position = 0; position < params.length; position++)
			{
				query.setParameter(position, params[position]);
			}
		}
	}

	private String changeHSQL(String hsql)
	{
		String uppercase = hsql.toUpperCase();
		return "SELECT COUNT(*) " + hsql.substring(uppercase.indexOf("FROM"));
	}

	private Integer changeObjectToInteger(Object object)
	{
		if (object instanceof Long)
		{
			return ((Long) object).intValue();
		}
		else
		{
			return (Integer) object;
		}
	}
}
