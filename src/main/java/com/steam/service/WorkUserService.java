package com.steam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entity.CourseWork;
import com.steam.entityview.WorkUser;
@Service
public class WorkUserService extends BaseServiceImpl<WorkUser>{
	
	public List<Map<String, Object>> showWorkDetail(Integer workId){
		String hql = "select workName as workName,workLink as workLink,userIntroduction as userIntroduction,userPhotoName as userPhotoName,userName as userName, workDescription as workDescription,likeCount as likeCount from WorkUser where workId = "+workId;
		return this.getResultByHQL(hql);
	}
	
	public Boolean userLikeIsExist(Integer workId,Integer userId){
		String hql = "select COUNT(*) from CourseWorkLike where workId = ? and likeUser = ?";
		Integer count = this.getTotalCountByHQL(hql,workId,userId) ;
		if (count > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void updateUserLike(CourseWork coursework,Integer userId){
		String hql = "INSERT STEAM_COURSEWORK_LIKE (WORK_ID,LIKE_USER) VALUES( "+ coursework.getWorkId() +","+ userId+" ) ";
		this.executeSQLUpdate(hql);
		hql = "UPDATE STEAM_COURSEWORK SET LIKE_COUNT = " + (coursework.getLikeCount()+1) +" WHERE COURSEWORK_ID = " +coursework.getWorkId();
		this.executeSQLUpdate(hql);
	}
	
	public void dropUserLike(CourseWork coursework,Integer userId){
		String hql = "DELETE FROM STEAM_COURSEWORK_LIKE WHERE WORK_ID = "+ coursework.getWorkId() +" AND LIKE_USER = "  + userId;
		this.executeSQLUpdate(hql);
		if(coursework.getLikeCount()>0)
			hql = "UPDATE STEAM_COURSEWORK SET LIKE_COUNT = " + (coursework.getLikeCount()-1) +" WHERE COURSEWORK_ID = " +coursework.getWorkId();
		this.executeSQLUpdate(hql);
	}
	
	
}
