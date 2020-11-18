package com.steam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.framework.utils.URLUtils;
import com.steam.entity.Video;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

@Service
public class VideoService extends BaseServiceImpl<Video> {
	@Autowired
	UserService userService;

	public void uploadVideoInStore(String videoName, String videoLink,
			Integer userId, Integer organizationId) {
		Video video = new Video();
		video.setVideoName(videoName);
		video.setVideoLink(videoLink);
		video.setCreationTime(new Date());
		video.setUploadUserId(userId);
		video.setOrganizationId(organizationId);
		this.save(video);
	}

	public List<Map<String, Object>> queryVideo(Integer organizationId) {
		System.out.println("organizationId" + organizationId);
		String hql = "select videoId as videoId, videoName as videoName, videoLink as videoLink from Video where organizationId = ? order by creationTime desc";
		return this.getResultByHQL(hql, organizationId);
	}

	// 返回所有视频
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryAllVideoService() {
		Results temp = new Results();
		String hql;
		try {
			User user = SystemContext.getCurrentUser();
			Integer userRole = user.getUserRole();
			List<Map<String, Object>> result;
			Integer organizationId = user.getOrganization().getId();
			if(userRole == 2){
			Integer userId = user.getId();
			hql = "select videoId as videoId, videoName as videoName, videoLink as videoLink,uploadUserId as uploadUserId from Video where organizationId = ? and uploadUserId = ?";
			result = this.getResultByHQL(hql, organizationId, userId);
			for (Map<String, Object> i : result) {
				User users = userService.get(User.class,
						Integer.parseInt(i.get("uploadUserId").toString()));
				i.put("userName", users.getUserName());
			}
			temp.setMessageValue(result);
			hql = "select videoId as videoId, videoName as videoName, videoLink as videoLink,uploadUserId as uploadUserId from Video where organizationId = ? and uploadUserId <> ?";
			result = this.getResultByHQL(hql, organizationId, userId);
			for (Map<String, Object> i : result) {
				User users = userService.get(User.class,
						Integer.parseInt(i.get("uploadUserId").toString()));
				i.put("userName", users.getUserName());
			}
			List<List<Map<String, Object>>> tempList = new ArrayList<List<Map<String, Object>>>();
			tempList.add((List<Map<String, Object>>) temp.getMessageValue());
			tempList.add(result);
			temp.addAllValue(true, tempList);
			}
			else if(userRole == 1){
				hql = "select videoId as videoId, videoName as videoName, videoLink as videoLink,uploadUserId as uploadUserId from Video where organizationId = ?";
				result = this.getResultByHQL(hql, organizationId);
				for (Map<String, Object> i : result) {
					User users = userService.get(User.class,
							Integer.parseInt(i.get("uploadUserId").toString()));
					i.put("userName", users.getUserName());
				}
				temp.setMessageValue(result);
				temp.setResultValue(true);
			}
		} catch (Exception e) {
			System.out.println(e);
			temp.addAllValue(false, null);
		}
		return temp.getResult();
	}

	// 删除视频
	public List<Map<String, Object>> deleteVideoService(List<Integer> videoId) {
		Results result = new Results();
		try{
			for (Integer i : videoId) {
				Video video = this.get(Video.class, i);
				String link = video.getVideoLink();
				File file = new File(SteamConstants.ABSOLUTE_URL_WITHOUT_ACCOUNT + link);
				System.out.println(file);
				if(file != null){
					file.delete();
				}
				this.delete(video);
				String hql = "delete from Video where videoId in(" + i + ")";
				this.executeHQLUpdate(hql);
		}
		result.addAllValue(true,"删除成功");
		}catch(Exception e){
			result.addAllValue(false,"删除失败");
		}
		return result.getResult();
	}

}
