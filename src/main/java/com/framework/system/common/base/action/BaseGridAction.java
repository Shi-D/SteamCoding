package com.framework.system.common.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;

import com.framework.authority.entity.User;
import com.framework.common.GsonUtil;
import com.framework.system.common.entity.FilterSearch;
import com.framework.system.common.entity.Pager;
import com.framework.system.common.entity.SearchRule;
import com.framework.system.common.entity.Sorter;
import com.google.gson.reflect.TypeToken;

public class BaseGridAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	// 用于导出Excel时使用，列标题名称
	protected String columnNames;

	// 用于导出Excel时使用，列对应实体属性名称
	protected String propertyNames;

	private Pager pager = new Pager();

	private String filters;

	private String sorters;

	protected FilterSearch getFilter() {
		/* 如果此次请求要执行条件查询，则准备查询参数 */
		FilterSearch filter = null;
		JSONObject jsonObject = JSONObject.fromObject(filters);
		Map<String, Class<?>> objectClass = new HashMap<String, Class<?>>();

		if (this.pager == null) {
			return filter;
		}
		if (filters != null && !"".equals(filters)) {
			objectClass.put("rules", SearchRule.class);
			filter = (FilterSearch) JSONObject.toBean(jsonObject, FilterSearch.class, objectClass);
		}
		if (sorters != null && !"".equals(sorters)) {
			List<Sorter> sorterList = GsonUtil.gson.fromJson(sorters, new TypeToken<List<Sorter>>() {
			}.getType());
			filter.setSorters(sorterList);
		}
		return filter;
	}

	protected FilterSearch getSorter() {
		System.out.println("base sorters is" + sorters);
		/* 如果此次请求要执行条件查询，则准备查询参数 */
		FilterSearch filter = new FilterSearch();

		if (this.pager == null) {
			return filter;
		}

		if (sorters != null && !"".equals(sorters)) {
			List<Sorter> sorterList = GsonUtil.gson.fromJson(sorters, new TypeToken<List<Sorter>>() {
			}.getType());
			System.out.println("sorterList is " + sorterList);
			filter.setSorters(sorterList);
		}

		return filter;

	}

	/**
	 * 组装查询参数
	 * 
	 * @param field
	 *            机构id查询条件名称
	 * @return
	 */
	protected FilterSearch getFilter(String field) {
		FilterSearch filter = this.getFilter();
		if (filter != null) {
			// 如果不是超级管理员
			if (!isSuperAdmin()) {
				boolean blank = true;
				List<SearchRule> rules = filter.getRules();
				for (SearchRule rule : rules) {
					if (rule.getField().equals("organization.id") || rule.getField().equals("organizationId")) {
						User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						rule.setData(String.valueOf(principal.getOrganization().getId()));
						blank = false;
					}
				}

				if (blank) {
					User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					SearchRule rule = new SearchRule(field, "eq", String.valueOf(principal.getOrganization().getId()));
					filter.addRule(rule);
				}
			}
		}

		return filter;
	}

	private boolean isSuperAdmin() {
		return false;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(String propertyNames) {
		this.propertyNames = propertyNames;
	}

	public String getSorters() {
		return sorters;
	}

	public void setSorters(String sorters) {
		this.sorters = sorters;
	}

}
