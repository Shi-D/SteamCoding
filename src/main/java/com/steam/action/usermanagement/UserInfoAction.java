package com.steam.action.usermanagement;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import com.framework.authority.entity.User;
import com.framework.authority.service.UserService;
import com.framework.system.common.base.action.BaseGridAction;


public class UserInfoAction extends BaseGridAction {
	private static final long serialVersionUID = -6885054480841334490L;
	@Autowired
	UserService userService;

	private String originalPWD;
	private String newPWD;
	private String confirmPWD;
	private String userName;
	private String userPhone;
	private String userMail;
	private String userIntroduction;
	private String userADDR;
	private String userPhotoName;
	private User currentUser;
	private File userPhoto;
	private String phone;
	private String password;
	private Integer verifiCode;
	private String organizationId;
	

	// 获得用户信息
	public String queryUserInfo() {
		this.setCurrentUser(userService.getUser());
		try{
			this.getCurrentUser().setUserPWD(null);
		}catch(Exception e){
			
		}
		return "result>json";
	}

	// 更新用户信息
	public String updateUserInfo() {
		results = userService.updateUserInfo(this.getUserMail(),this.getUserPhone(), this.getUserIntroduction());
		return "result>json";
	}

	// 更新用户头像
	public String uploadUserPhoto() {
		results = userService.uploadUserPhotoService(this.getUserPhoto());
		return "result>json";
	}
	//更新密码
	public String updatePassword() {
		results = userService.updatePasswordService(this.getOriginalPWD(),this.getNewPWD());
		return "result>json";
	}
	// 重置密码
	public String resetPassword() {
		results = userService.resetPasswordService(this.getPhone(),
				this.getVerifiCode(), this.getPassword());
		return "result>json";
	}

	// 重置密码发送验证码
	public String updatePwdSmsPort() {
		results = userService.updatePwdSmsPostService(this.getPhone());
		return "result>json";
	}

	// 注册用户
	public String registerUser() {
		results = userService.registerUserService(this.getUserName(),this.getPhone(), this.getPassword(), this.getVerifiCode(), this.getOrganizationId());
		return "result>json";
	}

	// 注册用户发送验证码
	public String smsPort() {
		results = userService.smsPostService(this.getPhone());
		return "result>json";
	}
	
	public String queryDefaultOrganization(){
		results = userService.queryDefaultOrganizationService();
		return "result>json";
	}

	public String getConfirmPWD() {
		return confirmPWD;
	}

	public void setConfirmPWD(String confirmPWD) {
		this.confirmPWD = confirmPWD;
	}

	public File getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(File userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getOriginalPWD() {
		return originalPWD;
	}

	public void setOriginalPWD(String originalPWD) {
		this.originalPWD = originalPWD;
	}

	public String getNewPWD() {
		return newPWD;
	}

	public void setNewPWD(String newPWD) {
		this.newPWD = newPWD;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getUserPhotoName() {
		return userPhotoName;
	}

	public void setUserPhotoName(String userPhotoName) {
		this.userPhotoName = userPhotoName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserIntroduction() {
		return userIntroduction;
	}

	public void setUserIntroduction(String userIntroduction) {
		this.userIntroduction = userIntroduction;
	}

	public String getUserADDR() {
		return userADDR;
	}

	public void setUserADDR(String userADDR) {
		this.userADDR = userADDR;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVerifiCode() {
		return verifiCode;
	}

	public void setVerifiCode(Integer verifiCode) {
		this.verifiCode = verifiCode;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
}
