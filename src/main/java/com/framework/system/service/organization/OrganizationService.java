package com.framework.system.service.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.User;
import com.framework.authority.service.RoleService;
import com.framework.authority.service.UserService;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.entity.Organization;

@Service
public class OrganizationService extends BaseServiceImpl<Organization>
{
	public static final String SERVICE_BEAN_NAME = "organizationService";
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	

	public List<Organization> querySimpleAll()
	{
		String hql = "select new Organization(id,organizationName) from Organization order by id";
		return this.find(hql);
	}

	/* 获取所有机构 */
	public Map<String, String> queryAllSimpleProperty()
	{
		String hql = "select organizationCode as organizationCode,organizationName as organizationName from Organization";
		List<Map<String, Object>> list = this.getResultByHQL(hql);
		Map<String, String> result = new HashMap<String, String>();
		for (Map<String, Object> map : list)
		{
			if (map.get("organizationCode") != null)
			{
				String organizationCode = map.get("organizationCode").toString();
				String organizationName = map.get("organizationName").toString();
				result.put(organizationCode, organizationName);
			}
		}
		return result;
	}

	/**
	 * 获取所有机构
	 * 
	 * @param containAdmin
	 *            true表示包含管理员，false表示不包含管理员
	 * @return
	 */
	public List<Organization> queryAllOrgs(Boolean containAdmin)
	{
		List<Organization> organizationList = null;
		if (containAdmin)
		{
			organizationList = this.findAll(Organization.class);
		}
		else
		{
			String hql = "FROM Organization WHERE id <> ?";
			organizationList = this.find(hql, 1);
		}
		return organizationList;
	}

	/**
	 * 根据机构编号创建机构树，机构编号如果为null则是全部机构。
	 * 
	 * @param organizationId
	 *            机构编号
	 * @return
	 */
	public String createTreeData(Integer organizationId)
	{
		List<Organization> list = null;

		if (organizationId == null)
		{
			list = this.findAll(Organization.class);
		}
		else
		{
			list = new ArrayList<Organization>(1);
			list.add(this.get(Organization.class, organizationId));
		}

		StringBuilder sb = new StringBuilder();

		Iterator<Organization> iter = list.iterator();

		sb.append("{children:[");

		while (iter.hasNext())
		{
			Organization org = iter.next();

			if (iter.hasNext())
			{
				sb.append("{id:" + org.getId() + ",organizationName:'" + org.getOrganizationName() + "',iconCls:'icon-organization',leaf:true},");
			}
			else
			{
				sb.append("{id:" + org.getId() + ",organizationName:'" + org.getOrganizationName() + "',iconCls:'icon-organization',leaf:true}");
			}
		}

		sb.append("]}");

		return sb.toString();
	}


	/**
	 * 新增机构
	 */
	public void save(Organization organization)
	{
		String hql = "select count(*) from Organization where organizationCode = ?";

		Integer count = this.getTotalCountByHQL(hql, organization.getOrganizationCode());

		if (count == 0)
		{
			organization.setId(organization.getOrganizationCode());

			super.save(organization);

			// 新增机构缓存
		
		}
	}

	/**
	 * 删除机构
	 * 
	 * @param organizationIds
	 */
	public void deleteOrganization(String organizationIds)
	{
		String[] ids = organizationIds.split(",");

		for (String id : ids)
		{
			Organization sourceOrganization = this.get(Organization.class, Integer.parseInt(id));

			List<User> userList = userService.queryByOrganization(sourceOrganization);

			// 清空用户与机构的关联关系
			for (User user : userList)
			{
				user.setOrganization(null);

				userService.update(user);
			}
			
			roleService.deleteRoleAndOrganizationRelation(sourceOrganization.getId());

			// 删除机构的系统参数关联关系

			this.delete(sourceOrganization);

			// 删除机构缓存
	
		}
	}

}