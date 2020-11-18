package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORK_USER_VIEW")
public class WorkUser {
	private Integer workId;
	private Integer userId;
	private Integer likeCount;
	private Integer isPublished;
	private Integer isRecommended;
	private Integer courseWorkType;
	private String workDescription;
	private String userIntroduction;
	private String userPhotoName;
	private String workLink;
	private String workName;
	private String teacherClassName;
	private String studentClassName;
	private String userName;
	private Date creationTime;
	private Integer organizationId;
	private String courseWorkName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WORK_ID")
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	@Column(name = "IS_PUBLISHED")
	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	@Column(name = "USER_INTRODUCTION")
	public String getUserIntroduction() {
		return userIntroduction;
	}

	public void setUserIntroduction(String userIntroduction) {
		this.userIntroduction = userIntroduction;
	}

	@Column(name = "IS_RECOMMENDED")
	public Integer getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "TEACHER_CLASS_NAME")
	public String getTeacherClassName() {
		return teacherClassName;
	}

	public void setTeacherClassName(String teacherClassName) {
		this.teacherClassName = teacherClassName;
	}

	@Column(name = "STUDENT_CLASS_NAME")
	public String getStudentClassName() {
		return studentClassName;
	}

	public void setStudentClassName(String studentClassName) {
		this.studentClassName = studentClassName;
	}
	@Column(name = "USER_PHOTO_NAME")
	public String getUserPhotoName() {
		return userPhotoName;
	}

	public void setUserPhotoName(String userPhotoName) {
		this.userPhotoName = userPhotoName;
	}

	@Column(name = "LIKE_COUNT")
	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	@Column(name = "COURSEWORK_TYPE")
	public Integer getCourseWorkType() {
		return courseWorkType;
	}

	public void setCourseWorkType(Integer courseWorkType) {
		this.courseWorkType = courseWorkType;
	}
	
	@Column(name = "WORK_DESCRIPTION")
	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	@Column(name = "WORK_LINK")
	public String getWorkLink() {
		return workLink;
	}

	public void setWorkLink(String workLink) {
		this.workLink = workLink;
	}

	@Column(name = "WORK_NAME")
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "ORGANIZATIONID")
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	@Column(name = "COURSEWORK_NAME")
	public String getCourseWorkName() {
		return courseWorkName;
	}

	public void setCourseWorkName(String courseWorkName) {
		this.courseWorkName = courseWorkName;
	}
	
}
