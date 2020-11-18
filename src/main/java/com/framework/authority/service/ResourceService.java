package com.framework.authority.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.Authority;
import com.framework.authority.entity.Resource;
import com.framework.system.common.base.service.impl.BaseServiceImpl;

@Service
public class ResourceService extends BaseServiceImpl<Resource> {

	public List<Resource> queryURLByAuthority(Integer authorityID) {
		String hql = "select auth.resource from Authority auth where auth.id = ?";
		List<Resource> list = (List<Resource>) this.find(hql, authorityID);
		return list;
	}

	/**
	 * 获取所有资源，并取出资源对象的value属性的值重新组装成字符串集合
	 * 
	 * @return
	 */
	public List<String> getResources() {
		List<Resource> resources = this.findAll(Resource.class);
		List<String> resourcesKey = new ArrayList<String>();
		for (Resource resource : resources) {
			resourcesKey.add(resource.getValue());
		}
		return resourcesKey;
	}

	public Collection<ConfigAttribute> getConfigAttributes(String resourceKey) {
		Resource res = getResourceByValue(resourceKey);
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		for (Authority auth : res.getAuthorities()) {
			atts.add(new SecurityConfig(auth.getAuthName()));
		}
		return atts;
	}

	public Resource getResourceByValue(String value) {
		String hql = "from Resource where value=?";
		Object resourceObj = this.uniqueResultByHQL(hql, value);
		if (resourceObj != null && resourceObj instanceof Resource) {
			return (Resource) resourceObj;
		}
		return null;
	}

}
