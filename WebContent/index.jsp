<%@ page import="java.util.Date"%>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","No-cache"); 
	response.setDateHeader("Expires", -1); 
	response.setHeader("Cache-Control", "No-store");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = SystemContext.getCurrentUser();
	String userName;
	if (user != null) {
		userName = user.getUsername();
		session.setAttribute("name", userName);
		session.setAttribute("role", user.getUserRole());
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="resources/plugins/bootstrap.min.css">
<style type="text/css">
body{
	background-color:rgb(237,246,251);
    position: absolute;
    width:100%;
	display:fixed;
	height:100%;
	overflow-y:scroll;
	overflow-x:hidden;
}
.whiteLine{
	margin-left: 13%;
	margin-right: 13%;
	height: 1px;
    border-top: solid white 1px;
    -webkit-transform: scaleY(.5);
    transform:scaleY(.5);
}

.blueLine{
	margin-left: 12%;
	margin-right: 13%;
	height: 1px;
    border-top: solid rgb(55,129,243) 1px;
    -webkit-transform: scaleY(.5);
    transform:scaleY(.5);
}

.workText{
	height:30px;
	font-weight:200;
	font-size:20px;
	text-align:center;
	overflow:hidden;
}

.workList{
	margin-top: 4%;
	margin-bottom: 4%;
	margin-left: 13%;
	margin-right: 13%;
}

.workCanvas {
	border-radius:12px;
	width: 140px;
	height: 140px;
	box-shadow: 3px 3px 5px #888888;
	display:inline-block;
    margin-top: auto;
    margin-bottom:auto;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}
.workCanvasTitle{
	border-radius:12px;
	width: 140px;
	height: 140px;
	position: absolute;
    margin: auto;
    top: 0;
    left: 10;
    right: 0;
    bottom: 0;
}
 .arrowRight,.arrowLeft{
 	border-radius:12px;
	width: 50px;
	position: absolute;
    margin: auto;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
 }
.container{
    width: 100%;
    padding:0;
    overflow-x: hidden;
    overflow-y: hidden;
    white-space: nowrap;
}
/* 使用伪类选择器 ::-webkit-scrollbar ,兼容chrome和safari浏览器 */
.container::-webkit-scrollbar {  
	width:0 !important;   /* remove scrollbar space */
    background: transparent;  /* optional: just make scrollbar invisible */
}
/* 兼容IE10+ */
.container { -ms-overflow-style: none; }
/* 但是firefox代码无效 */
.container { overflow: -moz-scrollbars-none; }
.container .item{
	margin-right:8px;
	margin-left:8px;
	margin-bottom:8px;
	width:140px;
    display:inline-block;
    box-sizing: border-box;
}
footer h3{
	margin-bottom:30px;
	color:white;
}
footer a{
	color:white;
}
footer li{
	margin-bottom:5px;
	list-style-type:none;
}

#_carousel img{
	width:100%;
}
</style>
</head>
<body>
	<%@include file="common/navigationBar.jsp"%>
	<script>
		document.getElementById("bar").getElementsByTagName("li")[0].classList
				.add("active");
	</script>
	<!--data-ride自动开始播放-->
	<div id="_carousel" class="carousel slide" data-ride="carousel" style="margin: 0;margin-top: 58px;">
		<!-- 指示符 -->
		<ul class="carousel-indicators">
			<li data-target="#_carousel" data-slide-to="0" class="active"></li>
			<li data-target="#_carousel" data-slide-to="1"></li>
			<li data-target="#_carousel" data-slide-to="2"></li>
		</ul>
		<!-- 轮播图片 -->
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img src="resources/img/home2.png" >
			</div>
			<div class="carousel-item">
				<img src="resources/img/home2.png" >
			</div>
			<div class="carousel-item">
				<img src="resources/img/home2.png" >
			</div>
		</div>
		<!-- 左右切换按钮 -->
		<a class="carousel-control-prev" href="#_carousel" data-slide="prev">
			<span class="carousel-control-prev-icon"></span>
		</a> <a class="carousel-control-next" href="#_carousel" data-slide="next">
			<span class="carousel-control-next-icon"></span>
		</a>
	</div>
	<div class="row"  style="margin:5% 24% 5% 24%;">
		<div class="col" align="center">
			<div style="color:rgb(238,174,85);font-size:37px;">线上编程平台入口</div>
			<a href="/SteamCoding/build/tmp/index.html">
			<img style='width: 100%; margin: 0 auto;' src="resources/img/web-001.png" />
			</a>
		</div>
		<div class="col" align="center">
			<div style="color:rgb(121,183,240);font-size:37px;">赛事活动报名入口</div>
			<img style='width: 100%; margin: 0 auto;' src="resources/img/web-002.png" />
		</div>
	</div>
	
	<div style="background-color: #c2e2fa; margin: 0;">
		<div class="row pt-5">
			<img style="height: 80px; margin:auto;" src="resources/img/web-06.png">
		</div>
		
		<div>
			<div class="row workList">
				<div class="col-1">
					<img src="/SteamCoding/resources/img/photo1.png" class="workCanvasTitle">
				</div> 
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-13.png" class="arrowLeft" >
				</div>
				<div class="container col-9" id="recommended"></div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-14.png" class="arrowRight" >
				</div>
			</div>
			<div class="whiteLine"></div>
			<div class="row workList">
				<div class="col-1">
					<img src="/SteamCoding/resources/img/photo2.png" class="workCanvasTitle">
				</div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-13.png" class="arrowLeft" >
				</div>
				<div class="container col-9" id="hotKing"></div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-14.png" class="arrowRight" >
				</div>
			</div>
			<div class="whiteLine"></div>
			<div class="row workList">
				<div class="col-1">
					<img src="/SteamCoding/resources/img/photo3.png" class="workCanvasTitle">
				</div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-13.png" class="arrowLeft" >
				</div>
				<div class="container col-9" id="newAppreciation"></div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-14.png" class="arrowRight" >
				</div>
			</div>
			<div class="row" style="margin-left:37%;margin-right:37%;padding-top:2%;padding-bottom:4%;">
				<div class="col" align="center">累 计 创 作 ：<span id="totolWork"></span></div>
				<div class="col" align="center">今 日 创 作 ：<span id="todayWork"></span></div>
			</div>
		</div>
	</div>

	<div style="background-color: rgb(237, 246, 251); margin: 0;">
		<div class="row" style="padding-top:6%">
			<img style='height: 60px; margin: 0 auto;'
				src="resources/img/web-05.png">
		</div>
		
		<div>
			<div class="row workList">
				<div class="col-1">
					<img src="/SteamCoding/resources/img/photo4.png" class="workCanvasTitle">
				</div> 
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-13.png" class="arrowLeft" >
				</div>
				<div class="container col-9" id="mostPraise"></div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-14.png" class="arrowRight" >
				</div>
			</div>
			<div class="blueLine"></div>
			<div class="row workList" style="margin-bottom:0 ; padding-bottom:8%;">
				<div class="col-1">
					<img src="/SteamCoding/resources/img/photo5.png" class="workCanvasTitle">
				</div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-13.png" class="arrowLeft" >
				</div>
				<div class="container col-9" id="mostBrowse"></div>
				<div class="col-1">
					<img src="/SteamCoding/resources/img/web-14.png" class="arrowRight" >
				</div>
			</div>
		</div>
	</div>

	<footer style="padding-top:2%;padding-right:10%;padding-left:10%;background-color:rgb(62,62,73);">
		<div>
			<div class="row" align="center">
				<div class="col">
					<ul>
						<h3>关于我们</h3>
						<li><a href="#">自主研发图形化编程平台</a></li>
						<li><a href="#">通过项目制Steam教育模式</a></li>
						<li><a href="#">全面提升学生发展核心素质</a></li>
						<li style="width:280px;float:center;margin-top:10px; "><a href="#">2018年被评为杭州Steam创客教育联盟秘书长单位</a></li>
					</ul>
				</div>
				<div class="col">
					<ul class="navber-nav">
						<h3>联系我们</h3>
						<li><a href="#">公司名称：浙江思帝姆科技有限公司</a></li>
						<li><a href="#">联系人：彭老师</a></li>
						<li><a href="#">联系电话：17764563919</a></li>
					</ul>
				</div>
				<div class="col">
					<ul>
						<h3>合作领域</h3>
						<li><a href="#">Steam创新教育课程/软件/硬件</a></li>
						<li><a href="#">人工智能教育/人工智能等级考试</a></li>
					</ul>
				</div>
				<div class="col">
					<ul>
						<h3>思帝姆</h3>
						<li><a href="#">Steam创新教育先行者</a></li>
					</ul>
				</div>
			</div>
			<p align="center" style="margin-top: 2%;margin-bottom:0; color: #878B91;">浙ICP备19024571号-1</p>
			<p align="center" style="margin-top: 1%;padding-bottom:2%;margin-bottom:0; color: #878B91;">Copyright &copy;2018-2019</p>
		</div>
	</footer>
	
</body>
	
	<script
		src="resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery-slim.min.js"></script>
	<script src="resources/plugins/jquery-3.3.1.js"></script>
	<script src="common/common.js"></script>
	<script
		src="resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
	<script src="resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
	<script src="index.js"></script>
	<script src="login.js"></script>
	<script src="resources/plugins/validator.js"></script>			
</html>
