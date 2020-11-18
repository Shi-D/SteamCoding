package com.steam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_COURSEWORK")
public class CourseWork {
	

	private Integer workId;
	private Integer userId;
	private String workLink;
	private String workName;
	private Integer likeCount;
	private Integer is_published;
	private String courseWorkName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COURSEWORK_ID")
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "COURSEWORK_LINK")
	public String getWorkLink() {
		return workLink;
	}
	public void setWorkLink(String workLink) {
		this.workLink = workLink;
	}
	@Column(name = "COURSEWORK_NAME")
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	@Column(name = "LIKE_COUNT")
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	@Column(name = "IS_PUBLISHED")
	public Integer getIs_published() {
		return is_published;
	}
	public void setIs_published(Integer is_published) {
		this.is_published = is_published;
	}
	@Column(name = "WORK_NAME")
	public String getCourseWorkName() {
		return courseWorkName;
	}
	public void setCourseWorkName(String courseWorkName) {
		this.courseWorkName = courseWorkName;
	}
	
	
}
