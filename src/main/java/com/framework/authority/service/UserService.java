package com.framework.authority.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.framework.authority.entity.Role;
import com.framework.authority.entity.User;
import com.framework.common.SystemContext;
import com.framework.constants.SteamConstants;
import com.framework.system.common.base.service.impl.BaseServiceImpl;
import com.framework.system.common.entity.Results;
import com.framework.system.common.global.CustomProperties;
import com.framework.system.entity.Organization;
import com.framework.system.service.organization.OrganizationService;
import com.framework.utils.URLUtils;
import com.steam.service.ClassesService;

@Service
public class UserService extends BaseServiceImpl<User> {
	public static final String SERVICE_BEAN_NAME = "userService";

	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ClassesService classesService;

	/**
	 * 获取所有用户，但是不级联查询
	 * 
	 * @return
	 */

	public List<User> queryAllWithNoCascade() {
		String hql = "FROM User u LEFT JOIN FETCH u.roles r";

		return this.find(hql);
	}

	/**
	 * 获取用户，并级联查询
	 * 
	 * @return
	 */
	public User queryWithCascade(Integer userId) {
		if (userId == null) {
			return null;
		}

		String hql = "FROM User u LEFT JOIN FETCH u.roles r WHERE u.id=?";

		return (User) this.uniqueResultByHQL(hql, userId);
	}

	/**
	 * 根据编号查询用户
	 * 
	 * @param codes
	 * @return
	 */
	public Map<String, String> queryByCodes(String codes) {
		String hql = "select new User(userCode,userName) from User where userCode in("
				+ codes + ")";
		List<User> userList = this.find(hql);
		Map<String, String> result = new HashMap<String, String>();
		for (User user : userList) {
			result.put(user.getUserCode(), user.getUserName());
		}
		return result;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userCode
	 *            账号
	 * @return
	 */
	public User getUser() {
		User user = SystemContext.getCurrentUser();
		String hql = "FROM User WHERE userCode=?";
		User users;
		try{
			users = (User) this.uniqueResultByHQL(hql, user.getUserCode());
		}catch(Exception e){
			users = null;
		}
		return users;
	}

	/**
	 * 判断账号是否存在
	 * 
	 * @param userCode
	 *            账号
	 * @return
	 */
	public Boolean isExist(String userCode) {
		String hql = "SELECT COUNT(*) FROM User WHERE userCode=?";

		Integer count = this.getTotalCountByHQL(hql, userCode);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* 根据IDs查用户 */
	public List<User> queryByIds(String ids) {
		String hql = "from User where id in(" + ids + ")";
		List<User> userList = this.find(hql);
		return userList;
	}

	public List<User> queryByCode(String userCode) {
		String hql = "from User where userCode = ?";
		return this.find(hql, userCode);
	}

	/* 将user按照机构组成Map */
	public Map<Organization, List<User>> TransformToMap(List<User> userList) {
		Map<Organization, List<User>> result = new HashMap<Organization, List<User>>();
		for (User user : userList) {
			List<User> users = result.get(user.getOrganization());
			if (users == null) {
				users = new ArrayList<User>();
				users.add(user);
				result.put(user.getOrganization(), users);
			} else {
				users.add(user);
				result.put(user.getOrganization(), users);
			}
		}
		return result;
	}

	/**
	 * security使用，通过code查用户，使用了级联查询角色、权限的相关信息
	 * 
	 * @param userCode
	 *            用户账号
	 * @return
	 */
	public User getByCode(String userCode) {
		String hql = "FROM User u LEFT JOIN FETCH u.organization o LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.authorities where u.userCode=?";

		Object userObj = this.uniqueResultByHQL(hql, userCode);

		if (userObj instanceof User) {
			return (User) userObj;
		}

		return null;
	}

	/* 重置密码 */
	public void initPassword(String userIds) {
		String[] ids = userIds.split(",");
		for (String id : ids) {
			User user = this.get(User.class, Integer.parseInt(id));
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.setUserPWD(encoder.encodePassword(
					CustomProperties.DEFAULT_PASSWORD, user.getUsername()));
			this.update(user);
		}
	}

	// 更新用户头像
	public List<Map<String, Object>> uploadUserPhotoService(File userPhoto) {
		Results result = new Results();
		User user = SystemContext.getCurrentUser();
		File file = new File(
				URLUtils.generateURLforUserInfo(user.getUsername()));
		if (!file.exists())
			file.mkdirs();
		try {
			FileUtils.copyFile(userPhoto, new File(file, userPhoto.getName()));
			String _userPhotoName = this.updateUserPhoto(user.getId(),
					user.getUsername() + "/userInfo/" + userPhoto.getName());
			if (!_userPhotoName.equals("defaultPhoto/defaultPhoto.jpg")) {
				File photo = new File(SteamConstants.ABSOLUTE_URL
						+ _userPhotoName);
				photo.delete();
			}
			result.setResultValue("更新成功");
		} catch (Exception e) {
			result.setResultValue("更新失败");
		}
		return result.getResult();
	}
	
	//创建管理员时获取账号的密码
	public void getPassword(String password,String userCode) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String newpassword = encoder.encodePassword(password, userCode);
		System.out.println("++++++++++++++++++++++getPassword");
		System.out.println(newpassword);
		return ;
	}

	// 更改密码用旧密码
	public List<Map<String, Object>> updatePasswordService(String originalPWD,
			String newPWD) {
		Results result = new Results();
		User user = this.getUser();
		try {
			String userPWD = user.getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String oripsd = encoder.encodePassword(originalPWD,
					user.getUserCode());
			String newpsd = encoder.encodePassword(newPWD, user.getUserCode());
			if (userPWD.equals(oripsd)) {
				user.setUserPWD(newpsd);
				this.update(user);
				result.addAllValue(true, "密码更改成功");
			} else {
				result.addAllValue(false, "密码更改失败");
			}
		} catch (Exception e) {
			result.addAllValue(false, "密码更改失败");
		}
		return result.getResult();
	}

	// 重置密码用验证码
	public List<Map<String, Object>> resetPasswordService(String phone,
			Integer Code, String psd) {
		Results result = new Results();
		String sql;
		try {
			sql = "SELECT CODE,TIME FROM TEMP_SMS WHERE PHONE = '" + phone
					+ "'";
			List<Map<String, Object>> sms = this.getResultBySQL(sql);
			if (Code != 0
					&& Code == Integer.parseInt(sms.get(0).get("CODE")
							.toString())
					&& new Date().getTime()
							- Long.parseLong(sms.get(0).get("TIME").toString()) <= 300000) {
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				psd = encoder.encodePassword(psd, phone);
				sql = "UPDATE SEC_USER SET USER_PWD = '" + psd
						+ "' WHERE USER_CODE = '" + phone + "'";
				this.executeSQLUpdate(sql);
				sql = "DELETE FROM TEMP_SMS WHERE PHONE = '" + phone + "'";
				this.executeSQLUpdate(sql);
				result.addAllValue(true, "密码重置成功");
			} else {
				result.addAllValue(false, "验证码无效");
			}
		} catch (Exception e) {
			result.addAllValue(false, "密码重置发生错误");
		}
		return result.getResult();
	}

	// 修改密码发送验证码
	public List<Map<String, Object>> updatePwdSmsPostService(String phone) {
		Results result = new Results();
		try {
			List<Map<String, Object>> passWord = this
					.getResultBySQL("SELECT USER_PWD FROM SEC_USER WHERE USER_CODE = '"
							+ phone + "'");
			if (passWord.size() == 0) {
				result.addAllValue(false, "该手机号未注册");
			} else {
				Integer smsNum = sendSms(phone);
				long timeStemp = new Date().getTime();
				if (this.executeSQLUpdate("UPDATE TEMP_SMS SET CODE =" + smsNum
						+ ",TIME = '" + timeStemp + "'  WHERE PHONE = '"
						+ phone + "'") == 0) {
					String sql = "INSERT INTO TEMP_SMS VALUES ('" + phone
							+ "','" + smsNum + "','" + timeStemp + "')";
					this.executeSQLUpdate(sql);
				}
				result.addAllValue(true, passWord.get(0).get("USER_PWD"));
			}
		} catch (Exception e) {
			result.addAllValue(false, "无法发送验证码");
		}
		return result.getResult();
	}

	/* 修改用户 */
	public List<Map<String, Object>> updateUserInfo(String userMail,
			String userPhone, String userIntroduction) {
		Results result = new Results();
		try {
			Integer userId = SystemContext.getCurrentUser().getId();
			this.get(User.class, userId);
			String hql = "UPDATE SEC_USER SET USER_MAIL = '" + userMail
					+ "', USER_PHONE ='" + userPhone
					+ "',USER_INTRODUCTION = '" + userIntroduction
					+ "'  WHERE USER_ID = " + userId;
			this.executeSQLUpdate(hql);
			result.setResultValue("更新成功");
		} catch (Exception e) {
			result.setResultValue("更新失败");
		}
		return result.getResult();
	}

	/* 修改管理员 */
	public List<Map<String, Object>> updateAdmin(Integer id,String adminName,String adminGender, Integer organizationId) {
		Results result = new Results();
		try {
			String sql = "UPDATE SEC_USER SET USER_NAME = '" + adminName
					+ "', USER_GENDER ='" + adminGender
					+ "',ORGANIZATIONID ='" + organizationId
					+ "'  WHERE USER_ID = " + id + " and USER_ROLE = " + 1;
			this.executeSQLUpdate(sql);
			result.setResultValue("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultValue("修改失败");
		}
		return result.getResult();
	}

	public String updateUserPhoto(Integer userId, String userPhotoName) {
		String _userPhotoName = this.get(User.class, userId).getUserPhotoName();
		String hql = "UPDATE SEC_USER SET USER_PHOTO_NAME = '" + userPhotoName
				+ "' WHERE USER_ID = " + userId;
		this.executeSQLUpdate(hql);
		return _userPhotoName;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public Integer addUser(String userName, String userCode, String userGender,
			Integer userType, Integer organizationId) {

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// 对密码进行MD5加密
		String userPWD = encoder.encodePassword(
				CustomProperties.DEFAULT_PASSWORD, userCode);
		User user = new User();
		user.setAuthorities(null);
		user.setRoles(null);
		user.setOrganization(organizationService.get(Organization.class,
				organizationId));
		user.setUserADDR(null);
		user.setUserCode(userCode);
		user.setUserCreationTime(new Date());
		user.setUserFlag(true);
		user.setUserGender(userGender);
		user.setUserIntroduction("这个人很懒，什么也没有留下。");
		user.setUserMail(null);
		user.setUserName(userName);
		user.setUserPhone(null);
		user.setUserPhotoName("defaultPhoto/defaultPhoto.jpg");
		user.setUserPWD(userPWD);
		user.setUserRole(userType);
		this.save(user);
		return user.getId();
		/*
		 * String hql =
		 * "insert into SEC_USER ( USER_CODE,USER_FLAG,USER_NAME,USER_GENDER,USER_PWD,USER_ROLE) VALUES ('"
		 * + userCode + "',1,'" + userName + "','" + userGender + "','" +
		 * userPWD + "'," + userType + ")"; this.executeSQLUpdate(hql);
		 */
	}

	/* 通过机构查询用户 */
	public List<User> queryByOrganization(Organization organization) {
		String hql = "from User where organization = ?";
		List<User> userList = this.find(hql, organization);
		return userList;
	}

	/**
	 * 通过机构ID查询用户
	 * 
	 * @param organizationId
	 *            机构编号
	 * @return
	 */
	public List<User> queryByOrganizationId(Integer organizationId) {
		String hql = "from User where organization.id = ?";
		List<User> userList = this.find(hql, organizationId);
		return userList;
	}

	/**
	 * 通过机构ID查询用户，{key: userId, value: User对象}
	 * 
	 * @param organizationId
	 *            机构编号
	 * @return
	 */
	public Map<Integer, User> queryByOrganizationIdToMap(Integer organizationId) {
		List<User> userList = this.queryByOrganizationId(organizationId);

		Map<Integer, User> map = new HashMap<Integer, User>();

		for (User user : userList) {
			map.put(user.getId(), user);
		}

		return map;
	}

	/* 通过机构组查询机构 */
	public List<Map<String, Object>> queryByOrganization(Integer organizationId) {
		String hql = "select user.id as id,user.userCode as userCode,user.email as email,user.userTEL as userTEL,user.userName as userName,user.wechat as wechat,organization.organizationName as organizationName from User user left join user.organization organization where organization.id =?";
		List<Map<String, Object>> userList = this.getResultByHQL(hql,
				organizationId);
		return userList;
	}

	/* 用户关联角色 */
	public void updateRelationRole(User user, List<Role> roles) {
		User sourceUser = this.get(User.class, user.getId());

		if (roles != null && roles.size() > 0) {
			Set<Role> sourceSet = new HashSet<Role>();

			for (Role role : roles) {
				Role sourceRole = roleService.get(Role.class, role.getId());

				sourceSet.add(sourceRole);
			}

			sourceUser.setRoles(sourceSet);
		} else {
			sourceUser.setRoles(null);
		}

		this.update(sourceUser);
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		user = this.get(User.class, user.getId());

		this.delete(user);

	}
	
	public List<Map<String, Object>> queryDefaultOrganizationService(){
		String sql;
		sql = "SELECT PARAMETER_DEFAULT_VALUE FROM T_SYSTEM_PARAMETER WHERE ID = 1";
		return this.getResultBySQL(sql);
	}

	// 注册用户
	public List<Map<String, Object>> registerUserService(String userName,
			String phone, String passWord, Integer verifiCode, String organizationId) {
		String sql;
		Results result = new Results();
		try {
			sql = "SELECT CODE,TIME FROM TEMP_SMS WHERE PHONE = '" + phone
					+ "'";
			List<Map<String, Object>> sms = this.getResultBySQL(sql);
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			passWord = encoder.encodePassword(passWord, phone);
			if (verifiCode != 0
					&& verifiCode == Integer.parseInt(sms.get(0).get("CODE")
							.toString())
					&& new Date().getTime()
							- Long.parseLong(sms.get(0).get("TIME").toString()) <= 300000) {
				
				
				sql = "INSERT INTO SEC_USER(USER_CODE,USER_FLAG,USER_NAME,USER_PWD,USER_PHONE,USER_ROLE,ORGANIZATIONID) VALUES ('"
						+ phone
						+ "',"
						+ 1
						+ ",'"
						+ userName
						+ "','"
						+ passWord
						+ "','" + phone + "'," + 3 + ",'" + organizationId + "')";
				this.executeSQLUpdate(sql);
				sql = "DELETE FROM TEMP_SMS WHERE PHONE = '" + phone + "'";
				this.executeSQLUpdate(sql);
				System.out.println(new Date().getTime()
						- Long.parseLong(sms.get(0).get("TIME").toString()));
				result.setResultValue("注册成功");
			} else {
				System.out.println(new Date().getTime()
						- Long.parseLong(sms.get(0).get("TIME").toString()));
				result.setResultValue("无效验证码");
			}
		} catch (Exception e) {
			result.setResultValue("注册失败");
		}
		return result.getResult();
	}

	// 发送验证码
	public Integer sendSms(String phone) {
		Integer smsNum = (int) ((Math.random() * 9 + 1) * 100000);
		try {
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			try {
				List<BasicNameValuePair> formparams = new ArrayList<>();
				formparams.add(new BasicNameValuePair("Account", "steam"));
				formparams.add(new BasicNameValuePair("Pwd",
						SteamConstants.SMS_PWD));// 登录后台 首页显示
				formparams.add(new BasicNameValuePair("Content", smsNum
						.toString()));
				formparams.add(new BasicNameValuePair("Mobile", phone));
				formparams.add(new BasicNameValuePair("SignId",
						SteamConstants.SMS_SIGN_ID));// 登录后台 添加签名获取id
				// 短信免审模板ID,id可在管理后台获取
				formparams.add(new BasicNameValuePair("TemplateId",
						SteamConstants.SMS_TEMPLATE_ID));
				HttpPost httpPost = new HttpPost(
						SteamConstants.SMS_TEMPLATE_URL);
				httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smsNum;
	}

	// 注册用户发送验证码
	public List<Map<String, Object>> smsPostService(String phone) {
		Results result = new Results();
		try {
			if (this.getResultBySQL(
					"SELECT * FROM SEC_USER WHERE USER_CODE = '" + phone + "'")
					.size() != 0) {
				result.setResultValue("该手机号已注册");
			} else {
				Integer smsNum = sendSms(phone);
				long timeStemp = new Date().getTime();
				if (this.executeSQLUpdate("UPDATE TEMP_SMS SET CODE =" + smsNum
						+ ",TIME = '" + timeStemp + "'  WHERE PHONE = '"
						+ phone + "'") == 0) {
					String sql = "INSERT INTO TEMP_SMS VALUES ('" + phone
							+ "','" + smsNum + "','" + timeStemp + "')";
					this.executeSQLUpdate(sql);
				}
				result.setResultValue(true);
			}
		} catch (Exception e) {
			result.setResultValue(false);
		}
		return result.getResult();
	}

	/*
	 * 
	 * 导入用户
	 */
	public List<Map<String, Object>> userImport(
			List<Map<String, String>> datas, Integer organizationId,
			Integer userRole) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> failResult = new HashMap<String, Object>();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		if (CollectionUtils.isEmpty(datas)) {
			result.put("error.empty.file", "上传的文件数据为空！");
			results.add(result);
			return results;
		}
		for (Map<String, String> map : datas) {
			if (userRole == 3) {
				try {
					this.addUser(map.get("学生名"), map.get("学生账号"),
							map.get("性别"), userRole, organizationId);
				} catch (Exception e) {
					failResult.put("学生账号", map.get("学生账号"));
					results.add(failResult);
				}
			} else if (userRole == 2) {
				try {
					this.addUser(map.get("教师名"), map.get("教师账号"),
							map.get("性别"), userRole, organizationId);
				} catch (Exception e) {
					failResult.put("教师账号", map.get("教师账号"));
					results.add(failResult);
				}
			}
		}
		return results;
	}

	/*
	 * 19：29 正在编辑 上传学生信息 查看学生是否存在 1.不存在则创建该学生 2.添加进入班级
	 * 
	 * 返回创建失败的学生账号和添加失败
	 */
	/*
	 * public List<Map<String, Object>> userImport(List<Map<String, String>>
	 * datas, Integer organizationId, Integer userRole, Integer classId) {
	 * Map<String, Object> result = new HashMap<String, Object>();
	 * List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
	 * if (CollectionUtils.isEmpty(datas)) { result.put("message",
	 * "上传的文件数据为空！"); results.add(result); return results; } String studentIds =
	 * ""; String studentLoginAccount = "";
	 * 
	 * // 判断是否有非法账号 for (Map<String, String> map : datas) { if
	 * (map.get("学生账号").indexOf(" ") != -1) { studentLoginAccount =
	 * map.get("学生账号") + ","; } } if (studentLoginAccount.length() > 0) {
	 * result.put("message", studentLoginAccount); results.add(result); return
	 * results; }
	 * 
	 * // 账号检查合法以后进行操作 for (Map<String, String> map : datas) { if
	 * (!this.isExist(map.get("学生账号"))) { // 如果学生不存在 try { // 如果账号不存在，则添加为新用户
	 * studentIds += this.addUser(map.get("学生名"), map.get("学生账号"),
	 * map.get("性别"), userRole, organizationId); } catch (Exception e) {
	 * result.put("message", "添加学生账号失败！"); results.add(result); } } else { //
	 * 如果账号已经存在，则记录该账号并返回给前端 studentLoginAccount = map.get("学生账号").toString() +
	 * ","; } studentIds += ","; }
	 * 
	 * studentIds = studentIds.substring(0, studentIds.length() - 1);
	 * studentLoginAccount = studentLoginAccount.substring(0,
	 * studentLoginAccount.length() - 1);
	 * 
	 * // 绑定学生与班级的关系 classesService.addStudentsToClass(classId,
	 * studentIds.split(","));
	 * 
	 * if (studentLoginAccount.length() == 0) { result.put("message",
	 * "添加班级成功！");
	 * 
	 * } else { result.put("message", studentLoginAccount); }
	 * 
	 * results.add(result); return results; }
	 */

	/**
	 * 批量删除用户
	 * 
	 * @param userIds
	 */
	public void deleteBatchUser(String userIds) {
		/* 删除用户之前先清空用户和角色之间的关联表 */
		/*
		 * String sql = "delete from SEC_USER_ROLES where USER_ID in(" + userIds
		 * + ")"; this.executeSQLUpdate(sql);
		 * 
		 * String hql = "DELETE UserProperty WHERE userId In (" + userIds + ")";
		 * this.executeHQLUpdate(hql);
		 */

		String hql = "delete User where id in(" + userIds + ")";
		this.executeHQLUpdate(hql);

	}

	/* 冻结用户 */
	public void updateDisabledUser(String userIds) {
		String hql = "update User set userFlag = ? where id in(" + userIds
				+ ")";
		this.executeHQLUpdate(hql, false);
	}

	/* 启用用户 */
	public void updateEnableUser(String userIds) {
		String hql = "update User set userFlag = ? where id in(" + userIds
				+ ")";
		this.executeHQLUpdate(hql, true);
	}

	public boolean isAuthorized(String authority) {
		return isAuthorized(new SimpleGrantedAuthority(authority));
	}

	/*-------------*/
	public boolean isAuthorized(GrantedAuthority authority) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return ((!(principal instanceof User)) || (!(((User) principal)
				.getAuthorities().contains(authority))));
	}

	public boolean isAuthorized(List<String> authorities) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			for (String authority : authorities) {
				if (!(((User) principal).getAuthorities()
						.contains(new SimpleGrantedAuthority(authority))))
					return false;
			}
		}
		return true;
	}

}
