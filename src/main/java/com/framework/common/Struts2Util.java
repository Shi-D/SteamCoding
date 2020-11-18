package com.framework.common;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

/**
 * 获得Request、Response、ServletContext、Session 设置、获取、移除Session中的参数 获取Request中的参数
 * 
 */
public class Struts2Util {

	/**
	 * 获取Request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获取ServletContext，即application
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 获取Response
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 设置Session参数
	 * 
	 * @param key
	 * @param object
	 */
	public static void setSessionAttribute(String key, Object object) {
		getSession().setAttribute(key, object);
	}

	/**
	 * 移除Session参数
	 * 
	 * @param key
	 */
	public static void removeSessionAttribute(String key) {
		getSession().removeAttribute(key);
	}

	/**
	 * 获取Session参数
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSessionAttribute(String key) {
		return getSession().getAttribute(key);
	}

	public static void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public static Object getRequestAttribute(String key) {
		return getRequest().getAttribute(key);
	}

	public static void removeRequestAttribute(String key) {
		getRequest().removeAttribute(key);
	}

	/**
	 * 获取Request参数
	 * 
	 * @param key
	 * @return
	 */
	public static String getParameter(String key) {
		return getRequest().getParameter(key);
	}

	public static void write(String out) {
		try {
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().print(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRemoteAddr() {
		return getRequest().getRemoteAddr();
	}

	public static String getRemoteHost() {
		return getRequest().getRemoteHost();
	}
}
