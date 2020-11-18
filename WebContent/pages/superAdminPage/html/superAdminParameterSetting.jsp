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
    <title>参数设置</title>
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
						<button class="btn btn-primary" id="add-parameter" data-toggle="modal" data-target="#add-parameter-modal">
							<i class="fa fa-plus-circle"></i>
							<span>添加参数</span>
						</button>
						<button class="disabled btn btn-primary" id="delete-parameter" data-toggle="modal" data-target="#delete-parameter-modal" >
							<i class="fa fa-minus-circle"></i>
							<span>删除参数</span>
						</button>
						<button class="disabled btn btn-primary" id="modify-parameter" data-toggle="modal" data-target="#modify-parameter-modal" >
							<i class="fa fa-reply-all"></i>
							<span>修改参数</span>
						</button>
						
						<div class="modal" id="add-parameter-modal" data-backdrop="static">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<p id="addParameter">添加参数</p>
									</div>
									<div class="modal-body">
										<div>
                                            <span style="color:red">*</span><label for="parameter-name">参数名称：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-name">
                                            <div class="invalid-feedback">
              									参数名称不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                        	<span style="color:red">*</span><label for="parameter-value">参数值：</label>
											<input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-value">
                                            <div class="invalid-feedback">
              									参数值不能为空
            								</div>
										</div>
                                        <br>
                                        <div>
                                            <span style="color:red">*</span><label for="parameter-comment">参数说明：</label>
											<input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-comment">
                                            <div class="invalid-feedback">
              									参数说明不能为空
            								</div>
                                        </div>
                                        <br>
                                     	<div>
                                            <span style="color:red">*</span><label for="parameter-sequence">参数排序：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-sequence" oninput="value=value.replace(/[^\d]/g,'')">
                                            <div class="invalid-feedback">
              									参数排序不能为空且为数字
            								</div>
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-serial">参数序号：</label>
                                            <input type="text" style="width:100%" id="parameter-serial" oninput="value=value.replace(/[^\d]/g,'')">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-typeCode">参数类型编码：</label>
                                            <input type="text" style="width:100%" id="parameter-typeCode" oninput="value=value.replace(/[^\d]/g,'')">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-typeName">参数类型名称：</label>
                                            <input type="text" style="width:100%" id="parameter-typeName">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-url">参数URL：</label>
                                            <input type="text" style="width:100%" id="parameter-url">
                                        </div>
                                        
									</div>
									<div class="modal-footer">
										<button class="btn btn-outline-secondary" data-dismiss="modal" id="addParameterCancel">取消</button>
										<button type="button" class="btn btn-primary" id="addParameterCommit">添加</button>
									</div>
								</div>
							</div>
						</div>
						<div class="modal" id="modify-parameter-modal" data-backdrop="static">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<p id="modifyParameter">修改参数</p>
									</div>
									<div class="modal-body">
										<div>
                                            <span style="color:red">*</span><label for="parameter-name-modify">参数名称：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-name-modify">
                                            <div class="invalid-feedback">
              									参数名称不能为空
            								</div>
                                        </div>
                                        <br>
                                        <div>
                                        	<span style="color:red">*</span><label for="parameter-value-modify">参数值：</label>
											<input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-value-modify">
                                            <div class="invalid-feedback">
              									参数值不能为空
            								</div>
										</div>
                                        <br>
                                        <div>
                                            <span style="color:red">*</span><label for="parameter-comment-modify">参数说明：</label>
											<input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-comment-modify">
                                            <div class="invalid-feedback">
              									参数说明不能为空
            								</div>
                                        </div>
                                        <br>
                                     	<div>
                                            <span style="color:red">*</span><label for="parameter-sequence-modify">参数排序：</label>
                                            <input type="text" class="form-control" name="cantbeNull" style="width:100%" id="parameter-sequence-modify" oninput="value=value.replace(/[^\d]/g,'')">
                                            <div class="invalid-feedback">
              									参数排序不能为空
            								</div>
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-serial-modify">参数序号：</label>
                                            <input type="text" style="width:100%" id="parameter-serial-modify" oninput="value=value.replace(/[^\d]/g,'')">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-typeCode-modify">参数类型编码：</label>
                                            <input type="text" style="width:100%" id="parameter-typeCode-modify" oninput="value=value.replace(/[^\d]/g,'')">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-typeName-modify">参数类型名称：</label>
                                            <input type="text" style="width:100%" id="parameter-typeName-modify">
                                        </div>
                                        <br>
                                     	<div>
                                            <label for="parameter-url-modify">参数URL：</label>
                                            <input type="text" style="width:100%" id="parameter-url-modify">
                                        </div>
                                        
									</div>
									<div class="modal-footer">
										<button class="btn btn-outline-secondary" data-dismiss="modal" id="modifyParameterCancel">取消</button>
										<button type="button" class="btn btn-primary" id="modifyParameterCommit">修改</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="modal" id="delete-parameter-modal" data-backdrop="static">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <p>提示！</p>
                                    </div>
                                    <div class="modal-body">
                                        	<p>请确认删除参数<span id="deletePameter-ParameterNames" style="color:red"></span>操作</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-outline-secondary" id="cancelToDelete" data-dismiss="modal">取消</button>
                                        <button class="btn btn-primary" id="sureToDelete" data-dismiss="modal">确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
				</div>
				<div class="table-context">
					<div class="tablet-context-header">
						<table>
							<tbody id="table-header">
								<tr>
									<th><input type='checkbox' id='CheckAll' class='selectAll'></th>
									<th></th>
									<th>参数名</th>
									<th>参数值</th>
									<th>参数说明</th>
									<th>参数排序</th>
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
<script src="../js/superAdminParameterSetting.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
</body>
</html>