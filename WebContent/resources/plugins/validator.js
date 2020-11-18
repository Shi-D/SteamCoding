(function(){
  'use strict';
  
  $(document).ready(function(){

  	let form = $('.bootstrap-form');

  	// On form submit take action, like an AJAX call
    $(form).submit(function(e){

        if(this.checkValidity() == false) {
            $(this).addClass('was-validated');
            e.preventDefault();
            e.stopPropagation();
        }

    });

    // On every :input focusout validate if empty
    $(':input').blur(function(){
    	let fieldType = this.type;
    	let fieldName = this.name;
    	
    	switch(fieldName){
    	case 'introduction':
    		validateIntroduction($(this));
    		break;
		case 'phone':
            validatePhone($(this));
            break;
		case 'code':
			validateCode($(this));
			break;
		case 'name':
			validateName($(this));
			break;
		case 'password':
			validatePassword($(this));
			break;
		case 'passwordConfirm-signUp':
			validatePasswordConfirmSignUp($(this));
			break;
		case 'passwordConfirm-updatePwd':
			validatePasswordConfirmUpdatePwd($(this));
			break;
		case 'email':
			validateEmail($(this));
    		break;
		case 'cantbeNull':
			validateCantBeNull($(this));
    		break;
		case 'homeworkContent':
			validateHomeworkContent($(this));
    		break;
		case 'timeCantBeNull':
			validateTimeCantBeNull($(this));
			break;
		case 'number':
			validateNumber($(this));
			break;
		default:
    		break;
	}
});


	// On every :input focusin remove existing validation messages if any
    $(':input').click(function(){

    	$(this).removeClass('is-valid is-invalid');

	});

    // On every :input focusin remove existing validation messages if any
    $(':input').keydown(function(){

        $(this).removeClass('is-valid is-invalid');

    });

	// Reset form and remove validation messages
    $(':reset').click(function(){
        $(':input, :checked').removeClass('is-valid is-invalid');
    	$(form).removeClass('was-validated');
    });

  });

  	function validateIntroduction(thisObj){
  		let fieldValue = thisObj.val();
  		let pattern = /^.{0,150}$/;
  		
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
  	}
  	
  	function validateHomeworkContent(thisObj){
  		let fieldValue = thisObj.val();
  		let pattern = /^.{1,500}$/;
  		
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
  	}
  	 
  	function validatePhone(thisObj) {
  		let fieldValue = thisObj.val();
  		let pattern = /^1(3|4|5|7|8)\d{9}$/;
  		$(".phone-feedback").html("手机号码格式错误");
	
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
  			$(".getVerifiCode").removeAttr("disabled");
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    	$(".getVerifiCode").attr("disabled","disabled");
	    }
	}
  	
  	function validateCode(thisObj) {
  		let fieldValue = thisObj.val();
  		let pattern = /^\d{6}$/;
  		$(".code-feedback").html("请输入6位数字验证码");
  		
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
	}
  	
  	function validateName(thisObj){
  		let fieldValue = thisObj.val();
  		let pattern = /^[\u4e00-\u9fa5]{2,10}$/;
  		
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
  	}
  	
  	function validatePassword(thisObj) {
  		let fieldValue = thisObj.val();
  		let pattern = /^.{6,20}$/;
  		
  		if(pattern.test(fieldValue)){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
	}
  	
  	$("#password-signUp").bind("input",function(){
  		let fieldValue = $("#passwordConfirm-signUp").val();
  		let pattern = $("#password-signUp").val() ;
  		
  		if(fieldValue == pattern){
  			$("#passwordConfirm-signUp").addClass('is-valid');
	    } else {
	    	$("#passwordConfirm-signUp").addClass("is-invalid");
	    }
  	  });
  	
  	$("#password-updatePwd").bind("input",function(){
  		let fieldValue = $("#passwordConfirm-updatePwd").val();
  		let pattern = $("#password-updatePwd").val() ;
  		
  		if(fieldValue == pattern){
  			$("#passwordConfirm-updatePwd").addClass('is-valid');
	    } else {
	    	$("#passwordConfirm-updatePwd").addClass("is-invalid");
	    }
  	  });
  	
 
  	function validatePasswordConfirmSignUp(thisObj) {
  		let fieldValue = thisObj.val();
  		let pattern = $("#password-signUp").val() ;
  		console.log(pattern)
  		
  		if(fieldValue == pattern){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
	}
  	
  	function validatePasswordConfirmUpdatePwd(thisObj) {
  		let fieldValue = thisObj.val();
  		let pattern = $("#password-updatePwd").val() ;
  		console.log(pattern)
  		
  		if(fieldValue == pattern){
  			$(thisObj).addClass('is-valid');
	    } else {
	    	$(thisObj).addClass("is-invalid");
	    }
	}
  	
    // Validate Text and password
    function validateText(thisObj) {
        let fieldValue = thisObj.val();
        if(fieldValue.length > 1) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }

    // Validate Email
    function validateEmail(thisObj) {
        let fieldValue = thisObj.val();
        let pattern = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i;

        if(pattern.test(fieldValue)) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }

    // Validate CheckBox
    function validateCheckBox(thisObj) {
         
        if($(':checkbox:checked').length > 0) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }

    // Validate Select One Tag
    function validateSelectOne(thisObj) {

        let fieldValue = thisObj.val();
        
        if(fieldValue != null) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }

    // Validate Select Multiple Tag
    function validateSelectMultiple(thisObj) {

        let fieldValue = thisObj.val();
        
        if(fieldValue.length > 0) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }
    
    function validateCantBeNull(thisObj){
    	
    	let fieldValue = thisObj.val();
        
        if(fieldValue.length > 0) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }
    
    //只能是数字
    function validateNumber(thisObj) {
        let fieldValue = thisObj.val();
        let pattern = /^\d*\.{0,1}\d{0,1}$/;

        if(pattern.test(fieldValue) && fieldValue.length > 0) {
            $(thisObj).addClass('is-valid');
        } else {
            $(thisObj).addClass('is-invalid');
        }
    }
    
    $("#deadLine").blur(function(){
    	if($("#deadLine").val()!=="") {
    		$("#deadLine").removeClass('is-invalid');
    		$("#deadLine").addClass('is-valid');
    		
        } else {
        	$("#deadLine").removeClass('is-valid');
        	$("#deadLine").addClass('is-invalid');
        	
        }
    });
    $("#deadLine").change(function(){
    	if($("#deadLine").val()!=="") {
    		$("#deadLine").removeClass('is-invalid');
    		$("#deadLine").addClass('is-valid');
        } else {
        	$("#deadLine").removeClass('is-valid');
        	$("#deadLine").addClass('is-invalid');
        	
        }
    });
    
    $("#previousEndtime").blur(function(){
    	if($("#previousEndtime").val()!=="") {
    		$("#previousEndtime").removeClass('is-invalid');
    		$("#previousEndtime").addClass('is-valid');
    		
        } else {
        	$("#previousEndtime").removeClass('is-valid');
        	$("#previousEndtime").addClass('is-invalid');
        	
        }
    });
    $("#previousEndtime").change(function(){
    	if($("#previousEndtime").val()!=="") {
    		$("#previousEndtime").removeClass('is-invalid');
    		$("#previousEndtime").addClass('is-valid');
        } else {
        	$("#previousEndtime").removeClass('is-valid');
        	$("#previousEndtime").addClass('is-invalid');
        	
        }
    });
    
    $("#copyEndtime").blur(function(){
    	if($("#copyEndtime").val()!=="") {
    		$("#copyEndtime").removeClass('is-invalid');
    		$("#copyEndtime").addClass('is-valid');
    		
        } else {
        	$("#copyEndtime").removeClass('is-valid');
        	$("#copyEndtime").addClass('is-invalid');
        	
        }
    });
    $("#copyEndtime").change(function(){
    	if($("#copyEndtime").val()!=="") {
    		$("#copyEndtime").removeClass('is-invalid');
    		$("#copyEndtime").addClass('is-valid');
        } else {
        	$("#copyEndtime").removeClass('is-valid');
        	$("#copyEndtime").addClass('is-invalid');
        	
        }
    });

})();
