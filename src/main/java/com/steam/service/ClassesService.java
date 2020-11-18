package com.steam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entity.Chapter;
import com.steam.entity.Classes;
@Service
public class ClassesService extends BaseServiceImpl<Classes> {

	/**
	 * 根据classIds数组批量删除
	 * 
	 * @param ids
	 */
	@Autowired
	ChapterService chapterService;

	public void deleteClassesInfo(String classIds) throws Exception {
		String hql = "delete from Classes where classId in(" + classIds + ")";
		this.executeHQLUpdate(hql);
	}

	public void addStudentsToClass(Integer classId, String[] studentId)  {
		int len = studentId.length;
		String sql = "";
		
		for (int i = 0; i < len; i++) {
			System.out.println(studentId[i]);
			sql = "insert into STEAM_CLASS_STUDENT ( CLASS_ID,STUDENT_ID ) values (" + classId + "," + studentId[i] + ")";
			this.executeSQLUpdate(sql);	
		}
	}

	public void addCoursesToClass(Integer classId, String[] courseId) throws Exception {
		int len = courseId.length;
		String hql;
		String sql;
		for (int i = 0; i < len; i++) {
			System.out.println(courseId[i]);
			hql = "insert into STEAM_CLASS_COURSE (CLASS_ID,COURSE_ID) values (" + classId + "," + courseId[i] + ")";
			System.out.println(hql);
			this.executeSQLUpdate(hql);
			// 将课程下的所有章节添加到课程进度表中
			List<Chapter> chapterList = chapterService.getChapterInCourse(Integer.valueOf(courseId[i]));
			for (int j = 0; j < chapterList.size(); j++) {
				sql = "INSERT INTO STEAM_CHAPTER_SCHEDULE (CLASS_ID,IS_FINISHED,CHAPTER_ID) VALUES (" + classId + "," + 0 + "," + chapterList.get(j).getChapterId() + ")";
				this.executeSQLUpdate(sql);
			}

		}
	}

	public Integer addClass(String className, Integer organizationId, Integer userId) throws Exception {
		Date date = new Date();
		Classes classes = new Classes(); 
		classes.setClassName(className);
		classes.setCreateTime(date);
		classes.setOrganizationId(organizationId);
		this.save(classes);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat);
		String hql = "INSERT INTO STEAM_CLASS_TEACHER VALUES ("+classes.getClassId()+","+userId+",'"+dateFormat.format(date)+"')";
		this.executeSQLUpdate(hql);
		return classes.getClassId();
	}
	//根据老师ID查询下属所有班级
	public List<Map<String,Object>> queryClassesByTeacherId(Integer teacherId){
		String sql ="SELECT CLASS_NAME as className, CLASS_ID as classId FROM TEACHER_CLASS_VIEW WHERE TEACHER_ID = ?";
		return this.getResultBySQL(sql,teacherId);
	}

	public String queryClassesByStudentId(Integer studentId){
		String hql ="select classId as classId from ClassStudent WHERE studentId = ?";
		List<Map<String,Object>> classStudent = this.getResultByHQL(hql,studentId);
		int len = classStudent.size();
		if(len == 0)
			return null;
		if(len == 1)
			return classStudent.get(0).get("classId").toString();
		String classIds = "";
		classIds += classStudent.get(0).get("classId");
		for(int i=1; i<len; i++){
			classIds += ",";
			classIds += classStudent.get(i).get("classId");
		}
		return classIds;
	}
	public 	List<Map<String,Object>> deleteCoursesInClassService(Integer classId,String courseIds) {
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try{
			String[] courseIdArray = courseIds.split(",");
			String sql = "";
			for(String course : courseIdArray){
				sql = "DELETE FROM STEAM_CLASS_COURSE WHERE CLASS_ID = ? AND COURSE_ID = ?";
				this.executeSQLUpdate(sql,classId,course);
			}
			basicInfo.put("result","刪除成功");
		}catch(Exception e){
			basicInfo.put("result","刪除失败");
		}
		result.add(basicInfo);
		return result;
	}
}
