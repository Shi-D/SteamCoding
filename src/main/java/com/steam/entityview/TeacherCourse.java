package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHER_COURSE_VIEW")
public class TeacherCourse {
	@Id
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	@Column(name = "COURSE_NAME")
	private String courseName;
	
	@Column(name = "TEACHER_CODE")
	private String teacherCode;
	
	@Column(name = "TEACHER_NAME")
	private String teacherName;
	
	@Column(name = "TEACHER_ID")
	private Integer teacherId;
	
	@Column(name = "SIZE")
	private Integer classSize;
	
	@Column(name = "COURSE_CREATE_TIME")
	private String courseCreateTime;
	
	@Column(name="ORGANIZATIONID")
	private String organizationId;
	
	@Column(name="IS_PUBLISHED")
	private Integer isPublished;
	
	public Integer getClassSize() {
		return classSize;
	}

	public void setClassSize(Integer classSize) {
		this.classSize = classSize;
	}

	public String getCourseCreateTime() {
		return courseCreateTime;
	}

	public void setCourseCreateTime(String courseCreateTime) {
		this.courseCreateTime = courseCreateTime;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}
	
	
}
