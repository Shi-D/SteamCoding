package com.framework.authority.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.framework.authority.entity.User;
import com.framework.system.service.log.LoginLogService;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

	@Autowired
	private LoginLogService loginLogService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException{
		User user = (User) authentication.getPrincipal();
   
		loginLogService.loginLog(request, user);
		
		logger.info("登录成功[用户名：{}，姓名：{}，机构：{}，IP：{}]", user.getUserCode(), user.getUserName(), user.getOrganization().getOrganizationName(), request.getRemoteAddr());
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
