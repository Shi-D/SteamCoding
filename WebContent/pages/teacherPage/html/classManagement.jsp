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
	<title>班级管理</title>
	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css" rel="stylesheet" />
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="../../../common/managementPage.css"/>
</head>
<body>
<div class="main">
	<script type="text/javascript">
	var index = 0;
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
						<button class="disabled btn btn-primary" id="add-student" data-toggle="modal" data-target="#add-student-modal" disabled>
							<i class="fa fa-plus-circle"></i>
							<span>添加学生</span>
						</button>
						<button class="disabled btn btn-primary" id="remove-student" data-toggle="modal" data-target="#remove-student-modal" disabled>
							<i class="fa fa-minus-circle"></i>
							<span>移除学生</span>
						</button>
						<button class="disabled btn btn-primary" id="add-course" data-toggle="modal" data-target="#add-course-modal" disabled>
							<i class="fa fa-plus-circle"></i>
							<span>添加课程</span>
						</button>
						<button class="disabled btn btn-primary" id="remove-course" data-toggle="modal" data-target="#remove-course-modal" disabled>
							<i class="fa fa-minus-circle"></i>
							<span>移除课程</span>
						</button>
						<button class="disabled btn btn-primary" id="update-courseProgress" data-toggle="modal" data-target="#update-courseProgress-modal" disabled>
							<i class="fa fa-plus-circle"></i>
							<span>更新课程进度</span>
						</button>
						<button class="btn btn-primary" id="delete-class" data-toggle="modal" data-target="#delete-class-modal" disabled>
							<i class="fa fa-trash-o"></i>
							<span>删除班级</span>
						</button>
						<button class="btn btn-primary" id="add-class" data-toggle="modal" data-target="#add-class-modal">
							<i class="fa fa-plus-circle"></i>
							<span>添加班级</span>
						</button>

						<div class="modal" id="add-student-modal" data-backdrop="static">

			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p id="addStudent_className">添加学生</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<input type="text" placeholder="输入学生姓名搜索" id="addStudentInputInfo">
						<button class="btn btn-primary selectAllModalItem" >全选</button>
						<button class="btn btn-primary notSelectAllModalItem">全不选</button>
						<div class="table-context-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th style="width:10%"></th>
										<th style="width:20%">序号</th>
										<th style="width:30%">姓名</th>
										<th style="width:40%">学号</th>
									</tr>
								</thead>
								<tbody id="modal_studentNotInClass_list" class="modal_list">
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="addStudentCommit">添加</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" id="remove-student-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p id="removeStudent_className">移除学生</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<input type="text" placeholder="输入学生姓名搜索" id="removeStudentInputInfo">
						<button class="btn btn-primary selectAllModalItem" >全选</button>
						<button class="btn btn-primary notSelectAllModalItem">全不选</button>
						<div class="table-context-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th style="width:10%"></th>
										<th style="width:20%">序号</th>
										<th style="width:30%">姓名</th>
										<th style="width:40%">学号</th>													
									</tr>
								</thead>
								<tbody id="modal_studentInClass_list" class="modal_list">
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="removeStudentCommit">移除</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal" id="add-course-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p id="addCourse_className">添加课程：</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<input type="text" placeholder="搜索课程" id="addCourseInputInfo">
						<button class="btn btn-primary selectAllModalItem" >全选</button>
						<button class="btn btn-primary notSelectAllModalItem">全不选</button>
						<div class="table-context-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th></th>
										<th>序号</th>
										<th>课程名称</th>
										<th>课程ID</th>
									</tr>
								</thead>
								<tbody id="modal_courseNotInClass_list" class="modal_list">
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="addCourseCommit">添加</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" id="remove-course-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p id="removeCourse_className">移除课程：</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<input type="text" placeholder="搜索课程" id="removeCourseInputInfo">
						<button class="btn btn-primary selectAllModalItem" >全选</button>
						<button class="btn btn-primary notSelectAllModalItem">全不选</button>
						<div class="table-context-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th></th>
										<th>序号</th>
										<th>课程名称</th>
										<th>课程ID</th>
									</tr>
								</thead>
								<tbody id="modal_courseInClass_list" class="modal_list">
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="removeCourseCommit">移除</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" id="update-courseProgress-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p id="addCourseChapter_className">更新课程进度：</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<div class="dropdown">
								<button type="button" id="choseCourseButton" class="btn dropdown-toggle" data-toggle="dropdown" value="请选择课程">
									 请选择课程
								</button>
								<hr>
								<div class="dropdown-menu" id="drapdown-menu">

								</div>
							</div>
						<div class="table-context-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th style="width: 10%"></th>
										<th style="width: 20%">序号</th>
										<th style="width: 30%">发布状态</th>
										<th style="width: 40%">章节名称</th>
									</tr>
								</thead>
								<tbody id="modal_chapterInCourse_list" class="modal_list">
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"  id="updateChapterCommit">确认更新</button>
					</div>
				</div>
			</div>
		</div>		
		<div class="modal" id="add-class-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p>添加班级</p>
					</div>
					<div class="modal-body">
							<div class="form-group">
								<label for="addClassName">请输入班级名称</label>
								<input type="text" name="cantbeNull" class="form-control" placeholder="班级名称" id="addClassName"/>
								<div class="invalid-feedback">
									班级名不能为空
								</div>
							</div>
							<div class="form-group">
								<label for="buttons">导入学生名单</label>
								<div>
									<div class="btn btn-primary">
										<label for="excel-file" style="margin: unset;">上传学生名单</label>
						 				<input type="file" id="excel-file" name="files" style="position:absolute;clip:rect(0 0 0 0);">
									</div>
									<button class="btn btn-primary" id="download-import-template">
                            		<i class="fa fa-cloud-download"></i>
                            		<span>下载导入模板</span>
                        			</button>
								</div>
							</div>
                        	<input type="text" class="form-control" disabled="disabled" value="未选择文件" id="fileName">
						
						</div>
					<div class="modal-footer">
						<button class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"  id="addClassCommit">添加</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal" id="delete-class-modal" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<p>删除班级</p>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<p>确认删除<span id="deleteClass_className" style="color:red"></span>?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" id="deleteClassCommit">确认</button>
					</div>
				</div>
			</div>
		</div>
</div>
				<div class="table-fliter" id="table-fliter">
					<div>
						<div>
							<label for="titleSearch">班级名：</label>
							<input id="titleSearch" type="text" placeholder="" data-value="className">
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
									<th>班级名</th>
									<th>学生数</th>
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
<script src="../js/classManagement.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
</body>
</html>