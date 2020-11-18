package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_HOMEWORK")
public class Homework {
	private Integer homeworkId;
	private String homeworkName;
	private Date creationTime;
	private String homeworkContent;
	private Date deadline;
	private String sourceLink;
	private Integer userId;
	private String fileName;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HOMEWORK_ID")
	public Integer getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}
	@Column(name = "HOMEWORK_NAME")
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Column(name = "HOMEWORK_CONENT")
	public String getHomeworkContent() {
		return homeworkContent;
	}
	public void setHomeworkContent(String homeworkContent) {
		this.homeworkContent = homeworkContent;
	}
	@Column(name = "DEADLINE")
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	@Column(name = "SOURCE_LINK")
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
