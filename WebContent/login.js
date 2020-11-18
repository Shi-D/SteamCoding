let countdown = 60;
let _postSrc = _URL+"user/";
let defaultOrganizationId="1";
let tryCount = 1;

var X = $('#login').offset().top;//元素在当前视窗距离顶部的位置
var Y = $('#login').offset().left;
console.log(X,Y);
$("#loginCard").css("top",(X-155)+"px");
$("#loginCard").css("left",(Y+150)+"px");

$("#cancelToVerify").click(function(){
	$(".card").css("visibility","hidden");
})
$("input").click(function(){
	$("#cancelToVerify").click();
})


/**点击登录**/
loginFunc("#login",true);

/**************用户注册*******************/

$(document).ready(function(){
	window_href = window.location.href;
	window_href = window_href.replace(/(\S*)login.jsp\?/, "");
	if(window_href == 'error=true'){
		$(".error_message").css({'display':'block'});
	}
	$.post(_postSrc+"queryDefaultOrganization.action",function(result){
		defaultOrganizationId = result[0].PARAMETER_DEFAULT_VALUE;
	})
});


$("#sign-up").click(function(){
	empty();
});
//获取验证码
$("#getVerifiCode-signUp").click(function(){
	if($("#phone-signUp").hasClass("is-valid")){
		$.post(_postSrc+"smsPort.action",{phone:$("#phone-signUp").val()},function(result){
			console.log(result);
			if(result[0].result=="该手机号已注册"){
				$(".phone-feedback").html("该手机号已注册");
				$("#phone-signUp").addClass("is-invalid");
				$("#getVerifiCode-signUp").attr("disabled","disabled");
			}
		});
		setTime();
	}
});

$("#confirm-signUp").click(function(){
	formData = new FormData();
	
	if($("#phone-signUp").hasClass("is-valid")&&
		$("#verifiCode-signUp").hasClass("is-valid")&&
		$("#userName").hasClass("is-valid")&&
		$("#password-signUp").hasClass("is-valid")&&
		$("#passwordConfirm-signUp").hasClass("is-valid")){
		
		formData.append("phone",$("#phone-signUp").val());
		formData.append("verifiCode",$("#verifiCode-signUp").val());
		formData.append("userName",$("#userName").val());
		formData.append("password",$("#password-signUp").val());
		formData.append("organizationId",defaultOrganizationId);
		
		$.ajax({
		    url : _postSrc+'registerUser.action',
		    type : 'POST',
		    async : false,
		    data : formData,
		    processData : false,
		    contentType : false,
		    success : function(result) {
		    	console.log(result);
		    	if(result[0].result == "注册成功"){
		    		alert("注册成功");
		    		$("#sign-up-modal").modal('hide');
		    		empty();
		    	}
		    	else if(result[0].result == "无效验证码"){
		    		$("#code-feedback-signUp").html("无效验证码");
		    		$("#verifiCode-signUp").addClass("is-invalid");
		    		$("#verifiCode-signUp").removeClass("is-valid");
		    	}
		    	else if(result[0].result == "注册失败"){
		    		alert("注册失败,请联系客服");
		    	}
		    },
		    error:function(){
		 		   //alert("上传失败!")
		 	}
		});
	}
});

/**************忘记密码*******************/
$("#reset-password").click(function(){
	empty();
});
//获取验证码
$("#getVerifiCode-updatePwd").click(function(){
	if($("#phone-updatePwd").hasClass("is-valid")){
		$.post(_postSrc+"updatePwdSmsPort.action",{phone:$("#phone-updatePwd").val()},function(result){
			console.log(result);
		});
		setTime();	
	}
});

$("#confirm-updatePwd").click(function(){
	formData = new FormData();
	if($("#phone-updatePwd").hasClass("is-valid")&&
		$("#verifiCode-updatePwd").hasClass("is-valid")&&
		$("#password-updatePwd").hasClass("is-valid")&&
		$("#passwordConfirm-updatePwd").hasClass("is-valid")){
		
		formData.append("phone",$("#phone-updatePwd").val());
		formData.append("verifiCode",$("#verifiCode-updatePwd").val());
		formData.append("password",$("#password-updatePwd").val());
		
		$.ajax({
		    url : _postSrc+'resetPassword.action',
		    type : 'POST',
		    async : false,
		    data : formData,
		    processData : false,
		    contentType : false,
		    success : function(result) {
		    	console.log(result);
		    	if(result[0].message == "密码重置成功"){
		    		alert("密码重置成功");
		    		$("#sign-up-resetPwd").modal('hide');
		    		empty();
		    	}
		    	else if(result[0].message == "验证码无效"){
		    		$("#code-feedback-updatePwd").html("验证码无效");
		    		$("#verifiCode-updatePwd").addClass("is-invalid");
		    		$("#verifiCode-updatePwd").removeClass("is-valid");
		    	}
		    	else if(result[0].message == "密码重置失败"){
		    		alert("密码重置失败,请联系客服");
		    	}
		    },
		    error:function(){
		 		   //alert("上传失败!")
		 	}
		});
	}
});

function empty(){
	$(".phone").val("");
	$(".phone").removeClass("is-valid");
	$(".phone").removeClass("is-invalid");
	$(".verifiCode").val("");
	$(".verifiCode").removeClass("is-valid");
	$(".verifiCode").removeClass("is-invalid");
	$(".userName").val("");
	$(".userName").removeClass("is-valid");
	$(".userName").removeClass("is-invalid");
	$(".password").val("");
	$(".password").removeClass("is-valid");
	$(".password").removeClass("is-invalid");
	$(".passwordConfirm").val("");
	$(".passwordConfirm").removeClass("is-valid");
	$(".passwordConfirm").removeClass("is-invalid");
	$(".getVerifiCode").attr("disabled","disabled");
	$(".getVerifiCode").val("免费获取验证码");
}

//倒计时
function setTime(){
	$(".verifiCode").removeAttr("disabled");
	$(".getVerifiCode").attr("disabled","disabled");
	if (countdown == 0) {
    	$(".getVerifiCode").removeAttr("disabled");
    	$(".getVerifiCode").val("免费获取验证码");
        countdown = 60;
    } else {
    	$(".getVerifiCode").attr("disabled", true);
    	$(".getVerifiCode").val("重新发送("+countdown+")");
        countdown--;
        setTimeout(function() {
            setTime()
        },1000)
    }
}
