package com.steam.action.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.system.common.base.action.BaseGridAction;
import com.framework.system.entity.Organization;
import com.framework.system.service.organization.OrganizationService;
import com.steam.entity.Chapter;
import com.steam.entity.Course;
import com.steam.service.ChapterService;
import com.steam.service.CourseService;

public class CourseLearnAction extends BaseGridAction {

	private static final long serialVersionUID = 713134642842075368L;

	private Integer classId;
	private Integer courseId;
	private Integer chapterId;
	private Chapter chapter;
	private List<Course> publishedCourses;
	private List<Chapter> chapters;
	private Map<String, Object> basicInfo = new HashMap<String, Object>();
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	ChapterService chapterService;

	/*
	 * 学生点击对应的课程 传递课程ID和班级ID 根据班级ID和课程ID查询对应的章节 如果只要课程ID，然后查询所有的章节
	 */

	public String getCourseInfo() {
		Course course = courseService.get(Course.class,this.getCourseId()); //先获取整个Course的信息 然后根据组织号查询组织名，再查询对应用户名
		results = new ArrayList<Map<String, Object>>();
		basicInfo.put("CourseInfo", course);
		basicInfo.put("OrganizationName",organizationService.get(Organization.class, course.getOrganizationId()).getOrganizationName());
		basicInfo.put("OwnerName", userService.get(User.class,courseService.getCourseOwner(courseId)).getUserName());
		results.add(basicInfo);
		return "result>json";
	}

	public String queryChatperInCourse() {
		/* 如果该角色在某个班级里并且班级 持有此课程 那么允许根据课程号查询章节 */
		// User user = SystemContext.getCurrentUser();
		results = courseService.queryChapterForLearn(this.getClassId(), this.getCourseId());
		return "result>json";
	}

	public String queryChapterInPublishedCourse() {
		if (courseService.checkCourseIsPublished(this.getCourseId())) {
			chapters = chapterService.getChapterInCourse(this.getCourseId());
		}
		return "result>json";
	}

	public String getChapterInfo() {
		this.setChapter(chapterService.getChapterInfo(this.getChapterId()));
		return "result>json";
	}

	public String queryAllIsPublishedCourse() {
		this.setPublishedCourses(courseService.getAllPublishedCourse());
		return "result>json";
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public List<Course> getPublishedCourses() {
		return publishedCourses;
	}

	public void setPublishedCourses(List<Course> publishedCourses) {
		this.publishedCourses = publishedCourses;
	}
}
