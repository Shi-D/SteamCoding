<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_ADMIN);role.add(ROLE_TEACHER);role.add(ROLE_STUDENT);role.add(ROLE_SUPERADMIN); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>个人信息</title>
	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/userInfo.css"/>
	<link rel="stylesheet" href="../../../common/managementPage.css"/>
</head>
<body>
<div class="main">
	<script type="text/javascript">
	var index = -1;
	</script>
	<%@include file = "../../../common/commonLeftBanner.jsp" %>
	<div class="layout">
		<div class="layout-header">
			<%@include file = "../../../common/header.jsp" %>
		</div>
		<div class="layout-content">
			<div class="layout-content-main">
				<div class="user-info-page">
					<h3>基本设置</h3>
					<hr>
					<div class="base-settings">
						<div class="update-base-info">
							<form>
								<div class="form-group">
									<label for="introduction">个人简介</label>
									<textarea class="form-control" name="introduction" id="introduction" rows="4" data-value=""></textarea>
									<div style="color:gray;font-size:12px;float:right"><span id="count">0</span>/150</div>
									<div class="invalid-feedback">
              							字数超过限制
            						</div>
								</div>
								<div class="form-group">
									<label for="email">邮箱</label>
									<input type="email" class="form-control" name="email" id="email" value="">
									<div class="invalid-feedback">
              							邮箱格式错误
            						</div>
								</div>
								<div class="form-group">
									<label for="phone">手机号码（公开）</label>
									<input type="tel" class="form-control" id="phone" name="phone" value="">
									<div class="invalid-feedback">
              							手机格式错误
            						</div>
								</div>
							</form>
							<button class="btn btn-primary" id="updateUserInfo">更新信息</button>
						</div>
						<div>
							<div>
								<p>头像</p>
							</div>
							<div>
								<img class="rounded-circle image-responsive" src="" id="userPhoto2">
							</div>
							<div>
								<label for="userPhotoFile" class="btn btn-outline-secondary" id="updateUserPhoto">更换头像</label>
								<input id="userPhotoFile" type="file" accept="image/*" style="display: none" >
							</div>
						</div>
					</div>
				</div>
				<div class="user-info-page">
					<h3>安全设置</h3>
					<hr>

					<div class="security-settings">
						<p>修改密码</p>
						<div class="update-password">
							<form>
								<div class="form-group">
									<label for="old-password">原密码</label>
									<input type="password" class="form-control" id="password-old" name="password" autocomplete="new-password">
									<div class="invalid-feedback" id="feedback-oldPwd">
										原密码错误
            						</div>
								</div>
								<div class="form-group">
									<label for="new-password">新密码</label>
									<input type="password" class="form-control" id="password-updatePwd" name="password" autocomplete="new-password">
									<div class="invalid-feedback">
              							密码长度不低于6位
            						</div>
								</div>
								<div class="form-group">
									<label for="confirm-new-password">确认新密码</label>
									<input type="password" class="form-control" id="passwordConfirm-updatePwd" name="passwordConfirm-updatePwd" autocomplete="new-password">
									<div class="invalid-feedback">
              							密码不一致
            						</div>
								</div>
							</form>
							<button class="btn btn-danger" id="updateUserPassword">修改密码</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../common/common.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/userInfo.js"></script>
</body>
</html>