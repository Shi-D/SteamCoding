<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <style>
  .error_message{
  	text-align:left !important;color:rgb(255,0,0);display:none;

  }
  .errorMessage{
	  height:25px;
  }
#loginShadowCard{
	visibility:hidden;
	position:fixed;
	z-index:100;
}
#cancelToVerify{
	color:#aaaaaa;
	font-size:25px;
	display:relative;
	right:0px;
}
  </style>
<link rel="stylesheet" href="../../../resources/plugins/slideVerify/slideVerify.css">   
<div aria-hidden="true" class="modal fade" id="modal-loginPrompt" data-backdrop="static">
	<div class="modal-dialog ">
		<div class="modal-content">
		<div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button></div>
			<div class="modal-body">
			
				<div class="card" id='loginShadowCard' style="width:350px">
					<div class="card-body">
						<span id="cancelToVerify"><i class="fa fa-times-circle-o"></i></span>
						<br>
				   		<div class="container" id="verifyContainer">
							<div id="captcha" style="position:relative"></div>
							<div id="msg"></div>
						</div>
				 	</div>
				</div>
				
				<h1 class="h4 text-center mb-5 mt-3" >请重新登录</h1>
					<div class="login">
						<div class="form-group">
							<label for="inputEmail">请输入用户名</label>
							<input type="text" id="inputEmail" class="form-control form-control-login" name="j_username" placeholder="请输入用户名" />
						</div>
						<div class="form-group">
							<label for="inputPassword">请输入密码</label>
							<input type="password" id="inputPassword" name="j_password" placeholder="请输入密码" class="form-control form-control-login" />
						</div>
						<div class="errorMessage">
							<p class="error_message">用户名或密码错误</p>
						</div>
						<a href="javascript:void(0);" class="mt-5 mb-5 btn btn-lg btn-primary btn-block" id="relogin_button">登录</a>
					</div>
			</div>
		</div>
	</div>
</div>

<script>
function getRandomNumberByRange(start, end) {
    return Math.round(Math.random() * (end - start) + start)
}
function getRandomImg() {
    return "../../../resources/plugins/slideVerify/img/" + getRandomNumberByRange(0, 6) + ".png"
}
</script>
<script src="../../../resources/plugins/slideVerify/slideVerify.js"></script>
<script>
jigsaw.init(document.getElementById('captcha'), function () {});
</script>
</html>
