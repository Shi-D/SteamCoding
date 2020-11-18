package com.framework.authority.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.framework.authority.entity.Authority;
import com.framework.authority.entity.MenuNode;
import com.framework.authority.entity.Role;
import com.framework.authority.entity.User;
import com.framework.common.CollectionUtil;
import com.framework.common.SystemContext;
import com.framework.system.common.base.service.impl.BaseServiceImpl;

@Service("authorityService")
public class AuthorityService extends BaseServiceImpl<Authority> {

	@Autowired
	private RoleService roleService;

	/**
	 * 获取用户的所有权限
	 * @param user
	 * @return
	 */
	public List<Authority> loadUserAuthorities(User user) {
		List<Authority> all_authority = new ArrayList<Authority>();
		List<Role> roles = roleService.queryByUser(user);
		for (Role role : roles) {
			all_authority.addAll(role.getAuthorities());
		}
		return all_authority;
	}
	
	/**
	 * 获取用户所有权限，并封装成GrantedAuthority对象集合保存到用户对象中
	 * @param user
	 * @return
	 */
	public void loadUserGrantedAuthorities(User user) {
		// 获取用户所有权限
		List<Authority> authorities = this.loadUserAuthorities(user);
		
		// 讲权限转换成GrantedAuthority对象集合
		List<GrantedAuthority> grantedAuthoritys = new ArrayList<GrantedAuthority>();
		Iterator<Authority> iter = authorities.iterator();
		while (iter.hasNext()) {
			Authority auth = iter.next();
			grantedAuthoritys.add(new SimpleGrantedAuthority(auth.getAuthName()));
		}
		
		user.setAuthorities(grantedAuthoritys);
	}
	
	/* 将权限转换成Map */
	public Map<String, Object> changeToMap(List<Authority> all_authority) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Authority authority : all_authority) {
			result.put(authority.getAuthName(), authority.getId());
		}
		return result;
	}

	/**
	 * 获取所有的权限，并且根据supId进行分组
	 * 
	 * @return
	 */
	public Map<Integer, List<Authority>> queryAllToMap() {
		String hql = "from Authority order by order";
		List<Authority> authorityList = this.find(hql);
		Map<Integer, List<Authority>> authMap = authToMap(authorityList);
		return authMap;
	}

	/* 将权限转成map */
	private Map<Integer, List<Authority>> authToMap(List<Authority> authorityList) {
		Map<Integer, List<Authority>> authMap = new HashMap<Integer, List<Authority>>();
		for (Authority authority : authorityList) {
			List<Authority> childAuth = authMap.get(authority.getSupId());
			if (childAuth == null) {
				childAuth = new ArrayList<Authority>();
			}
			childAuth.add(authority);
			authMap.put(authority.getSupId(), childAuth);
		}
		return authMap;
	}

	/* 根据当前用户获取权限 */
	public List<MenuNode> queryUserAuthByRole(Role role) {
		Set<Role> roles = SystemContext.getCurrentUser().getRoles();
		List<Authority> authList = new ArrayList<Authority>();
		for (Role r : roles) {
			authList.addAll(r.getAuthorities());
		}
		Map<Integer, List<Authority>> allAuthMap = authToMap(authList);
		List<Authority> hasAuthList = roleService.queryAuthority(role);
		List<Authority> parentAuthList = allAuthMap.get(0);

		List<MenuNode> children = generateChildren(parentAuthList, allAuthMap, hasAuthList);
		return children;
	}

	public List<MenuNode> queryByRole(Role role) {
		Map<Integer, List<Authority>> allAuthMap = queryAllToMap();
		List<Authority> hasAuthList = roleService.queryAuthority(role);
		List<Authority> parentAuthList = allAuthMap.get(0);

		List<MenuNode> children = generateChildren(parentAuthList, allAuthMap, hasAuthList);
		return children;
	}
	
	/**
	 * 获取所有权限
	 * 
	 * @return
	 * @author LEVEL
	 * @date 2016年9月25日 下午5:38:37
	 */
	public List<MenuNode> queryAllAuthorities() {
		Map<Integer, List<Authority>> allAuthMap = queryAllToMap();
		List<Authority> hasAuthList = new ArrayList<>();
		List<Authority> parentAuthList = allAuthMap.get(0);

		List<MenuNode> children = generateChildren(parentAuthList, allAuthMap, hasAuthList);
		return children;
	}

	/**
	 * 递归创建权限树
	 * 
	 * @param auths
	 *            最高父类权限集合
	 * @param allAuthMap
	 *            根据父类id归类好的权限Map
	 * @param hasAuthList
	 *            当前角色所拥有的权限，用于设置权限树的checked字段的值
	 * @return
	 */
	private List<MenuNode> generateChildren(List<Authority> auths, Map<Integer, List<Authority>> allAuthMap, List<Authority> hasAuthList) {
		if (auths == null) {
			return null;
		} else {
			// 初始化返回数据
			List<MenuNode> children = null;
			for (int i = 0; i < auths.size(); i++) {
				Authority auth = auths.get(i);

				if (children == null) {
					children = new ArrayList<MenuNode>();
				}
				/* 以下代码是将权限转换为节点 */
				MenuNode node = new MenuNode();
				node.setLeastSubclass(auth.getLeastSubclass());
				node.setId(auth.getId().toString());
				node.setText(auth.getAuthDescription());
				node.setParentId(auth.getSupId());
				// 根据数据库中icon字段判断是否设置图标
				String iconCls = auth.getIcon();
				if (iconCls != null && !"".equals(iconCls)) {
					node.setIconCls(iconCls);
				}
				// 如果拥有当前权限，则设置节点的checked值为true
				if (hasAuthList.contains(auth)) {
					node.setChecked(true);
				}
				children.add(node);

				// 获取子权限
				List<Authority> authChildren = allAuthMap.get(auth.getId().intValue());
				if (authChildren == null) { // 没有子权限，设置当前节点leaf为true，继续操作下一个权限对象
					node.setLeaf(true);
					continue;
				} else { // 有子权限，设置当前节点leaf为false，并递归子权限
					node.setLeaf(false);
					node.setChildren(generateChildren(authChildren, allAuthMap, hasAuthList));
				}
			}
			return children;
		}
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void batchDelete(List<Integer> ids)
	{
		String hql = "DELETE FROM Authority WHERE id IN(:param1)";

		Map<Object, Object> params = new HashMap<Object, Object>();

		params.put("param1", ids);

		this.executeHQLUpdate(hql, params);
	}
	
	/**
	 * 根据父类id集合获取子类id集合
	 * 
	 * @param ids
	 * @return
	 * @author LEVEL
	 * @date 2016年9月27日 下午11:21:01
	 */
	public List<Integer> getChiledrenIdsByParentsIds(List<Integer> ids)
	{
		String hql = "SELECT id AS id FROM Authority WHERE supId IN(:param1)";

		Map<Object, Object> params = new HashMap<Object, Object>();

		params.put("param1", ids);
		
		List<Map<String, Object>> result = this.getResultByHQL(hql, params);
		
		if (result == null || result.size() == 0)
		{
			return null;
		}
		
		List<Integer> list = new ArrayList<>(result.size());
		for (Map<String, Object> map : result)
		{
			list.add((Integer) map.get("id"));
		}
		return list;
	}
	
	/**
	 * 根据子类id集合获取所有层级父id集合
	 * 
	 * @param ids
	 * @return
	 * @author LEVEL
	 * @date 2016年10月31日 下午6:21:02
	 */
	public List<Integer> getSuperIdsByChildrenIds(List<Integer> ids)
	{
		String hql = "SELECT supId AS supId FROM Authority WHERE id IN(:param1)";

		Map<Object, Object> params = new HashMap<Object, Object>();

		params.put("param1", ids);
		
		List<Map<String, Object>> result = this.getResultByHQL(hql, params);
		
		if (result == null || result.size() == 0)
		{
			return null;
		}
		
		List<Integer> list = new ArrayList<>(result.size());
		for (Map<String, Object> map : result)
		{
			list.add((Integer) map.get("supId"));
		}
		return list;
	}
	
	/**
	 * 如果可用则修改当前权限以及所有层级父权限可用，否则修改当前权限以及所有层级子权限不可用
	 * 
	 * @param ids 当前权限主键集合
	 * @param hasAvailable 是否可用，true可用|false不可用
	 * @author LEVEL
	 * @date 2016年10月31日 下午5:22:15
	 */
	public void batchUpdateAvailable(List<Integer> ids, boolean hasAvailable)
	{
		if (CollectionUtil.isEmpty(ids))
		{
			return;
		}
		List<Integer> updateIds = new ArrayList<Integer>();
		
		if (!hasAvailable)
		{
			List<Integer> supIds = ids;
			while (supIds != null && !supIds.isEmpty())
			{
				updateIds.addAll(supIds);
				supIds = getChiledrenIdsByParentsIds(supIds);
			}
		}
		else
		{
			List<Integer> childrenIds = ids;
			while (childrenIds != null && !childrenIds.isEmpty())
			{
				updateIds.addAll(childrenIds);
				childrenIds = getSuperIdsByChildrenIds(childrenIds);
			}
		}
		
		String hql = "UPDATE Authority SET available=:param2 WHERE id IN(:param1)";

		Map<Object, Object> params = new HashMap<Object, Object>();

		params.put("param1", updateIds);
		params.put("param2", hasAvailable);

		this.executeHQLUpdate(hql, params);
	}
}
