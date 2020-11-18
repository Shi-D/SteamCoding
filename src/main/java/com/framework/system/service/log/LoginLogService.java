package com.framework.system.service.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.framework.authority.entity.User;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.entity.LoginLog;

@Component
public class LoginLogService extends BaseServiceImpl<LoginLog>
{
	/**
	 * 登录
	 */
	public void loginLog(HttpServletRequest request, User user)
	{
		LoginLog log = new LoginLog(user.getUserCode(), new Date(), request.getRemoteAddr(), user.getOrganization().getId());

		this.save(log);
	}

	/**
	 * 登出日志
	 */
	public void logoutLog(User user)
	{
		String hql = "FROM LoginLog ll WHERE ll.userCode = ? AND ll.logoutTime is null ORDER BY ll.loginTime DESC";

		LoginLog log = (LoginLog) this.uniqueResultByHQL(hql, user.getUserCode());

		log.setLogoutTime(new Date());

		this.update(log);
	}
}
