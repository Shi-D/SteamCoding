package com.steam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entityview.CourseClassStudent;

@Service
public class CourseClassStudentService extends BaseServiceImpl<CourseClassStudent> {
	
	// 获取学生的课程
	public List<Map<String, Object>> queryCoursebyStundetId(Integer studentId) {
		String hql = "select courseId as courseId, courseName as courseName, classId as classId,className as className, courseCover as courseCover,courseIntroduction as courseIntroduction ,courseFolderName as courseFolderName from CourseClassStudent where studentId = ?";
		return this.getResultByHQL(hql, studentId);
	}
	
	public Boolean checkStudentInClass(){
		return false;
	}
	
}
