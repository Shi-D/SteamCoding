package com.steam.action.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.system.common.base.action.BaseGridAction;
import com.steam.service.WorkService;

public class IndexInfoAction extends BaseGridAction {

	private static final long serialVersionUID = 4799109549500319502L;
	@Autowired
	WorkService workService;
	
	private Map<String, Object> basicInfo = new HashMap<String, Object>();//用于返回成功失败信息
	
	public String countAllWorks(){
		results = new ArrayList<Map<String, Object>>();
		basicInfo.put("NumberOfAllWorks", workService.countTotalPublishedWorks());
		basicInfo.put("NumberOfTodayWorks", workService.countTodayPublishedWorks());
		results.add(basicInfo);
		return "result>json";
	}
	
	public String queryLastestWorksByrecommented(){
		results = workService.queryLastestWorksByrecommented();
		return "result>json";
	}
	
	public String queryHottestWorks(){
		results = workService.queryHottestWorks();
		return "result>json";
	}
	
	public String queryLastestWorksByPublished(){
		results = workService.queryLastestWorksByPublished();
		return "result>json";
	}
	
	public String queryLikestWorks(){
		results = workService.queryLikestWorks();
		return "result>json";
	}
	
	public String queryPageviewsWorks(){
		results = workService.queryPageviewsWorks();
		return "result>json";
	}
	
}
