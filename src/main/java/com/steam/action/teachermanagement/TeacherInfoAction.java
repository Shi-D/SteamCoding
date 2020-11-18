package com.steam.action.teachermanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.cache.DefaultDBCache;
import com.framework.cache.impl.ColumnModelCacheImpl;
import com.framework.common.ExportExcelUtil;
import com.framework.common.SystemContext;
import com.framework.excel.entity.ColumnModel;
import com.framework.system.common.base.action.BaseGridAction;
import com.steam.entityview.TeacherInfo;
import com.steam.studentImport.DealWithData;

public class TeacherInfoAction extends BaseGridAction{
	
	private static final long serialVersionUID = 7322889466890967125L;
	
	@Autowired
	UserService userService;

	private String teacherName;
	private String teacherCode;
	private String teacherGender;
	private String ids;
	private File file;
	
	private Integer pageNo;
	private Integer pageSize;
	private boolean search;
	
	//查询老师
	@SuppressWarnings("unchecked")
	public String queryTeachersByTeacherName() {//查询老师   
		User user = SystemContext.getCurrentUser();
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "select teacherId as teacherId, teacherName as teacherName, teacherCode as teacherCode, teacherGender as teacherGender, teacherCreationTime as teacherCreationTime, className as className from TeacherInfo where organizationId = "+user.getOrganization().getId();
		System.out.println("*****************hql is"+ hql);
		userService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}
	
	//增加单个老师
	public String addTeacher() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		Integer userRole = 2;//教师角色为2
		try {
			userService.addUser(this.getTeacherName(),this.getTeacherCode(),this.getTeacherGender(),userRole, user.getOrganization().getId());
			basicInfo.put("result","添加成功");
		} catch (Exception e) {
			basicInfo.put("result","添加失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	//删除老师
	public String deleteTeacher(){
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if(user.getUserRole()==1)
			try{
				userService.deleteBatchUser(this.getIds());
				basicInfo.put("result","删除成功");
			}catch(Exception e){
				basicInfo.put("result","删除失败");
			}
		results.add(basicInfo);
		return "result>json";
	}
		
	//批量导出老师
	public void exportExcel() {
		String hql;
		List<Map<String, Object>> result = null;
		System.out.println("propertyNames:" + propertyNames);
		System.out.println("columnNames:" + columnNames);
		User user = SystemContext.getCurrentUser();
		DefaultDBCache.init();
		if (this.getIds() == null)   //前台未勾选用户
			hql = ExportExcelUtil.createHQL(TeacherInfo.class.getName(), propertyNames.split(",")) 
				+ " where organizationId = "+ user.getOrganization().getId();
		else   //导出前台勾选的用户
			hql = ExportExcelUtil.createHQL(TeacherInfo.class.getName(), propertyNames.split(",")) 
			+ " where organizationId = "	+ user.getOrganization().getId() + " and  teacherId in " + this.getIds();	
		result = userService.find(hql, this.getFilter());
		ColumnModelCacheImpl columnModelCache = (ColumnModelCacheImpl) DefaultDBCache.getCacheStore(ColumnModelCacheImpl.CACHE_NAME);
		columnModelCache.init();
		List<ColumnModel> cmList = columnModelCache.getColumnModelList(TeacherInfo.TABLE_CODE, propertyNames.split(","));
		ExportExcelUtil.exportExcel(result, cmList, "教师名单", columnNames.split(","));
	}
	
	public void downloadTemplate(){
		ExportExcelUtil.exportTemplate("教师名单导入模板", columnNames.split(","));
	}
	
	//批量导入老师
	public String importExcelForAddTeachers() {
		User user = SystemContext.getCurrentUser();
		results = userService.userImport(DealWithData.getDatas(this.getFile()), user.getOrganization().getId(),2);	//传入参数2代表导入教师	
		return "result>json";
	}

	//重置密码
	
	public void resetPWD() {
		userService.initPassword(this.getIds());
	}
	
	
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(String teacherGender) {
		this.teacherGender = teacherGender;
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

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}
	
	
}
