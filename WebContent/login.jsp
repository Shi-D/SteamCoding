<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.servlet.http.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<%-- <base href="<%=basePath %>"> --%>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SteamCoding</title>
<link rel="stylesheet" href="resources/plugins/font-awesome.css">
<link rel="stylesheet" href="resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<style type="text/css">
html,body {height: 100%;}
body {display: -ms-flexbox;display: flex;-ms-flex-align: center;align-items: center;padding-top:40px;padding-bottom: 40px;background-color: #f5f5f5;}
.form-signin {width: 100%;max-width: 330px;padding: 15px;margin: auto;}
.form-signin .checkbox {font-weight: 400;}
.form-signin .form-control {position: relative;box-sizing: border-box;height: auto;padding: 10px;font-size: 16px;}
.form-signin .form-control:focus {z-index: 2;}
.form-signin input[type="email"] {margin-bottom: -1px;border-bottom-right-radius: 0;border-bottom-left-radius: 0;}
.form-signin input[type="password"] {border-top-left-radius: 0;border-top-right-radius: 0;}
.register{position: absolute;right: 8%;top: 3%;}
.error_message{text-align:left !important;color:rgb(255,0,0);display:none;}
.errorMessage{height:25px;}
.card{left:100px;visibility:hidden;position:fixed;z-index:100;}
#cancelToVerify{color:#aaaaaa;font-size:25px;display:relative;right:0px;}
</style>
<link rel="stylesheet" href="resources/plugins/slideVerify/slideVerify.css">         
<script type="text/javascript">
function onbodyKeyDown(event){
	event=event?event:window.event;
	if(event.keyCode==13){
		$("#login").click();
	}
}

</script>
</head>
<body onkeydown="onbodyKeyDown(event)">

<div class="card" id="loginCard" style="width:350px">
  <div class="card-body">
	<span id="cancelToVerify"><i class="fa fa-times-circle-o"></i></span>
	<br>
    <div class="container" id="verifyContainer">
		<div id="captcha" style="position:relative"></div>
		<div id="msg"></div>
	</div>
  </div>
</div>
	
	<div class="register">
	<!-- id="sign-up"  -->
		<button class="btn btn-link"  id="sign-up" data-toggle="modal" data-target="#sign-up-modal"><span>注册</span></button>
		<button class="btn btn-link"  id="reset-password" data-toggle="modal" data-target="#reset-password-modal" ><span>忘记密码</span></button>
	</div>

	<form class="form-signin text-center" id="loginForm">
		<div class="login">
			<div class="login_infor">
				<div class="userlogin">
					<h1 class="h3 mb-3 font-weight-normal">欢迎登陆</h1>

					<input type="text" id="inputEmail" class="form-control form-control-login" name="j_username" placeholder="请输入用户名"
						value='<s:property value="#parameters['username'][0]"/>'>
					<input type="password" id="inputPassword" name="j_password" placeholder="请输入密码"
						class="form-control form-control-login"/>
					<div class='errorMessage'>
						<p class="error_message">用户名或密码错误</p>
					</div>
					<a class="btn btn-lg btn-primary btn-block" href='javascript:void(0);' id='login'>登录</a>
					<p class="mt-2 mb-3 text-danger">本系统不支持IE浏览器,推荐使用较新浏览器,例如Chrome或者FireFox</p>
				</div>
			</div>
		</div>
	</form>
	
                        
	<div class="modal" id="sign-up-modal" data-backdrop="static">
		<div class="modal-dialog " >
			<div class="modal-content">
				<div class="modal-header">
					<p>用户注册</p>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
			        <form id="signUp">
			          <div class="form-group">
			            <label for="phone" class="col-form-label">手机号</label>
			            <input type="tel" class="form-control phone" id="phone-signUp" name="phone" placeholder="请输入11位手机号">
			            <div class="invalid-feedback" id="phone-feedback">
              				手机号码格式错误
            			</div>
			          </div>
			          <div class="form-group">
			            <label for="verifiCode" class="col-form-label">短信验证码</label>
			            <div class="form-row">
				            <div class="col">
				            	<input type="text" class="form-control verifiCode" id="verifiCode-signUp" name="code" placeholder="请输入6位数字验证码" disabled></input>
				            	<div class="invalid-feedback" id="code-feedback-signUp">
              						请输入6位数字验证码
            					</div>
				            </div>
				            <div class="col">
				            	<input class="btn btn-primary getVerifiCode" id="getVerifiCode-signUp" value="免费获取验证码" disabled></input>
				            </div>
			            </div>
			          </div>
			          <div class="form-group">
			            <label for="userName" class="col-form-label">真实姓名</label>
			            <input type="text" name="name" class="form-control userName" placeholder="请输入真实姓名" id="userName">
			            <div class="invalid-feedback">
              				请输入中文名
            			</div>
			          </div>
			          <div class="form-group">
			            <label for="passwd" class="col-form-label">密码</label>
			            <input type="password" class="form-control password" id="password-signUp" name="password" placeholder="请输入密码">
			            <div class="invalid-feedback">
              				密码长度不低于6位
            			</div>
			          </div>
			          <div class="form-group">
			            <label for="passwdConfirm" class="col-form-label">确认密码</label>
			            <input type="password" class="form-control passwordConfirm" id="passwordConfirm-signUp" name="passwordConfirm-signUp" placeholder="再次输入密码" >
			            <div class="invalid-feedback" id="code-feedback-signUp">
              				密码不一致
            			</div>
			          </div>
			    	</form>
		        </div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="confirm-signUp">注册</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="reset-password-modal" data-backdrop="static">
		<div class="modal-dialog " >
			<div class="modal-content">
				<div class="modal-header">
					<p>找回密码</p>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
			        <form id="resetPwd">
			          <div class="form-group">
			            <label for="phone" class="col-form-label">手机号</label>
			            <input type="tel" class="form-control phone" id="phone-updatePwd" name="phone" placeholder="请输入11位手机号">
			            <div class="invalid-feedback" id="phone-feedback">
              				手机号码格式错误
            			</div>
			          </div>
			          <div class="form-group">
			            <label for="verifiCode" class="col-form-label">短信验证码</label>
			            <div class="form-row">
				            <div class="col">
				            	<input type="text" class="form-control verifiCode" id="verifiCode-updatePwd" name="code" placeholder="请输入6位数字验证码" disabled></input>
				            	<div class="invalid-feedback" id="code-feedback-updatePwd">
              						请输入6位数字验证码
            					</div>
				            </div>
				            <div class="col">
				            	<input class="btn btn-primary getVerifiCode" id="getVerifiCode-updatePwd" value="免费获取验证码" disabled></input>
				            </div>
			            </div>
			          </div>
			          <div class="form-group">
			            <label for="passwd" class="col-form-label">新的密码</label>
			            <input type="password" class="form-control password" id="password-updatePwd" name="password" placeholder="请输入密码">
			            <div class="invalid-feedback">
              				密码长度不低于6位
            			</div>
			          </div>
			          <div class="form-group">
			            <label for="passwdConfirm" class="col-form-label">确认密码</label>
			            <input type="password" class="form-control passwordConfirm" id="passwordConfirm-updatePwd" name="passwordConfirm-updatePwd" placeholder="再次输入密码">
			            <div class="invalid-feedback">
              				密码不一致
            			</div>
			          </div>
			    	</form>
		        </div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="confirm-updatePwd">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>


<script src="resources/plugins/jquery-3.3.1.js"></script>
<script src="common/common.js"></script>
<script>
function getRandomNumberByRange(start, end) {
    return Math.round(Math.random() * (end - start) + start)
}
function getRandomImg() {
    return "resources/plugins/slideVerify/img/" + getRandomNumberByRange(0, 6) + ".png"
}
</script>
<script src="resources/plugins/slideVerify/slideVerify.js"></script>
<script>
jigsaw.init(document.getElementById('captcha'), function () {});
</script>
<script src="login.js"></script>
<%-- <script src="resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery-slim.min.js"></script> --%>
<script src="resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="resources/plugins/validator.js"></script>
</html>
