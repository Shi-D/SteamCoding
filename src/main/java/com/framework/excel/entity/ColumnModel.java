package com.framework.excel.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Excel表格列配置类
 * 
 * @author {In-Death}
 * 
 */
@Entity
@Table(name = "T_EXCEL_COLUMN_MODEL")
public class ColumnModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TABLE_CODE")
	private Integer tableCode; // 表或者视图编号，人为指定

	@Column(name = "PROPERTY_NAME")
	private String propertyName; // 对应 Property类中的propertyName

	@Column(name = "COLUMN_HEADING")
	private String caption; // 列标题

	@Column(name = "COLUMN_WIDTH")
	private Integer width; // 列宽度

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "COLUMN_ID")
	private Set<ColumnChange> columnChanges;

	@Column(name = "CELL_FORMAT_TYPE")
	private Integer cellFormatType; // 单元格类型

	@Transient
	private Map<String, String> change; // 值转换

	/**
	 * 空构造方法，默认列宽度为15
	 */
	public ColumnModel()
	{
		this.width = 15;
	}

	/**
	 * 构造方法
	 * 
	 * @param propertyName
	 *            实体类属性名称
	 * @param caption
	 *            列标题
	 * @param width
	 *            列宽度
	 */
	public ColumnModel(String propertyName, String caption, Integer width)
	{
		this.propertyName = propertyName;
		this.caption = caption;
		this.width = width;
	}

	/**
	 * 构造方法
	 * 
	 * @param propertyName
	 *            实体类属性名称
	 * @param caption
	 *            列标题
	 * @param width
	 *            列宽度
	 * @param cellFormatType
	 *            单元格类型
	 */
	public ColumnModel(String propertyName, String caption, Integer width, Integer cellFormatType)
	{
		this.propertyName = propertyName;
		this.caption = caption;
		this.width = width;
		this.cellFormatType = cellFormatType;
	}

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * 设置主键
	 * 
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * 获取表或者视图编号
	 * 
	 * @return
	 */
	public Integer getTableCode()
	{
		return tableCode;
	}

	/**
	 * 设置表或者视图编号
	 * 
	 * @param tableCode
	 */
	public void setTableCode(Integer tableCode)
	{
		this.tableCode = tableCode;
	}

	/**
	 * 获取对应实体类中的属性名
	 * 
	 * @return
	 */
	public String getPropertyName()
	{
		return propertyName;
	}

	/**
	 * 设置对应实体类中的属性名
	 * 
	 * @param propertyName
	 */
	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	/**
	 * 获取列标题
	 * 
	 * @return
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * 设置列标题
	 * 
	 * @param caption
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	/**
	 * 获取列宽度
	 * 
	 * @return
	 */
	public Integer getWidth()
	{
		return width;
	}

	/**
	 * 设置列宽度
	 * 
	 * @param width
	 */
	public void setWidth(Integer width)
	{
		this.width = width;
	}

	/**
	 * 获取列值转换
	 * 
	 * @return
	 */
	public Set<ColumnChange> getColumnChanges()
	{
		return columnChanges;
	}

	/**
	 * 设置列值转换
	 * 
	 * @param columnChanges
	 */
	public void setColumnChanges(Set<ColumnChange> columnChanges)
	{
		this.columnChanges = columnChanges;
	}

	/**
	 * 获取Excel单元格格式化类型
	 * 
	 * @return
	 */
	public Integer getCellFormatType()
	{
		return cellFormatType;
	}

	/**
	 * 设置Excel单元格格式化类型
	 * 
	 * @param cellFormatType
	 */
	public void setCellFormatType(Integer cellFormatType)
	{
		this.cellFormatType = cellFormatType;
	}

	/**
	 * 获取该字段做值转换的Map对象
	 * 
	 * @return
	 */
	@Transient
	public Map<String, String> getChange()
	{
		return change;
	}

	/**
	 * 设置该字段做值转换的Map对象
	 * 
	 * @param change
	 */
	@Transient
	public void setChange(Map<String, String> change)
	{
		this.change = change;
	}

}
