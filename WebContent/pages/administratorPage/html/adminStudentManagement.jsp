<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_ADMIN); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
    <meta charset="UTF-8">
    <title>学生管理</title>
   	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
    <link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../common/managementPage.css"/>
</head>
<body>
<div class="main">
	<script type="text/javascript">
	var index = 1;
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
                        <button class="disabled btn btn-primary" id="delete-student" data-toggle="modal" data-target="#delete-stu-modal" disabled>
                            <i class="fa fa-trash-o"></i>
                            <span>删除学生</span>
                        </button>
                        <button class="disabled btn btn-primary" id="reset-password" data-toggle="modal" data-target="#reset-password-modal" disabled>
                            <i class="fa fa-reply-all"></i>
                            <span>重置密码</span>
                        </button>
                        <button class="btn btn-primary" id="export-stu-list">
                            <i class="fa fa-file-excel-o"></i>
                            <span>导出学生名单</span>
                        </button>
                        <div class="btn btn-primary" >
                            <i class="fa fa-file-excel-o"></i>
                            <label for="excel-file" style="margin: unset">导入学生名单</label>
                            <input type="file" id="excel-file" name="files" onchange="importExp()"  style="position:absolute;clip:rect(0 0 0 0);">
                        </div>
                        <button class="btn btn-primary" id="download-import-template">
                            <i class="fa fa-cloud-download"></i>
                            <span>下载导入模板</span>
                        </button>
                        <button class="btn btn-primary" id="import-singal-student" data-toggle="modal" data-target="#import-singal-student-modal">
                            <i class="fa fa-plus-circle"></i>
                            <span>添加单个学生</span>
                        </button>

                        <div class="modal" id="reset-password-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>提示！</p>
                                    </div>
                                    <div class="modal-body">
                                        请确认重置密码操作
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToReset" data-dismiss="modal">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="modal" id="delete-stu-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>提示！</p>
                                    </div>
                                    <div class="modal-body">
                                        请确认删除学生操作
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToDelete" data-dismiss="modal">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal" id="import-singal-student-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>添加学生</p>
                                    </div>
                                    <div class="modal-body">
                                        <div>
                                            <label for="student-name">学生姓名：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="student-name">
                                            <div class="invalid-feedback">
              									名字不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="student-code">学生账号：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="student-code" oninput="value=value.replace(/[^\d]/g,'')">
                                            <div class="invalid-feedback">
              									账号不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label>学生性别：</label>
                                            <label><input name="student-gender" type="radio" checked="checked" value="男"/>男</label>
                                            <label><input name="student-gender" type="radio" value="女" />女</label>
                                        </div>
                                       
                                        
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" id="cancelToAddSingleStudent" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToAddSingleStudent" >确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-fliter" id="table-fliter">
                        <div>
                            <div class="dropdown">
                                <button type="button" id="choseClassButton" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
                                    <span>所有学生</span>
                                </button>
                                <div class="dropdown-menu" id="drapdown-menu">
                                    <a class="dropdown-item">所有学生</a>
                                    <a class="dropdown-item">未分配</a>
                                </div>
                            </div>
                            <div>
                                <label for="titleSearch">学生姓名：</label>
                                <input id="titleSearch" type="text" placeholder="">
                            </div>
                            <div>
                                <label for="accountSearch">学生账号：</label>
                                <input id="accountSearch" type="text" placeholder="">
                            </div>
                        </div>
                        <div>
                            <button class="btn btn-primary" id="query">
                                <!-- <i class="fa fa-search"></i> -->
                                <span>查询</span>
                            </button>
                            <button class="btn btn-outline-secondary" id="reset">
                                <!-- <i class="fa fa-reply-all"></i> -->
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
										<th></th>
										<th>姓名</th>
										<th>性别</th>
										<th>账号</th>
										<th>创建时间</th>
										<th>班级</th>
										<th class='gutter'></th>
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
<script src="../../../common/tableManager.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/adminStudentManagement.js"></script>
</body>
</html>