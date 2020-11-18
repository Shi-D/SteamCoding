package com.steam.action.adminmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.common.SystemContext;
import com.framework.system.common.base.action.BaseGridAction;
import com.framework.system.entity.Organization;

public class AdminInfoAction extends BaseGridAction{

	private static final long serialVersionUID = -9152285321459179638L;
	
	@Autowired
	UserService userService;

	private Integer id;
	private Integer organizationId;
	private String adminName;
	private String adminCode;
	private String adminGender;
	private String ids;
	private File file;
	
	private Integer pageNo;
	private Integer pageSize;
	private boolean search;
	
	//查询管理员
	@SuppressWarnings("unchecked")
	public String queryAdminByAdminName() {//查询管理员
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "select adminId as userId, adminName as userName, adminCode as userCode, adminGender as userGender, adminCreationTime as creationTime ,"
				+ "organizationName as organizationName, organizationId as organizationId from AdminInfoView";
		userService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}
	
	//查询老师
	@SuppressWarnings("unchecked")
	public String queryTeachersByTeacherName() {//查询老师   
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		
		String hql = "select a.teacherId as userId, a.teacherName as userName, a.teacherCode as userCode, a.teacherGender as userGender, a.teacherCreationTime as creationTime, b.organizationName as organizationName"
				+ " from TeacherInfo a, Organization b where a.organizationId = b.id";
		userService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}
	
	//查询学生
	@SuppressWarnings("unchecked")
	public String queryStudentsByStudentName() {//查询学生
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "select a.studentId as userId, a.studentName as userName, a.studentCode as userCode, a.studentGender as userGender, a.studentCreationTime as creationTime, b.organizationName as organizationName"
				+ " from StudentInfo a, Organization b where a.organizationId = b.id";
		userService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}
	
	//增加管理员并分配机构
	public String addAdminToOrganization() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		Integer userRole = 1;//管理员角色为1
		try {
			userService.addUser(this.getAdminName(),this.getAdminCode(),this.getAdminGender(),userRole, this.getOrganizationId());
			basicInfo.put("result","添加成功");
		} catch (Exception e) {
			basicInfo.put("result","添加失败");
		}
		results.add(basicInfo);
		
		
		return "result>json";
	}

	//删除管理员
	public String deleteAdmin(){
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if(user.getUserRole()==0)
			try{
				userService.deleteBatchUser(this.getIds());
				basicInfo.put("result","删除成功");
			}catch(Exception e){
				basicInfo.put("result","删除失败");
			}
		results.add(basicInfo);
		return "result>json";
	}
	
	//修改管理员
	public String updateAdmin(){
		results = new ArrayList<Map<String, Object>>();
		results = userService.updateAdmin(this.getId(), this.getAdminName(), this.getAdminGender(), this.getOrganizationId());
		return "result>json";
	}
	
	//重置密码
	public String resetPWD() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try{
			userService.initPassword(this.getIds());
			basicInfo.put("result","重置成功");
		}catch(Exception e){
			basicInfo.put("result","重置失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

	public String getAdminGender() {
		return adminGender;
	}

	public void setAdminGender(String adminGender) {
		this.adminGender = adminGender;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	
}
