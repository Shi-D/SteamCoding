//URL与window.URL冲突，改为_URL
var _URL="http://localhost:8080/SteamCoding/";
//var _URL="http://172.22.203.91:8080/SteamCoding/";
// var _URL = "http://115.231.108.196/SteamCoding/";
var sessionTimeOutErrorCode = 901;
var userInfo = null;
var loginType =true;
tryCount = 2;
$(".form-control-login").focus(function(){
    $(".error_message").css({'display':'none'});
    $("#loginShadowCard").css("visibility",'hidden');
    $("#loginCard").css("visibility",'hidden');
});
function ajaxSessionJudge(){
    $('.modal').modal("hide");
    $('#modal-loginPrompt').modal("show");
}

//ajax 901错误 执行
$(document).ajaxError(function(event, XMLHttpRequest, ajaxOptions, thrownError){
    if(XMLHttpRequest.status == sessionTimeOutErrorCode){
        ajaxSessionJudge();
    }
});

//点击退出登录
$("#loginOut").click(function(){
    $.post(_URL+"j_spring_security_logout",function () {
        window.location.href = _URL ;
    });
});

console.log(screen.availWidth/2,screen.availHeight/2);
$("#loginShadowCard").css("top", (screen.availHeight/2-150)+"px");
$("#loginShadowCard").css("left","175px");



$("#cancelToVerify").click(function(){
	$("#loginShadowCard").css("visibility","hidden");
})

loginFunc("#relogin_button");

//登录按钮
function loginFunc(loginButton,type=false){
	$(loginButton).click(function(){
		console.log("ddd");
		$("#loginShadowCard").css("visibility","visible");
		$("#loginCard").css("visibility","visible");
		$("#indexCard").css("visibility","visible");
		$(".refreshIcon").click();
		loginType = type;
	});
}

//验证码
function verifyButton(){
var datas = {
			'j_username':$("#inputEmail").val(),
		    'j_password':$("#inputPassword").val()
		};
		$.ajax({
			type:'post',
		    url:'/SteamCoding/j_spring_security_check',
		    data: datas,
		    async:false,
		    success:function(result){
		   		if(result.indexOf("Bad credentials")>=0){
					$(".error_message").css({'display':'block'});
				}else if(loginType){
					if(result[0].userCode != null){
						window.location.href = _URL;
					}
				}else if(userInfo == null){
					if(result[0].userCode != null){
						$('.modal').modal("hide");
						$(".alert-info").animate({display:'block',top:'100px'},1000)
						.animate({top:'100px'},1000)
						.animate({top:'-100px'},1000,function(){$('.alert-info').css({display:'block'})});
						try{
							steamCoding.func.alert('登录成功');
							steamCoding.o.userId = result[0].userCode;
						}catch{}
					}
				}else{
					if(userInfo.userCode == result[0].userCode){
						$('.modal').modal("hide");
						try{
							steamCoding.func.alert('登录成功');
							steamCoding.o.userId = result[0].userCode;
						}catch{}
					}else{
						$('.modal').modal("hide");
						alert("非上一个用户，请回到首页！");
						window.location.replace(_URL);
						try{
							steamCoding.o.userId = -1;
						}catch{}
			        }	
				}
				userInfo = result[0];
			}
		});	
}

$.ajax({
	url:_URL+"user/queryUserInfo.action",
	type:'post',
	async:false,
	success:function(result){
		userInfo = result;
		$("#userName").html(result.userName);
		$("#userPhoto").attr("src",_URL+"account/"+result.userPhotoName);
	}
});