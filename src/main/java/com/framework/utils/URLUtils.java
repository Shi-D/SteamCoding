package com.framework.utils;

import com.framework.constants.SteamConstants;

public class URLUtils {

	public static String generateURLforWork(String path) {
        return SteamConstants.ABSOLUTE_URL + path + "/works";
	}

	public static String generateURLforCourse(String path) {
		return SteamConstants.ABSOLUTE_URL + path + "/courses";
	}
	
	
	public static String generateURLforUserInfo(String path) {
		return SteamConstants.ABSOLUTE_URL + path + "/userInfo";
	}
	
	public static String generateURLforVideoStore(String path) {
		return SteamConstants.ABSOLUTE_URL + path + "/video";
	}
	
	public static String generateURLforHomework(String path) {
		return SteamConstants.ABSOLUTE_URL + path + "/homework";
	}
	
	public static String generateURLforUpdateToDB(String userCode,String fileName) {
		return "account/"+userCode+ "/video/"+fileName;
	}
}
