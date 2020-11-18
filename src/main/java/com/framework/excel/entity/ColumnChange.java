package com.framework.excel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Excel表格列值转换类
 * 
 * @author {In-Death}
 *
 */
@Entity
@Table(name = "T_EXCEL_COLUMN_CHANGE")
public class ColumnChange
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CHANGE_KEY")
	private String changeKey;

	@Column(name = "CHANGE_VALUE")
	private String changeValue;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getChangeKey()
	{
		return changeKey;
	}

	public void setChangeKey(String changeKey)
	{
		this.changeKey = changeKey;
	}

	public String getChangeValue()
	{
		return changeValue;
	}

	public void setChangeValue(String changeValue)
	{
		this.changeValue = changeValue;
	}

}
