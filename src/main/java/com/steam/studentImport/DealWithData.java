package com.steam.studentImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DealWithData
{
	public static List<Map<String, String>> getDatas(File file)
	{
		InputStream is;
		Workbook book = null;
		try
		{
			is = new FileInputStream(file);
			book = Workbook.getWorkbook(is);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows(); // 行
			int columns = sheet.getColumns(); // 列
			// 获取第一行字段名
			String[] columnNames = new String[columns];
			for (int i = 0; i < columns; i++)
			{
				Cell cell = sheet.getCell(i, 0);
				columnNames[i] = cell.getContents();
			}
			// 从第二行开始获取数据
			if (columns < 2) {
				return null;
			}
			
			List<Map<String, String>> datas = new ArrayList<Map<String,String>>(rows - 2);
			for (int r = 1; r < rows; r++)
			{
				Map<String, String> data = new HashMap<String, String>(columns);
				data.put("rowIndex", String.valueOf(r + 1));
				for (int c = 0; c < columns; c++)
				{
					data.put(columnNames[c], sheet.getCell(c, r).getContents());
				}
				datas.add(data);
			}
			return datas;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (BiffException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (book != null)
			{
				book.close();
			}
		}
		return null;
	}
}
