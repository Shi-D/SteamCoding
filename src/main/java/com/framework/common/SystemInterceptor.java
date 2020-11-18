package com.framework.common;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class SystemInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;

	/* 此方法主要应用于在将报错信息打印到控制台.并将错误信息反应至前台 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
