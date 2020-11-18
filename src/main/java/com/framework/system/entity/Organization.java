package com.framework.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_ORGANIZATION")
public class Organization implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 表或者视图编号，人为设定 */
	public static final Integer TABLE_CODE = 10012;
	
	private Integer id;
	private Integer organizationCode;
	private Integer organizationOrder;
	private String organizationName;
	private String organizationSname;
	private String organizationClass;
	private String checkState;
	private Date creationTime;

	public Organization(){
		
	}

	public Organization(Integer id, String organizationName){
		this.id = id;
		this.organizationName = organizationName;
	}

	/**
	 * 更新自身数据
	 * 
	 * @param organization
	 */
	public void update(Organization organization){
		this.organizationOrder = organization.getOrganizationOrder();
		this.organizationName = organization.getOrganizationName();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORGANIZATION_ID")
	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	@Column(name = "ORGANIZATION_CODE")
	public Integer getOrganizationCode(){
		return this.organizationCode;
	}

	public void setOrganizationCode(Integer organizationCode){
		this.organizationCode = organizationCode;
	}

	@Column(name = "ORGANIZATION_NAME")
	public String getOrganizationName(){
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName){
		this.organizationName = organizationName;
	}

	@Column(name = "ORGANIZATION_ORDER")
	public Integer getOrganizationOrder(){
		return this.organizationOrder;
	}

	public void setOrganizationOrder(Integer organizationOrder){
		this.organizationOrder = organizationOrder;
	}
	@Column(name = "ORGANIZATION_CLASS")
	public String getOrganizationClass() {
		return organizationClass;
	}

	public void setOrganizationClass(String organizationClass) {
		this.organizationClass = organizationClass;
	}
	@Column(name = "ORGANIZATION_SNAME")
	public String getOrganizationSname() {
		return organizationSname;
	}
	
	public void setOrganizationSname(String organizationSname) {
		this.organizationSname = organizationSname;
	}
	
	@Column(name = "CHECK_STATE")
	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	@Column(name = "ORGANIZATION_CREATION_TIME")
	public Date getCreationTime() {
		return creationTime;
	}

	

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	
}
