<%@ page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>作品内容</title>
<link rel="stylesheet"
	href="../../../resources/plugins/font-awesome.css">
<link rel="stylesheet"
	href="../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/ProductionContent.css">
<link rel="stylesheet" href="../utitles/navigation.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/comment.css">
</head>
<body>
	<nav class="container-fluid  navbar-expand " id="itemText">
		<%@include file="../../../common/navigationBar.jsp"%>
		<script>
			document.getElementById("bar").getElementsByTagName("li")[3].classList.add("active");
		</script>
	</nav>
	<div class="container" id="containerStyle">
		<div class="row">
			<div class="col-8" id="workDisplay">
				<iframe name="frame1" src="../../build/workContent.html"></iframe>
				<i class="fa fa-expand" onclick='goWorkContentExpand()'></i>
			</div>

			<div class="col-4" style="padding-left: unset">
				<div id="user">
					<div class="row">
						<div id="userImage" class="col-4">
							<img id="userPhotoName" src="../../resources/img/userImg.jpg"
								height="60" width="60" class="rounded-circle" />
						</div>
						<div id="userInfo" class="col-8"></div>
					</div>
					<hr>

					<div id="likeRow">
						<label id="goodLabel">点赞数&nbsp&nbsp<span id="goodNum">0</span></label>
						<button type="button" class="btn btn-success" id="likeBtn">
							<img src="../../resources/ico/thumbsUpWhite.png" /><span
								id="btn-content">点赞</span>
						</button>
					</div>


				</div>


				<div id="production">
					<div>
						<span>作品介绍</span>
					</div>
					<hr>

					<div id="tabsContetnt" style="margin-top: 10%">
						<div id="productionIntroduction"></div>
					</div>
				</div>
			</div>
		</div>

		<div id="discussion">
			<span id="commentNum">评论：</span> <span id="discussionNum"></span>
			<!--<div class="form-group" id="inputComment">
                <textarea id="commentTextArea" class="form-control" rows="3" placeholder="请文明评论..."></textarea>
                <button id="submitComment" type="button" class="btn btn-success">提交</button>
                <label>最多输入200字</label>
            </div>


            <div id="commentInfo">
            </div>-->

			<div class="commentAll">
				<!--评论区域 begin-->
				<div class="reviewArea clearfix">
					<textarea class="content comment-input" placeholder="请文明评论&hellip;"
						onkeyup="keyUP(this)"></textarea>
					<a class="plBtn" id="commentBtn">评论</a>
				</div>
				<!--评论区域 end-->
				<!--回复区域 begin-->
				<div class="comment-show"></div>
				<!--回复区域 end-->
			</div>

		</div>
	</div>
</body>


<script src="../../resources/plugins/jquery-3.3.1.js"></script>
<script src="js/jqueryFlexText.js"></script>

<script src="../../common/common.js"></script>
<script
	src="../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script
	src="../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="js/ProductionContent.js"></script>
<script src="../../login.js"></script>
<script src="../../resources/plugins/validator.js"></script>
<script src="js/comment.js"></script>
</html>