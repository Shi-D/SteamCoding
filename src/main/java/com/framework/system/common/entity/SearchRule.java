package com.framework.system.common.entity;

public class SearchRule {
	public SearchRule() {
	}

	private String field; // 查询字段

	public SearchRule(String field, String op, String data) {
		super();
		this.field = field;
		this.op = op;
		this.data = data;
	}

	private String op; // 查询操作
	private String data; // 选择的查询值

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		if (op == null) {
			this.op = "cn";
		} else {
			this.op = op;
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 比较两个对象值是否相同
	 * 
	 * @param searchRule
	 * @return
	 */
	public boolean equals(SearchRule searchRule) {
		if (field.equals(searchRule.field) && op.equals(searchRule.op) && data.equals(searchRule.data)) {
			return true;
		}
		return false;
	}

}