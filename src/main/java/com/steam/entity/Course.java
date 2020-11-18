package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_COURSE")
public class Course {
	private Integer courseId;
	private String courseName;
	private String courseIntroduction;
	private String courseOverview;
	private String courseCover;
	private String courseType;
	private Integer isShared;
	private Date creationTime;
	private Double courseEvaluation;
	private String courseFolderName;
	private Integer organizationId;
	private Integer isPublished;
	private Date publishedTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COURSE_ID")
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Column(name = "COURSE_NAME")
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Column(name = "COURSE_INTRODUCTION")
	public String getCourseIntroduction() {
		return courseIntroduction;
	}

	public void setCourseIntroduction(String courseIntroduction) {
		this.courseIntroduction = courseIntroduction;
	}

	@Column(name = "COURSE_OVERVIEW", nullable = true)
	public String getCourseOverview() {
		return courseOverview;
	}

	public void setCourseOverview(String courseOverview) {
		this.courseOverview = courseOverview;
	}

	@Column(name = "COURSE_COVER")
	public String getCourseCover() {
		return courseCover;
	}

	public void setCourseCover(String courseCover) {
		this.courseCover = courseCover;
	}

	@Column(name = "COURSE_TYPE", nullable = true)
	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	@Column(name = "IS_SHARED", nullable = true)
	public Integer getIsShared() {
		return isShared;
	}

	public void setIsShared(Integer isShared) {
		this.isShared = isShared;
	}

	@Column(name = "COURSE_CREATE_TIME", nullable = true)
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "COURSE_EVALUATION", nullable = true)
	public Double getCourseEvaluation() {
		return courseEvaluation;
	}

	

	public void setCourseEvaluation(Double courseEvaluation) {
		this.courseEvaluation = courseEvaluation;
	}

	@Column(name = "COURSE_FOLDER_NAME")
	public String getCourseFolderName() {
		return courseFolderName;
	}

	public void setCourseFolderName(String courseFolderName) {
		this.courseFolderName = courseFolderName;
	}

	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "IS_PUBLISHED")
	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	@Column(name = "COURSE_PUBLISHED_TIME")
	public Date getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(Date publishedTime) {
		this.publishedTime = publishedTime;
	}
	
}
