package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE_CLASS_TEACHER_VIEW")
public class CourseClassTeacher {
	
	@Id
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	@Column(name = "CLASS_ID")
	private Integer classId;
	
	@Column(name = "COURSE_NAME")
	private String courseName;

	@Column(name = "CLASS_NAME")
	private String className;
	
	@Column(name = "TEACHER_CODE")
	private String teacherCode;
	
	@Column(name = "TEACHER_ID")
	private Integer teacherId;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
}
