package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HOMEWORK_INFO")
public class HomeworkInfo {
	private Integer homeworkId;
	private Integer userId;
	private String homeworkName;
	private String className;
	private Date creationTime;
	private Date deadline;
	private String classId;
	@Id
	@Column(name = "HOMEWORK_ID")
	public Integer getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "HOMEWORK_NAME")
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	@Column(name = "CLASS_ID")
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@Column(name = "CLASS_NAME")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Column(name = "DEADLINE")
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}