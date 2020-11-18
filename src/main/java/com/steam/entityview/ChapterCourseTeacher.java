package com.steam.entityview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHAPTER_COURSE_TEACHER_VIEW")
public class ChapterCourseTeacher {

	private Integer chapterId;
	private String chapterName;
	private String chapterContent;
	private Integer chapterType;
	private String chapterVedioLink;
	private Integer courseId;
	private String courseName;
	private String teacherCode;
	private String createTime;
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
	@Column(name = "CHAPTER_CONTENT")
	public String getChapterContent() {
		return chapterContent;
	}
	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}
	@Column(name = "CHAPTER_TYPE")
	public Integer getChapterType() {
		return chapterType;
	}
	public void setChapterType(Integer chapterType) {
		this.chapterType = chapterType;
	}
	@Column(name = "CHAPTER_VIDEO_LINK")
	public String getChapterVedioLink() {
		return chapterVedioLink;
	}
	public void setChapterVedioLink(String chapterVedioLink) {
		this.chapterVedioLink = chapterVedioLink;
	}
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
	@Column(name = "TEACHER_CODE")
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	@Column(name = "CREATE_TIME")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
