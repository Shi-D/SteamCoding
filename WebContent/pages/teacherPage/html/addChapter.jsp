<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
ArrayList<Integer> role = new ArrayList<Integer>();role.add(ROLE_ADMIN);role.add(ROLE_TEACHER);role.add(ROLE_STUDENT);role.add(ROLE_SUPERADMIN); 
%>
<%@include file = "../../../common/commonRedirect.jsp" %>
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的课程</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="../css/addChapter.css" />
	<link rel="stylesheet" href="../../../resources/plugins/bootstrap-4.0.0/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="../../../resources/plugins/summernote/dist/summernote-bs4.css" />
</head>

<body>
<div class="editor">
    <div class="editor_wrap">
        <div class="editor_top">
            <div class="top_wrap clearfix">
                <div class="logo W_fl">
                    <span>SteamCoding</span>
                </div>
                <div class="setup W_fr">
                <%
                	if(session.getAttribute("role").equals(1)){
                %>
                	<i class="W_ficon" id="back" onclick="javascript:window.location.href='../../administratorPage/html/adminCourseManagement.jsp'">返回</i>
                <%
                	}else if(session.getAttribute("role").equals(2)){
                %>		
                	<i class="W_ficon" id="back" onclick="javascript:window.location.href='courseManagement.jsp'">返回</i>	
                <%
                 	} 
                %>   
                </div>
            </div>
        </div>
        <div class="editor_sidebar">
            <div class="sidebar_box">
                <div class="sidebar_wrap">
                    <div class="sidebar_list">
                        <div class="list_add">
                            <div class="bor"><a href="javascript:;" id="newChapter"><i class="W_ficon"></i>新建章节</a></div>
                        </div>

                        <div class="list_wrap" style="overflow: scroll;height: auto" >
                            <ul class="" id="chapterList" ></ul>
                            <div style="width: 7px; min-height: 10px; background: rgba(0, 0, 0, 0.4); position: absolute; right: 2px; top: 0px; border-radius: 5px; display: none; z-index: 999; height: 847px;"></div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div class="editor_content">
                <div class="editor_main">
                    <div class="main_box">
                        <div class="main_wrap">
                            <div class="main_editor">
                                <div class="title" style="opacity: 0.5;">
                                    <input placeholder="请输入章节名称" class="W_input W_input_focus" style="height: 100px;" id="chapterName"></input>
                                </div>
                                <div class="editor_in_v2">
                                    <div class="summernote" id="chapterContent"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="editor_footer">
                    <div class="footer_wrap clearfix">
                        <div class="optbox W_fr">
                            <span class="preview"><a href="javascript:;" class="S_txt1" id="deleteChapter">删除</a></span>
                            <!-- <span class="preview"><a href="javascript:;" class="S_txt1" id="shutView">预览</a></span> -->
                            <span class="next"><a class="W_btn_a btn_34px" id="updateChapterCommit">保存</a></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="../../../resources/plugins/jquery-3.3.1.js"></script>
<script src="../../../common/common.js"></script>
<script src="../../../resources/plugins/jquery.cookie.js"></script>
<script src="../../../resources/plugins/upload.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="../../../resources/plugins/bootstrap-4.0.0/dist/js/bootstrap.js"></script>
<script src="../js/addChapter.js"></script>
<script src="../../../resources/plugins/summernote/dist/summernote-bs4.js"></script>

</html>