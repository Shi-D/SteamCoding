<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_ADMIN);role.add(ROLE_SUPERADMIN);role.add(ROLE_TEACHER);role.add(ROLE_STUDENT); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>我的课程</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=0" />
<link rel="stylesheet"
	href="../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="courseContent.css">
<link rel="stylesheet" href="../utitles/navigation.css">
</head>
<body style="background-color: whitesmoke">
	<%@include file="../../../common/navigationBar.jsp"%>
	<script>
		document.getElementById("bar").getElementsByTagName("li")[2].classList
				.add("active");
	</script>
	<div class="row"
		style="margin-top: 5%; margin-left: auto; margin-right: auto; width: 60%;">
		<div class="col-7">
			<div class=""
				style="background-color: white; width: 100%; height: auto; margin-right: 1%">
				<div class="row" id="courseCover"
					style="display: flex; align-items: center; justify-content: center; padding: 4%;">

				</div>
			</div>

			<div class="pt-2 pb-3"
				style="margin-top: 5%; background-color: white; width: 100%">
				<h2 class="col-12 pb-2 pt-2"
					style="margin-top: 3%; margin-left: 3%;">学习内容</h2>
				<div style="margin-left: 6%">
					<div class="list-group row " id="courseChapter"></div>
				</div>
			</div>
		</div>

		<div class="col-4 h-100"
			style="background-color: white; height: auto; min-height: 340px">
			<h3 class="col-12 pt-3">机构介绍</h3>
			<p id="organizationIntroduction" class="m-3"
				style="word-wrap: break-word; word-break: break-all; overflow: hidden;"></p>
			<h3 class="col-12 pt-3">教师介绍</h3>
			<p id="teacherIntroduction" class="m-3"
				style="word-wrap: break-word; word-break: break-all; overflow: hidden;"></p>
			<h3 class="col-12 pt-3">课程介绍</h3>
			<p id="courseIntroduction" class="m-3"
				style="word-wrap: break-word; word-break: break-all; overflow: hidden;"></p>
		</div>
	</div>
</body>

<script src="../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../common/common.js"></script>
<script
	src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script
	src="../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script
	src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery.idTabs.min.js"></script>
<script src="courseContent.js"></script>
<script src="../../login.js"></script>
<script src="../../resources/plugins/validator.js"></script>
</html>