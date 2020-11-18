<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_ADMIN);role.add(ROLE_TEACHER);role.add(ROLE_STUDENT);role.add(ROLE_SUPERADMIN); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>courseContent</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=0" />
<link rel="stylesheet" href="../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
</head>
<body style="background-color: whitesmoke">
	<%@include file="../../../common/navigationBar.jsp"%>
	<script>
		document.getElementById("bar").getElementsByTagName("li")[2].classList
				.add("active");
	</script>
	<div class="row"
		style="margin-top: 5%; margin-left: auto; margin-right: auto; width: 80%; background-color: white">
		<div class="col-12" id="chapterTitle"
			style="align-items: center; justify-content: center; padding: 4%;">
		</div>
		<div class="col-12" id="chapterContent"
			style="align-items: center; justify-content: center; padding: 4%;">
		</div>
	</div>
</body>

<script src="../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../common/common.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery.idTabs.min.js"></script>
<script src="chapterContent.js"></script>
<script src="../../login.js"></script>
<script src="../../resources/plugins/validator.js"></script>
</html>