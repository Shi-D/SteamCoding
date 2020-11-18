package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HOMEWORK_STUDENT_VIEW")
public class HomeworkStudentView {

	private Integer userId;
	private Integer homeworkId;
	private String sourceLink;
	private Date creationTime;
	private String userName;
	private String evaluation;
	private String className;
	private Double score;
	private Integer workId;
	@Id
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "HOMEWORK_ID")
	public Integer getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}
	@Column(name = "SOURCE_LINK")
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Column(name = "STUDENT_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "EVALUATION")
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	@Column(name = "SCORE")
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	@Column(name = "CLASS_NAME")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Column(name = "COURSEWORK_ID")
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	
}
