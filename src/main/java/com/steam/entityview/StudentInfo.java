package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_INFO_VIEW")
public class StudentInfo {
	@Id
	@Column(name = "STUDENT_ID")
	private Integer studentId;
	
	@Column(name = "STUDENT_NAME")
	private String studentName;
	
	@Column(name = "STUDENT_CODE")
	private String studentCode;
	
	@Column(name = "STUDENT_GENDER")
	private String studentGender;
	
	@Column(name = "ORGANIZATIONID")
	private Integer organizationId;
	
	@Column(name = "CLASS_NAME")
	private String className;
		
	@Column(name = "STUDENT_CREATION_TIME")
	private Date studentCreationTime;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getStudentCreationTime() {
		return studentCreationTime;
	}

	public void setStudentCreationTime(Date studentCreationTime) {
		this.studentCreationTime = studentCreationTime;
	}
}
