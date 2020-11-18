package com.framework.authority.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
@Entity
@Table(name = "SEC_RESOURCE")
@NamedQueries({ @javax.persistence.NamedQuery(name = "getResourceByValue", query = "select r from Resource r where r.value = :value") })
public class Resource implements Serializable {
	private Integer id;
	private String type;
	private String value;
	private String description;
	private Set<Authority> authorities = new LinkedHashSet();

	@Id
	@Column(name = "RESOURCE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "VALUE", length = 100, nullable = false)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = { javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "SEC_AUTHORITY_RESOURCES", joinColumns = { @javax.persistence.JoinColumn(name = "RESOURCE_ID") }, inverseJoinColumns = { @javax.persistence.JoinColumn(name = "AUTH_ID") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
}