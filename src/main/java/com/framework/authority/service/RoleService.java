package com.framework.authority.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.Authority;
import com.framework.authority.entity.Role;
import com.framework.authority.entity.User;
import com.framework.system.common.base.service.impl.BaseServiceImpl;

@Service
public class RoleService extends BaseServiceImpl<Role>
{
	public List<Authority> queryAuthority(Role role)
	{
		role = this.get(Role.class, role.getId());
		Set<Authority> authSet = role.getAuthorities();
		List<Authority> authList = new ArrayList<Authority>();
		authList.addAll(authSet);
		return authList;
	}

	/* 根据用户查询角色 */
	public List<Role> queryByUser(User user)
	{
		String hql = "select role from Role role join role.users users where users=?";
		List<Role> roleList = this.find(hql, user);
		return roleList;
	}

	/* 修改角色 */
	public void updateRole(Role role)
	{
		Role sourceRole = this.get(Role.class, role.getId());
		sourceRole.setRoleComment(role.getRoleComment());
		sourceRole.setRoleName(role.getRoleName());
		this.update(sourceRole);

	}

	/**
	 * 关联权限(关联权限之前先删除该角色与权限关联关系)<br/>
	 * 使用SQL语句来删除和插入数据，提交效率
	 * 
	 * @param role
	 * @param authorities
	 */
	public void updateRelationAuthority(Role role, String authorities)
	{
		/* 删除该角色与权限的旧关系 */
		long roleId = role.getId();
		String sql = "delete from SEC_ROLE_AUTHORITIES where ROLE_ID in(" + roleId + ")";
		this.executeSQLUpdate(sql);

		/* 关联该角色与权限的新关系 */
		if (authorities != null && !"".equals(authorities))
		{
			StringBuffer sb = new StringBuffer();
			sb.append("insert into SEC_ROLE_AUTHORITIES (ROLE_ID, AUTH_ID) values ");
			String[] ids = authorities.split(",");

			for (String id : ids)
			{
				sb.append("(" + roleId + "," + id + "),");
			}
			sql = StringUtils.stripEnd(sb.toString(), ",");
			this.executeSQLUpdate(sql);
		}
	}

	public void deleteRole(String roleIds)
	{
		/* 先删除角色与权限之间的关联关系，再删除角色 */
		String sql = "delete from SEC_ROLE_AUTHORITIES where ROLE_ID in(" + roleIds + ")";
		this.executeSQLUpdate(sql);
		String hql = "delete Role where id in(" + roleIds + ")";
		this.executeHQLUpdate(hql);
	}

	/**
	 * 删除角色和机构的关联关系
	 * 
	 * @param organizationId
	 *            机构编号
	 */
	public void deleteRoleAndOrganizationRelation(Integer organizationId)
	{
		String hql = "UPDATE Role SET organizationId=NULL WHERE organizationId=?";
		this.executeHQLUpdate(hql, organizationId);
	}
}
