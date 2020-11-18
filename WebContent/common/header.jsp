<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<i class="fa fa-outdent sider-menu-trigger" data-toggle="tooltip" data-placement="bottom" title="返回首页" onclick="window.location.href='../../../index.jsp'"></i>
<div>
	<span class="user-info">
		<div class="btn-group">
			<button type="button" class="btn btn-light" onclick="javascript:window.location.href = '../../userInfo/html/userInfo.jsp'">
				<img class="user-avatar rounded-circle"
					 src="" id="userPhoto">
				<span class="user-name" id="userName"></span>
			</button>
			<button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			</button>
			<div class="dropdown-menu">
				<a id = "loginOut" class="dropdown-item" href="javascript:void(0);">退出登录</a>
				<a id = "switchOut" class="dropdown-item" href="/SteamCoding/j_spring_security_logout">切换用户</a>
			</div>
		</div>
	</span>
</div>
