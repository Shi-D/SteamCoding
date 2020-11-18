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
    <title>我的作品</title>
    <link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
    <link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../resources/plugins/datePicker/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="../../../common/managementPage.css"/>
    <link rel="stylesheet" href="../../teacherPage/css/workManagement.css">
    <link rel="stylesheet" href="../css/studentWorkManagement.css">
</head>
<body>
	<div class="main">
		<script type="text/javascript">
			var index = 0;
		</script>
		<%@include file="../../../common/commonLeftBanner.jsp"%>
		<div class="layout">
			<div class="layout-header">
				<%@include file="../../../common/header.jsp"%>
			</div>
			<div class="layout-content">
				<div class="layout-content-main">
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link active setPointer"
							id="myWorks" style="color: black"> <i class="fa fa-user"></i>
								<label class="setPointer">我的作品</label>
						</a></li>
						<li class="nav-item"><a class="nav-link setPointer"
							id="otherWorks" style="color: black"> <i class="fa fa-group"></i>
								<label class="setPointer">其他作品</label>
						</a></li>
					</ul>
					<div class="table-part">
						<div class="table-operate" id="operate">
							<button class="btn btn-primary" id="add-work" data-toggle="modal"
								data-target="#add-work-modal">
								<i class="fa fa-plus-circle"></i> <span>开始创作</span>
							</button>
							<button class="btn btn-primary" id="publish-work"
								data-toggle="modal" data-target="#add-work-modal" disabled>
								<i class="fa fa-handshake-o"></i> <span>已发布的作品</span>
							</button>

							<button class="btn btn-primary" id="save-work"
								data-toggle="modal" data-target="#add-work-modal">
								<i class="fa fa-file-text"></i> <span>未发布的作品</span>
							</button>
						</div>
						<div class="table-fliter">
							<div>
								<div class="dropdown" id="dropdown">
									<button type="button" id="choseClassButton"
										class="btn btn-outline-secondary dropdown-toggle"
										data-toggle="dropdown">
										<span>全部班级</span>
									</button>
									<div class="dropdown-menu" id="drapdown-menu">
										<a class="dropdown-item">全部班级</a>
									</div>
								</div>
								<div>
									<label for="searchBarWorkName">作品名称：</label> <input
										id="searchBarWorkName" type="text" placeholder=""
										data-value="className">
								</div>
								<div id="authorName">
									<label for="searchBarAuthorName">作者姓名：</label> <input
										id="searchBarAuthorName" type="text" placeholder=""
										data-value="className">
								</div>
								<form action="" class="form-horizontal">
									<div class="control-group">
										<label class="control-label" for="from_date">发布时间：</label> <input
											size="16" type="text" id="from_date"
											class="form_date date-input"> <label
											class="control-label" for="to_date">至</label> <input
											size="16" type="text" id="to_date"
											class="form_date date-input">
									</div>
								</form>
							</div>
							<div>
								<button class="btn btn-primary" id="query">
									<span>查询</span>
								</button>
								<button class="btn btn-outline-secondary" id="reset">
									<span>重置</span>
								</button>
							</div>
						</div>
						<div class="table-context" id="work-context"
							style="min-height: 65vh"></div>
						<%@include file="../../../common/commonTableFooter.jsp"%>
						<%@include file="../../../common/commonWorkModal.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../../common/common.js"></script>
<script src="../../../common/checkTime.js"></script>
<script src="../../../common/footer.js"></script>
<!--导入datePicker的js-->
<script src="../../../resources/plugins/datePicker/bootstrap-datetimepicker.js"></script>
<script src="../../../common/commonWorkManagemernt.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/studentWorkManagement.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
</html>