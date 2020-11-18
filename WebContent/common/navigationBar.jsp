<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#bar li a{
	color:black;
	font-size:25px;
}
.active a{
	color:rgb(55,128,240) !important; 
}
</style>
<nav class="container navbar-expand">
	<div class="row">
		<!--导航栏-->
		<nav class="navbar fixed-top" style="background:white;height:76px;">
			<div class="navbar-header col-3">
				<img src="/SteamCoding/resources/img/web-03.png" style="width:15%;height:auto;float:right" >
			</div>
			<div class="col-6">
				<ul class="navbar-nav nav-justified" id="bar">
					<li class="nav-item"><a class="nav-link "
						href="/SteamCoding/index.jsp">首页</a></li>
					<li class="nav-item"><a class="nav-link"
						href="/SteamCoding/build/tmp/index.html">创作</a></li>
					<li class="nav-item"><a class="nav-link"
						href="/SteamCoding/pages/course/course.jsp">课程</a></li>
					<li class="nav-item"><a class="nav-link"
						href="/SteamCoding/pages/productionExhibiton/productionExhibiton.jsp">作品</a></li>
				</ul>
			</div>
			<div class="col-3 d-flex justify-content-end" style="margin:0 auto;">
				<div class="btn-group" style="height:38px;">
					<button class="btn btn-light mr-4" id="sign-up"
					data-toggle="modal" data-target="#sign-up-modal">注册</button>
				</div>
				<form class="form-inline" style="float: right;"
					action="/SteamCoding/login.jsp">
					<%
						if (session.getAttribute("name") == null) {
					%>
					<input class="btn btn-light" onclick="../../login.jsp"
						type="submit" value="登录" />
					<%
						} else {
					%>
					<div class="btn-group">
						<%
							if (session.getAttribute("role").equals(0)) {
						%>
						<button type="button" class="btn btn-light"
							onclick="javascript:window.location.href='/SteamCoding/pages/superAdminPage/html/superAdminOrganizationManagement.jsp'">个人中心</button>
						<%
							} else if (session.getAttribute("role").equals(1)) {
						%>
						<button type="button" class="btn btn-light"
							onclick="javascript:window.location.href='/SteamCoding/pages/administratorPage/html/adminClassManagement.jsp'">个人中心</button>
						<%
							} else if (session.getAttribute("role").equals(2)) {
						%>
						<button type="button" class="btn btn-light"
							onclick="javascript:window.location.href='/SteamCoding/pages/teacherPage/html/classManagement.jsp'">个人中心</button>
						<%
							} else if (session.getAttribute("role").equals(3)) {
						%>
						<button type="button" class="btn btn-light"
							onclick="javascript:window.location.href='/SteamCoding/pages/studentPage/html/studentWorkManagement.jsp'">个人中心</button>
						<%
							}
						%>
						<button type="button"
							class="btn btn-primary dropdown-toggle dropdown-toggle-split"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						</button>
						<div class="dropdown-menu">
							<a id = "loginOut" class="dropdown-item" href="javascript:void(0);">退出登录</a>
							<a id = "switchUser" class="dropdown-item" href="/SteamCoding/j_spring_security_logout">切换用户</a>
						</div>
					</div>
					<%
						}
					%>
				</form>
			</div>
		</nav>
	</div>
</nav>

<div class="modal" id="sign-up-modal" data-backdrop="static">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<p>用户注册</p>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<form id="signUp">
						<div class="form-group">
							<label for="phone" class="col-form-label">手机号</label> <input
								type="tel" class="form-control phone" id="phone-signUp"
								name="phone" placeholder="请输入11位手机号">
							<div class="invalid-feedback" id="phone-feedback">手机号码格式错误
							</div>
						</div>
						<div class="form-group">
							<label for="verifiCode" class="col-form-label">短信验证码</label>
							<div class="form-row">
								<div class="col">
									<input type="text" class="form-control verifiCode"
										id="verifiCode-signUp" name="code" placeholder="请输入6位数字验证码"
										disabled></input>
									<div class="invalid-feedback" id="code-feedback">
										请输入6位数字验证码</div>
								</div>
								<div class="col">
									<input class="btn btn-primary getVerifiCode"
										id="getVerifiCode-signUp" value="免费获取验证码" disabled></input>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="userName" class="col-form-label">真实姓名</label> <input
								type="text" name="name" class="form-control userName"
								placeholder="请输入真实姓名" id="userName">
							<div class="invalid-feedback">请输入中文名</div>
						</div>
						<div class="form-group">
							<label for="passwd" class="col-form-label">密码</label> <input
								type="password" class="form-control password"
								id="password-signUp" name="password" placeholder="请输入密码">
							<div class="invalid-feedback">密码长度不低于6位</div>
						</div>
						<div class="form-group">
							<label for="passwdConfirm" class="col-form-label">确认密码</label> <input
								type="password" class="form-control passwordConfirm"
								id="passwordConfirm-signUp" name="passwordConfirm-signUp"
								placeholder="再次输入密码">
							<div class="invalid-feedback">密码不一致</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="confirm-signUp">注册</button>
				</div>
			</div>
		</div>
	</div>