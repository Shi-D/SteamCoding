package com.framework.system.common.global;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来合并JS文件
 * 
 * @author {In-Death}
 *
 */
public class MergeJSFile
{
	public static final String JS_FILE_BASEPATH = "src/main/webapp/app";
	
	public static final String MERGE_JS_FILE_PATH = "src/main/webapp/app/allApp.js";
	
	public static void main(String[] args) throws IOException
	{
		File baseFile = new File(JS_FILE_BASEPATH);
		
		List<String> allFilesPath = new ArrayList<String>();
		
		for (File f : baseFile.listFiles())
		{
			if (!f.isFile() && !f.getName().equals("utils"))
			{
				getAllFiles(f, allFilesPath);
			}
		}
		
		File allAppFile = new File(MERGE_JS_FILE_PATH);
		
		if (!allAppFile.exists())
		{
			allAppFile.createNewFile();
		}
		
		OutputStream os = new BufferedOutputStream(new FileOutputStream(allAppFile));
		
		for (String path : allFilesPath)
		{
			File file = new File(path);
			
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			
			int b = 0;
			
			while ((b = is.read()) != -1)
			{
				os.write((byte)b);
			}
			
			is.close();
		}
		
		os.close();
	}
	
	public static void getAllFiles(File file, List<String> allFilesPath)
	{
		if (file.isFile())
		{
			allFilesPath.add(file.getPath());
		}
		else
		{
			for (File f : file.listFiles())
			{
				getAllFiles(f, allFilesPath);
			}
		}
	}
}
