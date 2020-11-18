package com.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class FILEUtils {
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/**/
	public static boolean GenerateImage(String imgStr, String path) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		try {
			System.out.println("__________________________________");
			System.out.println(imgStr);
			System.out.println("__________________________________");
			String img = imgStr.split(",")[1];
			byte[] b = Base64.getDecoder().decode(img);
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	public static Boolean saveUploadFile(File uploadfile, String path, String fileName) throws IOException {
//		如果出现文件重名覆盖问题 可以考虑重新为文件命名
//		  Date date = new Date(); 
//		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//		  String newFileName = dateFormat.format(date)+fileName.substring(fileName.lastIndexOf(".")+ 1);
		 
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		FileUtils.copyFile(uploadfile, new File(file, fileName));
		return true;
	}

}
