package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLASS_CHAPTER_VIEW")
public class ClassChapter {

	private Integer chapterId;
	private String chapterName;
	private Integer chapterSortKey;
	private Integer courseId;
	private Integer chapterType;
	private Integer classId;
	private Integer isFinished;
	private String courseName;
	private String courseCover;
	private String chapterContent;
	private String chapterVedioLink;
	
	@Id
	@Column(name = "CHAPTER_ID")
	public Integer getChapterId() {
		return chapterId;
	}
	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}
	@Column(name = "CHAPTER_NAME")
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	@Column(name = "CHAPTER_SORT_KEY")
	public Integer getChapterSortKey() {
		return chapterSortKey;
	}
	public void setChapterSortKey(Integer chapterSortKey) {
		this.chapterSortKey = chapterSortKey;
	}
	@Column(name = "COURSE_ID")
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	@Column(name = "CHAPTER_TYPE")
	public Integer getChapterType() {
		return chapterType;
	}
	public void setChapterType(Integer chapterType) {
		this.chapterType = chapterType;
	}
	@Column(name = "CLASS_ID")
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	@Column(name = "IS_FINISHED")
	public Integer getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}
	@Column(name = "COURSE_NAME")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Column(name = "CHAPTER_VIDEO_LINK")
	public String getChapterVedioLink() {
		return chapterVedioLink;
	}
	public void setChapterVedioLink(String chapterVedioLink) {
		this.chapterVedioLink = chapterVedioLink;
	}
	@Column(name = "CHAPTER_CONTENT")
	public String getChapterContent() {
		return chapterContent;
	}
	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}
	@Column(name = "COURSE_COVER")
	public String getCourseCover() {
		return courseCover;
	}
	public void setCourseCover(String courseCover) {
		this.courseCover = courseCover;
	}
}
