package com.steam.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.framework.system.entity.Organization;

@Service
public class OrganizationAdminService extends BaseServiceImpl<Organization> {

	/**
	 * 根据OrganizationIds数组批量删除
	 * 
	 * @param ids
	 */
	public void deleteOrganization(String organizationIds) throws Exception {
//		String hql = "delete from Organization where id in(" + 3 + ")";
		String hql = "delete from Organization where id in(" + organizationIds + ")";
		this.executeHQLUpdate(hql);
	}
	
	public Integer addOrganization(String organizationName, String organizationSname) throws Exception {
		Date date = new Date();
		Organization organization = new Organization();
		organization.setOrganizationName(organizationName);
		organization.setOrganizationSname(organizationSname);
		organization.setCreationTime(date);
		this.save(organization);
		/*String hql = "INSERT INTO STEAM_ORGANIZATION VALUES ("+organization.getOrganizationId()+",'"+dateFormat.format(date)+"')";
		this.executeSQLUpdate(hql);*/
		return organization.getId();
	}

	public List<Map<String, Object>> updateOrganization(Integer id,String organizationName,String organizationSname) {
		Results result = new Results();
		try {
			String sql = "UPDATE T_ORGANIZATION SET ORGANIZATION_NAME = '" + organizationName
					+ "', ORGANIZATION_SNAME ='" + organizationSname + "'  WHERE ORGANIZATION_ID = " + id;
			this.executeSQLUpdate(sql);
			result.setResultValue("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultValue("修改失败");
		}
		return result.getResult();
	}
	
	public List<Map<String, Object>> getOrganization() {
		String hql = "select id as organizationId, organizationName as organizationName from Organization";
		return this.getResultByHQL(hql);
	}
	
	/*
	public void addAdminToOrganization(Integer organizationId, Integer[] adminId)  {
		int len = adminId.length;
		String sql = "";
		
		for (int i = 0; i < len; i++) {
			System.out.println(adminId[i]);
			sql = "insert into STEAM_ORGANIZATION ( ORGANIZATION_ID,ADMIN_ID ) values (" + organizationId + "," + adminId[i] + ")";
			this.executeSQLUpdate(sql);	
		}
	}
	*/
}

	