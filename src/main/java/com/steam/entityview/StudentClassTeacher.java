package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_CLASS_TEACHER_VIEW")
public class StudentClassTeacher {
	
	@Id
	@Column(name = "STUDENT_ID")
	private Integer studentId;
	
	@Column(name = "STUDENT_NAME")
	private String studentName;
	
	@Column(name = "TEACHER_CODE")
	private String teacherCode;

	@Column(name = "CLASS_ID")
	private Integer classId;
	
	@Column(name = "ORGANIZATIONID")
	private Integer organizationId;

	@Column(name = "STUDENT_CODE")
	private String studentCode;
	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
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

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	
	
}
