let sendMessage = { search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let formData = new FormData();
let list = [];
let postSrc = _URL+"course/";
let courseList;
let searchCourseName = "";
let myVideoList = [];
let otherVideoList = [];
init();

//初始化
function init() {
	$("#table-body").html("");
	pageNo=1;
    sendMessage = {search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"courseCreateTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"searchAllInfoCourses.action",sendMessage,function(result){
        setList(result);
    });
}

function setList(result){
	if (result.length - 1 >= pageSize) {
    	courseList=result.slice(0,pageSize);
    }
    else {
    	courseList=result.slice(0,result.length-1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    updateTable();
    updateFooter();
    selectChanged();
}

function setSelectedIndex(){
	if(selectItemIndex!=-1)
		$("input[name='items']").eq(selectItemIndex).prop("checked", true);
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
    for (let i = 0, index = firstIndex;i<courseList.length; i++, index++) {
    	console.log(courseList);
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(courseList[i].courseName));
        if(courseList[i].classSize == null)
        	tr.append($("<td></td>").text("0"));
        else
        	tr.append($("<td></td>").text(courseList[i].classSize));
        tr.append($("<td></td>").text(courseList[i].courseCreateTime.substring(0,19)));
        $("#table-body").append(tr);
    }
}

/******************修改课程**********************/
$("#modify-course").click(function () {
    $("#modifyCourseName").html(getSelectCourseName());
    $.post(postSrc+"getCourseInfo.action",{courseId:getSelectCourseId()},function (result) {
        $("#modifyNewCourseName").val(result[0].courseName);
        $("#modifyNewCourseIntroduction").val(result[0].courseIntroduction);
        $("#count_modify").html(result[0].courseIntroduction.length);
        $("#showModifyCover").attr("src",_URL+"account/"+result[0].courseFolderName+result[0].courseCover);
    });
});

$("#modifyCourseCommit").click(function () {
	let formData = new FormData();
	if($("#modifyNewCourseName").val() == "")
		$("#modifyNewCourseName").addClass("is-invalid");
	if(!$("#modifyNewCourseName").hasClass("is-invalid")&&(!$("#modifyNewCourseIntroduction").hasClass("is-invalid"))){
		formData.append("courseId",getSelectCourseId());
		formData.append("courseName",$("#modifyNewCourseName").val());
		formData.append("courseIntroduction",$("#modifyNewCourseIntroduction").val());
		if($("#uploadModifyCover")[0].files[0])
			formData.append("uploadFile",$("#uploadModifyCover")[0].files[0]);
		$.ajax({
		    url : postSrc+'updateCourseInfo.action',
		    type : 'POST',
		    async : false,
		    data : formData,
		    processData : false,
		    contentType : false,
		    success:function (result) {
		    	if(result[0].result == "修改成功"){
		    		alert(result[0].result);
		    		$("#modifyCourseCancel").click();
		    	}else
		    		alert("修改失败");
		    	getInfoCopy();
		    }
		});
	}
});

$("#modifyCourseCancel").click(function(){
	 $("#modifyNewCourseName").val("");
	 $("#modifyNewCourseName").removeClass("is-valid");
	 $("#modifyNewCourseName").removeClass("is-invalid");
     $("#modifyNewCourseIntroduction").val("");
     $("#modifyNewCourseIntroduction").removeClass("is-valid");
     $("#modifyNewCourseIntroduction").removeClass("is-invalid");
     $("#count_modify").html("0");
     $("#uploadModifyCover").val("");
     $("#showModifyCover").attr("src","/SteamCoding/resources/img/setCover.png");
});

/******************添加课程**********************/
$("#createCourseCommit").click(function(){
	if($("#createNewCourseName").val() == "")
		$("#createNewCourseName").addClass("is-invalid");
	if($("#createNewCourseName").hasClass("is-valid")&&(!$("#createNewCourseIntroduction").hasClass("is-invalid"))){
		let formData = new FormData();
		formData.append("courseName",$("#createNewCourseName").val());
	    formData.append("courseIntroduction",$("#createNewCourseIntroduction").val());
	    if($("#uploadNewCover")[0].files[0])
			formData.append("uploadFile",$("#uploadNewCover")[0].files[0]);
		
	    $.ajax({
	        url : postSrc+'uploadCourse.action',
	        type : 'POST',
	        async : false,
	        data : formData,
	        processData : false,
	        contentType : false,
	        success:function (result) {
	        	if(result[0].result == "添加成功"){
	        		alert("添加成功");
	        		$("#createCourseCancel").click();
	        	}else
	        		alert("添加失败");
	        	init();
	        }
	    });
	}
});

//重置表单
$("#createCourseCancel").click(function(){
	 $("#createNewCourseName").val("");
	 $("#createNewCourseName").removeClass("is-valid");
	 $("#createNewCourseName").removeClass("is-invalid");
     $("#createNewCourseIntroduction").val("");
     $("#createNewCourseIntroduction").removeClass("is-valid");
     $("#createNewCourseIntroduction").removeClass("is-invalid");
     $("#count_create").html("0");
     $("#uploadNewCover").val("");
     $("#showNewCover").attr("src","/SteamCoding/resources/img/setCover.png");
});


function changepic(obj,id) {
    var newsrc=getObjectURL(obj.files[0]);
    document.getElementById(id).src=newsrc;
}
//建立一個可存取到該file的url 
function getObjectURL(file) {
    var url = null ;
    // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

/******************删除课程**********************/
$("#delete-course").click(function (e) {
    $("#deleteCourse_courseName").html(getSelectCourseName());
});
$("#deleteCourseCommit").click(function (e) {
	let courseId=getSelectCourseId();
    $.post(postSrc+"deleteCourse.action",{courseIds:courseId},function(result){
        init();
    });
});
function getCourses(){
    $.post(postSrc+"searchAllInfoCourses.action",function (result) {
    });
}

/******************更新章节内容**********************/
$("#update-course").click(function(){
	window.open("addChapter.jsp?courseId="+getSelectCourseId()+"&courseName="+getSelectCourseName());
});

/******************查看视频库**********************/
$("#video-lib").click(function(){
	getVideoList();
});

function getVideoList(){
	$.post(postSrc+"queryAllVideo.action",function(result){
		$("#my-video-list").html("");
		$("#other-video-list").html("");
		myVideoList = result[0].message[0];
		otherVideoList = result[0].message[1];
		if((myVideoList.length == 0) && ($("#my-video-tab").hasClass('active'))){
			$("#video-play").attr('src', "");
			$("#deleteVideoCommit").attr("disabled","disabled");
		}
		else if((myVideoList.length != 0) && ($("#my-video-tab").hasClass('active')))
			$("#deleteVideoCommit").removeAttr("disabled");
		
		if((otherVideoList.length == 0) && ($("#other-video-tab").hasClass('active')))
			$("#video-play").attr('src', "");
		
		for(i = 0;i < myVideoList.length;i++){
			if(i == 0){
				$("#my-video-list").append("<li class='list-group-item active' id='"+myVideoList[i].videoId+"' value='"+myVideoList[i].videoLink+"'>"+myVideoList[i].videoName+"</li>");
				$("#video-play").attr('src', _URL + myVideoList[i].videoLink);
			}
			else{
				$("#my-video-list").append("<li class='list-group-item' id='"+myVideoList[i].videoId+"' value='"+myVideoList[i].videoLink+"'>"+myVideoList[i].videoName+"</li>");
			}
		}
		for(i = 0;i < otherVideoList.length;i++){
			if(i == 0){
				$("#other-video-list").append("<li class='list-group-item active' value='"+otherVideoList[i].videoLink+"'>"+otherVideoList[i].videoName+"("+otherVideoList[i].userName+")</li>");
			}
			else{
				$("#other-video-list").append("<li class='list-group-item' value='"+otherVideoList[i].videoLink+"'>"+otherVideoList[i].videoName+"("+otherVideoList[i].userName+")</li>");
			}
		}
	});
}

$(document).on("click","#my-video-list li",function(){
	$(this).siblings('li').removeClass('active');  // 删除其兄弟元素的样式
	$(this).addClass('active');                    // 为点击元素添加类名
	$("#video-play").attr('src', _URL + $(this)[0].attributes.value.nodeValue);
});

$(document).on("click","#other-video-list li",function(){
	$(this).siblings('li').removeClass('active');  // 删除其兄弟元素的样式
	$(this).addClass('active');                    // 为点击元素添加类名
	$("#video-play").attr('src', _URL + $(this)[0].attributes.value.nodeValue);
});

$("#deleteVideoCommit").click(function(){
	let videoIds = myVideoList[$("#my-video-list .active").index()].videoId;
	console.log(videoIds);
	$.post(postSrc+"deleteVideo.action",{"videoIds":videoIds},function(result){
		if(result[0].result){
			getVideoList();
		}
		else{
			alert("视频删除失败，请联系维护人员");
		}
	});
});

$("#other-video-tab").click(function(){
	$("#deleteVideoCommit").attr("disabled","disabled");
	if(otherVideoList.length == 0)
		$("#video-play").attr('src', "");
	else
		$("#video-play").attr('src', _URL + otherVideoList[$("#other-video-list .active").index()].videoLink);
});
$("#my-video-tab").click(function(){
	if(myVideoList.length != 0)
		$("#deleteVideoCommit").removeAttr("disabled");
	if(myVideoList.length == 0)
		$("#video-play").attr('src', "");
	else
		$("#video-play").attr('src', _URL + myVideoList[$("#my-video-list .active").index()].videoLink);
});

//获取选中的课程ID
function getSelectCourseId(){
    let courseIds="";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0; i<items.length; ++i){
        //添加选中的课程
        if(items[i].checked===true){
        	selectItemIndex=i;
            courseIds += courseList[i].courseId+",";
        }
    }
    return courseIds.substring(0,courseIds.length-1);
}

//获取当前页的内容(适用于分页查询)
function getInfo(){
	$("#table-body").html("");
	sendMessage = {courseName:searchCourseName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"courseCreateTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoCoursesByCourseName.action",sendMessage,function(result){
        if (result.length - 1 >= pageSize) {
        	courseList=result.slice(0,pageSize);
        }
        else {
        	courseList=result.slice(0,result.length-1);
        }
        updateTable();
        updateFooter();
        selectChanged();
    });
}

//获取当前页面的内容（不适用于分页）
function getInfoCopy(){
	$("#table-body").html("");
	sendMessage = {courseName:searchCourseName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"courseCreateTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoCoursesByCourseName.action",sendMessage,function(result){
        if (result.length - 1 >= pageSize) {
        	courseList=result.slice(0,pageSize);
        }
        else {
        	courseList=result.slice(0,result.length-1);
        }
        updateTable();
        updateFooter();
        setSelectedIndex();//多了此处，还原选中的课程
        selectChanged();
    });
}

//获取选中的课程名称
function getSelectCourseName(){
    let courseNames="";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0;i<items.length;++i){
        //添加选中的课程
        if(items[i].checked===true){
            courseNames +=courseList[i].courseName+",";
        }
    }
    return courseNames.substring(0,courseNames.length-1);
}
//查询课程
$("#query").click(function (e) {
	searchCourseName=$("#titleSearch").val();
	pageNo=1;//重置当前页
    pageSize = 20; //页面数据条数
    currentPage=1;//重置当前页数
	$("#table-body").html("");
    sendMessage = {courseName:searchCourseName, search: true, pageNo: pageNo, pageSize: pageSize,  sorters: "[{\"property\":\"courseCreateTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoCoursesByCourseName.action",sendMessage,function(result){
    	setList(result);
    });
});

function selectChanged(){
	//判断check_item被选中的个数
    selectNum = $(".selectAll:checked").length;
    let statue =document.getElementById("CheckAll")//控制全选的复选框不纳入selectNum
    if(statue.checked)
        selectNum-=1;
    if(selectNum===0){
        $("#delete-course").attr("disabled", true).addClass("disabled");
        $("#update-course").attr("disabled", true).addClass("disabled");
        $("#modify-course").attr("disabled", true).addClass("disabled");
    }
    else if(selectNum===1){
        $("#delete-course").removeAttr("disabled").removeClass("disabled");
        $("#update-course").removeAttr("disabled").removeClass("disabled");
        $("#modify-course").removeAttr("disabled").removeClass("disabled");
    }
    else{
        $("#delete-course").removeAttr("disabled").removeClass("disabled");
        $("#update-course").attr("disabled", true).addClass("disabled");
        $("#modify-course").attr("disabled", true).addClass("disabled");
    }
}

$("#createNewCourseIntroduction").bind('input propertychange',function(){
    $("#count_create").html($(this).val().length);
});

$("#modifyNewCourseIntroduction").bind('input propertychange',function(){
    $("#count_modify").html($(this).val().length);
});
