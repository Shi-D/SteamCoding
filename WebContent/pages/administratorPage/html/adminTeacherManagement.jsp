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
    <title>教师管理</title>
   	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
    <link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../common/managementPage.css"/>
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
                    <div class="table-operate" id="operate">
                        <button class="disabled btn btn-primary" id="delete-teacher" data-toggle="modal" data-target="#delete-teacher-modal" disabled>
                            <i class="fa fa-trash-o"></i>
                            <span>删除教师</span>
                        </button>
                        <button class="disabled btn btn-primary" id="reset-password" data-toggle="modal" data-target="#reset-password-modal" disabled>
                            <i class="fa fa-reply-all"></i>
                            <span>重置密码</span>
                        </button>
                        <button class="btn btn-primary" id="export-teacher-list">
                            <i class="fa fa-file-excel-o"></i>
                            <span>导出教师名单</span>
                        </button>
                        <div class="btn btn-primary">
                            <i class="fa fa-file-excel-o"></i>
                            <label for="excel-file" style="margin: unset">导入教师名单</label>
                            <input type="file" id="excel-file" name="files" onchange="importExp()"  style="position:absolute;clip:rect(0 0 0 0);">
                        </div>
                        <button class="btn btn-primary" id="download-import-template">
                            <i class="fa fa-cloud-download"></i>
                            <span>下载导入模板</span>
                        </button>
                        <button class="btn btn-primary" id="import-singal-teacher" data-toggle="modal" data-target="#import-singal-teacher-modal">
                            <i class="fa fa-plus-circle"></i>
                            <span>添加单个教师</span>
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

                        <div class="modal" id="delete-teacher-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>提示！</p>
                                    </div>
                                    <div class="modal-body">
                                        请确认删除教师操作
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToDelete" data-dismiss="modal">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal" id="import-singal-teacher-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>添加教师</p>
                                    </div>
                                    <div class="modal-body">
                                        <div>
                                            <label for="teacher-name">教师姓名：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="teacher-name">
                                            <div class="invalid-feedback">
              									名字不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="teacher-name">教师账号：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="teacher-code" oninput="value=value.replace(/[^\d]/g,'')">
                                            <div class="invalid-feedback">
              									账号不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="teacher-name">教师性别：</label>
                                            <label><input name="teacher-gender" type="radio" checked="checked" value="男"/>男</label>
                                            <label><input name="teacher-gender" type="radio" value="女" />女</label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" id="cancelToAddSingleteacher" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToAddSingleteacher">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-fliter" id="table-fliter">
                        <div>
                            <div>
                                <label for="titleSearch">教师姓名：</label>
                                <input id="titleSearch" type="text" placeholder="">
                            </div>
                            <div>
								<label for="accountSearch">教师账号：</label>
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
<script src="../../../common/Server.js"></script>
<script src="../../../common/tableManager.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/adminTeacherManagement.js"></script>
</body>
</html>