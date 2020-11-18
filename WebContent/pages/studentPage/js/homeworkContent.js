let postSrc=_URL+"homework/";
let homeworkId = "";
init();
function init(){
	
	let attStr=window.location.href.split("&");
	homeworkId = attStr[1];
	
	$.post(postSrc+"getDetailHomework.action",{homeworkId:attStr[1]},function(message){
		let homeworkContent="";
		console.log(message[0]);
		homeworkContent = homeworkContent + 
		'<h2><span class="font-weight-homework">作业名称：</span>'+message[0].result.homeworkName+'</h2>'+
		'<p><span class="font-weight-homework">作业内容：</span>'+message[0].result.homeworkContent+'</p>'+
		'<p><span class="font-weight-homework">开始时间：</span><span class="timeStyle">'+message[0].result.creationTime.replace("T", " ")+'</span>'+
		'<p><span class="font-weight-homework">截止时间：</span><span class="timeStyle">'+message[0].result.deadline.replace("T", " ")+'</span></div>';
		
		if(message[0].result.sourceLink === null){
			homeworkContent = homeworkContent+'<p><span class="font-weight-homework">教师附件：</span><span>无</span></p>';
		}
		else{
			homeworkContent = homeworkContent+'<p><span class="font-weight-homework">教师附件：</span><a style="color:#007BFF;" href="'+_URL+message[0].result.sourceLink+'" >点击下载</a></p>';
		}
		console.log(message[0].submitable);
		if(message[0].submitable){//可提交作业
			$("#homeworkContent").append(homeworkContent);
				$.post(postSrc+"queryCoursework.action",function(result){
					let submitHomewrok = "";
					let hasSubmittedHomewrok = "";
					let workID = message[0].workID;
					if(message[0].sourceLink && message[0].workLink){
						hasSubmittedHomewrok = hasSubmittedHomewrok + '<hr><div class="hasSubmittedContent">'
						+'<p><span class="font-weight-homework">已提交的作业附件：</span><a style="color:#007BFF;" href="'+_URL+message[0].sourceLink+'" >点击下载</a></p>'
						+'<p><span class="font-weight-homework">已提交的在线作品：</span><a style="color:#007BFF;" href="../../../build/tmp/index.html'+ '?id=' + message[0].workId +'">'+message[0].workName+'</a></p></div>';
						$(".table-part").append(hasSubmittedHomewrok);
					}else if(!message[0].sourceLink && message[0].workLink){
						hasSubmittedHomewrok = hasSubmittedHomewrok + '<hr><div class="hasSubmittedContent">'
						+'<p><span class="font-weight-homework">已提交的作业附件：</span><a>无</a></p>'
						+'<p><span class="font-weight-homework">已提交的在线作品：</span><a style="color:#007BFF;" href="../../../build/tmp/index.html'+ '?id=' + message[0].workId +'">'+message[0].workName+'</a></p></div>';
						$(".table-part").append(hasSubmittedHomewrok);

					}else if(message[0].sourceLink && !message[0].workLink){
						hasSubmittedHomewrok = hasSubmittedHomewrok + '<hr><div class="hasSubmittedContent">'
						+'<p><span class="font-weight-homework">已提交的作业附件：</span><a style="color:#007BFF;" href="'+_URL+message[0].sourceLink+'" >点击下载</a></p>'
						+'<p><span class="font-weight-homework">已提交的在线作品：</span><a>无</a></p>'
						$(".table-part").append(hasSubmittedHomewrok);
					}

					submitHomewrok = submitHomewrok + '<hr><div class="homeworkContent">'
					+'<p><span class="font-weight-homework">选择文件：</span><input id="uploadHomework" type="file"></p>'
					+'<div class="form-group">'
					+'<label class="font-weight-homework"><span>选择作品：</span></label>'
            		+'<button class="btn dropdown-toggle " type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择作品</button>'
					+'<div class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="height:300px;overflow:scroll" >'	
					+'<table class="table table-hover" id="workList"></table></div>'  	
					+'<button type="button" id="submit" class="btn btn-success submitHomework">提交</button></div></div>';	
					$(".table-part").append(submitHomewrok);

					for(let i=0;i<result.length;i++){
						let tr=$("<tr></tr>");
						tr.append($("<td><input type='radio' id="+result[i].workId+" name='workList' value="+result[i].workName+"></td>"));
						tr.append($("<td></td>").text(result[i].workName));
						$("#workList").append(tr);
					}
					$("#workList").on("change","input",function(){
						$("#dropdownMenuButton").text(getWorkName());
					});
				});	
		}
		else{
			if(message[0].sourceLink && message[0].workLink){
				homeworkContent = homeworkContent 
				+'<p><span class="font-weight-homework">已提交的作业附件：</span><a style="color:#007BFF;" href="'+_URL+message[0].sourceLink+'" >点击下载</a></p>'
				+'<p><span class="font-weight-homework">已提交的在线作品：</span><a style="color:#007BFF;" href="../../../build/tmp/index.html'+ '?id=' + message[0].workId +'">'+message[0].workName+'</a></p>';
			}else if(!message[0].sourceLink && message[0].workLink){
				homeworkContent = homeworkContent  
				+'<p><span class="font-weight-homework">已提交的作业附件：</span><a>无</a></p>'
				+'<p><span class="font-weight-homework">已提交的在线作品：</span><a style="color:#007BFF;" href="../../../build/tmp/index.html'+ '?id=' + message[0].workId +'">'+message[0].workName+'</a></p>';
				
			}else if(message[0].sourceLink && !message[0].workLink){
				homeworkContent = homeworkContent 
				+'<p><span class="font-weight-homework">已提交的作业附件：</span><a style="color:#007BFF;" href="'+_URL+message[0].sourceLink+'" >点击下载</a></p>'
				+'<p><span class="font-weight-homework">已提交的在线作品：</span><a>无</a></p>'
				
			}
			if(message[0].score){//教师评语
				homeworkContent = homeworkContent+
				'<p><span class="font-weight-homework">教师评语：</span>'+message[0].evaluation+'</p>'+
				'<p><span class="font-weight-homework">作业得分：</span><span class="timeStyle">'+message[0].score+'</span></p>';
			}
			$("#homeworkContent").append(homeworkContent);
		}
	});
}

//获取作品的名称
function getWorkName(){
	let items = document.getElementById("workList").getElementsByTagName("input");
	for(let i=0; i<items.length; ++i){
	    //添加选中的班级
	    if(items[i].checked===true){
	    	return items[i].value; 
	    }
	}
}


$(".table-part").on("click", ".submitHomework", function(){
	formData = new FormData();
	if($("#uploadHomework")[0].files[0]||$("input[name='workList']:checked").length){
		formData.append("homeworkId",homeworkId);
		
		if($("#uploadHomework")[0].files[0]){
			formData.append("homeworkFile",$("#uploadHomework")[0].files[0]);
			formData.append("fileName",$("#uploadHomework")[0].files[0].name);			
		}
		
		if($("input[name='workList']:checked").length)
			formData.append("workId",$("input[name='workList']:checked")[0].id);
		
		$.ajax({
	    url : postSrc+'submitHomework.action',
	    type : 'POST',
	    async : false,
	    data : formData,
	    processData : false,
	    contentType : false,
	    success : function(result) {
	    	alert(result[0].result);
	    	window.location.href='studentHomeWorkmanagement.jsp';
	    },
	    error:function(e){
	 		   alert("上传失败!")
	 	}
	});
	}
	else{
		alert("请选择文件或作品!")
	}
	
})



