package com.framework.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;

/**
 * 
 * @author {In-Death}
 *
 */
@Service
public class SystemContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	private static User system = null;

	static {
		system = new User();
		system.setId(1);
		system.setUserCode("system");
		system.setUserName("系统");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SystemContext.applicationContext = applicationContext;
	}

	public static User getSystemUser() {
		return system;
	}

	/**
	 * 获取当前用户登录ID
	 * 
	 * @return
	 */
	public static Integer getCurrentUserId() {
		User user = getCurrentUser();
		if (user == null) {
			return null;
		}

		return user.getId();
	}

	/**
	 * 获取当前用户登录名
	 * 
	 * @return
	 */
	public static String getCurrentUserCode() {
		User user = getCurrentUser();
		if (user == null)
			return "";
		return user.getUsername();
	}

	/**
	 * 获取当前用户真实姓名
	 * 
	 * @return
	 */
	public static String getCurrentUserName() {
		User user = getCurrentUser();
		if (user == null)
			return "";
		return user.getUsername();
	}

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		try {
			Authentication authen = SecurityContextHolder.getContext().getAuthentication();
			if (authen == null) {
				return null;
			}

			Object object = authen.getPrincipal();

			if (object == null) {

				return null;
			}

			if (object instanceof User) {
				return (User) object;
			}

			return null;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从Spring里获取Bean
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}

}
