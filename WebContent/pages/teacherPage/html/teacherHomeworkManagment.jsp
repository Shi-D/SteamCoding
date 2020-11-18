<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="zh-CN">
<head>
<%ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_TEACHER); %>
<%@include file = "../../../common/commonRedirect.jsp" %>
	<meta charset="UTF-8">
    <title>作业管理</title>
	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css"/>
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="../../../common/managementPage.css"/>
        <link rel="stylesheet" href="../../../resources/plugins/datePicker/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="../css/teacherHomeworkManagement.css">
    <style>
    /* #content{border: 1px solid saddlebrown;padding: 16px;border-radius: 2px} */
    .list {top: 15px;width: 140px;height: 40px; border:1px solid #0082E6; display:inline-block;border-radius: 2px;position: relative; line-height: 40px;}   
    #file{position: absolute;opacity: 0;color:white;width: 100%;height: 100%;z-index: 100;}
    .list span{
        display: inline-block;
        text-align: center;
        width: 100%;
        line-height: 40px;
        position: absolute;
        color: #0082E6;
    }

    ._p{
        margin: 14px;
    }
    ._p input{
        display: inline-block;
        width: 70%;
        margin-left: 6px;
    }
    ._p span{
        font-size: 15px;
    }  
    </style>
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
                    	<button class="btn btn-primary" id="publishHomework" data-toggle="modal" data-target="#publishHomework-modal">
                            <i class="fa fa-file-text-o"></i>
                            <span>发布作业</span>
                        </button>
                        <button class="disabled btn btn-primary" id="deleteHomework" data-toggle="modal" data-target="#deleteHomework-modal" disabled>
                            <i class="fa fa-trash-o"></i>
                            <span>删除作业</span>
                        </button>
                        <button class="btn btn-primary" id="modifyHomework"  data-toggle="modal" data-target="#modifyHomework-modal" disabled>
                            <i class="fa fa-edit"></i>
                            <span>修改作业</span>
                        </button>
                        <button class="btn btn-primary" id="studentHomework" disabled>
                            <i class="fa fa-file-text-o"></i>
                            <span>批改作业</span>
                        </button>
                        <button class="btn btn-primary" id="copyHomework" data-toggle="modal" data-target="#copyHomework-modal" disabled>
                            <i class="fa fa-copy"></i>
                            <span>复制作业</span>
                        </button>
                    </div>
                    
                    <div class="modal" id="deleteHomework-modal" data-backdrop="static">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<p>删除作业</p>
								</div>
								<div class="modal-body">
									<p>确认删除<span id="deleteHomework_homeworkName" style="color:red"></span>?</p>
								</div>
								<div class="modal-footer">
									<button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
									<button type="button" class="btn btn-primary" data-dismiss="modal" id="deleteHomeworkCommit">确认</button>
								</div>
							</div>
						</div>
					</div>
					
                    <div class="modal" id="publishHomework-modal" data-backdrop="static">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <p>发布作业</p>
					            </div>
					            <div class="modal-body">
					                <div class="form-group">
					                    <label for="homeworkName"><span class="starColor">*</span>作业名称：</label>
					                    <input class="form-control" name="cantbeNull" style="width:100%;" type="text" id="homeworkName"/>
					                    <div class="invalid-feedback">
	              							作业名称不能为空
	            						</div>
					                </div>
					                <br>
					                <div class="form-group">
					                    <label><span class="starColor">*</span>作业内容：</label>
					                    <textarea class="form-control" name="homeworkContent" style="width:100%;" id="homeworkContent"></textarea>
					                    <div style="color:gray;font-size:12px;float:right"><span id="count_create">0</span>/500</div>
										<div class="invalid-feedback">
	              							字数不能为空或超过限制
	            						</div>
					                </div>
					                <br>
				                	<div class="form-group">
				                		<label  class="control-label"><span class="starColor">*</span>发布班级：</label>
											<button class="btn dropdown-toggle form-control" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										    	请选择班级
										  	</button>
										  	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
										  	<table class="table table-hover" id="modal-context-list">
										  	</table>
											</div>
											<div class="invalid-feedback">
		              							班级不能为空
		            						</div>
				                	</div>
					                <br>
					                
					                <div class="form-group">
										<div id="selectedClassList"></div>	
				                	</div>
					                <br>
					                
					                <div class="form-group">
					                    <label><span class="starColor">*</span>截止时间：</label>
					                    <input class="form_date date-input form-control" name="" size="16" type="text" id="deadLine">
					                    <div class="invalid-feedback">
	              							截止时间不能为空
	            						</div>
					                </div>
					                <br>
					                <div>
										<label for="homeworkFile" class="btn btn-primary">上传文件</label>
										<input id="homeworkFile" type="file" style="display: none" onchange="fileSelected();" >
					                </div>
					                <div>
					                	<span>已选文件：</span>
					                	<span id="fileName"></span>
					                </div>
					                <br>
					            </div>
					            <div class="modal-footer">
					                <button class="btn btn-outline-secondary" data-dismiss="modal" id="publishHomeworkCancel">取消</button>
					                <button class="btn btn-primary" id="publishHomeworkCommit" >确定</button>
					            </div>
					        </div>
					    </div>
					</div>
					
                    <div class="modal" id="modifyHomework-modal" data-backdrop="static">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <p>修改作业(<span id="selectNameforModify"></span>)</p>
					            </div>
					            <div class="modal-body">
					                <div class="form-group">
					                    <label for="previousHomeworkName"><span class="starColor">*</span>作业名称：</label>
					                    <input name="cantbeNull" class="form-control" style="width:100%;" type="text" id="previousHomeworkName"/>
					                    <div class="invalid-feedback">
	              							作业名称不能为空
	            						</div>
					                </div>
					                <br>
					                <div class="form-group">
					                    <label><span class="starColor">*</span>作业内容：</label>
					                    <textarea name="homeworkContent" class="form-control" style="width:100%;" id="previousHomeworkContent"></textarea>
					                    <div style="color:gray;font-size:12px;float:right"><span id="count_modify">0</span>/500</div>
										<div class="invalid-feedback">
	              							字数不能为空或超过限制
	            						</div>
					                </div>
					                <br>
				                	<div class="form-group">
				                		<label  class="control-label"><span class="starColor">*</span>发布班级：</label>
											<button class="form-control btn dropdown-toggle" type="button" id="dropdownMenuButton1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										    	请选择班级
										  	</button>
										  	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
										  	<table class="table table-hover" id="modal-context-list-modify">
										  	</table>
											</div>
										<div class="invalid-feedback">
	              							班级不能为空
	            						</div>
				                	</div>
					                <br>
					                
					                <div class="form-group">
										<div id="previousClass">
											<!-- <button type="button" class="btn btn-outline-secondary" disabled>暂无选中班级</button> -->
										</div>	
				                	</div>
					                <br>
					                
					                <div class="form-group">
					                    <label><span class="starColor">*</span>截止时间：</label>
					                    <input name="" class="form-control form_date date-input" size="16" type="text" id="previousEndtime">
					                    <div class="invalid-feedback">
	              							截止时间不能为空
	            						</div>
	            						
					                </div>
					                <br>
					                
					               	<div>
										<label for="modifyHomeworkFile" class="btn btn-primary">上传文件</label>
										<input id="modifyHomeworkFile" type="file" style="display: none" onchange="fileSelectedModify();" >
										<label for="removeModifyHomeworkFile" class="btn btn-primary">清除文件</label>
										<input id="removeModifyHomeworkFile" style="display: none" >
					                </div>
					                <div>
					                	<span>已选文件：</span>
					                	<span id="modifyFileName"></span>
					                </div>
					                <br>
					            </div>
					            <div class="modal-footer">
					            	<button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
					                <button class="btn btn-primary" id="modifyHomeworkCommit" >确认修改</button>
					            </div>
					        </div>
					    </div>
					</div>	
                    
                    <div class="modal" id="copyHomework-modal" data-backdrop="static">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <p>复制作业(<span id="selectNameforCopy"></span>)</p>
					            </div>
					            <div class="modal-body">
					                <div>
					                    <label for="copyHomeworkName"><span class="starColor">*</span>作业名称：</label>
					                    <input name="cantbeNull" class="form-control" style="width:100%;" type="text" id="copyHomeworkName"/>
					                    <div class="invalid-feedback">
	              							名称不能为空
	            						</div>
					                </div>
					                <br>
					                <div>
					                    <label><span class="starColor">*</span>作业内容：</label>
					                    <textarea name="homeworkContent" class="form-control" style="width:100%;" id="copyHomeworkContent"></textarea>
					                	<div style="color:gray;font-size:12px;float:right"><span id="count_copy">0</span>/500</div>
										<div class="invalid-feedback">
	              							字数不能为空或超过限制
	            						</div>
					                </div>
					                <br>
				                	<div class="form-group">
				                		<label  class="control-label"><span class="starColor">*</span>发布班级：</label>
											<button class="btn dropdown-toggle " type="button" id="dropdownMenuButton2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										    	请选择班级
										  	</button>
										  	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
										  	<table class="table table-hover" id="modal-context-list-copy">
										  	</table>
											</div>
				                	</div>
					                <br>
					                
					                <div class="form-group">
										<div id="copyClass">
											<!-- <button type="button" class="btn btn-outline-secondary" disabled>暂无选中班级</button> -->
										</div>	
				                	</div>
					                <br>
					                
					                <div>
					                    <label><span class="starColor">*</span>截止时间：</label>
					                    <input name="" size="16" type="text" id="copyEndtime" class="form-control form_date date-input">
					                	<div class="invalid-feedback">
	              							截止时间不能为空
	            						</div>
					                </div>
					                <br>
					                <div>
										<label for="copyHomeworkFile" class="btn btn-primary">上传文件</label>
										<input id="copyHomeworkFile" type="file" style="display: none" onchange="fileSelectedCopy();" >
										<label for="removeCopyHomeworkFile" class="btn btn-primary">清除文件</label>
										<input id="removeCopyHomeworkFile" style="display: none" >
					                </div>
					                <div>
					                	<span>已选文件：</span>
					                	<span id="copyFileName"></span>
					                </div>
					                <br>
					            </div>
					            <div class="modal-footer">
					            	<button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
					                <button class="btn btn-primary" id="copyHomeworkCommit" >确认复制</button>
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
                                    <!-- <a class="dropdown-item">所有班级</a> -->
                                </div>
                            </div>
                            <div>
                                <label for="search-student">作业名称：</label>
                                <input id="search-homeworkName" type="text" placeholder="">
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
                    <div class="table-context">
                        <div class="tablet-context-header">
                            <table>
                                <tbody id="table-header">
	                                <tr>
	                                    <th><input type='checkbox' id='CheckAll' class='selectAll'></th>
	                                    <th></th>
	                                    <th>作业名称</th>
	                                    <th>开始时间</th>
	                                    <th>截止时间</th>
	                                    <th>上交人数</th>
	                                    <th>发布班级</th>
	                                    <th class="gutter"></th>
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
<script src="../../../common/checkTime.js"></script>
<!--导入datePicker的js-->
<script src="../../../resources/plugins/datePicker/bootstrap-datetimepicker.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
<script src="../js/teacherHomeworkManagement.js"></script>
<script src="http://oss.sheetjs.com/js-xlsx/xlsx.full.min.js"></script>

</body>
</html>


