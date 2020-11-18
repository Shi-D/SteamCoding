<%@ page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>课程</title>
<link rel="stylesheet" href="../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="course.css">
<link rel="stylesheet" href="../utitles/navigation.css">
</head>
<body>
	<%@include file = "../../common/navigationBar.jsp" %>
	<script>
		document.getElementById("bar").getElementsByTagName("li")[2].classList.add("active");
	</script>
	<div class="container" style="margin-top: 100px">
		<span id="a"></span>
		<div class="row" style="margin-top: 50px">
			<h2 style="color: #00ccff; margin-bottom: 30px">
				<span> <i>-</i> <span>精品课程</span>
				</span>
			</h2>
			<div class="container row" id="highQualityCourses"></div>
		</div>


<!-- 		<div class="row" style="margin-top: 50px">
			<h2 style="color: #00ccff; margin-bottom: 30px">
				<span> <i>-</i> <span>入门课程</span>
				</span>
			</h2>
			<div class="container" id="introductoryCourse"></div>
		</div> -->

	</div>
</body>
<script src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery-slim.min.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../common/common.js"></script>
<script src="course.js"></script>
<script src="../../login.js"></script>
<script src="../../resources/plugins/validator.js"></script>	
</html>