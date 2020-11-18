package com.steam.action.studentmanagement;

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
import com.steam.entityview.ClassTeacherStudent;
import com.steam.entityview.StudentInfo;
import com.steam.service.ClassTeacherStudentService;
import com.steam.studentImport.DealWithData;

public class StudentInfoAction extends BaseGridAction {

	private static final long serialVersionUID = -98754734909313130L;

	@Autowired
	ClassTeacherStudentService classTeacherStudentService;
	@Autowired
	UserService userService;

	private String className;
	private String studentName;
	private Integer pageNo;
	private Integer pageSize;
	private boolean search;
	private String Ids;
	private String studentCode;
	private String studentGender;
	private File file;

	public String queryAllClasses() {//用于获得该机构下所有班级
		User user = SystemContext.getCurrentUser();
		results = classTeacherStudentService.queryAllClasses(user.getOrganization().getId());
		return "result>json";
	}

	@SuppressWarnings("unchecked")
	public String queryStudentsByStudentName() {//查询班级下的所有学生
		User user = SystemContext.getCurrentUser();
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		String hql;
			
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		if(null!=getClassName() && getClassName().equals("未分配")){
			hql = "select studentId as studentId, studentName as studentName, studentCode as studentCode, studentGender as studentGender, className as className, studentCreationTime as studentCreationTime from StudentInfo where organizationId = "
					+ user.getOrganization().getId()+" and className is null ";
		}
		else
			hql = "select studentId as studentId, studentName as studentName, studentCode as studentCode, studentGender as studentGender, className as className, studentCreationTime as studentCreationTime from StudentInfo where organizationId = "
					+ user.getOrganization().getId();
		System.out.println("*********************************" + this.getFilters());
		classTeacherStudentService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}

	public String addStudent() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		Integer userRole = 3;//学生角色为3
		try {
			userService.addUser(studentName,studentCode,studentGender,userRole , user.getOrganization().getId());
			basicInfo.put("result","添加成功");
		} catch (Exception e) {
			basicInfo.put("result","添加失败");
		}
		results.add(basicInfo);
		return "result>json";
	}
	public String deleteStudent(){
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if(user.getUserRole()==1||user.getUserRole()==2)
			try{
				userService.deleteBatchUser(this.getIds());
				basicInfo.put("result","删除成功");
			}catch(Exception e){
				basicInfo.put("result","删除失败");
			}
		results.add(basicInfo);
		return "result>json";
	}

	
	public void exportExcel() {
		String hql;
		List<Map<String, Object>> result = null;
		System.out.println("propertyNames:" + propertyNames);
		System.out.println("columnNames:" + columnNames);
		User user = SystemContext.getCurrentUser();
		DefaultDBCache.init();
		if (this.getIds() == null)   //前台未勾选用户
			if(null!=this.getClassName() && this.getClassName().equals("未分配"))   //导出所有未分配班级的学生
				hql = ExportExcelUtil.createHQL(StudentInfo.class.getName(), propertyNames.split(",")) 
				+ " where organizationId = "	+ user.getOrganization().getId()+" and className is null "; 
			else	//如果前台未传递className,那么返回该机构下所有学生
				hql = ExportExcelUtil.createHQL(StudentInfo.class.getName(), propertyNames.split(",")) 
				+ " where organizationId = "	+ user.getOrganization().getId();
		else   //导出前台勾选的用户
			hql = ExportExcelUtil.createHQL(StudentInfo.class.getName(), propertyNames.split(",")) 
			+ " where organizationId = "	+ user.getOrganization().getId() + " and  studentId in " + this.getIds();	
		result = classTeacherStudentService.find(hql, this.getFilter());
		ColumnModelCacheImpl columnModelCache = (ColumnModelCacheImpl) DefaultDBCache.getCacheStore(ColumnModelCacheImpl.CACHE_NAME);
		columnModelCache.init();
		List<ColumnModel> cmList = columnModelCache.getColumnModelList(ClassTeacherStudent.TABLE_CODE, propertyNames.split(","));
		ExportExcelUtil.exportExcel(result, cmList, "学生名单", columnNames.split(","));
	}
	
	public void downloadTemplate(){
		ExportExcelUtil.exportTemplate("学生名单导入模板", columnNames.split(","));
	}
	
	public String importExcelForAddStudents() {
		User user = SystemContext.getCurrentUser();
		results = userService.userImport(DealWithData.getDatas(file), user.getOrganization().getId(),3);	//传入参数3 代表导入学生	
		return "result>json";
	}

	public void resetPWD() {
		System.out.println(this.getIds());
		userService.initPassword(this.getIds());
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}

	public String getIds() {
		return Ids;
	}

	public void setIds(String ids) {
		Ids = ids;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
