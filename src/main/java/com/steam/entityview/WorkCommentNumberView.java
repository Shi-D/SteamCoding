package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORK_COMMENTNUMBER_VIEW")
public class WorkCommentNumberView {

	private Integer workId;
	private String workName;
	private String courseWorkName;
	private String workDescription;
	private String workLink;
	private Integer isPublished;
	private Integer isRecommended;
	private Integer courseWorkType;
	private Integer likeCount;
	private Integer authorId;
	private String authorName;
	private Date creationTime;
	private Integer num;

	@Id
	@Column(name = "WORK_ID")
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	@Column(name = "WORK_NAME")
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	@Column(name = "COURSEWORK_NAME")
	public String getCourseWorkName() {
		return courseWorkName;
	}

	public void setCourseWorkName(String courseWorkName) {
		this.courseWorkName = courseWorkName;
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

	@Column(name = "IS_PUBLISHED")
	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	@Column(name = "IS_RECOMMENDED")
	public Integer getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}

	@Column(name = "COURSEWORK_TYPE")
	public Integer getCourseWorkType() {
		return courseWorkType;
	}

	public void setCourseWorkType(Integer courseWorkType) {
		this.courseWorkType = courseWorkType;
	}

	@Column(name = "CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "LIKE_COUNT")
	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	@Column(name = "AUTHOR_ID")
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@Column(name = "AUTHOR_NAME")
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
