<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_SUPERADMIN); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
<title>机构管理</title>
<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css">
<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../../../common/managementPage.css"/>
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
					<div class="table-part">
						<div class="table-operate" id="operate">
							<button class="btn btn-primary" id="add-organization"
								data-toggle="modal" data-target="#add-orginization-modal">
								<i class="fa fa-plus-circle"></i> <span>添加机构</span>
							</button>
							<button class="btn btn-primary" id="delete-organization"
								data-toggle="modal" data-target="#delete-organization-modal"
								disabled>
								<i class="fa fa-minus-circle"></i> <span>删除机构</span>
							</button>
							<button class="btn btn-primary" id="modify-organization"
								data-toggle="modal" data-target="#modify-orginization-modal"
								disabled>
								<i class="fa fa-edit"></i> <span>修改机构</span>
							</button>

							<div class="modal" id="add-orginization-modal"
								data-backdrop="static">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<p>新增机构</p>
										</div>
										<div class="modal-body">
											<div class="form-group">
												<label for="addOrganizationName">请输入机构名称</label>
												<input type="text" name="cantbeNull" class="form-control" placeholder="机构名称" id="addOrganizationName"/>
												<div class="invalid-feedback">
													机构名不能为空
												</div>
											</div>
											<div >
												<label for="addOrganizationSname">请输入机构简称</label>
												<input type="text" name="cantbeNull" class="form-control" placeholder="机构简称" id="addOrganizationSname"/>
												<div class="invalid-feedback">
													机构简称不能为空
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button class="btn btn-outline-secondary" id="addOrganizationCancel"
												data-dismiss="modal">取消</button>
											<button type="button" class="btn btn-primary"
												id="addOrganizationCommit">添加</button>
										</div>
									</div>
								</div>
							</div>

							<div class="modal" id="delete-organization-modal"
								data-backdrop="static">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<p>删除机构</p>
										</div>
										<div class="modal-body">
											<p>确认删除<span id="deleteOrganization-OrganizationNames" style="color:red"></span>?</p>
										</div>
										<div class="modal-footer">
											<button class="btn btn-outline-secondary" id="deleteOrganizationCancel"
												data-dismiss="modal">取消</button>
											<button type="button" class="btn btn-primary"
												id="deleteOrganizationCommit">删除</button>
										</div>
									</div>
								</div>
							</div>
							
							<div class="modal" id="modify-orginization-modal"
								data-backdrop="static">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<p>修改机构</p>
										</div>
										<div class="modal-body">
											<div class="form-group">
												<label for="modifyOrganizationName">请输入机构名称</label>
												<input type="text" name="cantbeNull" class="form-control" placeholder="机构名称" id="modifyOrganizationName"/>
												<div class="invalid-feedback">
													机构名不能为空
												</div>
											</div>
											<div >
												<label for="modifyOrganizationSname">请输入机构简称</label>
												<input type="text"  name="cantbeNull" class="form-control" placeholder="机构简称" id="modifyOrganizationSname"/>
												<div class="invalid-feedback">
													机构简称不能为空
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button class="btn btn-outline-secondary" id="modifyOrganizationCancel"
												data-dismiss="modal">取消</button>
											<button type="button" class="btn btn-primary"
												id="modifyOrganizationCommit">修改</button>
										</div>
									</div>
								</div>
							</div>
							
						</div>

						<div class="table-fliter" id="table-fliter">
							<div>
								<div>
									<label for="titleSearch">输入机构名查找：</label> <input
										id="titleSearch" type="text" placeholder=""
										data-value="className">
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
											<th><input type='checkbox' id='CheckAll'
												class='selectAll'></th>
											<th></th>
											<th>机构名</th>
											<th>机构简称</th>
											<th>机构管理员</th>
											<th>创建时间</th>
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
						<%@include file="../../../common/commonTableFooter.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
	<script src="../../../common/common.js"></script>
	<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
	<script	src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
	<script src="../../../common/tableManager.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
	<script src="../js/superAdminOrganizationManagement.js"></script>
	<script src="../../../common/headerUserInfo.js"></script>
</body>
</html>


