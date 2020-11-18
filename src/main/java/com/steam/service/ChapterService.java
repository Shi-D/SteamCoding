package com.steam.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entity.Chapter;

@Service
public class ChapterService extends BaseServiceImpl<Chapter> {
	@Autowired
	CourseService courseService;

	public void deleteChapter(Integer chapterId) throws Exception {
		String hql = "delete from Chapter where chapterId in(" + chapterId + ")";
		this.executeHQLUpdate(hql);
	}

	public void addNewChapter(Integer courseId) {
		Chapter chapter = new Chapter();
		chapter.setChapterName("未命名");
		chapter.setChapterContent("");
		chapter.setCreationTime(new Date());
		chapter.setCourseId(courseId);
		this.save(chapter);
		/*
		 * String hql = "insert into STEAM_CHAPTER ( COURSE_ID ) values (" +
		 * courseId + ")"; this.executeSQLUpdate(hql);
		 */
		if (courseService.checkCourseInClassOrNot(courseId)) {
			String hql = "select CLASS_ID as classId from STEAM_CLASS_COURSE where COURSE_ID = " + courseId;
			List<Map<String, Object>> classIds = this.getResultBySQL(hql);
			for (int i = 0; i < classIds.size(); i++) {
				hql = "INSERT INTO STEAM_CHAPTER_SCHEDULE (CLASS_ID,IS_FINISHED,CHAPTER_ID) VALUES (" + classIds.get(i).get("classId") + "," + 0 + "," + chapter.getChapterId() + ")";
				System.out.println("hql******************"+hql);
				this.executeSQLUpdate(hql);
			}
		}
	}

	public void updateChapterForCourse(Integer courseId, Integer chapterId, String chapterName, String chapterContent) {
		if (null != chapterId) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String hql = "UPDATE STEAM_CHAPTER SET CHAPTER_NAME = '" + chapterName + "',CHAPTER_CONTENT ='" + chapterContent + "',CHAPTER_CREATION_TIME = '"+dateFormat.format(new Date())+"' WHERE CHAPTER_ID = " + chapterId;
			this.executeSQLUpdate(hql);
		} else {
			Chapter chapter = new Chapter();
			chapter.setChapterName(chapterName);
			chapter.setChapterContent(chapterContent);
			chapter.setCourseId(courseId);
			this.save(chapter);
		}
	}

	public List<Map<String, Object>> queryChapterInCourse(Integer courseId) {
		String hql = "select chapterId as chapterId, chapterName as chapterName,chapterContent as chapterContent, chapterType as chapterType,creationTime as createTime from Chapter  where courseId = ?  order by creationTime desc";
		return this.getResultByHQL(hql, courseId);
	}
	
	

	public List<Chapter> getChapterInCourse(Integer courseId) {
		String hql = "from Chapter where courseId = ?";
		return this.find(hql, courseId);
	}

	public Chapter getChapterInfo(Integer chapterId){
		return this.get(Chapter.class, chapterId);
	}
}
