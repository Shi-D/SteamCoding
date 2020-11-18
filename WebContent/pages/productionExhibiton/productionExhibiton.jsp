<%@ page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>作品展示</title>
<link rel="stylesheet"
	href="../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="productionExhibition.css">
<link rel="stylesheet" href="../utitles/navigation.css">
</head>
<body>
	<%@include file="../../common/navigationBar.jsp"%>
	<script>
		document.getElementById("bar").getElementsByTagName("li")[3].classList.add("active");
	</script>
	<div class='modal' id='loadInfo'>
		<div class="spinner">
			<div class="rect1"></div>
			<div class="rect2"></div>
			<div class="rect3"></div>
			<div class="rect4"></div>
			<div class="rect5"></div>
		</div>
	</div>
	<div class="container" style="margin-top: 100px">
		<div id="searchContain">
			<h2>作品分类</h2>
		    <div id="searchContext">
		        <div id="workName" class="searchinput">
					<label for="searchBarWorkName">作品名称：</label>
					<input id="searchBarWorkName" type="text" placeholder="" data-value="className">
				</div>
				<div id="authorName" class="searchinput">
					<label for="searchBarAuthorName">作者姓名：</label>
					<input id="searchBarAuthorName" type="text" placeholder="" data-value="className">
				</div>
			</div>
			<div id="searchButton">
				<button class="btn btn-primary btn-sm" id="query">
					<span>查询</span>
				</button>
				<button class="btn btn-outline-secondary btn-sm" id="reset">
					<span>重置</span>
				</button>
			</div>
	    </div>
		<hr>
		<div id="productionTabs">
			<div style="margin-bottom: 2%">
				<a>&nbsp</a> <a href='#scratchProgrammingNew'
					id="selectScratchProgrammingNew" class='selected'>最近发布</a> <a
					href='#scratchProgrammingHot' id='selectScratchProgrammingHot'
					class="">最热评论</a> <a href='#scratchProgrammingGood'
					id='selectScratchProgrammingGood' class="">点赞优先</a>
			</div>

			<div id="scratchProgrammingContetnt">
				<div id="scratchProgrammingAll"></div>
				
				<div id="scratchProgrammingNew"></div>

				<div id="scratchProgrammingHot"></div>

				<div id="scratchProgrammingGood"></div>
			</div>
		</div>
	</div>
</body>

<script src="../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../common/common.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script	src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/jquery.idTabs.min.js"></script>
<script src="productionExhibition.js"></script>
<script src="../../login.js"></script>
<script src="../../resources/plugins/validator.js"></script>
</html>