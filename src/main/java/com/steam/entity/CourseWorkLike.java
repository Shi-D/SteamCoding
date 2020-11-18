package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_COURSEWORK_LIKE")
public class CourseWorkLike {

	private Integer likeId;
	private Integer workId;
	private Integer likeUser;
	private Date creationTime;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LIKE_ID")
	public Integer getLikeId() {
		return likeId;
	}
	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}
	@Column(name = "WORK_ID")
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	@Column(name = "LIKE_USER")
	public Integer getLikeUser() {
		return likeUser;
	}
	public void setLikeUser(Integer likeUser) {
		this.likeUser = likeUser;
	}
	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
}
