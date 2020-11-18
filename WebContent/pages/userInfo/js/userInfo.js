let postSrc=_URL+"user/";
let email = "";
let phone = "";
let introduction = "";



function initPage(){
	$.get(postSrc+"queryUserInfo.action",function (result) {
		email = result.userMail;
		phone = result.userPhone;
		introduction = result.userIntroduction;
		console.log(result);
		$("#count").html(result.userIntroduction.length);
		$("#userName").html(result.userName);
		$("#email").val(result.userMail);
		$("#phone").val(result.userPhone);
		$("#introduction").val(result.userIntroduction);
		$("#userPhoto").attr("src",_URL+"account/"+result.userPhotoName);
		$("#userPhoto2").attr("src",_URL+"account/"+result.userPhotoName);
    });
}

//更新用户信息
$("#updateUserInfo").click(function () {
	let formData = new FormData();
	if((email != $("#email").val())||(phone != $("#phone").val())||(introduction != $("#introduction").val())){
		if(($("#email").hasClass("is-invalid"))||($("#phone").hasClass("is-invalid"))||($("#introduction").hasClass("is-invalid"))){
			//none
		}else{
			formData.append("userMail",$("#email").val());
			formData.append("userPhone",$("#phone").val());
			formData.append("userIntroduction",$("#introduction").val());
			$.ajax({
		        url : postSrc+'updateUserInfo.action',
		        type : 'POST',
		        async : false,
		        data : formData,
		        processData : false,
		        contentType : false,
		        success:function (result) {
		        	console.log(result);
		        	alert("信息修改成功");
		        	$("#email").removeClass("is-valid");
		    		$("#email").removeClass("is-invalid");
		    		$("#phone").removeClass("is-valid");
		    		$("#phone").removeClass("is-invalid");
		    		$("#introduction").removeClass("is-valid");
		    		$("#introduction").removeClass("is-invalid");
		        }
			});
		}
	}
});

$('#introduction').bind('input propertychange',function(){
    $('#count').html($(this).val().length);
});


//更新用户头像
$("#userPhotoFile").change(function () {
	let form = new FormData();
    let reader = new FileReader();
    reader.readAsDataURL($(this)[0].files[0]);
    form.append("userPhoto",$(this)[0].files[0]);
    reader.onload = function () {
    	let photoSrc=this.result;
        $.ajax({
            url : postSrc+'uploadUserPhoto.action',
            type : 'POST',
            async : false,
            data : form,
            processData : false,
            contentType : false,
            success:function (result) {
            	if(result[0].result=="更新成功"){
            		alert("头像更新成功");
            		$("#userPhoto2").attr("src",photoSrc);
                    $("#userPhoto").attr("src", photoSrc);
            	}
            	else{
            		alert("头像更新失败");
            	}
            }
        });
    }
});

//更新用户密码
$("#updateUserPassword").click(function () {
	if($("#password-updatePwd").hasClass("is-valid")&&
	    $("#passwordConfirm-updatePwd").hasClass("is-valid")&&
	    $("#password-old").hasClass("is-valid")){
	    $.post(postSrc+"updatePassword.action",{"originalPWD":$("#password-old").val(),"newPWD":$("#password-updatePwd").val()},function (result) {
	    	console.log(result);
	    	if(result[0].result){
	    		alert("密码更改成功");
	    		$("#password-old").val("");
	    		$("#password-updatePwd").val("");
	    		$("#passwordConfirm-updatePwd").val("");
	    		$("#password-old").removeClass("is-valid");
	    		$("#password-old").removeClass("is-invalid");
	    		$("#password-updatePwd").removeClass("is-valid");
	    		$("#password-updatePwd").removeClass("is-invalid");
	    		$("#passwordConfirm-updatePwd").removeClass("is-valid");
	    		$("#passwordConfirm-updatePwd").removeClass("is-invalid");
	    	}
	    	else{
	    		$("#password-old").removeClass("is-valid");
	    		$("#password-old").addClass("is-invalid");
	    	}
	    });
	}
});

initPage();