package com.framework.system.common.base.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.framework.system.common.base.dao.IBaseHibernateDao;
import com.framework.system.common.base.service.IBaseService;
import com.framework.system.common.entity.FilterSearch;
import com.framework.system.common.entity.Pager;
import com.framework.system.common.entity.SearchRule;
import com.framework.system.common.entity.Sorter;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class BaseServiceImpl<T> implements IBaseService<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IBaseHibernateDao<T> baseHibernateDao;

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public void merge(T entity) {
		this.baseHibernateDao.merge(entity);
	}

	@Override
	public void flush() {
		this.baseHibernateDao.flush();
	}

	@Override
	public void clear() {
		this.baseHibernateDao.clear();
	}

	@Override
	public void evict(T entity) {
		this.baseHibernateDao.evict(entity);
	}

	@Override
	public void save(T entity) {
		this.baseHibernateDao.save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.baseHibernateDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<T> entities) {
		this.baseHibernateDao.saveOrUpdateAll(entities);
	}

	@Override
	public void update(T entity) {
		this.baseHibernateDao.update(entity);
	}

	@Override
	public void delete(T entity) {
		this.baseHibernateDao.delete(entity);
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		return this.baseHibernateDao.get(clazz, id);
	}

	@Override
	public T load(Class<T> clazz, Serializable id) {
		return this.baseHibernateDao.load(clazz, id);
	}

	@Override
	public List<T> loadAll(Class<T> clazz) {
		return this.baseHibernateDao.loadAll(clazz);
	}

	@Override
	public List<T> findAll(Class<T> clazz) {
		return this.baseHibernateDao.find("from " + clazz.getName());
	}

	@Override
	public Integer getTotalCountByHQL(String hql, Object... params) {
		return this.baseHibernateDao.getTotalCountByHQL(hql, params);
	}

	@Override
	public Integer getTotalCountByHQL(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.getTotalCountByHQL(hql, params);
	}

	@Override
	public Integer getTotalCountBySQL(String sql, Object... params) {
		return this.baseHibernateDao.getTotalCountBySQL(sql, params);
	}

	@Override
	public Integer getTotalCountBySQL(String sql, Map<Object, Object> params) {
		return this.baseHibernateDao.getTotalCountBySQL(sql, params);
	}

	@Override
	public int executeHQLUpdate(String hql, Object... params) {
		return this.baseHibernateDao.executeHQLUpdate(hql, params);
	}

	@Override
	public int executeHQLUpdate(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.executeHQLUpdate(hql, params);
	}

	@Override
	public List<T> executeHQLQuery(String hql, Object... params) {
		return this.baseHibernateDao.executeHQLQuery(hql, params);
	}

	@Override
	public List<T> executeHQLQuery(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.executeHQLQuery(hql, params);
	}

	@Override
	public int executeSQLUpdate(final String sql, final Object... params) {
		return this.baseHibernateDao.executeSQLUpdate(sql, params);
	}

	@Override
	public int executeSQLUpdate(String sql, Map<Object, Object> params) {
		return this.baseHibernateDao.executeSQLUpdateByNamed(sql, params);
	}

	@Override
	public List<Object[]> executeSQLQuery(String sql, Object... params) {
		return this.baseHibernateDao.executeSQLQuery(sql, params);
	}

	@Override
	public List<Object[]> executeSQLQueryByNamed(final String sql, final Map<Object, Object> params) {
		return this.baseHibernateDao.executeSQLQueryByNamed(sql, params);
	}

	public List<T> find(String hql, Object... params) {
		return this.baseHibernateDao.find(hql, params);
	}

	@Override
	public List<T> findByNamedParam(String hql, String paramName, Object value) {
		return this.baseHibernateDao.findByNamedParam(hql, paramName, value);
	}

	@Override
	public List<T> findByNamedParam(String hql, String[] paramNames, Object[] values) {
		return this.baseHibernateDao.findByNamedParam(hql, paramNames, values);
	}

	@Override
	public List<T> findByNamedParam(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.findByNamedParam(hql, params);
	}

	@Override
	public List<T> findByNamedQuery(String queryName) {
		return this.baseHibernateDao.findByNamedQuery(queryName);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object value) {
		return this.baseHibernateDao.findByNamedQuery(queryName, value);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		return this.baseHibernateDao.findByNamedQuery(queryName, values);
	}

	@Override
	public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) {
		return this.baseHibernateDao.findByNamedQueryAndNamedParam(queryName, paramName, value);
	}

	@Override
	public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
		return this.baseHibernateDao.findByNamedQueryAndNamedParam(queryName, paramNames, values);
	}

	public List<Map<String, Object>> findBySQL(String sql, Object... params) {
		return this.baseHibernateDao.getResultBySQL(sql, params);
	}

	public IBaseHibernateDao<T> getBaseHibernateDao() {
		return baseHibernateDao;
	}

	public void setBaseHibernateDao(IBaseHibernateDao<T> baseHibernateDao) {
		this.baseHibernateDao = baseHibernateDao;
	}

	public void updateState(HibernateCallback action) {
		this.getHibernateTemplate().execute(action);
	}

	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.baseHibernateDao.findByCriteria(criteria);
	}

	public <E> E queryByHql(HibernateCallback<E> action) {
		return this.getHibernateTemplate().execute(action);
	}

	@Override
	public Query getHQLQuery(String hql, Object... params) {
		return this.baseHibernateDao.getHQLQuery(hql, params);
	}

	@Override
	public Query getHQLQuery(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.getHQLQuery(hql, params);
	}

	@Override
	public SQLQuery getSQLQuery(String sql, Object... params) {
		return this.baseHibernateDao.getSQLQuery(sql, params);
	}

	@Override
	public SQLQuery getSQLQuery(String sql, Map<Object, Object> params) {
		return this.baseHibernateDao.getSQLQuery(sql, params);
	}

	@Override
	public List<Map<String, Object>> getResultByHQL(String hql, Object... params) {
		return this.baseHibernateDao.getResultByHQL(hql, params);
	}

	@Override
	public List<Map<String, Object>> getResultByHQL(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.getResultByHQL(hql, params);
	}

	@Override
	public List<Map<String, Object>> getResultBySQL(String sql, Object... params) {
		return this.baseHibernateDao.getResultBySQL(sql, params);
	}

	@Override
	public List<Map<String, Object>> getResultBySQL(String sql, Map<Object, Object> params) {
		return this.baseHibernateDao.getResultBySQL(sql, params);
	}

	/**
	 * 閼惧嘲褰囩仦鐐达拷褍鍙忛崥宥勭瑢鐏炵偞锟窖冨焼閸氬秶娈戦弰鐘茬殸闂嗗棗鎮�
	 * 
	 * @param hql
	 *            閸忓磭閮撮弽瑙勫祦hql鐠囶厼褰炴稉鐠閸忓磭閮寸涵顔肩暰 select mainEntity.ID as
	 *            ID,mainEntity.inforValue as inforValue,mainEntity.inforType as
	 *            inforType, customer.ID as customerID from BasicInfor as
	 *            mainEntity閵嗗倶锟藉倶锟斤拷 鐏炵偞锟窖冨弿閸氬超ainEntity.ID-鐏炵偞锟窖冨焼閸氬不D
	 *            鐏炵偞锟窖冨弿閸氬超ainEntity.inforValue-鐏炵偞锟窖冨焼閸氬常nforValue
	 *            鐏炵偞锟窖冨弿閸氬超ainEntity.inforType -鐏炵偞锟窖冨焼閸氬常nforType
	 * 
	 * @return
	 */
	private Map<String, String> getObjectPropertyFullNameOtherNameMap(String hql) {
		try {
			String propertyHql = hql.substring(hql.indexOf("select ") + 7, hql.indexOf(" from "));
			if (propertyHql == null) {
				return null;
			}
			String[] splitByComma = propertyHql.split(",");// 鐏忓敄elect .... from
															// 闂傚娈戠�涙顑佹稉韫簰','閸掑棙鍨氶弫鎵矋;
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < splitByComma.length; i++) {
				try {
					String str = splitByComma[i];
					String propertyFullName = str.substring(0, str.indexOf(" as ")).trim();
					String otherName = str.substring(str.indexOf(" as ") + 4).trim();
					map.put(otherName, propertyFullName);
				} catch (Exception e) {
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}

	/**
	 * @param prefix
	 *            閸撳秶绱戦妴浣稿焼閸氾拷 閺嶈宓乧onditions鏉烆剚宕查幋鎭爍l閺嶇厧绱� 閺嶈宓侀弶鈥叉閹疯壈顥奡QL鐠囶厼褰為敍鍫濆涧闂勬劒绨瑆here娑斿鎮楅惃鍕蒋娴犺绱濇稉宥呭瘶閹风憜here閿涳拷
	 */
	private String tranToSQL(Map<String, String> propertyMap, FilterSearch filterSearch) {
		StringBuilder sb = new StringBuilder("");
		if (null != filterSearch && filterSearch.getRules() != null) {
			List<SearchRule> rules = filterSearch.getRules();
			int count = 0;
			if (null != rules && (count = rules.size()) > 0) {
				for (SearchRule rule : rules) {
					if (null != rule.getField() && null != rule.getData() && null != rule.getOp()) {
						/*
						 * 婵″倹鐏夐張澶婂缂傦拷閿涘苯鍨幎濠傚缂傦拷閸旂姳绗傞敍灞剧槨娑擃亝娼禒鍫曞厴娴兼岸娓剁憰浣稿缂傦拷 閺堝绨洪弻銉嚄閺夆�叉鐏炵偞锟窖冨弿閸氬秴褰查懗鎴掕礋缁岀尨绱濈亸鍙樹簰閺屻儴顕楅弮鍓佹畱鐎涙顔岄崥锟�
						 */
						String 属性全名 = propertyMap.get(rule.getField());
						sb.append((属性全名 == null || 属性全名.indexOf("(") != -1) ? rule.getField() : 属性全名);
						// 鏉╁洦鎶ら幒澶婄摟缁楋缚瑕嗘稉銈囶伂缁岀儤鐗�
						rule.setData(rule.getData().trim());
						/* 缁涘绨� */
						if ("eq".equalsIgnoreCase(rule.getOp())) {
							sb.append(" = ").append("'").append(rule.getData()).append("'");
							/* 娑撳秶鐡戞禍锟� */
						} else if ("ne".equalsIgnoreCase(rule.getOp())) {
							sb.append(" != ").append("'").append(rule.getData()).append("'");
							/* 鐏忓繋绨� */
						} else if ("lt".equalsIgnoreCase(rule.getOp())) {
							sb.append(" < ").append("'").append(rule.getData()).append("'");
							/* 鐏忓繋绨粵澶夌艾 */
						} else if ("le".equalsIgnoreCase(rule.getOp())) {
							sb.append(" <= ").append("'").append(rule.getData()).append("'");
							/* 婢堆傜艾 */
						} else if ("gt".equalsIgnoreCase(rule.getOp())) {
							sb.append(" > ").append("'").append(rule.getData()).append("'");
							/* 婢堆傜艾缁涘绨� */
						} else if ("ge".equalsIgnoreCase(rule.getOp())) {
							sb.append(" >= ").append("'").append(rule.getData()).append("'");
							/* 瀵拷婵绨� */
						} else if ("bw".equalsIgnoreCase(rule.getOp())) {
							sb.append(" like ").append("'").append(rule.getData()).append("%").append("'");
							/* 娑撳秴绱戞慨瀣╃艾 */
						} else if ("bn".equalsIgnoreCase(rule.getOp())) {
							sb.append(" not like ").append("'").append(rule.getData()).append("%").append("'");
							/* 缂佹挻娼禍锟� */
						} else if ("ew".equalsIgnoreCase(rule.getOp())) {
							sb.append(" like ").append("'").append("%").append(rule.getData()).append("'");
							/* 娑撳秶绮ㄩ弶鐔剁艾 */
						} else if ("en".equalsIgnoreCase(rule.getOp())) {
							sb.append(" not like ").append("'").append("%").append(rule.getData()).append("'");
							/* 閸栧懎鎯堟禍锟� */
						} else if ("cn".equalsIgnoreCase(rule.getOp())) {
							String data = rule.getData();
							if (data.contains("[")) {
								data = data.replaceAll("\\[", "[[]");
							}
							sb.append(" like ").append("'").append("%").append(data).append("%").append("'");
							/* 娑撳秴瀵橀崥顐＄艾 */
						} else if ("nc".equalsIgnoreCase(rule.getOp())) {
							sb.append(" not like ").append("'").append("%").append(rule.getData()).append("%").append("'");
						} else if ("in".equalsIgnoreCase(rule.getOp())) {
							sb.append(" in ").append("(").append(rule.getData()).append(")");
						} else if ("is".equalsIgnoreCase(rule.getOp())) {
							sb.append(" is ").append(rule.getData());
						} else {

						}
						count--;
						if (count > 0) {
							if (null != filterSearch.getGroupOp()) {
								if (filterSearch.getGroupOp().toLowerCase().equals("and")) {
									sb.append(" and ");
								} else {
									sb.append(" or ");
								}
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 鏉╂柨娲栭張顏呭潑閸旓拷 order by閻ㄥ埅ql
	 * 
	 * @param filter
	 * @param jqgridID
	 * @return
	 */
	public StringBuilder getUnSortedHQL(FilterSearch filter, String basehql) {
		StringBuilder hql = new StringBuilder(basehql);
		/* 閼惧嘲褰囩仦鐐达拷褍鍙忛崥宥勭瑢閸掝偄鎮曢崗宕囬兇闂嗗棗鎮� */
		Map<String, String> propertyMap = getObjectPropertyFullNameOtherNameMap(hql.toString());
		/* 閹疯壈顥婇弻銉嚄閺夆�叉 */
		String tranSql = tranToSQL(propertyMap, filter);
		if (tranSql.length() > 5) {
			if (hql.indexOf(" where ") != -1) {
				if (hql.toString().contains("@")) {
					String temp = hql.toString();
					int index = temp.indexOf("@");
					temp = temp.substring(0, index) + " and " + tranSql + " " + temp.substring(index + 1, temp.length());
					hql = new StringBuilder(temp);
				} else {
					if (this.checkCondition(hql.toString())) {
						String temp = hql.toString();
						int index = temp.indexOf("group by");
						temp = temp.substring(0, index) + " and " + tranSql + " " + temp.substring(index, temp.length());
						hql = new StringBuilder(temp);
					} else if (hql.indexOf("order by") != -1) {
						String temp = hql.toString();
						int index = temp.indexOf("order by");
						temp = temp.substring(0, index) + " and " + tranSql + " " + temp.substring(index, temp.length());
						hql = new StringBuilder(temp);
					} else {
						hql.append(" and ").append(tranSql).append(" ");
					}
				}
			} else {
				if (this.checkCondition(hql.toString())) {
					String temp = hql.toString();
					int index = temp.indexOf("group by");
					temp = temp.substring(0, index) + " where " + tranSql + " " + temp.substring(index, temp.length());
					hql = new StringBuilder(temp);
				} else if (hql.indexOf("order by") != -1) {
					String temp = hql.toString();
					int index = temp.indexOf("order by");
					temp = temp.substring(0, index) + " where " + tranSql + " " + temp.substring(index, temp.length());
					hql = new StringBuilder(temp);
				} else {
					hql.append(" where ").append(tranSql);
				}
			}
		} else {
			if (hql.toString().contains("@")) {
				String temp = hql.toString();
				int index = temp.indexOf("@");
				temp = temp.substring(0, index) + temp.substring(index + 1, temp.length());
				hql = new StringBuilder(temp);
			}
		}
		return hql;
	}

	private Boolean checkCondition(String hql) {
		if (hql.contains("group by")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param page
	 * @param basehql
	 *            //閺堬拷閸樼喎顫愰惃鍒猶l
	 * @param filter
	 *            //閸欏倹鏆�
	 * @return 鏉╂柨娲杙age鐎电钖�
	 */
	public Pager find(Pager page, String basehql, FilterSearch filter) {
		StringBuilder hql = null;

		try {
			hql = getUnSortedHQL(filter, basehql);
		} catch (Exception e) {
			return page;
		}
		
		System.out.println(hql);
		
		/* 閼惧嘲褰囬幀鏄忣唶瑜版洘鏆� */
		page.setTotalCount(this.getTotalCountByHQL(hql.toString()));
		page.setTotalPage((page.getTotalCount() - 1) / page.getPageSize() + 1);

		/* 閸︹暏ql鐠囶厼褰炴稉顓炲閸忋儲甯撴惔蹇曟祲閸忓啿鐡у▓锟� */
		if (filter != null) {
			List<Sorter> sorters = filter.getSorters();

			if (sorters != null && sorters.size() > 0) {
				StringBuilder orderBy = null;

				if (hql.indexOf("order by") == -1) {
					orderBy = new StringBuilder(" order by ");
				} else {
					orderBy = new StringBuilder(",");
				}

				for (Sorter sorter : sorters) {
					String property = sorter.getProperty();
					String direction = sorter.getDirection();

					if (property != null && !"".equals(property) && direction != null && !"".equals(direction)) {
						orderBy.append(property).append(" ").append(direction).append(",");
					}
				}

				orderBy.deleteCharAt(orderBy.length() - 1);

				hql.append(orderBy);
			}
		}

		Query query = this.getHQLQuery(hql.toString());
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Map<String, Object>> result = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		page.setDataset(result);
		return page;
	}

	/**
	 * @param page
	 * @param basehql
	 *            //閺堬拷閸樼喎顫愰惃鍒猶l
	 * @param filter
	 *            //閸欏倹鏆�
	 * @return 鏉╂柨娲杙age鐎电钖�
	 */
	public List<Map<String, Object>> find(String basehql, FilterSearch filter) {
		StringBuilder hql = null;

		try {
			hql = getUnSortedHQL(filter, basehql);
		} catch (Exception e) {
			return null;
		}

		/* 閸︹暏ql鐠囶厼褰炴稉顓炲閸忋儲甯撴惔蹇曟祲閸忓啿鐡у▓锟� */
		if (filter != null) {
			List<Sorter> sorters = filter.getSorters();

			if (sorters != null && sorters.size() > 0) {
				StringBuilder orderBy = null;

				if (hql.indexOf("order by") == -1) {
					orderBy = new StringBuilder(" order by ");
				} else {
					orderBy = new StringBuilder(",");
				}

				for (Sorter sorter : sorters) {
					String property = sorter.getProperty();
					String direction = sorter.getDirection();

					if (property != null && !"".equals(property) && direction != null && !"".equals(direction)) {
						orderBy.append(property).append(" ").append(direction).append(",");
					}
				}

				orderBy.deleteCharAt(orderBy.length() - 1);

				hql.append(orderBy);
			}
		}

		Query query = this.getHQLQuery(hql.toString());

		List<Map<String, Object>> result = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		return result;
	}

	@Override
	public Object uniqueResultByHQL(String hql, Object... params) {
		return this.baseHibernateDao.uniqueResultByHQL(hql, params);
	}

	@Override
	public Object uniqueResultByHQL(String hql, Map<Object, Object> params) {
		return this.baseHibernateDao.uniqueResultByHQL(hql, params);
	}

	@Override
	public Object uniqueResultBySQL(String sql, Object... params) {
		return this.baseHibernateDao.uniqueResultBySQL(sql, params);
	}

	@Override
	public Object uniqueResultBySQL(String sql, Map<Object, Object> params) {
		return this.baseHibernateDao.uniqueResultBySQL(sql, params);
	}

	/**
	 * 閼惧嘲绶辩粭锕�鎮庨弶鈥叉閻ㄥ嫬鐤勬担鎾绘肠閸氾拷 闁俺绻冩晶鐐插閺夆�叉閸欏倹鏆熺�靛湱绮ㄩ弸婊堟肠鏉╂稖顢戠粵娑拷锟� 闁俺绻冮崷鈺l娑擃叀顔曠純顔藉笓鎼村繐鐡у▓鍏哥瑢閺傜懓绱＄�靛湱绮ㄩ弸婊堟肠鏉╂稖顢戦幒鎺戠碍
	 * 
	 * @param hql
	 *            閺屻儴顕楃拠顓炲綖 eg:hql="from User u where u.sex=? order by u.name desc"
	 * @param params
	 *            閺夆�叉閸欏倹鏆� eg:params={"man"}
	 * @param firstResult
	 *            鐟曚浇骞忛崣鏍唶瑜版洟娉﹂惃鍕磻婵娼弫锟� eg:firstResult=10
	 * @param maxResult
	 *            鐟曚浇骞忛崣鏍唶瑜版洟娉﹂惃鍕付婢堆勬蒋閺侊拷 eg:maxResult=20
	 * @return 閺屻儴顕楃紒鎾寸亯闂嗭拷
	 */
	public List<?> getEntityListByHQL(String hql, Integer firstResult, Integer maxResult, Object... params) {
		Query query = getHQLQuery(hql, params);
		if (firstResult != null)
			query.setFirstResult(firstResult);
		if (maxResult != null)
			query.setMaxResults(maxResult);
		return query.list();
	}

	/**
	 * 閸掋倖鏌囬弫鐗堝祦鎼存挷鑵戦弰顖氭儊鐎涙ê婀幐鍥х暰閻ㄥ嫯銆冮敍灞肩矌閻€劋绨琒QL SERVER閺佺増宓佹惔锟�
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public boolean existsTable(String tableName) throws Exception {
		return this.baseHibernateDao.existsTable(tableName);
	}
}
