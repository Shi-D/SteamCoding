<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${sessionScope.role==0}">
    <div class="sider-menu">
        <div class="sider-menu-header">
            <img class="logo" src="/SteamCoding/resources/img/steamEducation.jpg">
        </div>
        <ul class="management-menu" id="management-menu">
            <li>
				<span>
					<i class="fa fa-graduation-cap"></i>
					<a href="/SteamCoding/pages/superAdminPage/html/superAdminOrganizationManagement.jsp">机构管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-group"></i>
					<a href="/SteamCoding/pages/superAdminPage/html/superAdminPersonnelManagement.jsp">人员管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-cog"></i>
					<a href="/SteamCoding/pages/superAdminPage/html/superAdminParameterSetting.jsp">参数设置</a>
				</span>
            </li>
        </ul>
    </div>
</c:if> 

<c:if test="${sessionScope.role==1}">
    <div class="sider-menu">
        <div class="sider-menu-header">
            <img class="logo" src="/SteamCoding/resources/img/steamEducation.jpg">
        </div>
        <ul class="management-menu" id="management-menu">
            <li>
				<span>
					<i class="fa fa-graduation-cap"></i>
					<a href="/SteamCoding/pages/administratorPage/html/adminClassManagement.jsp">班级管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-group"></i>
					<a href="/SteamCoding/pages/administratorPage/html/adminStudentManagement.jsp">学生管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-group"></i>
					<a href="/SteamCoding/pages/administratorPage/html/adminTeacherManagement.jsp">教师管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-book"></i>
					<a href="/SteamCoding/pages/administratorPage/html/adminCourseManagement.jsp">课程管理</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-pencil-square-o"></i>
					<a href="/SteamCoding/pages/administratorPage/html/adminWorkManagement.jsp">作品管理</a>
				</span>
            </li>
        </ul>
    </div>
</c:if>   
 
<c:if test="${sessionScope.role==2}">    
    <div class="sider-menu">
		<div class="sider-menu-header">
			 <img class="logo" src="/SteamCoding/resources/img/steamEducation.jpg">
		</div>
		<ul class="management-menu" id="management-menu">
			<li>
				<span>
					<i class="fa fa-graduation-cap"></i>
					<a href="/SteamCoding/pages/teacherPage/html/classManagement.jsp">班级管理</a>
				</span>
			</li>
			<li>
				<span>
					<i class="fa fa-group"></i>
					<a href="/SteamCoding/pages/teacherPage/html/studentManagement.jsp">学生管理</a>
				</span>
			</li>
			<li>
				<span>
					<i class="fa fa-book"></i>
					<a href="/SteamCoding/pages/teacherPage/html/courseManagement.jsp">课程管理</a>
				</span>
			</li>
			<li>
				<span>
					<i class="fa fa-pencil-square-o"></i>
					<a href="/SteamCoding/pages/teacherPage/html/workManagement.jsp">作品管理</a>
				</span>
			</li>
			<li>
				<span>
					<i class="fa fa-file-text-o"></i>
					<a href="/SteamCoding/pages/teacherPage/html/teacherHomeworkManagment.jsp">作业管理</a>
				</span>
			</li>
		</ul>
	</div>
</c:if>    
    
<c:if test="${sessionScope.role==3}">
    <div class="sider-menu">
        <div class="sider-menu-header">
            <img class="logo" src="/SteamCoding/resources/img/steamEducation.jpg">
        </div>
        <ul class="management-menu" id="management-menu">
            <li>
				<span>
					<i class="fa fa-pencil-square-o"></i>
					<a href="/SteamCoding/pages/studentPage/html/studentWorkManagement.jsp">我的作品</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-book"></i>
					<a href="/SteamCoding/pages/studentPage/html/studentCourseManagement.jsp">我的课程</a>
				</span>
            </li>
            <li>
				<span>
					<i class="fa fa-file-text-o"></i>
					<a href="/SteamCoding/pages/studentPage/html/studentHomeWorkmanagement.jsp">我的作业</a>
				</span>
			</li>
        </ul>
    </div>    
</c:if>    
    
<script type="text/javascript">
function getHoverIndex(index){
	if(index !== -1){
		var ul_menu = document.getElementById("management-menu");
		var li_list = ul_menu.getElementsByTagName("li");
	    li_list[index].className = 'active';
	}
}
window.onload=getHoverIndex(index); 
</script>
    