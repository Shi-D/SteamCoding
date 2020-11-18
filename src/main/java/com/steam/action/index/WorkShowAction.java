package com.steam.action.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.system.common.base.action.BaseGridAction;
import com.steam.entity.WorkComment;
import com.steam.service.WorkCommentService;
import com.steam.service.WorkService;
import com.steam.service.WorkUserService;

public class WorkShowAction extends BaseGridAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5563004724122689610L;
	@Autowired
	WorkService workService;
	@Autowired
	WorkCommentService workCommentService;
	@Autowired
	WorkUserService workUserService;

	private String content;
	private Integer replyCommentId;
	private Integer replyUserId;
	private Integer commentId;
	private WorkComment workComment;

	private Integer pageNo;
	private Integer pageSize;
	private Boolean search;

	private Integer workId;

	Map<String, Object> basicInfo = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public String queryPopularWorks() {
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "select workId as workId, workName as workName,courseWorkName as courseWorkName, authorName as authorName, likeCount as likeCount,num as num, creationTime as createTime, workLink as workLink from WorkCommentNumberView where isPublished = 1";
		workService.find(this.getPager(), hql, this.getFilter());
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		results.add(basicInfo);
		return "result>json";
	}

	public String showWorkDetail() {
		results = new ArrayList<Map<String, Object>>();
		try {
			User user = SystemContext.getCurrentUser();
			basicInfo.put(
					"result",
					workUserService.userLikeIsExist(this.getWorkId(),
							user.getId()));
			basicInfo.put("status", true);
		} catch (Exception e) {
			basicInfo.put("result", false);
			basicInfo.put("status", false);
		}
		results = workUserService.showWorkDetail(this.getWorkId());
		results.add(basicInfo);
		return "result>json";
	}

	public String clickWorkLike() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		if (null == user) {
			basicInfo.put("result", "点赞失败");
			return "result>json";
		}
		try {
			workUserService.updateUserLike(
					workService.getWork(this.getWorkId()), user.getId());
			basicInfo.put("result", "点赞成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "点赞失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String dropWorkLike() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		try {
			workUserService.dropUserLike(workService.getWork(this.getWorkId()),
					user.getId());
			basicInfo.put("result", "撤回成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "撤回失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String queryComment() {
		Integer userId;
		try {
			userId = SystemContext.getCurrentUser().getId();
			basicInfo.put("status", true);
		} catch (Exception e) {
			userId = null;
			basicInfo.put("status", false);
		}
		results = workCommentService.queryCommentIsNotReply(userId,
				this.getWorkId());
		Integer len = results.size();
		for (int i = 0; i < len; i++) {// 遍历每一条非回复评论，查找对应的回复
										// 应该将评论表和回复表分开。查询效率会提升。
			results.get(i).put(
					"commentReply",
					workCommentService.queryCommentIsReply(userId,
							(Integer) results.get(i).get("commentId")));
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String addComment() {
		try {
			User user = SystemContext.getCurrentUser();
			this.setWorkComment(workCommentService.addComment(this.getWorkId(),user.getId(), this.getContent(), this.getReplyCommentId(),this.getReplyUserId()));
		} catch (Exception e) {
			results = null;
		}
		return "result>json";
	}

	public String deleteComment() {
		results = new ArrayList<Map<String, Object>>();
		try {
			workCommentService.deleteComment(this.getCommentId());
			basicInfo.put("result", "删除成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "删除失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String clickLikeComment() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		try {
			workCommentService.updateCommentLike(
					workCommentService.getWorkComment(this.getCommentId()),
					user.getId());
			basicInfo.put("result", "点赞成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "点赞失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public String dropLikeComment() {
		results = new ArrayList<Map<String, Object>>();
		User user = SystemContext.getCurrentUser();
		try {
			workCommentService.dropCommentLike(
					workCommentService.getWorkComment(this.getCommentId()),
					user.getId());
			basicInfo.put("result", "撤回成功");
		} catch (Exception e) {
			System.out.println(e);
			basicInfo.put("result", "撤回失败");
		}
		results.add(basicInfo);
		return "result>json";
	}

	public WorkComment getWorkComment() {
		return workComment;
	}

	public void setWorkComment(WorkComment workComment) {
		this.workComment = workComment;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(Integer replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}
