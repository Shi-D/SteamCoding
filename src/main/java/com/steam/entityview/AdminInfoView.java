package com.steam.entityview;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "ADMIN_INFO_VIEW")
public class AdminInfoView {

	private Integer adminId;
	private String adminName;
	private String adminCode;
	private String adminGender;
	private Integer organizationId;
	private Date adminCreationTime;
	private String organizationName;
	
	@Id
	@Column(name = "ADMIN_ID")
	public Integer getAdminId() {
		return adminId;
	}
	
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	@Column(name = "ADMIN_NAME")
	public String getAdminName() {
		return adminName;
	}
	
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "ADMIN_CODE")
	public String getAdminCode() {
		return adminCode;
	}
	
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

	@Column(name = "ADMIN_GENDER")
	public String getAdminGender() {
		return adminGender;
	}
	
	public void setAdminGender(String adminGender) {
		this.adminGender = adminGender;
	}

	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "ADMIN_CREATION_TIME")
	public Date getAdminCreationTime() {
		return adminCreationTime;
	}
	
	public void setAdminCreationTime(Date adminCreationTime) {
		this.adminCreationTime = adminCreationTime;
	}
	@Column(name = "ORGANIZATION_NAME")
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
