package com.framework.system.common.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

public interface IBaseService<T>
{
	/**
	 * 合并，同步缓存与当前对象，以当前对象为准
	 * 
	 * @param entity
	 * @return
	 */
	public void merge(T entity);

	/**
	 * 将Hibernate缓存中的数据提交到数据库,如果这时数据库处在一个事物当中,则数据库将这些SQL语句缓存起来
	 */
	public void flush();

	/**
	 * 把缓冲区内的全部对象清除，但不包括操作中的对象
	 */
	public void clear();

	/**
	 * 指定的缓冲对象进行清除
	 * 
	 * @param entity
	 *            要清楚的缓冲对象
	 */
	public void evict(T entity);

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 保存或者更新实体
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);

	/**
	 * 保存或者更新集合中的全部实体
	 * 
	 * @param entities
	 */
	public void saveOrUpdateAll(Collection<T> entities);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 根据实体类型和主键获取实体。如果没有相应的实体，返回null
	 * 
	 * @param clazz
	 *            实体类类型
	 * @param id
	 *            实体id
	 * @return
	 */
	public T get(Class<T> clazz, Serializable id);

	/**
	 * 根据实体类型和主键获取实体，如果没有相应的实体，抛出异常
	 * 
	 * @param clazz
	 *            实体类类型
	 * @param id
	 *            实体id
	 * @return
	 */
	public T load(Class<T> clazz, Serializable id);

	/**
	 * 根据实体类型获取全部实体
	 * 
	 * @param clazz
	 *            实体类类型
	 * @return
	 */
	public List<T> loadAll(Class<T> clazz);

	/**
	 * 根据实体类型生成HQL语句获取全部实体
	 * 
	 * @param clazz
	 *            实体类类型
	 * @return
	 */
	public List<T> findAll(Class<T> clazz);

	/**
	 * 获得查询条件下的记录条数
	 * 
	 * @param hql
	 *            eg:SELECT * FROM User u WHERE u.name=? AND u.age=?
	 * @param params
	 *            eg:name,age
	 * @return
	 */
	public Integer getTotalCountByHQL(String hql, Object... params);

	/**
	 * 获得查询条件下的记录条数
	 * 
	 * @param sql
	 *            eg1:SELECT * FROM User u WHERE u.name=:name AND u.age=:age<br/>
	 *            eg2:SELECT * FROM User u WHERE u.name=? AND u.age=?
	 * @param params
	 *            eg1:{name: zhangsan,age: 20}<br/>
	 *            eg2:{0: zhangsan,1: 20}
	 * @return
	 */
	public Integer getTotalCountByHQL(String hql, Map<Object, Object> params);
	
	/**
	 * 获得查询条件下的记录条数
	 * 
	 * @param hql
	 *            eg:SELECT * FROM T_USER u WHERE u.name=? AND u.age=?
	 * @param params
	 *            eg:name,age
	 * @return
	 */
	public Integer getTotalCountBySQL(String sql, Object... params);

	/**
	 * 获得查询条件下的记录条数
	 * 
	 * @param sql
	 *            eg1:SELECT * FROM T_USER u WHERE u.name=:name AND u.age=:age<br/>
	 *            eg2:SELECT * FROM T_USER u WHERE u.name=? AND u.age=?
	 * @param params
	 *            eg1:{name: zhangsan,age: 20}<br/>
	 *            eg2:{0: zhangsan,1: 20}
	 * @return
	 */
	public Integer getTotalCountBySQL(String sql, Map<Object, Object> params);

	/**
	 * 执行HQL的增、删、改语句，如果HQL语句带有占位符只能为?
	 * 
	 * @param hql
	 * @param params
	 */
	public int executeHQLUpdate(String hql, Object... params);

	/**
	 * 执行HQL的增、删、改语句，
	 * 
	 * @param hql
	 * @param params
	 */
	public int executeHQLUpdate(String hql, Map<Object, Object> params);

	/**
	 * 执行HQL的查询语句，如果HQL语句带有占位符只能为?
	 * 
	 * @param hql
	 * @param params
	 */
	public List<T> executeHQLQuery(String hql, Object... params);

	/**
	 * 执行HQL的查询语句
	 * 
	 * @param hql
	 * @param params
	 */
	public List<T> executeHQLQuery(String hql, Map<Object, Object> params);

	/**
	 * 执行SQL语句的增、删、改操作，如果SQL语句带有占位符只能为?
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSQLUpdate(String sql, Object... params);

	/**
	 * 执行SQL语句的增、删、改操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSQLUpdate(String sql, Map<Object, Object> params);

	/**
	 * 执行SQL语句的查询操作，如果SQL语句带有占位符只能为?
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> executeSQLQuery(String sql, Object... params);

	/**
	 * 执行SQL语句的查询操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> executeSQLQueryByNamed(String sql, Map<Object, Object> params);

	/**
	 * 简单查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> find(String hql, Object... params);

	/**
	 * 使用带单个字符串占位符和参数的HQL语句检索数据
	 * 
	 * @param hql
	 *            eg:SELECT * FROM User u WHERE u.name=:name
	 * @param paramName
	 *            eg:name
	 * @param value
	 *            eg:zhangsan
	 * @return
	 */
	public List<T> findByNamedParam(String hql, String paramName, Object value);

	/**
	 * 使用带多个字符串占位符和参数的HQL语句检索数据
	 * 
	 * @param hql
	 *            eg:SELECT * FROM User u WHERE u.name=:name AND u.age=:age
	 * @param paramNames
	 *            eg:["name", "age"]
	 * @param values
	 *            eg:["zhangsan", 20]
	 * @return
	 */
	public List<T> findByNamedParam(String hql, String[] paramNames, Object[] values);

	/**
	 * 使用带多个字符串占位符和参数的HQL语句检索数据
	 * 
	 * @param hql
	 *            eg:SELECT * FROM User u WHERE u.name=:name AND u.age=:age
	 * @param params
	 *            eg:{"name" : "zhangsan", "age" :　20}
	 * @return
	 */
	public List<T> findByNamedParam(String hql, Map<Object, Object> params);

	/**
	 * 首先需要在实体类或者实体类映射文件中定义命名查询<br/>
	 * 使用方法：<br/>
	 * 注解方式<br/>
	 * "@NamedQueries({ @javax.persistence.NamedQuery(name = "queryAllUser
	 * ", query = "select u from User") })"<br/>
	 * 映射文件方式<br/>
	 * <hibernate-mapping> <class>......</class> <query
	 * name="queryAllUser"><!--此查询被调用的名字--> <![CDATA[ from bean.User ]]></query>
	 * </hibernate-mapping> <br/>
	 * this.getHibernateTemplate().findByNamedQuery("queryAllUser");
	 * 
	 * @param queryName
	 * @return
	 */
	public List<T> findByNamedQuery(String queryName);

	/**
	 * 参考findByNamedQuery(String queryName)方法，使用单个?占位符
	 * 
	 * @param queryName
	 * @param value
	 * @return
	 */
	public List<T> findByNamedQuery(String queryName, Object value);

	/**
	 * 参考findByNamedQuery(String queryName)方法，使用多个?占位符
	 * 
	 * @param queryName
	 * @param values
	 * @return
	 */
	public List<T> findByNamedQuery(String queryName, Object[] values);

	/**
	 * 参考findByNamedParam(String hql, String paramName, Object
	 * value)方法，使用单个字符串占位符和参数
	 * 
	 * @param queryName
	 * @param paramName
	 * @param value
	 * @return
	 */
	public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);

	/**
	 * 参考findByNamedParam(String hql, String[] paramNames, Object[]
	 * values)方法，使用多个字符串占位符和参数
	 * 
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @return
	 */
	public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);

	/**
	 * 根据HQL语句获取Query
	 * 
	 * @param hql
	 * @return
	 */
	public Query getHQLQuery(String hql, Object... params);
	
	/**
	 * 根据HQL语句获取Query
	 * 
	 * @param hql
	 * @return
	 */
	public Query getHQLQuery(String hql, Map<Object, Object> params);

	/**
	 * 根据SQL语句获取SQLQuery
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public SQLQuery getSQLQuery(String sql, Object... params);
	
	/**
	 * 根据SQL语句获取SQLQuery
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public SQLQuery getSQLQuery(String sql, Map<Object, Object> params);

	/**
	 * 根据HQL获取Map格式数据集合
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getResultByHQL(String hql, Object... params);

	/**
	 * 根据HQL获取Map格式数据集合
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getResultByHQL(String hql, Map<Object, Object> params);

	/**
	 * 根据SQL获取Map格式数据集合
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getResultBySQL(String sql, Object... params);

	/**
	 * 根据SQL获取Map格式数据集合
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getResultBySQL(String sql, Map<Object, Object> params);
	
	/**
	 * 根据HQL语句返回单个Object类型的对象。有可能是个数组 eg1:hql=="select count(*) from User"
	 * 执行该方法返回User记录总数。需要自己根据需要转型 eg2:hql=
	 * "from User u where lower(u.name) like 'miley%' and u.sex in ('girl','boy')"
	 * ; 执行该方法返回查询name为miley并且性别是girl的第一条记录，类型为User
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object uniqueResultByHQL(String hql, Object... params);
	
	public Object uniqueResultByHQL(String hql, Map<Object, Object> params);

	/**
	 * 根据SQL语句返回单个Object类型的对象。<br/>
	 * 有可能是个数组 eg1:hql=="select count(*) from User"执行该方法返回User记录总数。需要自己根据需要转型 eg2:hql=
	 * "from User u where lower(u.name) like 'miley%' and u.sex in ('girl','boy')"
	 * 执行该方法返回查询name为miley并且性别是girl的第一条记录，类型为User
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object uniqueResultBySQL(String sql, Object... params);
	
	public Object uniqueResultBySQL(String sql, Map<Object, Object> params);

}
