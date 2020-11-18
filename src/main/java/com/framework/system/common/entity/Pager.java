package com.framework.system.common.entity;

import java.util.List;

/**
 * 用于在客户端分页显示查询与后台通信时封装请求参数与 服务器返回结果的痛哟个对象 本系统中主要用于Ext与服务器的通信信息封装
 */
public class Pager
{
	private boolean search = false;// 是否是查询
	private Integer pageNo = 1;// 当前页号
	private Integer pageSize = 20;// 每页记录数
	private Integer totalPage;// 总页数
	private Integer totalCount;// 总记录数
	private String dir;// 排序方式 asc/desc
	private String sortKey;// 排序依据的实体属性名
	private Long nd;// 已经发送请求次数的参数名称
	private List<?> result;// 返回结果
	private List<?> dataset;

	public boolean isSearch()
	{
		return search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	public Integer getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(Integer pageNo)
	{
		this.pageNo = pageNo;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(Integer totalPage)
	{
		this.totalPage = totalPage;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
	}

	public String getSortKey()
	{
		return sortKey;
	}

	public void setSortKey(String sortKey)
	{
		this.sortKey = sortKey;
	}

	public void setNd(Long nd)
	{
		this.nd = nd;
	}

	public Long getNd()
	{
		return nd;
	}

	public void setResult(List<?> result)
	{
		this.result = result;
	}

	public List<?> getResult()
	{
		return result;
	}

	public List<?> getDataset()
	{
		return dataset;
	}

	public void setDataset(List<?> dataset)
	{
		this.dataset = dataset;
	}

}
