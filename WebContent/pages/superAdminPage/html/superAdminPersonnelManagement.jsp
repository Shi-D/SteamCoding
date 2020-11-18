<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_SUPERADMIN); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
    <title>人员管理</title>
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
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link active setPointer" id="admin" style="color: black">
							<i class="fa fa-user"></i>
							<label class="setPointer">管理员</label>
						</a>
					</li>
					<li class="nav-item">
						<a class="nav-link setPointer" id="teacher" style="color: black">
							<i class="fa fa-group" ></i>
							<label class="setPointer">教师</label>
						</a>
					</li>
					<li class="nav-item">
						<a class="nav-link setPointer" id="student" style="color: black">
							<i class="fa fa-group" ></i>
							<label class="setPointer">学生</label>
						</a>
					</li>
				</ul>
				
				<div class="table-part">
					<div class="table-operate" id="operate">
						<button class="btn btn-primary" id="add-admin" data-toggle="modal" data-target="#add-admin-modal">
							<i class="fa fa-plus-circle"></i>
							<span>新增管理员</span>
						</button>
						<button class="disabled btn btn-primary" id="modify-admin" data-toggle="modal" data-target="#modify-admin-modal">
							<i class="fa fa-edit"></i>
							<span>修改管理员</span>
						</button>
						<button class="disabled btn btn-primary" id="delete-admin" data-toggle="modal" data-target="#delete-admin-modal" disabled>
							<i class="fa fa-minus-circle"></i>
							<span>删除管理员</span>
						</button>
						<button class="disabled btn btn-primary" id="resetPassword" data-toggle="modal" data-target="#reset-password-modal" disabled>
							<i class="fa fa-reply-all"></i>
							<span>重置密码</span>
						</button>
						
						<div class="modal" id="add-admin-modal" data-backdrop="static">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<p id="addAdmin">新增管理员</p>
									</div>
									<div class="modal-body">
										<div>
                                            <label for="admin-name">管理员姓名：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="admin-name">
                                            <div class="invalid-feedback">
              									名字不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="admin-code">管理员账号：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="admin-code">
                                            <div class="invalid-feedback">
              									账号不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="admin-gender">管理员性别：</label>
                                            <label><input name="admin-gender" type="radio" checked="checked" value="男"/>男</label>
                                            <label><input name="admin-gender" type="radio" value="女" />女</label>
                                        </div>
                                        <br>
                                     
                                        <div class="dropdown" id="dropdown">
                                        	<label for="choseOrganizationButtonForAdmin">所属机构：</label>
											<button type="button" id="choseOrganizationButtonForAdmin" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
											<span id="dropdownTitle"></span>
											</button>
											<div class="dropdown-menu" id="drapdown-menu-addAdmin">
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button class="btn btn-outline-secondary" data-dismiss="modal" id="addAdminCancel">取消</button>
										<button type="button" class="btn btn-primary" id="addAdminCommit">添加</button>
									</div>
								</div>
							</div>
						</div>
						
						
						<div class="modal" id="modify-admin-modal" data-backdrop="static">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<p id="modifyAdmin">修改管理员</p>
									</div>
									<div class="modal-body">
										<div>
                                            <label for="admin-name-modify">管理员姓名：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="admin-name-modify">
                                            <div class="invalid-feedback">
              									名字不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                            <label for="admin-code-modify">管理员账号：</label>
                                            <input type="text" readonly="readonly" style="width:100%" id="admin-code-modify">
                                        </div>
                                        <br>
                                        <div>
                                            <label for="admin-gender-modify">管理员性别：</label>
                                            <label><input name="admin-gender-modify" type="radio" checked="checked" value="男"/>男</label>
                                            <label><input name="admin-gender-modify" type="radio" value="女" />女</label>
                                        </div>
                                        <br>
                                     
                                        <div class="dropdown" id="dropdown">
                                        	<label for="choseOrganizationButtonForModify">所属机构：</label>
											<button type="button" id="choseOrganizationButtonForModify" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
											<span id="dropdownTitleForModify"></span>
											</button>
											<div class="dropdown-menu" id="drapdown-menu-modifyAdmin">
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button class="btn btn-outline-secondary" data-dismiss="modal" id="modifyAdminCancel">取消</button>
										<button type="button" class="btn btn-primary" id="modifyAdminCommit">修改</button>
									</div>
								</div>
							</div>
						</div>
						
						
						<div class="modal" id="delete-admin-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>提示！</p>
                                    </div>
                                    <div class="modal-body">
                                        	<p>请确认删除管理员<span id="deleteAdmin-AdminNames" style="color:red"></span>操作</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" id="cancelToDelete" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToDelete" data-dismiss="modal">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
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
						
				</div>
				
				<div class="table-fliter" id="table-fliter">
					<div class="dropdown" id="dropdown">
						<button type="button" id="choseOrganizationButton" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
						<span>全部机构</span>
						</button>
						<div class="dropdown-menu" id="drapdown-menu">
							<a class="dropdown-item">全部机构</a>
						</div>
						<div>
							<label for="titleSearch">输入名字：</label>
							<input id="titleSearch" type="text" placeholder="" data-value="userName">
						</div>
						<div>
							<label for="titleSearchCode">输入账号：</label>
							<input id="titleSearchCode" type="text" placeholder="" data-value="userName">
						</div>
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
									<th></th>
									<th>姓名</th>
									<th>性别</th>
									<th>账号</th>
									<th>创建时间</th>
									<th>机构</th>
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
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/superAdminPersonnelManagement.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
</body>
</html>