package com.steam.action.workmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.system.common.base.action.BaseGridAction;
import com.steam.service.WorkService;

public class WorkInfoAction extends BaseGridAction{

	private static final long serialVersionUID = -4054967888244449269L;
	
	@Autowired
	WorkService workService;
	
	private Integer workId;
	private String workName;
	private String workDescription;
	
	private String className;
	
	private Integer pageNo;
	private Integer pageSize;
	private boolean search;
	
	private Map<String, Object> basicInfo = new HashMap<String, Object>();//用于返回成功失败信息
	
	//查询本人作品
	@SuppressWarnings("unchecked")
	public String queryMyWorks(){
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "select workId as workId, userId as userId,userName as userName,isRecommended as isRecommended, courseWorkName as courseWorkName, workLink as workLink,likeCount as likeCount, workName as workName,creationTime as createTime,workDescription as workDescription from WorkUser where userId = "+user.getId();
		workService.find(this.getPager(), hql, this.getFilter());
		
		if(this.getPager().getTotalCount()==0){
			basicInfo.put("totalCount", 0);
			basicInfo.put("totalPage", 1);
		}
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		String link="";
		for (int i = 0; i < results.size(); i++) {
			link = results.get(i).get("workName").toString()+".png";
			results.get(i).put("workCover", link);
		}
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}
	//查询机构下的作品
	@SuppressWarnings("unchecked")
	public String queryOtherWorks(){
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		this.getPager().setPageNo(pageNo);
		this.getPager().setPageSize(pageSize);
		this.getPager().setSearch(search);
		String hql = "";
		if(null!=this.getClassName())
			hql = "select workId as workId, userId as userId,userName as userName,isRecommended as isRecommended, workLink as workLink,likeCount as likeCount, workName as workName, courseWorkName as courseWorkName, creationTime as createTime from WorkUser where organizationId = "+user.getOrganization().getId()
					+" and isPublished = 1 and userId not in (select userId as userId from WorkUser where userId = "+user.getId()+") and (studentClassName like '%"+this.getClassName()+"%' or teacherClassName like '%"+this.getClassName()+"%')";
		else
			hql = "select workId as workId, userId as userId,userName as userName,isRecommended as isRecommended, workLink as workLink,likeCount as likeCount, workName as workName,courseWorkName as courseWorkName, creationTime as createTime from WorkUser where organizationId = "+user.getOrganization().getId()
					+" and isPublished = 1 and userId not in (select userId as userId from WorkUser where userId = "+user.getId()+")";

		workService.find(this.getPager(), hql, this.getFilter());
		if(this.getPager().getTotalCount()==0){
			basicInfo.put("totalCount", 0);
			basicInfo.put("totalPage", 1);
		}
		basicInfo.put("totalCount", this.getPager().getTotalCount());
		basicInfo.put("totalPage", this.getPager().getTotalPage());
		results = (List<Map<String, Object>>) this.getPager().getDataset();
		String link="";
		for (int i = 0; i < results.size(); i++) {
			link = results.get(i).get("workName").toString()+".png";
			results.get(i).put("workCover", link);
		}
		if (!results.isEmpty())
			results.add(basicInfo);
		return "result>json";
	}

	//删除作品
	public String deleteWork(){
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		try{
			workService.deleteWork(user.getUsername(),this.getWorkId());
			basicInfo.put("result", "删除成功");
		}catch(Exception e){
			basicInfo.put("result", "删除失败");
        }
		results.add(basicInfo);
		return "result>json";
	}
	//发布作品
	public String publishWork(){
		results = new ArrayList<Map<String, Object>>();
		try{
			workService.publishWork(this.getWorkId(),this.getWorkName(),this.getWorkDescription());
			basicInfo.put("result", "发布成功");
		}catch(Exception e){
			System.out.println(e);
			basicInfo.put("result", "发布失败");
        }
		results.add(basicInfo);
		return "result>json";
	}
	//推荐作品
	public String recommendWork(){
		results = new ArrayList<Map<String, Object>>();
		try{
			workService.recommendOrRecallWork(this.getWorkId(),1);//参数传1代表推荐
			basicInfo.put("result", "推荐成功");
		}catch(Exception e){
			System.out.println(e);
			basicInfo.put("result", "推荐失败");
        }
		results.add(basicInfo);
		return "result>json";
	}
	//取消推荐作品
	public String recallWork(){
		results = new ArrayList<Map<String, Object>>();
		try{
			workService.recommendOrRecallWork(this.getWorkId(),0);//参数传0代表取消推荐
			basicInfo.put("result", "撤回成功");
		}catch(Exception e){
			System.out.println(e);
			basicInfo.put("result", "撤回失败");
        }
		results.add(basicInfo);
		return "result>json";
	}

    //根据作品id获取作品的路径
	public String getWorkLink(){
		User user = SystemContext.getCurrentUser();
		
		results = new ArrayList<Map<String, Object>>();
		results.addAll(workService.getWorkLink(workId, user.getId(), user.getUserRole()));
		
		String link="";
		for (int i = 0; i < results.size(); i++) {
			link = results.get(i).get("workLink").toString()+results.get(i).get("fileName").toString()+".sb3";
			results.get(i).put("link", link);
		}
		if (results.isEmpty()){
			basicInfo.put("result",false);
			results.add(basicInfo);
		}
		return "result>json";
	}
	
	
	public String recallPublish(){
		results = workService.recallPublishService(this.getWorkId());
		return "result>json";
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}
	public String getWorkDescription() {
		return workDescription;
	}
	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

}
