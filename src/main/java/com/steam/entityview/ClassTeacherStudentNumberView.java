package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLASS_TEACHER_STUDENTNUMBER_VIEW")
public class ClassTeacherStudentNumberView {

	@Id
	@Column(name = "CLASS_ID")
	private Integer classId;

	@Column(name = "CLASS_NAME")
	private String className;

	@Column(name = "TEACHER_CODE")
	private String teacherCode;

	@Column(name = "TEACHER_NAME")
	private String teacherName;
	
	@Column(name = "TEAHCER_ID")
	private Integer teacherId;

	@Column(name = "ORGANIZATIONID")
	private Integer organizationId;

	@Column(name = "STUDENT_NUMBER")
	private Integer studentNumber;

	@Column(name = "CREATION_TIME")
	private String creationTime;

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
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

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
}
