package com.steam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entityview.ClassTeacherStudent;

@Service
public class ClassTeacherStudentService extends BaseServiceImpl<ClassTeacherStudent> {

	public List<Map<String, Object>> queryAllClasses(Integer organizationId) {
		String hql = "select distinct className as className, classId as classId from ClassTeacherStudentNumberView where organizationId = ? ";
		return this.getResultByHQL(hql, organizationId);
	}

	public List<Map<String, Object>> queryStudentNotInClass(Integer organizationId,Integer classId) {
		String hql = "select distinct studentName as studentName, studentId as studentId, studentCode as studentCode from StudentClassTeacher where studentId not in (select studentId as studentId from StudentClassTeacher where organizationId = ? and classId =?)";
		return this.getResultByHQL(hql, organizationId,classId);
	}
	
	public List<Map<String, Object>> queryStudentInClass(Integer organizationId,Integer classId) {
		String hql = "select distinct studentName as studentName, studentId as studentId, studentCode as studentCode from StudentClassTeacher where organizationId = ? and classId =?)";
		return this.getResultByHQL(hql, organizationId,classId);
	}
	
	public void deleteStudents(String[] studentId, Integer classId) {
		int len = studentId.length;
		String hql = "";
		for (int i = 0; i < len; i++) {
		hql = "delete from ClassStudent where studentId =" + studentId[i] + " and classId =" + classId ;
		this.executeHQLUpdate(hql);
		}
		
	}

}
