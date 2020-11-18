package com.framework.system.common.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.common.Struts2Util;
import com.framework.common.SystemContext;
import com.framework.system.common.entity.AjaxResult;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 作为所有Action的基类
 */
@ParentPackage("default-package")
public abstract class BaseAction extends ActionSupport implements Preparable
{
	private static final long serialVersionUID = 1L;
	
	protected Logger logger = null;
	
	public BaseAction()
	{
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public static final String RESULT_MAP = "result";
	public static final String RESULTS_LIST_MAP = "results";
	public static final String AJAX_RESULT = "ajaxResult";
	public static final String GRID_SEARCH = "gridSearch";
	
	@Autowired
	private UserService userService;
	/**
	 * 
	 */
	protected HttpServletRequest request = Struts2Util.getRequest();

	protected AjaxResult ajaxResult = new AjaxResult();

	private Map<String, String> uuids;

	/**
	 * 存放Map型的单个结果,Map对于JSON来说比较好控制
	 */
	protected Map<String, Object> result = new HashMap<String, Object>();

	/**
	 * 多个结果
	 */
	protected List<Map<String, Object>> results;

	protected List<?> listResult;
	
	protected Object jsonResult;

	/**
	 * 默认实现
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	@Override
	public void prepare() throws Exception
	{

	}

	public AjaxResult getAjaxResult()
	{
		return ajaxResult;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addAjaxResultMessage(String key, Object value)
	{
		this.ajaxResult.getMessages().put(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void addAjaxResultFieldError(String key, Object value)
	{
		this.ajaxResult.setSubOk(false);
		this.ajaxResult.getErrors().put(key, value);
	}

	/**
	 * 获取用户ip
	 * 
	 * @return
	 */
	public String getRemoteAddr()
	{
		return request.getRemoteAddr();
	}

	/**
	 * 获得当前登录用户
	 * 
	 * @return
	 */
	public User getCurrentUser()
	{
		return SystemContext.getCurrentUser();
	}



	/**
	 * 是否被授予指定的权限
	 * 
	 * @param authorizedUrl
	 *            权限
	 * @return true|false
	 */
	protected boolean isAuthorized(String authorizedUrl)
	{
		if (null == userService)
		{
			userService = (UserService) SystemContext.getBean("userService");
		}

		// 授权是否是管理员
		return !userService.isAuthorized(authorizedUrl);
	}

	
	public Map<String, String> getUuids()
	{
		return uuids;
	}

	public void setUuids(Map<String, String> uuids)
	{
		this.uuids = uuids;
	}

	public Map<String, Object> getResult()
	{
		return result;
	}

	public void setResult(Map<String, Object> result)
	{
		this.result = result;
	}

	public List<Map<String, Object>> getResults()
	{
		return results;
	}

	public void setResults(List<Map<String, Object>> results)
	{
		this.results = results;
	}

	public List<?> getListResult()
	{
		return listResult;
	}

	public void setListResult(List<?> listResult)
	{
		this.listResult = listResult;
	}

	public Object getJsonResult()
	{
		return jsonResult;
	}

	public void setJsonResult(Object jsonResult)
	{
		this.jsonResult = jsonResult;
	}

}
