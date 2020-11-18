/**
 * com.zjpost.philately.model.system
 * MenuNode.java
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-7-16 Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.framework.authority.entity;

import java.util.List;

/**
 * ClassName:MenuNode Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author Libin Hong
 * @version
 * @since Ver 1.1
 * @Date 2011 2011-7-16 下午02:58:15
 * 
 */

public class MenuNode {
	private Integer entityId;
	private String id;
	private String text;
	private boolean leaf;
	private String iconCls;
	private List<MenuNode> children;
	private String quickSearchIndex;
	private boolean checked = false;
	private String leastSubclass;
	private Integer parentId;
	private Integer organizationId;
	private Integer ccAddressid;

	public MenuNode() {
	}

	public MenuNode(List<MenuNode> children) {
		super();
		this.children = children;
	}

	public String getQuickSearchIndex() {
		return quickSearchIndex;
	}

	public void setQuickSearchIndex(String quickSearchIndex) {
		this.quickSearchIndex = quickSearchIndex;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getLeastSubclass() {
		return leastSubclass;
	}

	public void setLeastSubclass(String leastSubclass) {
		this.leastSubclass = leastSubclass;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getCcAddressid() {
		return ccAddressid;
	}

	public void setCcAddressid(Integer ccAddressid) {
		this.ccAddressid = ccAddressid;
	}

	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

}
