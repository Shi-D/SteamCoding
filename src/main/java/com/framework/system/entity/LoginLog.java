package com.framework.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LOG_LOGIN")
public class LoginLog
{
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "USER_CODE")
	private String userCode; // user

	@Column(name = "LOGIN_TIME")
	private Date loginTime; // loginTime

	@Column(name = "LOGOUT_TIME")
	private Date logoutTime; // logoutTime

	@Column(name = "LOGIN_IP")
	private String loginIp; // computerIp

	@Column(name = "ORGANIZATION_ID")
	private Integer organizationId; // organizationId

	public LoginLog()
	{
	}

	public LoginLog(String userCode, Date loginTime, String loginIp, Integer organizationId)
	{
		this.userCode = userCode;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.organizationId = organizationId;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUserCode()
	{
		return userCode;
	}

	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}

	public Date getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	public Date getLogoutTime()
	{
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime)
	{
		this.logoutTime = logoutTime;
	}

	public String getLoginIp()
	{
		return loginIp;
	}

	public void setLoginIp(String loginIp)
	{
		this.loginIp = loginIp;
	}

	public Integer getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId)
	{
		this.organizationId = organizationId;
	}

}
