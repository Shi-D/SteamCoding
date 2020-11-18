package com.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.steam.entity.WorkComment;

@Service
public class WorkCommentService  extends BaseServiceImpl<WorkComment>{
	
	public List<Map<String, Object>>  queryCommentIsReply (Integer userId, Integer commentId){
		String hql = "select commentId as commentId, reviewerId as reviewerId, reviewerName as reviewerName,reviewerPhotoName as reviewerPhotoName, content as content, replyCommentId as replyCommentId,replyUserId as replyUserId, replyUserName as replyUserName,contentTime as commentTime, commentLikeCount as commentLikeCount from WorkCommentUserView where replyCommentId = ? order by contentTime asc";
		List<Map<String, Object>> results = this.getResultByHQL(hql, commentId);
		if(userId == null){
			for(Map<String, Object> result : results){
				result.put("isLike", userLikeIsExist(userId,(Integer)result.get("commentId")));
			}
		}else{
			for(Map<String, Object> result : results){
				result.put("isLike", userLikeIsExist(userId,(Integer)result.get("commentId")));
			}
		}
		return	results;
	}
	
	public List<Map<String, Object>>  queryCommentIsNotReply (Integer userId,Integer workId){
		String hql = "select commentId as commentId, reviewerId as reviewerId, reviewerName as reviewerName,reviewerPhotoName as reviewerPhotoName, content as content, replyCommentId as replyCommentId,replyUserId as replyUserId, replyUserName as replyUserName,contentTime as commentTime, commentLikeCount as commentLikeCount from WorkCommentUserView where workId = ? and replyCommentId is null order by contentTime asc";
		List<Map<String, Object>> results = this.getResultByHQL(hql, workId);
		if(userId == null){
			for(Map<String, Object> result : results){
				result.put("isLike",false);
			}
		}else{
			for(Map<String, Object> result : results){
				result.put("isLike", userLikeIsExist(userId,(Integer)result.get("commentId")));
			}
		}
		return	results;
	}

	
	public WorkComment addComment(Integer workId,Integer userId,String content,Integer replyCommentId,Integer replyUserId){
		Integer likeCount = 0;
		WorkComment workComment = new WorkComment();
		workComment.setContent(content);
		workComment.setContentTme(new Date());
		workComment.setLikeCount(likeCount);
		if(null!=replyCommentId&&!replyCommentId.equals(""))
			workComment.setReplyCommentId(replyCommentId);
		else
			workComment.setReplyCommentId(null);
		if(null!=replyUserId&&!replyUserId.equals(""))
			workComment.setReplyUserId(replyUserId);
		else
			workComment.setReplyUserId(null);
		workComment.setUserId(userId);
		workComment.setWorkId(workId);
		this.save(workComment);
		return workComment;
	}
	
	
	public void deleteComment(Integer commentId){
		WorkComment workComment = this.get(WorkComment.class, commentId);
		String sql = "SELECT commentId FROM WorkComment WHERE replyCommentId = ?";
		if(workComment.getReplyCommentId() == null&&this.getTotalCountByHQL(sql,commentId)!=0){ //如果是父评论并且有子评论
			sql = "DELETE FROM STEAM_COURSEWORK_COMMENT WHERE REPLY_COMMENT_ID = "+commentId;
			this.executeSQLUpdate(sql);
		}
		sql = "DELETE FROM STEAM_COURSEWORK_COMMENT WHERE COMMENT_ID = "+commentId;
		this.executeSQLUpdate(sql);
	}
	
	public void updateCommentLike(WorkComment workComment,Integer userId){
		String hql = "INSERT STEAM_COMMENT_LIKE (COMMENT_ID,USER_ID) VALUES( "+ workComment.getCommentId() +","+ userId+" ) ";
		this.executeSQLUpdate(hql);
		hql = "UPDATE STEAM_COURSEWORK_COMMENT SET LIKE_COUNT = " + (workComment.getLikeCount()+1) +"WHERE COMMENT_ID = " +workComment.getCommentId();
		this.executeSQLUpdate(hql);
	}
	
	public void dropCommentLike(WorkComment workComment,Integer userId){
		String hql = "DELETE FROM STEAM_COMMENT_LIKE WHERE COMMENT_ID = "+ workComment.getCommentId() +" AND USER_ID = "  + userId;
		this.executeSQLUpdate(hql);
		if(workComment.getLikeCount()>0)
			hql = "UPDATE STEAM_COURSEWORK_COMMENT SET LIKE_COUNT = " + (workComment.getLikeCount()-1) +"WHERE COMMENT_ID = " +workComment.getCommentId();
		this.executeSQLUpdate(hql);
	}
	public Boolean userLikeIsExist(Integer userId,Integer commentId){
		//根据作品id查找到作品下的评论
		String hql = "SELECT COUNT(*) FROM STEAM_COMMENT_LIKE WHERE COMMENT_ID = ? AND USER_ID = ?";
		Integer count = this.getTotalCountBySQL(hql,commentId,userId) ;
		if (count > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public List<Map<String, Object>>  queryAllComment(Integer workId){
		String hql = "select commentId as commentId from WorkComment where workId = ? ";
		return	this.getResultByHQL(hql, workId);
	}
	
	public WorkComment getWorkComment(Integer commentId){
		return this.get(WorkComment.class,commentId);
	}
	
	
}
