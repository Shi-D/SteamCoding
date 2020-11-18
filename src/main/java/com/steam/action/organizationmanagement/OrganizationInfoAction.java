package com.steam.action.organizationmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.system.common.base.action.BaseGridAction;
import com.steam.service.OrganizationAdminService;

public class OrganizationInfoAction extends BaseGridAction{

	private static final long serialVersionUID = -4848068792061552971L;
	
	@Autowired
	OrganizationAdminService organizationAdminService;
	
	private Integer organizationId;
	private String organizationIds;
	private String organizationName;
	private String organizationSname;
	private String adminName;
	private File file;
	
	private Integer pageNo;
	private Integer pageSize;
	private Boolean search;
	
	// 分页查询查询所有机构信息
	@SuppressWarnings("unchecked")
	public String searchAllOrganization() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql;
		hql = "select organizationId as organizationId, organizationName as organizationName, organizationSname as organizationSname,"
				+ "adminName as adminName, creationTime as creationTime from OrganizationAdminInfoView";
		organizationAdminService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}
	

	//添加机构
	public String addOrganization() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
//			organizationAdminService.addOrganization("567","5");
			organizationAdminService.addOrganization(this.getOrganizationName(),this.getOrganizationSname());
			basicInfo.put("result", "添加机构成功");
		} catch (Exception e) {
			basicInfo.put("result", "添加机构失败");
		}
		results.add(basicInfo);
		return "result>json";
	}
	
	//删除机构
	public String deleteOrganizationByOrganizationId() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
			organizationAdminService.deleteOrganization(organizationIds);
			basicInfo.put("result", "删除成功");
		} catch (Exception e) {
			basicInfo.put("result", "删除失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	//修改机构
	public String updateOrganization(){
		results = new ArrayList<Map<String, Object>>();
		results = organizationAdminService.updateOrganization(this.getOrganizationId(),this.getOrganizationName(),
				this.getOrganizationSname());
		return "result>json";
	}
	
	//获取机构Id和名字
	public String queryOrganization() {
		results = organizationAdminService.getOrganization();
		return "result>json";
	}
	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationIds() {
		return organizationIds;
	}


	public void setOrganizationIds(String organizationIds) {
		this.organizationIds = organizationIds;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationSname() {
		return organizationSname;
	}

	public void setOrganizationSname(String organizationSname) {
		this.organizationSname = organizationSname;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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
