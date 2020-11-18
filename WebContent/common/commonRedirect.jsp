<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page import="com.framework.common.SystemContext"%>
<%@ page import="com.framework.authority.entity.User"%>
<%@ page import="javax.servlet.http.*" %>
<%@include file = "../pages/loginshadow/loginShadow.jsp" %>
<%! int ROLE_SUPERADMIN = 0;%>
<%! int ROLE_ADMIN = 1;%>
<%! int ROLE_TEACHER = 2;%>
<%! int ROLE_STUDENT = 3;%>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","No-cache"); 
	response.setDateHeader("Expires", -1); 
	response.setHeader("Cache-Control", "No-store");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = SystemContext.getCurrentUser();
	//String URL = "http://115.231.108.196/SteamCoding/login.jsp";
	String URL="http://localhost:8080/SteamCoding/login.jsp";
	if (user != null) {
		if(!role.contains(user.getUserRole())){
			response.sendRedirect(URL);
			return;
		}
		session.setAttribute("name", user.getUsername());
		session.setAttribute("role", user.getUserRole());
	}else{
		response.sendRedirect(URL);
		return;
	}
%>

