package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_VIDEO")
public class Video {
	
	private Integer videoId;
	private String videoName;
	private String videoLink;
	private Integer uploadUserId;
	private Date creationTime;
	private Integer organizationId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VIDEO_ID")
	public Integer getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	@Column(name = "VIDEO_NAME")
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	@Column(name = "VIDEO_LINK")
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	@Column(name = "UPLOAD_USER_ID")
	public Integer getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(Integer uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
}
