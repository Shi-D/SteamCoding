package com.steam.action.uploadscratchfile;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.system.common.base.action.BaseGridAction;
import com.framework.utils.FILEUtils;
import com.framework.utils.URLUtils;
import com.steam.service.WorkService;

public class UploadSB3Action extends BaseGridAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -145596564961804760L;
	
	private File uploadFile;
	private File uploadFileCover;
	private String uploadFileUrl;
	private Integer workId;
	private String courseWorkName;
	
	@Autowired
	WorkService workService;
	
//	public String uploadSB3File()  throws IOException {
//		results = new ArrayList<Map<String,Object>>();
//		Map<String,Object> temp = new HashMap<String,Object>();
//		User user = SystemContext.getCurrentUser();
//		// 获取要保存文件夹的物理路径(绝对路径)
//		File file = new File(URLUtils.generateURLforWork(user.getUsername()));
//		// 测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
//		if (!file.exists())
//			file.mkdirs();
//		try {
//			if(!uploadFileUrl.equals("null")){  //获取原本上传文件的名字  如果是修改文件
//				uploadFileUrl = uploadFileUrl.split(".sb3")[0];
//				String fileName = uploadFileUrl.split("/works/")[1];
//				
//				File oldFile = new File(file, fileName+".sb3");
//				File oldFileCover = new File(file, fileName);
//				oldFile.delete();
//				oldFileCover.delete();
//
//				FileUtils.copyFile(uploadFile, new File(file, fileName+".sb3"));
//				FILEUtils.GenerateImage(uploadFileCover,URLUtils.generateURLforWork(user.getUsername())+"/"+fileName);
//				temp.put("result",true);
//				temp.put("message",uploadFileUrl);
//			}		
//			else if(user!=null){//如果是新增文件
//				Date date = new Date();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//				String fileName = user.getUserCode()+"-"+dateFormat.format(date);
//				FileUtils.copyFile(uploadFile, new File(file, fileName+".sb3"));
//				FILEUtils.GenerateImage(uploadFileCover,URLUtils.generateURLforWork(user.getUsername())+"/"+fileName);
//				workService.addWork(user.getId(), "account/"+user.getUsername()+"/works/", fileName);
//				String uploadFileUrlTemp = "account/"+user.getUsername()+"/works/"+fileName;
//				temp.put("result",true);
//				temp.put("message",uploadFileUrlTemp);
//			}	
//		} catch (Exception e) {
//			temp.put("result",false);
//			System.out.println(e);
//			e.printStackTrace();
//		}
//        results.add(temp);
//		return "result>json";
//	}
	
	public String uploadSB3File(){
		Map<String,Object> temp = new HashMap<String,Object>();
	try {
        System.out.println(uploadFile+"+++++++++++++++");
		System.out.println(workId+"+++++++++++++++");
		System.out.println(courseWorkName+"+++++++++++++++");
		results = new ArrayList<Map<String,Object>>();
		User user = SystemContext.getCurrentUser();
		System.out.println(user.getId()+"+++++++++++++++");
		// 获取要保存文件夹的物理路径(绝对路径)
		File file = new File(URLUtils.generateURLforWork(user.getUsername()));
		// 测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		if (!file.exists())
			file.mkdirs();
			if(workId!=null){  //获取原本作品的ID  如果是修改文件则不空
				String fileName = workService.getFileName(user.getId(),workId);
				if(fileName == ""){
					temp.put("result",false);
				}else{

					File oldFile = new File(file, fileName+".sb3");
					File oldFileCover = new File(file, fileName+".png");
					oldFile.delete();
					oldFileCover.delete();
					
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
					String newFileName=user.getUserCode()+"-"+dateFormat.format(date);
					
					FileUtils.copyFile(uploadFile, new File(file, newFileName+".sb3"));
//					FILEUtils.GenerateImage(uploadFileCover,URLUtils.generateURLforWork(user.getUsername())+"/"+courseWorkName);
					FileUtils.copyFile(uploadFileCover, new File(file,newFileName+".png"));
					workService.updateCourseWork(user.getId(), workId, courseWorkName, newFileName);
					temp.put("result",true);
					temp.put("message",uploadFileUrl);
				}
			}		
			else if(user!=null){//如果是新增文件
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String fileName = user.getUserCode()+"-"+dateFormat.format(date);
				FileUtils.copyFile(uploadFile, new File(file, fileName+".sb3"));
//				FILEUtils.GenerateImage(uploadFileCover,URLUtils.generateURLforWork(user.getUsername())+"/"+fileName);
				FileUtils.copyFile(uploadFileCover, new File(file,fileName+".png"));
				workService.addWork(user.getId(), "account/"+user.getUsername()+"/works/", fileName,courseWorkName);
				temp.put("result",true);
				List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
				l = workService.getWorkId(user.getId(),fileName);
				temp.put("workId", l.get(0).get("workId"));
			}
		} catch (Exception e) {
			System.out.println("exception+++++++++++++++");
			temp.put("result",false);
		}
        results.add(temp);
		return "result>json";
	}
	
	
	public File getUploadFile() {
		return uploadFile;
	}
	
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileUrl() {
		return uploadFileUrl;
	}

	public void setUploadFileUrl(String uploadFileUrl) {
		this.uploadFileUrl = uploadFileUrl;
	}

	public File getUploadFileCover() {
		return uploadFileCover;
	}

	public void setUploadFileCover(File uploadFileCover) {
		this.uploadFileCover = uploadFileCover;
	}


	public Integer getWorkId() {
		return workId;
	}


	public void setWorkId(Integer workId) {
		this.workId = workId;
	}


	public String getCourseWorkName() {
		return courseWorkName;
	}


	public void setCourseWorkName(String courseWorkName) {
		this.courseWorkName = courseWorkName;
	}

	
	

}
