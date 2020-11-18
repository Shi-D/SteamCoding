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
    <link rel="stylesheet" href="../../../resources/plugins/datePicker/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="../../../common/managementPage.css"/>
    <link rel="stylesheet" href="../../teacherPage/css/workManagement.css">
    <link rel="stylesheet" href="../css/studentHomeworkManagement.css">
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
                    <div class="table-fliter">
                        <div>
                            <div>
                                <label for="searchBarWorkName">作业名称：</label>
                                <input id="searchBarWorkName" type="text" placeholder="" data-value="className">
                            </div>

                            <form action="" class="form-horizontal">
                                <div class="control-group">
                                    <label class="control-label" for="from_date">发布时间：</label>
                                    <input size="16" type="text" id="from_date" class="form_date date-input">
                                    <label class="control-label" for="to_date">至</label>
                                    <input size="16" type="text" id="to_date" class="form_date date-input">
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


                    <div class="table-context" id="table-context" style="min-height:72vh;">
                       
                    </div>
                    <%@include file = "../../../common/commonTableFooter.jsp" %>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../common/common.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../../resources/plugins/datePicker/bootstrap-datetimepicker.js"></script>
<script src="../../../common/tableManager.js"></script>
<script src="../../../common/checkTime.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
<script src="../js/studentHomeworkManagement.js"></script>
</body>
</html>


