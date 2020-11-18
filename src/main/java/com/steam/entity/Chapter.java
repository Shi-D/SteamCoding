package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_CHAPTER")
public class Chapter {

	private Integer chapterId;
	private String chapterName;
	private String chapterContent;
	private Integer chapterType;
	private Integer chapterSortKey;
	private String chapterVideoLink;
	private Integer courseId;
	private Date creationTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CHAPTER_ID")
	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	@Column(name = "CHAPTER_CREATION_TIME", nullable = true)
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "CHAPTER_NAME", nullable = true)
	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	@Column(name = "CHAPTER_CONTENT", nullable = true)
	public String getChapterContent() {
		return chapterContent;
	}

	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}

	@Column(name = "CHAPTER_TYPE", nullable = true)
	public Integer getChapterType() {
		return chapterType;
	}

	public void setChapterType(Integer chapterType) {
		this.chapterType = chapterType;
	}

	@Column(name = "CHAPTER_SORT_KEY", nullable = true)
	public Integer getChapterSortKey() {
		return chapterSortKey;
	}

	public void setChapterSortKey(Integer chapterSortKey) {
		this.chapterSortKey = chapterSortKey;
	}

	@Column(name = "CHAPTER_VIDEO_LINK", nullable = true)
	public String getChapterVideoLink() {
		return chapterVideoLink;
	}

	public void setChapterVideoLink(String chapterVideoLink) {
		this.chapterVideoLink = chapterVideoLink;
	}

	@Column(name = "COURSE_ID")
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}
