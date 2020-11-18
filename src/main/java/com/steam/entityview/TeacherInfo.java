package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHER_INFO_VIEW")
public class TeacherInfo {
	
	public static final Integer TABLE_CODE = 10002;
	
	private Integer teacherId;
	private String teacherName;
	private String teacherCode;
	private String teacherGender;
	private String className;
	private Date teacherCreationTime;
	private Integer organizationId;
	@Id
	@Column(name = "TEACHER_ID")
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	@Column(name = "TEACHER_NAME")
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	@Column(name = "TEACHER_CODE")
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	@Column(name = "TEACHER_GENDER")
	public String getTeacherGender() {
		return teacherGender;
	}
	public void setTeacherGender(String teacherGender) {
		this.teacherGender = teacherGender;
	}
	@Column(name = "CLASS_NAME")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Column(name = "TEACHER_CREATION_TIME")
	public Date getTeacherCreationTime() {
		return teacherCreationTime;
	}
	public void setTeacherCreationTime(Date teacherCreationTime) {
		this.teacherCreationTime = teacherCreationTime;
	}
	@Column(name = "ORGANIZATIONID")
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
	
}
