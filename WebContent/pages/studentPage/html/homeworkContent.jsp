<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<!doctype html>
<html lang="zh-CN">
<head>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_STUDENT); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
    <meta charset="UTF-8">
    <title>作业管理</title>
    <link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
    <link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../common/managementPage.css"/>
	<link rel="stylesheet" href="../css/homeworkContent.css">
    
</head>
<body>
<div class="main">
    	<script type="text/javascript">
	var index = 2;
	</script>
	<%@include file = "../../../common/commonLeftBanner.jsp" %>
    <div class="layout">
        <div class="layout-header">
			<%@include file = "../../../common/header.jsp" %>
		</div>
        <div class="layout-content">
            <div class="layout-content-main">
                <div class="table-part">
                	<a class="btn btn-link"  href="studentHomeWorkmanagement.jsp"><i class="fa fa-reply"></i>作业列表</a>
                    <div class="homeworkRequest pt-4" id="homeworkContent">
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
<script src="../../../common/tableManager.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
<script src="../js/homeworkContent.js"></script>
</body>
</html>


