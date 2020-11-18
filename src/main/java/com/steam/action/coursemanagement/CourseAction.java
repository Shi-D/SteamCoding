package com.steam.action.coursemanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMethod;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.action.BaseGridAction;
import com.framework.utils.FILEUtils;
import com.framework.utils.URLUtils;
import com.steam.service.ChapterService;
import com.steam.service.CourseClassStudentService;
import com.steam.service.CourseService;
import com.steam.service.VideoService;

public class CourseAction extends BaseGridAction {

	private static final long serialVersionUID = -5635745875398125531L;

	@Autowired
	CourseService courseService;
	@Autowired
	ChapterService chapterService;
	@Autowired
	CourseClassStudentService courseClassStudentService;
	@Autowired
	VideoService videoService;

	private File uploadFile;
	private String courseName;
	private String courseIntroduction;
	private Integer courseId;
	private Integer chapterId;
	private String chapterName;
	private String chapterContent;
	private String courseIds;
	private String videoName;
	private String videoTitle;
	private String teacherName;
	private List<Integer> videoIds;

	private Integer pageNo = 1;
	private Integer pageSize = 20;
	private Boolean search = true;

	private Map<String, Object> basicInfo = new HashMap<String, Object>();// 用于返回成功失败信息

/*	 根据学生ID查询所有课程 
	@SuppressWarnings("unchecked")
	public String getAllCoursesByStundetId() {
		User user = SystemContext.getCurrentUser();
		String hql;
		if (null != user)
			hql = "select courseId as courseId, courseName as courseName, classId as classId,className as className, courseCover as courseCover,courseIntroduction as courseIntroduction ,courseFolderName as courseFolderName from CourseClassStudent where studentId = "
					+ user.getId();
		else
			return "result>json";
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		 根据学生的id 去查询学生所在的班级,根据班级去查询班级下所有的课程 
		courseClassStudentService.find(this.getPager(), hql, this.getSorter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}*/

	// 分页查询所有课程信息
	@SuppressWarnings("unchecked")
	public String searchAllInfoCourses() {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "";
		if (user.getUserRole() == 1||user.getUserRole() == 2)
			hql = "select courseId as courseId, courseName as courseName, teacherName as teacherName, teacherId as teacherId, classSize as classSize, courseCreateTime as courseCreateTime, isPublished as isPublished from TeacherCourse where organizationId = '"
					+ user.getOrganization().getId() + "'";
		else if (user.getUserRole() == 3)
			hql = "select courseId as courseId, courseName as courseName, classId as classId,className as className, courseCover as courseCover,courseIntroduction as courseIntroduction ,courseFolderName as courseFolderName from CourseClassStudent where studentId = '"
					+ user.getId() + "'";
		/*else
			return "result>json";*/
		courseService.find(this.getPager(), hql, this.getSorter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}

	// 根据课程名搜索课程teacher
	// 根据课程名或教师名字搜索课程admin
	@SuppressWarnings("unchecked")
	public String queryAllInfoCoursesByCourseName() {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql="";
		if (user.getUserRole() == 1)
			hql = "select courseId as courseId, courseName as courseName, teacherName as teacherName, teacherId as teacherId, classSize as classSize, courseCreateTime as courseCreateTime from TeacherCourse where organizationId = '"
					+ user.getOrganization().getId() + "' and courseName like '%" + this.getCourseName() + "%" + "' or teacherName like '%" + this.getCourseName() + "%'";// 此处减少Action的使用使用getCourseName代替getTeacherName();
		else if(user.getUserRole() == 2)
			hql = "select courseId as courseId, courseName as courseName, teacherName as teacherName, teacherId as teacherId, classSize as classSize, courseCreateTime as courseCreateTime from TeacherCourse where organizationId = '"
					+ user.getOrganization().getId() + "' and courseName like '%" + this.getCourseName() + "%'";
		else if (user.getUserRole() == 3)
			hql = "select courseId as courseId, courseName as courseName, classId as classId,className as className, courseCover as courseCover,courseIntroduction as courseIntroduction ,courseFolderName as courseFolderName from CourseClassStudent where studentId = '"
					+ user.getId() + "' and courseName like '%" + this.getCourseName() + "%'";
		courseService.find(this.getPager(), hql, this.getSorter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		// if (!results.isEmpty())
		results.add(basicInfo);
		return "result>json";
	}

	public String getCourseInfo() {
		results = courseService.getCourseInfo(this.getCourseId());
		return "result>json";
	}

	// 更新课程
	public String updateCourseInfo() {
		User user = SystemContext.getCurrentUser();
		File file = new File(URLUtils.generateURLforCourse(user.getUsername()));
		results = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(this.getUploadFile());
			if (null != this.getUploadFile()) {
				courseService.updateCourse(this.getCourseId(), this.getCourseName(), this.getUploadFile().getName(), this.getCourseIntroduction(), user);
				FileUtils.copyFile(uploadFile, new File(file, this.getUploadFile().getName()));
			} else {
				courseService.updateCourse(this.getCourseId(), this.getCourseName(), this.getCourseIntroduction());
			}
			basicInfo.put("result", "修改成功");
		} catch (Exception e) {
			basicInfo.put("result", "修改失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	// 公开课程
	public String publishCourse() {
		results = new ArrayList<Map<String, Object>>();
		try {
			courseService.recommendOrRecallCourse(this.getCourseIds(), 1);// 参数传1代表推荐
			basicInfo.put("result", "添加精品成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "添加精品失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	// 取消公开课程
	public String recallCourse() {
		results = new ArrayList<Map<String, Object>>();
		try {
			courseService.recommendOrRecallCourse(this.getCourseIds(), 0);// 参数传0代表取消推荐
			basicInfo.put("result", "撤回成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "撤回失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	// 上传课程
	public String uploadCourse() {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		File file = new File(URLUtils.generateURLforCourse(user.getUsername()));
		// 测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		if (!file.exists())
			file.mkdirs();
		try {
			String fileName = "defaultCourseCover.jpg";
			if(uploadFile != null){
				fileName = this.getUploadFile().getName();
				FileUtils.copyFile(uploadFile, new File(file, fileName));
			}else{
				File courseCover = new File(SteamConstants.ABSOLUTE_URL+"default/defaultCourseCover.jpg");
				FileUtils.copyFile(courseCover, new File(file, fileName));
			}
			courseService.uploadCourse(this.getCourseName(), fileName, this.getCourseIntroduction(), user);
			basicInfo.put("result", "添加成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "添加失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	/* 删除课程 */
	public String deleteCourse() {
		results = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> deleteFile = new ArrayList<Map<String, Object>>();
		try {
			deleteFile = courseService.deleteCourse(this.getCourseIds());
			for (Map<String, Object> map : deleteFile) {
				FILEUtils.deleteFile(SteamConstants.ABSOLUTE_URL + map.get("courseFolderName") + map.get("courseCover"));
			}
			basicInfo.put("result", "操作成功");
		} catch (Exception e) {
			basicInfo.put("result", "操作异常");
			System.out.println(e);
		}
		return "result>json";
	}

	public String queryChapterInCourse() {
		User user = SystemContext.getCurrentUser();
		if(user.getUserRole()==1||user.getUserRole()==2)
			results = chapterService.queryChapterInCourse(this.getCourseId());
		return "result>json";
	}

	/* 新建章节 */
	public String addNewChapter() {
		results = new ArrayList<Map<String, Object>>();
		try {
			chapterService.addNewChapter(this.getCourseId());
			basicInfo.put("result", "操作成功");
		} catch (Exception e) {
			basicInfo.put("result", "操作异常");
			System.out.println(e);
		}
		results.add(basicInfo);
		return "result>json";
	}

	/* 删除章节 */
	public String deleteChapterByChapterId() {
		results = new ArrayList<Map<String, Object>>();
		try {
			chapterService.deleteChapter(chapterId);
			basicInfo.put("result", "删除完成");
		} catch (Exception e) {
			basicInfo.put("result", "删除失败");
		}
		return "result>json";
	}

	public String updateChapterForCourse() {
		results = new ArrayList<Map<String, Object>>();
		try {
			chapterService.updateChapterForCourse(this.getCourseId(), this.getChapterId(), this.getChapterName(), this.getChapterContent());
			basicInfo.put("result", "操作成功");
		} catch (Exception e) {
			basicInfo.put("result", "操作异常");
		}
		results.add(basicInfo);
		return "result>json";
	}
	
	//private final int BYTES_PER_SLICE = 1<<20;
	//上传视频到视频库
	public String uploadVideoInStore() {
		//User user = SystemContext.getCurrentUser();
		try{
		//RandomAccessFile a;
			//MultipartFileParam b;
			
			
		//	MappedByteBuffer c;
		}catch(Exception e){
			
		}
		
		
		
		return "result>json";
		/*User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		try {
			if(FILEUtils.saveUploadFile(this.getUploadFile(),URLUtils.generateURLforVideoStore(user.getUsername()),this.getVideoName())){
				videoService.uploadVideoInStore(this.getVideoTitle(), URLUtils.generateURLforUpdateToDB(user.getUsername(),this.getVideoName()), user.getId());
				System.out.println("*****fileupload.....");
				basicInfo.put("result", "添加成功");
			}else{
				basicInfo.put("result", "添加失败");
			}
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "添加失败");
		}
		results.add(basicInfo);
		return "result>json";*/
	}

	public String queryVideoLink() {
		User user = SystemContext.getCurrentUser();
		results = videoService.queryVideo(user.getOrganization().getId());
		return "result>json";
	}
	
	//返回所有视频信息给前端
	public String queryAllVideo(){
		results = videoService.queryAllVideoService();
		return "result>json";
	}
	//删除视频
	public String deleteVideo(){
		results = videoService.deleteVideoService(this.getVideoIds());
		return "result>json";
	}
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getChapterContent() {
		return chapterContent;
	}

	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseIntroduction() {
		return courseIntroduction;
	}

	public void setCourseIntroduction(String courseIntroduction) {
		this.courseIntroduction = courseIntroduction;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
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

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public List<Integer> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<Integer> videoIds) {
		this.videoIds = videoIds;
	}
	
}
