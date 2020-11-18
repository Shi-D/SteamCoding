package com.framework.authority.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.Authority;
import com.framework.authority.entity.Role;
import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;

/**
 * security进行用户验证获取用户对象
 * 
 * @author Administrator
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService
{
	private Logger logger = LoggerFactory.getLogger(MyUserDetailServiceImpl.class);

	@Autowired
	private UserService userService;
	

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{
		User user = userService.getByCode(username);
		if (null == user){
			logger.error("用户不存在，用户名：{}", username);
			throw new UsernameNotFoundException("Bad credentials");
		}else if (!user.getUserFlag()){
			logger.error("该用户处于锁定状态，用户名：{}", username);
			throw new UsernameNotFoundException("该用户处于锁定状态，用户名：" + username);
		}
		
		/*
		 * 获取用户所有权限，并封装成GrantedAuthority对象集合保存到用户对象中
		 */
		List<GrantedAuthority> grantedAuthoritys = new ArrayList<GrantedAuthority>();

		// 将权限转换成GrantedAuthority对象集合
		for (Role role : user.getRoles())
		{
			for (Authority authority : role.getAuthorities())
			{
				grantedAuthoritys.add(new SimpleGrantedAuthority(authority.getAuthName()));
			}
		}

		user.setAuthorities(grantedAuthoritys);

		return user;
	}
}