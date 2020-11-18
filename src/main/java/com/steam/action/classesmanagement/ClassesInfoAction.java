package com.steam.action.classesmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.action.BaseGridAction;
import com.steam.service.ClassTeacherStudentService;
import com.steam.service.ClassesService;
import com.steam.service.CourseClassTeacherService;
import com.steam.service.CourseService;
import com.steam.studentImport.DealWithData;

public class ClassesInfoAction extends BaseGridAction {

	private static final long serialVersionUID = -5115239031440479110L;

	@Autowired
	ClassTeacherStudentService classTeacherStudentService;
	@Autowired
	UserService userService;
	@Autowired
	ClassesService classesService;
	@Autowired
	CourseService courseService;
	@Autowired
	CourseClassTeacherService courseClassTeacherService;

	private String className;
	private String classIds;
	private String studentIds;
	private String courseIds;
	private String courseName;
	private Integer classId;
	private Integer courseId;
	private Integer teacherId;
	private Integer studentId;
	private String chapterIds;
	private File file;
	
	private Integer pageNo;
	private Integer pageSize;
	private Boolean search;

	// 分页查询查询所有班级信息
	@SuppressWarnings("unchecked")
	public String searchAllInfoClasses() {
		User user = SystemContext.getCurrentUser();
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql;
		if(user.getUserRole() == SteamConstants.ROLE_ADMIN )
			hql = "select classId as classId, className as className, creationTime as creationTime, studentNumber as studentNumber, teacherName as teacherName from ClassTeacherStudentNumberView where organizationId = '" + user.getOrganization().getId() + "'";
		else
			hql = "select classId as classId, className as className, creationTime as creationTime, studentNumber as studentNumber from ClassTeacherStudentNumberView where teacherCode = '" + user.getUsername() + "'";
		classTeacherStudentService.find(this.getPager(), hql, this.getSorter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}

	// 根据班级名字查看班级
	@SuppressWarnings("unchecked")
	public String queryAllInfoClassesByClassName() {
		User user = SystemContext.getCurrentUser();
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql;
		if(user.getUserRole() == SteamConstants.ROLE_ADMIN )
			hql = "select classId as classId, className as className, creationTime as creationTime, studentNumber as studentNumber, teacherName as teacherName from ClassTeacherStudentNumberView where organizationId = '" + user.getOrganization().getId()
			+ "' and className like '%" + this.getClassName() + "%" + "' or teacherName like '%" + this.getClassName() + "%'";//此处减少Action的使用使用getClassName()代替getTeacherName();
		else
			hql = "select classId as classId, className as className, creationTime as creationTime, studentNumber as studentNumber from ClassTeacherStudentNumberView where teacherCode = '" + user.getUsername()
				+ "' and className like '%" + this.getClassName() + "%'";
		classTeacherStudentService.find(this.getPager(), hql, this.getSorter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}

	// 添加班级
	public String addClass() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		Integer newClassId = 0;
		try {
			newClassId = classesService.addClass(this.getClassName(), user.getOrganization().getId(), user.getId());
			basicInfo.put("result", "添加班级成功");
		} catch (Exception e) {
			basicInfo.put("result", "添加班级失败");
			results.add(basicInfo);
			return "result>json";
		}
		if(this.getFile()!=null){
			List<Map<String, String>> datas = DealWithData.getDatas(this.getFile());
			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			if (CollectionUtils.isEmpty(datas)) {
				basicInfo.put("result", "上传的文件数据为空！");
				results.add(basicInfo);
				return "result>json";
			}
			// 保存添加成功的学生ID，绑定班级时使用
			String studentIds = "";
			// 保存已存在的学生的登录账号
			String studentExistLoginAccount = "";

			// 判断是否有非法账号
			for (Map<String, String> map : datas) {
				if (map.get("学生账号").indexOf(" ") != -1) {
					studentExistLoginAccount = map.get("学生账号") + ",";
				}
			}
			if (studentExistLoginAccount.length() > 0) {
				basicInfo.put("result", studentExistLoginAccount);
				results.add(basicInfo);
				return "result>json";
			}

			// 账号检查合法以后进行操作
			for (Map<String, String> map : datas) {
				if (!userService.isExist(map.get("学生账号"))) { // 如果学生不存在
					try {
						// 如果账号不存在，则添加为新用户
						studentIds += userService.addUser(map.get("学生名"), map.get("学生账号"), map.get("性别"), SteamConstants.ROLE_STUDENT, user.getOrganization().getId())+",";
					} catch (Exception e) {		
						basicInfo.put("result", "添加学生账号失败！");
						results.add(basicInfo);
						return "result>json";
					}
				} else {
					// 如果账号已经存在，则记录该账号并返回给前端
					studentExistLoginAccount += "["+map.get("学生名").toString()+","+map.get("学生账号").toString() + "]、";
				}
			}

			studentIds = studentIds.equals("") ? "" : studentIds.substring(0, studentIds.length() - 1);
			studentExistLoginAccount = studentExistLoginAccount.equals("") ? "" : studentExistLoginAccount.substring(0, studentExistLoginAccount.length() - 1);

			// 绑定学生与班级的关系,如果所有账户均已创建则不执行绑定操作
			if(!studentIds.equals(""))
			    classesService.addStudentsToClass(newClassId, studentIds.split(","));
			if (studentExistLoginAccount.equals("") && newClassId != 0) {
				basicInfo.put("result", "添加班级并绑定学生成功！");
			} else {
				basicInfo.put("result", "已存在的账号为：" + studentExistLoginAccount);
			}
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String deleteClassesByClassIds() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
			classesService.deleteClassesInfo(classIds);
			basicInfo.put("result", "添加成功");
		} catch (Exception e) {
			basicInfo.put("result", "添加失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String queryStudentNotInClass() {
		User user = SystemContext.getCurrentUser();
		results = classTeacherStudentService.queryStudentNotInClass(user.getOrganization().getId(), this.getClassId());
		System.out.println(results);
		return "result>json";
	}

	public String queryStudentInClass() {
		User user = SystemContext.getCurrentUser();
		results = classTeacherStudentService.queryStudentInClass(user.getOrganization().getId(), this.getClassId());
		System.out.println(results);
		return "result>json";
	}

	public String deleteStudentInClass() {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
			classTeacherStudentService.deleteStudents(studentIds.split(","), classId);
			basicInfo.put("result", "删除完成");
		} catch (Exception e) {
			basicInfo.put("result", "删除失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	// 添加多学生到单个班级
	public String addStudentsToClass() throws Exception {

		Map<String, Object> basicInfo = new HashMap<String, Object>();
		results = new ArrayList<Map<String, Object>>();
		try {
			classesService.addStudentsToClass(classId, studentIds.split(","));
			basicInfo.put("result", "插入成功");
		} catch (Exception e) {
			basicInfo.put("result", "插入失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String queryCoursesByClassId() {
		User user = SystemContext.getCurrentUser();
		results = courseClassTeacherService.queryCourseInClass(user.getUsername(), classId);
		return "result>json";
	}

	public String queryCourseNotInClass() {
		User user = SystemContext.getCurrentUser();
		results = courseClassTeacherService.queryCourseNotInClass(user.getUsername(), this.getClassId());
		return "result>json";
	}
	
	public String queryCourseInClass() {
		User user = SystemContext.getCurrentUser();
		results = courseClassTeacherService.queryCourseInClass(user.getUsername(), this.getClassId());
		return "result>json";
	}

	// 班级添加多个课程
	public String addCoursesToClass() {
		try {
			System.out.println(courseIds);
			classesService.addCoursesToClass(classId, courseIds.split(","));
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("插入失败");
		}
		return "result>json";
	}
	//此处应该使用ID查询
	public String queryChapterInCourse() {
		results = courseService.queryChapterInCourse(this.getClassId(),this.getCourseId());
		System.out.println(results);
		return "result>json";
	}
	//更新课程进度
	public String updateChapterSchedule(){
		results=courseService.updateChapterSchedule(this.getClassId(),this.getChapterIds());
		return "result>json";
	}
	
	//移除课程
	public String deleteCoursesInClass(){
		results = classesService.deleteCoursesInClassService(this.getClassId(),this.getCourseIds());
		return "result>json";
	}
	
	
	public String getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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

	public String getClassIds() {
		return classIds;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
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
