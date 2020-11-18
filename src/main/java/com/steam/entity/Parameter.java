package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_SYSTEM_PARAMETER")
public class Parameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PARAMETER_COMMENT")
	private String parameterComment;
	
	@Column(name = "PARAMETER_DEFAULT_VALUE")
	private String parameterValue;
	
	@Column(name = "PARAMETER_NAME")
	private String parameterName;
	
	@Column(name = "PARAMETER_SEQUENCE")
	private Integer parameterSequence;
	
	@Column(name = "PARAMETER_SERIAL_NO")
	private Integer parameterSerialNo;
	
	@Column(name = "PARAMETER_TYPE_CODE")
	private Integer parameterTypeCode;
	
	@Column(name = "PARAMETER_TYPE_NAME")
	private String parameterTypeName;
	
	@Column(name = "PARAMETER_URL")
	private String parameterUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameterComment() {
		return parameterComment;
	}

	public void setParameterComment(String parameterComment) {
		this.parameterComment = parameterComment;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Integer getParameterSequence() {
		return parameterSequence;
	}

	public void setParameterSequence(Integer parameterSequence) {
		this.parameterSequence = parameterSequence;
	}

	public Integer getParameterSerialNo() {
		return parameterSerialNo;
	}

	public void setParameterSerialNo(Integer parameterSerialNo) {
		this.parameterSerialNo = parameterSerialNo;
	}

	public Integer getParameterTypeCode() {
		return parameterTypeCode;
	}

	public void setParameterTypeCode(Integer parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}

	public String getParameterTypeName() {
		return parameterTypeName;
	}

	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}

	public String getParameterUrl() {
		return parameterUrl;
	}

	public void setParameterUrl(String parameterUrl) {
		this.parameterUrl = parameterUrl;
	}

	
}
