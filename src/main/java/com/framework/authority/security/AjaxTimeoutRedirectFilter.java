package com.framework.authority.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;

/**
 * 自定义AJAX请求时Session过期重定向的过滤器
 * 
 * @author In-Death
 * 
 */
public class AjaxTimeoutRedirectFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(AjaxTimeoutRedirectFilter.class);

	private int customSessionExpiredErrorCode = 901;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		User user = SystemContext.getCurrentUser();
		StringBuffer requestUrl = request.getRequestURL();
		try{
			if (user == null) {
				// 根据'x-requested-with : XMLHttpRequest'请求头的信息判断是否为AJAX请求
				String ajaxHeader = request.getHeader("X-Requested-With");
				if ("XMLHttpRequest".equals(ajaxHeader) && requestUrl.indexOf("/user/") < 0){
					logger.info("Ajax call detected, send {} error code", this.customSessionExpiredErrorCode);
					HttpServletResponse resp = (HttpServletResponse) response;
					resp.sendError(this.customSessionExpiredErrorCode);
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void setCustomSessionExpiredErrorCode(int customSessionExpiredErrorCode) {
		this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
	}
}
