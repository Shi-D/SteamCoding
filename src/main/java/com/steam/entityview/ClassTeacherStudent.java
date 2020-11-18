package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLASS_TEACHER_STUDENT_VIEW")
public class ClassTeacherStudent {

	public static final Integer TABLE_CODE = 10001;

	@Id
	@Column(name = "STUDENT_ID")
	private Integer studentId;
	
	@Column(name = "STUDENT_NAME")
	private String studentName;

	@Column(name = "STUDENT_CODE")
	private String studentCode;
	
	@Column(name = "STUDENT_GENDER")
	private String studentGender;
	
	@Column(name = "CLASS_NAME")
	private String className;
	
	@Column(name = "CLASS_ID")
	private Integer classId;
	
	@Column(name = "TEACHER_CODE")
	private String teacherCode;
	
	@Column(name = "TEAHCER_NAME")
	private String teacherName;
	
	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
