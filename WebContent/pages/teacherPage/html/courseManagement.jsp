<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%
ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_TEACHER);
%>
<%@include file = "../../../common/commonRedirect.jsp" %>
	<meta charset="UTF-8">
	<title>课程管理</title>
	<link rel="stylesheet" href="../../../resources/plugins/font-awesome.css" />
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="../../../common/managementPage.css"/>
	<style type="text/css">
    .starColor{
    font-size: 20px;
    color: red;
	}
    #content{border: 1px solid saddlebrown;padding: 16px;border-radius: 2px}
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
    video{
        margin-top: 8px;
        border-radius: 4px;
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

						<button class="btn btn-primary" id="add-course" data-toggle="modal" data-target="#add-course-modal" >
                            <i class="fa fa-plus-circle"></i>
                            <span>添加课程</span>
                        </button>

						<button class="btn btn-primary" id="modify-course" data-toggle="modal" data-target="#modify-course-modal" disabled>
							<i class="fa fa-plus-circle"></i>
							<span>修改课程</span>
						</button>

						<button class="disabled btn btn-primary" id="delete-course" data-toggle="modal" data-target="#delete-course-modal" disabled>
							<i class="fa fa-times-circle"></i>
							<span>删除课程</span>
						</button>

			            <button class="btn btn-primary" id="update-course" data-toggle="modal" data-target="#update-course-modal" disabled>
							<i class="fa fa-cloud-upload"></i>
							<span>更新章节内容</span>
						</button>
						
						<button class="btn btn-primary" id="upload-video" data-toggle="modal" data-target="#upload-video-modal">
							<i class="fa fa-cloud-upload"></i>
							<span>上传视频至视频库</span>
						</button>
						
						<button class="btn btn-primary" id="video-lib" data-toggle="modal" data-target="#video-lib-modal">
							<i class="fa fa-cloud"></i>
							<span>查看视频库</span>
						</button>
						
						<div class="modal" id="add-course-modal" data-backdrop="static">
							<div class="modal-dialog " >
								<div class="modal-content">
									<div class="modal-header">
										<p>添加课程</p>
									</div>
									<div class="modal-body">
										<label for="createNewCourseName"><span class="starColor">*</span>课程名称</label>
										<input type="text" name="cantbeNull"  class="form-control" placeholder="请输入课程名称" id="createNewCourseName">
										<div class="invalid-feedback">
	              							课程名称不能为空
	            						</div>
									</div>

									<div class="modal-body">
										<label for="createNewCourseIntroduction">课程介绍</label>
										<textarea style="height: 140px" name="introduction"  class="form-control" id="createNewCourseIntroduction" placeholder="请输入课程介绍"></textarea>
										<div style="color:gray;font-size:12px;float:right"><span id="count_create">0</span>/150</div>
										<div class="invalid-feedback">
	              							字数超过限制
	            						</div>
									</div>

									<div class="modal-body">
										<label for="createNewCover" style="">请上传课程封面</label>
										<div id="createNewCover">
											<label for="uploadNewCover" style=""><img src="/SteamCoding/resources/img/setCover.png" id="showNewCover" width="300" height="160"></label>
							 				<input type="file" id="uploadNewCover" onchange="changepic(this,'showNewCover')" name="files" accept="image/*" style="position:absolute;clip:rect(0 0 0 0);">
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" id="createCourseCancel">取消</button>
										<button type="button" class="btn btn-primary" id="createCourseCommit">添加</button>
									</div>
								</div>
							</div>
						</div>

						<div class="modal" id="modify-course-modal" data-backdrop="static">
							<div class="modal-dialog " >
								<div class="modal-content">
									<div class="modal-header">
										<p>修改课程(<span id="modifyCourseName"></span>)</p>
									</div>
									<div class="modal-body">
										<label for="modifyNewCourseName"><span class="starColor">*</span>课程名称</label>
										<input type="text" name="cantbeNull"  class="form-control" placeholder="请输入课程名称" id="modifyNewCourseName">
										<div class="invalid-feedback">
	              							课程名称不能为空
	            						</div>
									</div>
									
									<div class="modal-body">
										<label for="modifyNewCourseIntroduction">课程介绍</label>
										<textarea style="height: 140px;width: 100%;"  name="introduction"  class="form-control" id="modifyNewCourseIntroduction" placeholder="请输入课程介绍"></textarea>
										<div style="color:gray;font-size:12px;float:right"><span id="count_modify">0</span>/150</div>
										<div class="invalid-feedback">
	              							字数超过限制
	            						</div>
									</div>

									<div class="modal-body">
										<label for="createModifyCover" style="">请上传课程封面</label>
										<div id="createModifyCover">
											<label for="uploadModifyCover" style=""><img src="" id="showModifyCover" width="300" height="160"></label>
							 				<input type="file" id="uploadModifyCover" onchange="changepic(this,'showModifyCover')" name="files" accept="image/*" style="position:absolute;clip:rect(0 0 0 0);">
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" id="modifyCourseCancel">取消</button>
										<button type="button" class="btn btn-primary" id="modifyCourseCommit" >修改</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="modal" id="delete-course-modal" data-backdrop="static">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<p>删除课程</p>
									</div>
									<div class="modal-body">
										<p>确认删除<span id="deleteCourse_courseName" style="color:red"></span>?</p>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" >取消</button>
										<button type="button" class="btn btn-primary" data-dismiss="modal" id="deleteCourseCommit">确定</button>
									</div>
								</div>
							</div>
						</div>

						<div class="modal" id="upload-video-modal"  data-backdrop="static">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<p>上传视频</p>
									</div>
									<div class="modal-body">
										<div id="content"  >
									      <div class="_p"><span>视频标题</span>：
									      <input id="title" name="cantbeNull" type="text" class="form-control" placeholder="请输入视频标题">
									      <div class="invalid-feedback" style="margin-left:13%">
              									视频标题不能为空
            							  </div>
            							  </div>
									      <div class="_p">
									          <span>选择视频： </span>
									          <!--文件选择按钮-->
									        <a class="list" href="javascript:;"> 
									            <input id="file" type="file" name="cantbeNull" accept="video/*" onchange="fileSelected();"/><span>选择视频</span><span id="time"></span>
									        </a>
									      </div>
									      <!--显示消失-->
									      
									       <ul id="videoList" class="el-upload-list el-upload-list--text" style="display:  none;">
									          <li tabindex="0" class="el-upload-list__item is-success" >
									              <a class="el-upload-list__item-name">
									                  <i class="el-icon-document"></i><span id="videoName"></span>
									       		 </a> 
									        <label class="el-upload-list__item-status-label" >
									            <i class="el-icon-upload-success el-icon-circle-check" ></i>
									        </label>
									        <i class="el-icon-close" onclick="del();"></i>
									        <i class="el-icon-close-tip"></i>
									          </li>
									      </ul>
									      
											<!--进度条-->
											<div class="progress" id="progressBar" style="display: none;">
												<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:0;">0%</div>
											</div>
									    </div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-outline-secondary"   onclick="cancleUploadFile()" id="uploadVideoCancel">取消</button>
										<button type="button" class="btn btn-primary"   onclick="uploadMaxFile()" id="uploadVideoCommit">开始上传</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="modal" id="video-lib-modal" data-backdrop="static">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<p>视频库</p>
										<button type="button" class="close" data-dismiss="modal">&times;</button>
									</div>
									<div class="modal-body">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
										  <li class="nav-item">
										    <a class="nav-link active" id="my-video-tab" style="color: black" data-toggle="tab" href="#my-video" role="tab" aria-controls="my-video" aria-selected="true">我的视频</a>
										  </li>
										  <li class="nav-item">
										    <a class="nav-link" id="other-video-tab" style="color: black" data-toggle="tab" href="#other-video" role="tab" aria-controls="other-video" aria-selected="false">其他视频</a>
										  </li>
										</ul>
									</div>
									<div class="modal-body" style="max-height:300px; overflow: auto;">
										<div class="tab-content">
										  <div class="tab-pane fade show active" id="my-video" role="tabpanel" aria-labelledby="my-video-tab">
										  	<ul class="list-group" id="my-video-list"></ul>
										  </div>
										  <div class="tab-pane fade" id="other-video" role="tabpanel" aria-labelledby="other-video-tab">
										  	<ul class="list-group" id="other-video-list"></ul>
										  </div>
										</div>
									</div>
									<div class="modal-body">
										<video id="video-play" height="360px" width="100%" src="" controls="controls" ></video>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-primary" id="deleteVideoCommit">删除</button>
									</div>
								</div>
							</div>
						</div>
						
					</div>
					<div class="table-fliter">
						<div>
							<div>
								<label for="titleSearch">课程名：</label>
								<input id="titleSearch" type="text" placeholder="" data-value="className">
							</div>
						</div>
						<div>
							<button class="btn btn-primary" id="query">
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
										<th>课程名</th>
										<th>开班数</th>
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
</body>

<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../common/common.js"></script>
<script src="../../../resources/plugins/jquery.cookie.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="../../../common/headerUserInfo.js"></script>
<script src="../../../common/tableManager.js"></script>
<script src="../../../resources/plugins/uploadVideo.js"></script>
<script src="../js/courseManagement.js"></script>
<script src="../../../resources/plugins/validator.js"></script>
</html>