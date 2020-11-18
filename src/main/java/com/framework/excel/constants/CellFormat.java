package com.framework.excel.constants;

import jxl.biff.DisplayFormat;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;

/**
 * excel单元格格式化工具类
 * 
 * @author {In-Death}
 * 
 */
public class CellFormat
{
	/** 格式：yyyy-mm-dd hh:mm:ss */
	public static final int FORMAT_DATETIME = 1;
	/** 格式：yyyy-mm-dd */
	public static final int FORMAT_DATE = 2;
	/** 格式：hh:mm:ss */
	public static final int FORMAT_TIME = 3;
	/** 格式：0 */
	public static final int FORMAT_INTEGER = 4;
	/** 格式：#,##0 */
	public static final int FORMAT_THOUSANDS_INTEGER = 5;
	/** 格式：0.0 */
	public static final int FORMAT_DECIMAL_1 = 6;
	/** 格式：0.00 */
	public static final int FORMAT_DECIMAL_2 = 7;
	/** 格式：0.000 */
	public static final int FORMAT_DECIMAL_3 = 8;
	/** 格式：#,##0.0 */
	public static final int FORMAT_THOUSANDS_DECIMAL_1 = 9;
	/** 格式：#,##0.00 */
	public static final int FORMAT_THOUSANDS_DECIMAL_2 = 10;
	/** 格式：#,##0.000 */
	public static final int FORMAT_THOUSANDS_DECIMAL_3 = 11;
	/** 格式：0% */
	public static final int FORMAT_PERCENT_0 = 12;
	/** 格式：0.0% */
	public static final int FORMAT_PERCENT_1 = 13;
	/** 格式：0.00% */
	public static final int FORMAT_PERCENT_2 = 14;
	/** 格式：hh:mm */
	public static final int FORMAT_TIME_HM = 15;
	/** 格式：yyyy-mm */
	public static final int FORMAT_DATE_YM = 16;

	/**
	 * 获取单元格格式
	 * 
	 * @param type
	 * @return
	 */
	public static DisplayFormat getDisplayFormat(int type)
	{
		DisplayFormat df = null;
		switch (type)
		{
			case FORMAT_DATETIME:
				df = new DateFormat("yyyy-MM-dd hh:mm:ss");
				break;
			case FORMAT_DATE:
				df = new DateFormat("yyyy-MM-dd");
				break;
			case FORMAT_TIME:
				df = new DateFormat("hh:mm:ss");
				break;
			case FORMAT_INTEGER:
				df = new NumberFormat("0");
				break;
			case FORMAT_THOUSANDS_INTEGER:
				df = new NumberFormat("#,##0");
				break;
			case FORMAT_DECIMAL_1:
				df = new NumberFormat("0.0");
				break;
			case FORMAT_DECIMAL_2:
				df = new NumberFormat("0.00");
				break;
			case FORMAT_DECIMAL_3:
				df = new NumberFormat("0.000");
				break;
			case FORMAT_THOUSANDS_DECIMAL_1:
				df = new NumberFormat("#,##0.0");
				break;
			case FORMAT_THOUSANDS_DECIMAL_2:
				df = new NumberFormat("#,##0.00");
				break;
			case FORMAT_THOUSANDS_DECIMAL_3:
				df = new NumberFormat("#,##0.000");
				break;
			case FORMAT_PERCENT_0:
				df = new NumberFormat("0%");
				break;
			case FORMAT_PERCENT_1:
				df = new NumberFormat("0.0%");
				break;
			case FORMAT_PERCENT_2:
				df = new NumberFormat("0.00%");
				break;
			case FORMAT_TIME_HM:
				df = new DateFormat("hh:mm");
				break;
			case FORMAT_DATE_YM:
				df = new DateFormat("yyyy-MM");
				break;
		}

		return df;
	}

	/**
	 * 获取单元格格式
	 * 
	 * @param type
	 * @return
	 */
	public static WritableCellFormat getWritableCellFormat(int type)
	{
		DisplayFormat df = CellFormat.getDisplayFormat(type);
		WritableCellFormat wcf = null;
		if (df != null)
		{
			wcf = new WritableCellFormat(df);
		}
		return wcf;
	}
}
