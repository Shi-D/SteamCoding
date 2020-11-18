package com.framework.authority.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.system.common.base.action.BaseAction;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -8357649612172585316L;
	private String failureMessage;
	
	/* 用户验证登录 */
	public String login() throws IOException {
		User user = SystemContext.getCurrentUser();
		results = new ArrayList<Map<String, Object>>();
		Map<String, Object> basicInfo = new HashMap<String, Object>();
		if (user != null) {
			basicInfo.put("userCode",user.getUserCode());
		} else {
			basicInfo.put("userCode",null);
		}
		results.add(basicInfo);
		return "result>json";
	}
	
	/*用户登录失败*/
	public String loginFailure() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		failureMessage = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString();
		System.out.println(failureMessage);
		return "failureMessage";
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
}
