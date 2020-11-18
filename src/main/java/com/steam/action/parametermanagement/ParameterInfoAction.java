package com.steam.action.parametermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.system.common.base.action.BaseGridAction;
import com.steam.service.ParameterService;

public class ParameterInfoAction extends BaseGridAction{

	private static final long serialVersionUID = -4271498415048444619L;
	
	@Autowired
	ParameterService parameterService;
	
	private Integer parameterId;
	private String parameterIds;
	private String parameterComment;
	private String parameterValue;
	private String parameterName;
	private Integer parameterSequence;
	private Integer parameterSerialNo;
	private Integer parameterTypeCode;
	private String parameterTypeName;
	private String parameterUrl;

	private Integer pageNo;
	private Integer pageSize;
	private Boolean search;
	
	//添加参数
	public String addParameter(){
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String,Object>>();
		try{
			parameterService.addParameterService(this.getParameterComment(), this.getParameterValue(), 
					this.getParameterName(), this.getParameterSequence(), this.getParameterSerialNo(), 
					this.getParameterTypeCode(), this.getParameterTypeName(), this.getParameterUrl());
			basicInfo.put("result", "添加成功");
		} catch (Exception e){
			basicInfo.put("result", "添加失败");
		}
		results.add(basicInfo);
		return "result>json";
	}
	
	//删除参数
	public String deleteParameters() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
			parameterService.deleteParametersService(this.getParameterIds());
			basicInfo.put("result", "删除成功");
		} catch (Exception e) {
			basicInfo.put("result", "删除失败");
		}
		results.add(basicInfo);
		return "result>json";
	}
	
	//修改参数
	public String updateParameter(){
		results = new ArrayList<Map<String, Object>>();
		results = parameterService.updateParameterService(this.getParameterId(),this.getParameterComment(),
				this.getParameterValue(), this.getParameterName(), this.getParameterSequence(), 
				this.getParameterSerialNo(), this.getParameterTypeCode(), this.getParameterTypeName(), 
				this.getParameterUrl());
		return "result>json";
	}
	
	//查询参数
	@SuppressWarnings("unchecked")
	public String queryParameter() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		String hql;
		hql = "select id as parameterId, parameterName as parameterName, parameterValue as parameterValue,"
				+ "parameterComment as parameterComment, parameterSequence as parameterSequence,"
				+ "parameterSerialNo as parameterSerialNo, parameterTypeCode as parameterTypeCode,"
				+ "parameterTypeName as parameterTypeName, parameterUrl as parameterUrl from Parameter";
		parameterService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}
	/*
	public String queryParameter(){
		results = new ArrayList<Map<String, Object>>();
		results = parameterService.queryParameterService();
		return "result>json";
	}
	*/
	public Integer getParameterId() {
		return parameterId;
	}
	
	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}
	
	public String getParameterIds() {
		return parameterIds;
	}

	public void setParameterIds(String parameterIds) {
		this.parameterIds = parameterIds;
	}

	public String getParameterComment() {
		return parameterComment;
	}
	
	public void setParameterComment(String parameterComment) {
		this.parameterComment = parameterComment;
	}
	
	public String getParameterValue() {
		return parameterValue;
	}
	
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	public Integer getParameterSequence() {
		return parameterSequence;
	}
	
	public void setParameterSequence(Integer parameterSequence) {
		this.parameterSequence = parameterSequence;
	}
	
	public Integer getParameterSerialNo() {
		return parameterSerialNo;
	}
	
	public void setParameterSerialNo(Integer parameterSerialNo) {
		this.parameterSerialNo = parameterSerialNo;
	}
	
	public Integer getParameterTypeCode() {
		return parameterTypeCode;
	}
	
	public void setParameterTypeCode(Integer parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}
	
	public String getParameterTypeName() {
		return parameterTypeName;
	}
	
	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}
	
	public String getParameterUrl() {
		return parameterUrl;
	}
	
	public void setParameterUrl(String parameterUrl) {
		this.parameterUrl = parameterUrl;
	}
	

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}