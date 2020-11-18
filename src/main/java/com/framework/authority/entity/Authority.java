package com.framework.authority.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEC_AUTHORITY")
public class Authority implements Serializable {
	private static final long serialVersionUID = 8900311142883074230L;
	private Integer id;
	private String authName;
	private String authDescription;
	private Boolean leaf; // 叶节电标志
	private Boolean available = true; // 可用标志
	private Integer order; // 级别
	private Integer supId; // 父级ID
	private String icon; // 图标
	private String leastSubclass;// 子节电最少个数

	@Id
	@Column(name = "AUTH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "AUTH_NAME", length = 20, nullable = false)
	public String getAuthName() {
		return this.authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Column(name = "AUTH_DESCRIPTION", length = 50)
	public String getAuthDescription() {
		return this.authDescription;
	}

	public void setAuthDescription(String authDescription) {
		this.authDescription = authDescription;
	}

	@Column(name = "SUP_ID")
	public Integer getSupId() {
		return supId;
	}

	@Column(name = "LEAF")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "AVAILABLE")
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Column(name = "TAB_INDEX")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name = "LEAST_SUBCLASS")
	public String getLeastSubclass() {
		return leastSubclass;
	}

	public void setLeastSubclass(String leastSubclass) {
		this.leastSubclass = leastSubclass;
	}

	public void setSupId(Integer supId) {
		this.supId = supId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		Authority other = (Authority) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}