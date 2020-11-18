package com.framework.system.common.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class FilterSearch implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	private String groupOp; // 多字段查询时分组类型，主要是AND或者OR
	private List<SearchRule> rules; // 多字段查询时候，查询条件的集合

	private String seachElemsJSONValue = null;// 搜索栏 里面的html
	private Boolean exactSeach = false;
	private String orderBy;// 根据哪个字段排序
	private String sortord;// 升序还是降序
	private String selectMode;
	private Integer pageNum;
	private Integer pageSize;
	private List<Sorter> sorters;

	/**
	 * 判断查询条件是否相等 只要查询出的记录相同就是相等 rules groupOp
	 * 
	 * @param filterSearch
	 * @return
	 */
	public boolean equals(FilterSearch filterSearch) {
		if (filterSearch.getGroupOp() != null && !filterSearch.getGroupOp().equals(groupOp))
			return false;
		if (rules != null && filterSearch.getRules() != null) {
			if (rules.size() != filterSearch.getRules().size()) {
				return false;
			}
			for (SearchRule r : rules) {
				boolean hasSame = false;
				for (SearchRule ro : filterSearch.getRules()) {
					if (r.equals(ro)) {
						hasSame = true;
						break;
					}
				}
				if (hasSame == false) {
					return false;
				}
			}
		} else if (rules != filterSearch.getRules()) {
			if ((rules != null && rules.size() > 0) || (filterSearch.getRules() != null && filterSearch.getRules().size() > 0))
				return false;
		}
		return true;
	}

	/**
	 * 克隆
	 */
	public FilterSearch clone() {
		FilterSearch filterSearch = null;
		try {
			filterSearch = (FilterSearch) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return filterSearch;
	}

	/**
	 * 添加查询条件
	 * 
	 * @param rule
	 */
	public void addRule(SearchRule rule) {
		if (rule != null) {
			this.rules.add(rule);
		}
	}

	/**
	 * 根据查询条件中字段名称获取对应的数据
	 * 
	 * @param field
	 *            查询字段名称
	 * @return
	 */
	public Object getDataByField(String field) {
		for (SearchRule sr : rules) {
			if (sr.getField().equals(field)) {
				return sr.getData();
			}
		}
		return null;
	}

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<SearchRule> getRules() {
		return rules;
	}

	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortord() {
		return sortord;
	}

	public void setSortord(String sortord) {
		this.sortord = sortord;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setSeachElemsJSONValue(String seachElemsJSONValue) {
		this.seachElemsJSONValue = seachElemsJSONValue;
	}

	public String getSeachElemsJSONValue() {
		return seachElemsJSONValue;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSeachElemIdValueMap() {
		if (this.seachElemsJSONValue == null)
			return null;
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(this.seachElemsJSONValue), Map.class);
		return map;
	}

	public void setExactSeach(Boolean exactSeach) {
		this.exactSeach = exactSeach;
	}

	public Boolean getExactSeach() {
		return exactSeach;
	}

	public List<Sorter> getSorters()
	{
		return sorters;
	}

	public void setSorters(List<Sorter> sorters)
	{
		this.sorters = sorters;
	}

}
