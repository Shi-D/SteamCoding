package com.framework.common;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.framework.excel.constants.CellFormat;
import com.framework.excel.entity.ColumnModel;

public class ExportExcelUtil {
	/**
	 * 导出Excel
	 * 
	 * @param result
	 *            数据结果集
	 * @param columnModelList
	 *            列模式集合
	 * @param fileName
	 *            导出文件名称
	 */
	public static void exportExcel(List<Map<String, Object>> result, List<ColumnModel> columnModelList, String fileName) {
		WritableWorkbook wb = null;

		ServletOutputStream out = null;

		try {
			out = Struts2Util.getResponse().getOutputStream();

			wb = Workbook.createWorkbook(out);

			// create a new sheet
			WritableSheet s = wb.createSheet(fileName, 0);

			SheetSettings sheetSettings = s.getSettings();

			// 设置冻结首行
			sheetSettings.setVerticalFreeze(1);

			// 单元格格式化对象集合
			List<WritableCellFormat> wcfList = new ArrayList<WritableCellFormat>();

			// 填充标题行
			int colModelSize = columnModelList.size();

			for (int colIndex = 0; colIndex < colModelSize; colIndex++) {
				ColumnModel cm = columnModelList.get(colIndex);

				Label label = new jxl.write.Label(colIndex, 0, cm.getCaption());

				s.setColumnView(colIndex, cm.getWidth());
				s.addCell(label);

				// 创建格式化对象并存入集合中
				WritableCellFormat wcf = CellFormat.getWritableCellFormat(cm.getCellFormatType());

				wcfList.add(wcf);
			}

			// 填充数据
			int resultSize = result.size();

			for (int rowIndex = 1; rowIndex <= resultSize; rowIndex++) {
				Map<String, Object> map = result.get(rowIndex - 1);

				insertRow(s, rowIndex, map, columnModelList, wcfList);
			}

			// 向客户端返回excel文件
			String postName = SystemContext.getCurrentUser().getOrganization().getOrganizationName();

			String dateTime = DateUtil.formatDateToString(new Date(), "yyyy年MM月dd日HH点mm分ss秒");

			fileName = "[" + postName + "][" + fileName + "]" + dateTime;

			String agent = Struts2Util.getRequest().getHeader("USER-AGENT");

			// String fileName = "export.xlsx";
			// 如果客户端为IE浏览器，采用URLEncoder进行编码
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			// 如果客户端为火狐，
			else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			}

			Struts2Util.getResponse().addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

			Struts2Util.getResponse().setContentType("application/msexcel;");

			Struts2Util.getResponse().setCharacterEncoding("utf-8");
			// out = new FileOutputStream(filename);
			wb.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeWorkbook(wb);
		}
	}

	public static void exportTemplate(String fileName, String[] columnNames)  {
		WritableWorkbook wb = null;

		ServletOutputStream out = null;

		try {
			out = Struts2Util.getResponse().getOutputStream();

			wb = Workbook.createWorkbook(out);

			// create a new sheet
			WritableSheet s = wb.createSheet(fileName, 0);

			SheetSettings sheetSettings = s.getSettings();

			// 设置冻结首行
			sheetSettings.setVerticalFreeze(1);
			int len = columnNames.length;
			for (int colIndex = 0; colIndex < len; colIndex++) {
				Label label = new jxl.write.Label(colIndex, 0, columnNames[colIndex]);
				s.addCell(label);
			}
			String agent = Struts2Util.getRequest().getHeader("USER-AGENT");

			//String fileName = "export.xlsx";
			// 如果客户端为IE浏览器，采用URLEncoder进行编码
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			// 如果客户端为火狐，
			else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			}

			Struts2Util.getResponse().addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

			Struts2Util.getResponse().setContentType("application/msexcel;");

			Struts2Util.getResponse().setCharacterEncoding("utf-8");
			// out = new FileOutputStream(filename);
			wb.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeWorkbook(wb);
		}
	}

	/**
	 * 导出Excel，使用web页面传过来的列名称
	 * 
	 * @param result
	 *            数据结果集
	 * @param columnModelList
	 *            列模式集合
	 * @param fileName
	 *            导出文件名称
	 * @param columnNames
	 *            列名称
	 */
	public static void exportExcel(List<Map<String, Object>> result, List<ColumnModel> columnModelList, String fileName, String[] columnNames) {
		WritableWorkbook wb = null;

		ServletOutputStream out = null;

		try {
			out = Struts2Util.getResponse().getOutputStream();

			wb = Workbook.createWorkbook(out);

			// create a new sheet
			WritableSheet s = wb.createSheet(fileName, 0);

			SheetSettings sheetSettings = s.getSettings();

			// 设置冻结首行
			sheetSettings.setVerticalFreeze(1);

			// 单元格格式化对象集合
			List<WritableCellFormat> wcfList = new ArrayList<WritableCellFormat>();

			// 填充标题行
			int colModelSize = columnModelList.size();

			for (int colIndex = 0; colIndex < colModelSize; colIndex++) {
				ColumnModel cm = columnModelList.get(colIndex);
				Label label = new jxl.write.Label(colIndex, 0, columnNames[colIndex]);

				s.setColumnView(colIndex, cm.getWidth());
				s.addCell(label);

				// 创建格式化对象并存入集合中
				WritableCellFormat wcf = CellFormat.getWritableCellFormat(cm.getCellFormatType());

				wcfList.add(wcf);
			}

			// 填充数据
			int resultSize = result.size();
			for (int rowIndex = 1; rowIndex <= resultSize; rowIndex++) {
				Map<String, Object> map = result.get(rowIndex - 1);

				insertRow(s, rowIndex, map, columnModelList, wcfList);
			}

			// 向客户端返回excel文件
			String postName = SystemContext.getCurrentUser().getOrganization().getOrganizationName();

			String dateTime = DateUtil.formatDateToString(new Date(), "yyyy年MM月dd日HH点mm分ss秒");

			fileName = "[" + postName + "][" + fileName + "]" + dateTime;

			String agent = Struts2Util.getRequest().getHeader("USER-AGENT");

			// String fileName = "export.xlsx";
			// 如果客户端为IE浏览器，采用URLEncoder进行编码
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			// 如果客户端为火狐，
			else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			}

			Struts2Util.getResponse().addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

			Struts2Util.getResponse().setContentType("application/msexcel;");

			Struts2Util.getResponse().setCharacterEncoding("utf-8");
			// out = new FileOutputStream(filename);
			wb.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeWorkbook(wb);
		}
	}

	/**
	 * 向Sheet中插入行数据
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param data
	 * @param colModelList
	 * @param wcfList
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static void insertRow(WritableSheet sheet, int rowIndex, Map<String, Object> data, List<ColumnModel> columnModelList, List<WritableCellFormat> wcfList) throws RowsExceededException,
			WriteException {
		int size = columnModelList.size();

		for (int colIndex = 0; colIndex < size; colIndex++) {
			ColumnModel cm = columnModelList.get(colIndex);

			// 对应字段的值
			Object value = data.get(cm.getPropertyName());

			Map<String, String> change = cm.getChange();

			// 判断是否对该字段的值做转换
			if (change != null) {
				if (value == null) {
					value = "未知";
				} else {
					value = change.get(value.toString());
				}
			}

			WritableCellFormat wcf = wcfList.get(colIndex);

			sheet.addCell(createCell(colIndex, rowIndex, value, wcf));
		}
	}

	/**
	 * 创建单元格
	 * 
	 * @param colIndex
	 *            列序号
	 * @param rowIndex
	 *            行序号
	 * @param value
	 *            数据
	 * @param wcf
	 *            单元格格式
	 * @return
	 */
	public static WritableCell createCell(int colIndex, int rowIndex, Object value, WritableCellFormat wcf) {
		WritableCell cell = null;

		if (value == null) {
			cell = new jxl.write.Label(colIndex, rowIndex, "");
		} else if (value instanceof String) {
			cell = ExportExcelUtil.createLabelCell(colIndex, rowIndex, value.toString(), wcf);
		} else if (value instanceof Date) {
			cell = ExportExcelUtil.createDateTimeCell(colIndex, rowIndex, (Date) value, wcf);
		} else if (value instanceof Integer) {
			cell = ExportExcelUtil.createNumberCell(colIndex, rowIndex, ((Integer) value).doubleValue(), wcf);
		} else if (value instanceof Long) {
			cell = ExportExcelUtil.createNumberCell(colIndex, rowIndex, ((Long) value).doubleValue(), wcf);
		} else if (value instanceof Double) {
			cell = ExportExcelUtil.createNumberCell(colIndex, rowIndex, ((Double) value).doubleValue(), wcf);
		} else if (value instanceof Float) {
			cell = ExportExcelUtil.createNumberCell(colIndex, rowIndex, ((Float) value).doubleValue(), wcf);
		} else {
			cell = new jxl.write.Label(colIndex, rowIndex, "");
		}

		return cell;
	}

	/**
	 * 创建日期单元格
	 * 
	 * @param col
	 *            列序号
	 * @param row
	 *            行序号
	 * @param date
	 *            日期数据
	 * @param wcf
	 *            单元格格式
	 * @return
	 */
	public static DateTime createDateTimeCell(int col, int row, Date date, WritableCellFormat wcf) {
		DateTime dCell = null;

		if (wcf != null) {
			dCell = new jxl.write.DateTime(col, row, date, wcf);
		} else {
			dCell = new jxl.write.DateTime(col, row, date);
		}

		return dCell;
	}

	/**
	 * 创建文本单元格
	 * 
	 * @param col
	 *            列序号
	 * @param row
	 *            行序号
	 * @param date
	 *            日期数据
	 * @param wcf
	 *            单元格格式
	 * @return
	 */
	public static Label createLabelCell(int col, int row, String data, WritableCellFormat wcf) {
		Label lCell = null;

		if (wcf != null) {
			lCell = new jxl.write.Label(col, row, data, wcf);
		} else {
			lCell = new jxl.write.Label(col, row, data);
		}

		return lCell;
	}

	/**
	 * 创建数字单元格
	 * 
	 * @param col
	 *            列序号
	 * @param row
	 *            行序号
	 * @param date
	 *            日期数据
	 * @param wcf
	 *            单元格格式
	 * @return
	 */
	public static Number createNumberCell(int col, int row, double data, WritableCellFormat wcf) {
		Number nCell = null;

		if (wcf != null) {
			nCell = new Number(col, row, data, wcf);
		} else {
			nCell = new Number(col, row, data);
		}

		return nCell;
	}

	/**
	 * 创建布尔单元格
	 * 
	 * @param col
	 *            列序号
	 * @param row
	 *            行序号
	 * @param date
	 *            日期数据
	 * @param wcf
	 *            单元格格式
	 * @return
	 */
	public static jxl.write.Boolean createBooleanCell(int col, int row, boolean data, WritableCellFormat wcf) {
		jxl.write.Boolean bCell = null;

		if (wcf != null) {
			bCell = new jxl.write.Boolean(col, row, data, wcf);
		} else {
			bCell = new jxl.write.Boolean(col, row, data);
		}

		return bCell;
	}

	/**
	 * 关闭工作簿
	 * 
	 * @param workbook
	 */
	public static void closeWorkbook(WritableWorkbook workbook) {
		try {
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String format(Object obj) {
		DecimalFormat df = new DecimalFormat();

		df.applyPattern("0.00");

		df.setRoundingMode(RoundingMode.HALF_UP);

		if (obj == null || obj.equals("")) {
			return "0.00";
		} else {
			return df.format(obj);
		}
	}

	/**
	 * 创建HQL语句
	 * 
	 * @param className
	 *            实体类名称
	 * @param propertyNames
	 *            属性名称数组
	 * @return
	 */
	public static String createHQL(String className, String[] propertyNames) {
		StringBuilder sb = new StringBuilder();

		sb.append("select ");

		int size = propertyNames.length;

		for (int i = 0; i < size; i++) {
			String property = propertyNames[i];

			// 如果包含except_文字，则表示该属性不用于查询
			if (property.contains("except_")) {
				continue;
			}

			if (i < size - 1) {
				sb.append(property + " as " + property + ",");
			} else {
				sb.append(property + " as " + property);
			}
		}

		sb.append(" from ");
		sb.append(className);

		return sb.toString();
	}
}
