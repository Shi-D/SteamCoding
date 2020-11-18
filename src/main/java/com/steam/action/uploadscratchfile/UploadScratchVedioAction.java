package com.steam.action.uploadscratchfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.utils.URLUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.steam.service.VideoService;

public class UploadScratchVedioAction extends ActionSupport{
	private static final long serialVersionUID = 837481714629791752L;
	@Autowired
	VideoService videoService;
	private String videoTitle;
	private File file;
    private String fileName;
    private String fileSize;
    private String uploadSize;
    private String responseInfo;
    
    public String getUploadedSize() throws IOException{
    	User user = SystemContext.getCurrentUser();//获取当前用户
    	File folder = new File(URLUtils.generateURLforVideoStore(user.getUserCode()));
		// 测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		if (!folder.exists())
			folder.mkdirs();
    	String path = SteamConstants.ABSOLUTE_URL+user.getUserCode()+"/video/"+fileName;
        uploadSize = "0";
        File newFile = new File(path);
        File fileUploadDetal = new File(path+".rhxy");//临时文件，记录文件上传的状态
        if (newFile.exists()) {
            if (!fileUploadDetal.exists()) {
                //此文件夹下有重名文件，不允许上传
                return "falier";
            }else{
                FileReader fileReader = new FileReader(fileUploadDetal);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                bufferedReader.readLine();
                String name = bufferedReader.readLine();
                bufferedReader.readLine();
                String size = bufferedReader.readLine();
                bufferedReader.readLine();
                String uploadedSize = bufferedReader.readLine();
                bufferedReader.readLine();
                String createTime = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();
                System.out.println("文件："+name+" "+size+"字节, 已上传"+uploadedSize+"字节,更新时间"+createTime);
                if (name.equals(fileName)) {
                    uploadSize = uploadedSize;
                }else{
                    System.out.println("文件："+fileName+" 上传信息有错误。");
                }
            }
        }
        return "uploadsize";
    }
    
    public String doUpload() throws Exception {
    	User user = SystemContext.getCurrentUser();
    	File folder = new File(URLUtils.generateURLforVideoStore(user.getUserCode()));
		// 测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		if (!folder.exists())
			folder.mkdirs();
    	String path = SteamConstants.ABSOLUTE_URL+user.getUserCode()+"/video/"+fileName;
        File newFile = new File(path);
        File fileUploadDetal = new File(path+".rhxy");//临时文件，记录文件上传的状态
        if (!newFile.exists()) {
            newFile.createNewFile();
        }else{
            if (!fileUploadDetal.exists()) {
                //此文件夹下有重名文件，不允许上传
                return "falier";
            }
        }
        copyFile(file,newFile);
        if (uploadSize.equals(fileSize)) {
            fileUploadDetal.delete();
            File _newFile = new File(path);
            String newFileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()).toString()+"."+fileName.substring(fileName.lastIndexOf(".") + 1);
            _newFile.renameTo(new File(SteamConstants.ABSOLUTE_URL+user.getUserCode()+"/video/"+newFileName));
            videoService.uploadVideoInStore(this.getVideoTitle(), URLUtils.generateURLforUpdateToDB(user.getUserCode(),newFileName), user.getId(),user.getOrganization().getId());
        }else{
            if (!fileUploadDetal.exists()) {
                fileUploadDetal.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileUploadDetal);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("文件名：");
            bufferedWriter.newLine();
            bufferedWriter.write(fileName);
            bufferedWriter.newLine();
            bufferedWriter.write("文件大小：");
            bufferedWriter.newLine();
            bufferedWriter.write(fileSize);
            bufferedWriter.newLine();
            bufferedWriter.write("已传大小：");
            bufferedWriter.newLine();
            bufferedWriter.write(uploadSize);
            bufferedWriter.newLine();
            bufferedWriter.write("更新时间：");
            bufferedWriter.newLine();
            bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            bufferedWriter.close();
            fileWriter.close();
        }         
        responseInfo = "上传成功!";
        return "doUpload";
    }
    
    private void copyFile(File oldFile,File newFile) throws IOException{
        InputStream in = new FileInputStream(oldFile);
        OutputStream out = new FileOutputStream(newFile,true);
        byte[] buffer = new byte[1024];  
        int len = -1;  
        while ((len = in.read(buffer)) != -1) {  
            out.write(buffer, 0, len);  
        }  
        in.close();
        out.close();
    }
 
    public File getFile() {
        return file;
    }
 
    public void setFile(File file) {
        this.file = file;
    }
 
    public String getResponseInfo() {
        return responseInfo;
    }
 
    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }
    
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public String getFileSize() {
        return fileSize;
    }
 
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
 
    public String getUploadSize() {
        return uploadSize;
    }
 
    public void setUploadSize(String uploadSize) {
        this.uploadSize = uploadSize;
    }

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
    
    
}
