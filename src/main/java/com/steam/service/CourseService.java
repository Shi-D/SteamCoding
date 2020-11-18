package com.steam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.utils.FILEUtils;
import com.steam.entity.Course;

@Service
public class CourseService extends BaseServiceImpl<Course> {

	/* 查询课程章节 */
	public List<Map<String, Object>> queryChapterInCourse(Integer classId,Integer courseId) {
		String hql = "select chapterId as chapterId, chapterName as chapterName, chapterType as chapterType, isFinished as isFinished from ClassChapter where classId = ? and courseId = ? order by chapterSortKey desc ";
		return this.getResultByHQL(hql,classId,courseId);
	}
	
	/*查询学生学习的课程*/
	public List<Map<String, Object>> queryChapterForLearn(Integer classId, Integer courseId) {
		String hql = "select chapterId as chapterId, chapterName as chapterName, chapterType as chapterType,chapterVedioLink as chapterVedioLink from ClassChapter where isFinished = 1 and classId = ? and courseId = ? order by chapterSortKey desc ";
		return this.getResultByHQL(hql, classId, courseId);
	}
	

	/* 更新章节进度 */
	public List<Map<String, Object>> updateChapterSchedule(Integer classId, String chapterIds) {
		String[] chapterId = chapterIds.split(",");
		int len = chapterId.length;
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < len; i++) {
			try {
				String hql = "UPDATE STEAM_CHAPTER_SCHEDULE SET IS_FINISHED = 1 WHERE CLASS_ID = " + classId + " and CHAPTER_ID = " + chapterId[i];
				this.executeSQLUpdate(hql);
			} catch (Exception e) {
				System.out.println(e);
				basicInfo.put("更新失败的章节", chapterId[i]);
			}
		}
		results.add(basicInfo);
		return results;
	}

	/* 删除课程 */
	public List<Map<String, Object>> deleteCourse(String courseIds) throws Exception {
		List<Map<String, Object>> deleteFile = new ArrayList<Map<String, Object>>();
		String hql1 = "select courseCover as courseCover, courseFolderName as courseFolderName from Course WHERE courseId in(" + courseIds + ")";
		deleteFile = this.getResultByHQL(hql1);
		String hql2 = "DELETE FROM Course WHERE courseId in(" + courseIds + ")";
		System.out.print(hql2);
		this.executeHQLUpdate(hql2);
		return deleteFile;
	}

	/*修改课程信息*/
	public void updateCourse(Integer courseId, String courseName, String fileName, String courseIntroduction, User user) {
		Course course = this.get(Course.class, courseId);
		FILEUtils.deleteFile(SteamConstants.ABSOLUTE_URL + course.getCourseFolderName() + course.getCourseCover());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat);
		String hql = "UPDATE STEAM_COURSE SET COURSE_CREATE_TIME = '"+dateFormat.format(new Date())+"', COURSE_NAME ='"+courseName+"',COURSE_INTRODUCTION = '"+courseIntroduction+"',COURSE_COVER = '"+fileName+"' WHERE COURSE_ID = "+courseId;
		this.executeSQLUpdate(hql);
	}
	
	/*重载修改课程信息，不修改封面*/
	public void updateCourse(Integer courseId, String courseName, String courseIntroduction){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = "UPDATE STEAM_COURSE SET COURSE_CREATE_TIME = '"+dateFormat.format(new Date())+"', COURSE_NAME ='"+courseName+"',COURSE_INTRODUCTION = '"+courseIntroduction+"'WHERE COURSE_ID = "+courseId;
		this.executeSQLUpdate(hql);
	}

	/* 上传课程 */
	public void uploadCourse(String courseName, String fileName, String courseIntroduction, User user) {
		// 先添加课程，再绑定课程和老师的关系
		Course course = new Course();
		course.setCourseName(courseName);
		course.setCourseCover(fileName);
		course.setCourseIntroduction(courseIntroduction);
		course.setCreationTime(new Date());
		course.setIsShared(0);
		course.setCourseFolderName(user.getUsername() + "/courses/");
		course.setOrganizationId(user.getOrganization().getId());
		course.setIsPublished(0);
		this.save(course);
		String hql = "INSERT INTO STEAM_COURSE_TEACHER (COURSE_ID,TEACHER_ID) VALUES (" + course.getCourseId() + "," + user.getId() + ")";
		this.executeSQLUpdate(hql);
	}
	
	public List<Map<String, Object>> getCourseInfo(Integer courseId) {
		String hql = "select  courseName as courseName, courseIntroduction as courseIntroduction, courseCover as courseCover, courseFolderName as courseFolderName, courseOverview as courseOverview from Course where courseId = ?";
		return this.getResultByHQL(hql, courseId);
	}

	//公开课程或取消公开 (设为精品课程)
	public void recommendOrRecallCourse(String courseIds,Integer type){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = null;
		if(type == 1)//推荐
			 hql = "UPDATE STEAM_COURSE SET COURSE_PUBLISHED_TIME = '"+dateFormat.format(new Date())+"', IS_PUBLISHED = "+type+" WHERE COURSE_ID IN ( "+courseIds+")";
		else if(type == 0)
			 hql = "UPDATE STEAM_COURSE SET IS_PUBLISHED = "+type+" WHERE COURSE_ID IN ( "+courseIds+")";
		this.executeSQLUpdate(hql);
	}
	
	public List<Course> getAllPublishedCourse(){
		String hql = "from Course where isPublished = 1 ORDER BY publishedTime DESC";
		return this.find(hql);
	}
	
	public Integer getCourseOwner(Integer courseId) {
		String hql = "select TEACHER_ID as ownerId from STEAM_COURSE_TEACHER where COURSE_ID = ?";
		return  Integer.parseInt(this.getResultBySQL(hql, courseId).get(0).get("ownerId").toString());
	}
	
	public Boolean checkCourseIsPublished(Integer courseId){
		String hql = "SELECT COUNT(*) FROM STEAM_COURSE WHERE IS_PUBLISHED = 1 AND COURSE_ID =  " + courseId;
		if (this.getTotalCountBySQL(hql) > 0)
			return true;
		return false;
	}
	
	public Boolean checkCourseInClassOrNot(Integer courseId) {
		String sql = "SELECT COUNT(*) FROM STEAM_CLASS_COURSE WHERE COURSE_ID =  " + courseId;
		if (this.getTotalCountBySQL(sql) > 0)
			return true;
		return false;
	}
}
