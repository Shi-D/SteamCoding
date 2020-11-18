package com.steam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entityview.CourseClassTeacher;

@Service
public class CourseClassTeacherService extends BaseServiceImpl<CourseClassTeacher>{

	public List<Map<String, Object>> queryCourseNotInClass(String teacherCode,Integer classId) {
		String hql = "select distinct courseName as courseName, courseId as courseId from CourseClassTeacher where courseId not in (select courseId as courseId from CourseClassTeacher where teacherCode = ? and classId =?)";
		return this.getResultByHQL(hql, teacherCode,classId);
	}
	public List<Map<String, Object>> queryCourseInClass(String teacherCode,Integer classId) {
		String hql = "select distinct courseName as courseName , courseId as courseId from CourseClassTeacher where teacherCode = ? and classId =?)";
		return this.getResultByHQL(hql, teacherCode,classId);
	}
}
