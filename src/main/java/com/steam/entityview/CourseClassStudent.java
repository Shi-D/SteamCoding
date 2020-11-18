package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "COURSE_CLASS_STUDENT_VIEW")
public class CourseClassStudent {
	
	@Id
	@Column(name = "STUDENT_ID")
	private Integer studentId;

	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	@Column(name = "CLASS_ID")
	private Integer classId;
	
	@Column(name="CLASS_NAME")
	private String className;
	
	@Column(name = "COURSE_NAME")
	private String courseName;
	
	@Column(name = "COURSE_COVER")
	private String courseCover;
	
	@Column(name="COURSE_FOLDER_NAME")
	private String courseFolderName;
	
	@Column(name="COURSE_INTRODUCTION")
	private String courseIntroduction;
	
	@Column(name="CREATION_TIME")
	private Date creationTime;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCourseIntroduction() {
		return courseIntroduction;
	}

	public void setCourseIntroduction(String courseIntroduction) {
		this.courseIntroduction = courseIntroduction;
	}

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

	public String getCourseCover() {
		return courseCover;
	}

	public void setCourseCover(String courseCover) {
		this.courseCover = courseCover;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getCourseFolderName() {
		return courseFolderName;
	}

	public void setCourseFolderName(String courseFolderName) {
		this.courseFolderName = courseFolderName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
