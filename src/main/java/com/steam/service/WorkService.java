package com.steam.service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.framework.utils.URLUtils;
import com.steam.entity.CourseWork;

@Service
public class WorkService extends BaseServiceImpl<CourseWork> {
	
	public List<Map<String,Object>> queryWork(Integer userId){
		String hql = "select workId as workId, courseWorkName as workName, workLink as workLink from CourseWork where userId = ? order by workId desc";
		return this.getResultByHQL(hql, userId);
	}
	
	public void addWork(Integer userId, String courseLink, String fileName, String courseWorkName) {
		String hql = "INSERT INTO STEAM_COURSEWORK (USER_ID, COURSEWORK_LINK, COURSEWORK_NAME,WORK_NAME) values ( "+userId+",'"+courseLink+"','"+fileName+"','"+courseWorkName+"')";
		this.executeSQLUpdate(hql);
	}
	
	public String getFileName(Integer userId,Integer workId){
		CourseWork courseWork = this.get(CourseWork.class, workId);
		if(courseWork.getUserId().equals(userId)){
			return courseWork.getWorkName();
		}else{
			return "";
		}
	}
	public List<Map<String,Object>> getWorkId(Integer userId,String workName){
		String hql = "select workId as workId from CourseWork where userId = ? and workName = ?";
		return this.getResultByHQL(hql, userId,workName);
	}
	public void updateWork(Integer userId,String fileName, String newFileName){
		String hql = "UPDATE STEAM_COURSEWORK SET COURSEWORK_NAME = '"+newFileName+"' WHERE USER_ID = "+userId+" AND COURSEWORK_NAME = '"+fileName+"'" ;
		this.executeSQLUpdate(hql);
	}
	//修改作品
	public void updateCourseWork(Integer userId, Integer courseWorkId, String newWorkName, String newFileName){
		String hql = "UPDATE STEAM_COURSEWORK SET COURSEWORK_NAME = '"+newFileName+"', WORK_NAME = '"+newWorkName+"' WHERE USER_ID = "+userId+" AND COURSEWORK_ID = '"+courseWorkId+"'" ;
		this.executeSQLUpdate(hql);
	}
	public void updateWorkName(String username,Integer workId,String workName){
		String sql = "UPDATE STEAM_COURSEWORK SET COURSEWORK_NAME = " + workName + " WHERE COURSEWORK_ID = " + workId;
		this.executeSQLUpdate(sql);
	}
	public void deleteWork(String username,Integer workId){
		CourseWork courseWork = this.get(CourseWork.class, workId);
		File fileCover = new File(URLUtils.generateURLforWork(username)+"/"+courseWork.getWorkName()+".png");
		File fileSB3 = new File(URLUtils.generateURLforWork(username)+"/"+courseWork.getWorkName()+".sb3");
		fileCover.delete();
		fileSB3.delete();
		String hql = "delete from CourseWork where workId =" + workId;
		this.executeHQLUpdate(hql);
	}
	
	public CourseWork getWork(Integer workId){
		return this.get(CourseWork.class, workId);
	}
	//发布作品
	public void publishWork(Integer workId, String workName, String workDescription){
		String sql = "UPDATE STEAM_COURSEWORK SET WORK_NAME = '"+workName+"',COURSEWORK_DESCRIPTION = '"+workDescription+"', IS_PUBLISHED = "+1+" WHERE COURSEWORK_ID = "+workId;
		this.executeSQLUpdate(sql);
	}
	//推荐作品
	public void recommendOrRecallWork(Integer workId,Integer type){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = null;
		if(type == 1)//推荐
			hql = "UPDATE STEAM_COURSEWORK SET COURSEWORK_RECOMMENDED_TIME = '"+dateFormat.format(new Date())+"', IS_RECOMMENDED = "+1+" WHERE COURSEWORK_ID = "+workId;
		else if(type == 0)
			hql = "UPDATE STEAM_COURSEWORK SET IS_RECOMMENDED = "+0+" WHERE COURSEWORK_ID = "+workId;
		this.executeSQLUpdate(hql);
	}
	
	public Integer countTotalPublishedWorks(){//查询所有发布的作品数
		String hql = "select count(*) from WorkUser where isPublished = 1";
		return this.getTotalCountByHQL(hql);
	}
	
	public Integer countTodayPublishedWorks(){//查询当日所有发布的作品数
		String hql = "select count(*) from WorkUser where isPublished = 1 and DateDiff(dd,creationTime,getdate())=0";
		return this.getTotalCountByHQL(hql);
	}
	
	//取消发布
	public List<Map<String,Object>> recallPublishService(Integer workId){
		Results result = new Results();
		try{
			String hql = "update CourseWork set is_published = 0 where workId = "+workId;
			this.executeHQLUpdate(hql);
			result.setResultValue("撤销成功");
			return result.getResult();
		}catch(Exception e){
			result.setResultValue("撤销失败");
			return result.getResult();
		}	
	}
	/*下面五个函数代码可以整理*/
	//评论数最多的（热门票王）
	public List<Map<String, Object>> queryHottestWorks(){
		String sql= "SELECT TOP 10 WORK_ID as workId, WORK_NAME as workName, COURSEWORK_NAME as courseWorkName, WORK_LINK as workLink,AUTHOR_NAME AS authorName,LIKE_COUNT AS likeCount,NUM AS commentNumber FROM WORK_COMMENTNUMBER_VIEW WHERE IS_PUBLISHED = 1 ORDER BY NUM DESC";
		return this.getResultBySQL(sql);
	}
	//点赞最多的（点赞最多）
	public List<Map<String, Object>> queryLikestWorks(){
		String sql= "SELECT TOP 10 a.WORK_ID as workId, a.WORK_NAME as workName, a.WORK_LINK as workLink,AUTHOR_NAME AS authorName,a.LIKE_COUNT AS likeCount,USER_PHOTO_NAME as userPhotoName FROM WORK_COMMENTNUMBER_VIEW a, WORK_USER_VIEW b WHERE a.IS_PUBLISHED = 1 and a.WORK_ID = b.WORK_ID ORDER BY a.LIKE_COUNT DESC";
		return this.getResultBySQL(sql);
	}
	//最新发布的（新作赏析）
	public List<Map<String, Object>> queryLastestWorksByPublished(){
		String sql= "SELECT TOP 10 WORK_ID as workId, WORK_NAME as workName, COURSEWORK_NAME as courseWorkName, WORK_LINK as workLink,AUTHOR_NAME AS authorName,LIKE_COUNT AS likeCount,NUM AS commentNumber FROM WORK_COMMENTNUMBER_VIEW WHERE IS_PUBLISHED = 1 ORDER BY CREATION_TIME DESC";
		return this.getResultBySQL(sql);
	}
	//最新推荐的（精品推荐）
	public List<Map<String, Object>> queryLastestWorksByrecommented(){
		String sql= "SELECT TOP 10 WORK_ID as workId, WORK_NAME as workName, WORK_LINK as workLink,AUTHOR_NAME AS authorName,LIKE_COUNT AS likeCount,NUM AS commentNumber, COURSEWORK_NAME as courseWorkName FROM WORK_COMMENTNUMBER_VIEW WHERE IS_RECOMMENDED = 1 ORDER BY RECOMMENDED_TIME DESC";
		return this.getResultBySQL(sql);
	}
	//浏览量最多的（浏览最多）
	public List<Map<String, Object>> queryPageviewsWorks(){
		String sql= "SELECT TOP 10 a.WORK_ID as workId, a.WORK_NAME as workName, a.WORK_LINK as workLink,AUTHOR_NAME AS authorName,a.LIKE_COUNT AS likeCount,USER_PHOTO_NAME as userPhotoName FROM WORK_COMMENTNUMBER_VIEW a, WORK_USER_VIEW b WHERE a.IS_PUBLISHED = 1 and a.WORK_ID = b.WORK_ID ORDER BY a.PAGEVIEWS DESC";
		return this.getResultBySQL(sql);
	}		

	public List<Map<String,Object>> getWorkLink(Integer workId, Integer UserId, Integer userType){
		User user = SystemContext.getCurrentUser();
		String sql="";
		if (userType == 3) {
			sql = "SELECT COURSEWORK_NAME AS workName, WORK_LINK AS workLink, WORK_NAME AS fileName FROM WORK_USER_VIEW WHERE WORK_ID = "+workId+" AND USER_ID = "+UserId;
		}
		else{
			sql = "SELECT COURSEWORK_NAME AS workName, WORK_LINK AS workLink, WORK_NAME AS fileName FROM WORK_USER_VIEW WHERE WORK_ID = "+workId+" AND USER_ID IN (SELECT STUDENT_ID FROM CLASS_TEACHER_STUDENT_VIEW UNION SELECT USER_ID FROM WORK_USER_VIEW WHERE USER_ID = "+user.getId()+")";
		}
		return this.getResultBySQL(sql);
	}
	
}
