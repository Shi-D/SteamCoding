package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORGANIZATION_ADMIN_INFO_VIEW")
public class OrganizationAdminInfoView {
	
	@Id
	@Column(name = "ORGANIZATION_ID")
	private Integer organizationId;
	
	@Column(name = "ORGANIZATION_NAME")
	private String organizationName;
	
	@Column(name = "ORGANIZATION_SNAME")
	private String organizationSname;
	
	@Column(name = "ADMIN_ID")
	private Integer adminId;
	
	@Column(name = "ADMIN_NAME")
	private String adminName;
	
	@Column(name = "ORGANIZATION_CREATION_TIME")
	private Date creationTime;

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationSname() {
		return organizationSname;
	}

	public void setOrganizationSname(String organizationSname) {
		this.organizationSname = organizationSname;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreateTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	
	
}
