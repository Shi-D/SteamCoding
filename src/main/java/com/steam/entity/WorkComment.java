package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "STEAM_COURSEWORK_COMMENT")
public class WorkComment {

	private Integer workId;
	private Integer userId;
	private String content;
	private Date contentTme;
	private Integer replyCommentId;
	private Integer commentId;
	private Integer replyUserId;
	private Integer likeCount;
	
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
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "CONTENT_TIME")
	public Date getContentTme() {
		return contentTme;
	}
	public void setContentTme(Date contentTme) {
		this.contentTme = contentTme;
	}
	@Column(name = "REPLY_COMMENT_ID")
	public Integer getReplyCommentId() {
		return replyCommentId;
	}
	public void setReplyCommentId(Integer replyCommentId) {
		this.replyCommentId = replyCommentId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMENT_ID")
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	@Column(name = "REPLY_USER_ID")
	public Integer getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	@Column(name = "LIKE_COUNT")
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
}
