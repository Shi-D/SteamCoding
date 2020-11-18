<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="zh-CN">
<head>
<%
ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_TEACHER); 
%>
<%@include file = "../../../common/commonRedirect.jsp" %>
	<meta charset="UTF-8">
    <title>作业管理</title>
	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css"/>
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="../../../common/managementPage.css"/>
    <link rel="stylesheet" href="../../../resources/plugins/datePicker/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="../css/teacherHomeworkManagement.css">
    <link rel="stylesheet" href="../css/checkStudentHomework.css">
</head>
<body>
<div class="main">
    <script type="text/javascript">
	var index = 4;
	</script>
	<%@include file = "../../../common/commonLeftBanner.jsp" %>
	<div class="layout">
		<div class="layout-header">
			<%@include file = "../../../common/header.jsp" %>
		</div>
        <div class="layout-content">
            <div class="layout-content-main">
                <div class="table-part">
                 <div class="table-operate" id="operate">
	                    <a href="teacherHomeworkManagment.jsp">
	                      <button class="btn btn-primary" id="back">
	                            <i class="fa fa-reply"></i>
	                            <span>作业列表</span>
	                       </button>
	                    </a>
                        <button class="btn btn-primary" id="evaluateHomework" disabled data-toggle="modal" data-target="#evaluateHomework-modal" disabled>
                            <i class="fa fa-file-text-o"></i>
                            <span>作业批改</span>
                        </button>
                        <button class="btn btn-primary" id="checkEvaluation" disabled data-toggle="modal" data-target="#evaluateHomework-modal" disabled>
                            <i class="fa fa-file-text-o"></i>
                            <span>修改批改</span>
                        </button>
                    </div>
                    
                    <div class="modal" id="evaluateHomework-modal" >
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<p>作业批改</p>
								</div>  
								<div class="modal-body">
									<div>
										<button class="btn btn-primary" id="downloadWork" >下载作业</button>
										<button class="btn btn-primary" id="viewWork" >查看作品</button>
									</div>
									<div>
					                    <label><span class="starColor">*</span>作业评价：</label>
					                    <textarea name="homeworkContent" type="text" class="form-control" id="homeworkEvaluation"></textarea>
					                	<div style="color:gray;font-size:12px;float:right"><span id="count">0</span>/500</div>
										<div class="invalid-feedback">
	              							字数不能为空或超过限制
	            						</div>
					                </div>
					                <br>
									<div>
					                    <label><span class="starColor">*</span>作业分数：</label>
					                    <input name="cantbeNull" class="form-control" type="number" oninput="if(value>100)value=100;if(value<0)value=0" id="homeworkScore"></textarea>
					                	<div class="invalid-feedback">
	              							作业分数不能为空
	            						</div>
					                </div>
								</div>
								<div class="modal-footer">
									<button class="btn btn-outline-secondary" id="dismissEvaluateHomeworkModal" data-dismiss="modal">取消</button>
									<button class="btn btn-primary" id="sureToEvaluateHomework">确定</button>
								</div>
							</div>
						</div>
					</div>
                    <div class="table-fliter" id="table-fliter">
                            <div>
                                <div class="dropdown">
                                <button type="button" id="choseClassButton" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
                                    <span>所有班级</span>
                                </button>
                                <div class="dropdown-menu" id="drapdown-menu">
                                    
                                </div>
                                </div>
                            <div>
                                <label for="search-student">学生姓名：</label>
                                <input id="search-student" type="text" placeholder="">
                            </div>
                            <form action="" class="form-horizontal">
                                <div class="control-group">
                                    <label class="control-label" for="from_date">提交时间：</label>
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
                    <div class="table-context">
                        <div class="tablet-context-header">
                            <table>
                                <tbody id="table-header">
	                                <tr>
 	                                    <th><input type='checkbox' id='CheckAll' class='selectAll'></th>
 	                                    <th class="gutter"></th>
	                                    <th>学生姓名</th>
	                                    <th>所在班级</th>
	                                    <th>提交时间</th>
	                                    <th>作业得分</th>
	                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-context-body" id="table-context-body">
                            <table>
                                <tbody id="table-body">

                                </tbody>
                            </table>
                        </div>
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
<script src="../../../common/headerUserInfo.js"></script>
<script src="../../../common/tableManager.js"></script>
<script src="../../../common/footer.js"></script>
<script src="../../../common/checkTime.js"></script>
<!--导入datePicker的js-->
<script src="../../../resources/plugins/datePicker/bootstrap-datetimepicker.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/checkStudentHomework.js"></script>
</body>
</html>


