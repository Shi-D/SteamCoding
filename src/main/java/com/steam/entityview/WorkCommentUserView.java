package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORK_COMMENT_USER_VIEW")
public class WorkCommentUserView {

	private Integer id;
	private Integer workId;
	private String workName;
	private String workDescription;
	private String workLink;
	private Integer isPublished;
	private Integer isRecommended;
	private Integer courseWorkType;
	private Date creationTime;
	private Integer likeCount;
	private Integer authorId;
	private String authorName;
	private Integer commentId;
	private Integer reviewerId;
	private String reviewerName;
	private String reviewerPhotoName;
	private String content;
	private Integer replyCommentId;
	private Integer replyUserId;
	private String replyUserName;
	private Integer commentLikeCount;
	private Date contentTime;
	private Date recommendedTime;
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	@Column(name = "REVIEWER_ID")
	public Integer getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}
	@Column(name = "REVIEWER_NAME")
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	@Column(name = "REVIEWER_PHOTO_NAME")
	public String getReviewerPhotoName() {
		return reviewerPhotoName;
	}
	public void setReviewerPhotoName(String reviewerPhotoName) {
		this.reviewerPhotoName = reviewerPhotoName;
	}
	
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "CONTENT_TIME")
	public Date getContentTime() {
		return contentTime;
	}
	public void setContentTime(Date contentTime) {
		this.contentTime = contentTime;
	}
	@Column(name = "REPLY_COMMENT_ID")
	public Integer getReplyCommentId() {
		return replyCommentId;
	}
	public void setReplyCommentId(Integer replyCommentId) {
		this.replyCommentId = replyCommentId;
	}
	@Column(name = "REPLY_USER_ID")
	public Integer getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	@Column(name = "REPLY_USER_NAME")
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	@Column(name = "COMMENT_LIKE_COUNT")
	public Integer getCommentLikeCount() {
		return commentLikeCount;
	}
	public void setCommentLikeCount(Integer commentLikeCount) {
		this.commentLikeCount = commentLikeCount;
	}
	@Column(name = "COMMENT_ID")
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	@Column(name = "RECOMMENDED_TIME")
	public Date getRecommendedTime() {
		return recommendedTime;
	}
	public void setRecommendedTime(Date recommendedTime) {
		this.recommendedTime = recommendedTime;
	}
	

	
}
