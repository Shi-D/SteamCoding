let sendMessage = { filters:null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let userList;//表格中使用的列表
let organizationList;//下拉框的机构列表
let postSrc=_URL+"superAdmin/";
let postSrc2=_URL+"organization/";
let filter="";
let personType = 'admin'; //'admin'表示管理员管理界面，'teacher'表示教师管理界面，'student'表示教师管理界面
let userName="",
	userCode="",
	selectedOrganization='全部机构',
	selectedOrganizationForModify='杭州思帝姆教育科技有限公司',
	selectedOrganizationForAdmin='杭州思帝姆教育科技有限公司';
let selectedOrganizationId=1;



init();

/***************新增管理员******************/
//点击确认添加管理员
$("#addAdminCommit").click(function () {
	if($("#admin-name").val() == ""){
		$("#admin-name").addClass("is-invalid");
	}
	if($("#admin-name").hasClass("is-valid")){
		$.post(postSrc+"addAdminToOrganization.action",{adminName:$("#admin-name").val(),adminCode:$("#admin-code").val(),adminGender:$("input[name='admin-gender']:checked").val(),organizationId:selectedOrganizationId},function (result) {
			if(result[0].result == "添加成功"){
				alert("添加成功");
				$("#addAdminCancel").click();
				init();
				clearModel();
			}else{
				$("#addAdminCancel").click();
				alert("添加失败");
			}
	    });
	}
});
//点击取消添加管理员
$("#addAdminCancel").click(function(){
	console.log("dsf");
	selectedOrganizationForAdmin='杭州思帝姆教育科技有限公司';
    $("#choseOrganizationButtonForAdmin").html(selectedOrganizationForAdmin);
	selectedOrganizationId=1;
	clearModel();
});

/***************删除管理员******************/
//点击删除管理员
$("#delete-admin").click(function(){
	$("#deleteAdmin-AdminNames").html(getSelectUserNames());
});
//点击确认删除管理员
$("#sureToDelete").click(function(){
	$.post(postSrc+"deleteAdmin.action",{Ids:getSelectUserIds()},function(result){
		if(result[0].result == "删除成功"){
			alert("删除成功");
			$("#cancelToDelete").click();
			init();
		}
	});
});
//点击取消删除管理员
$("#cancelToDelete").click(function(){
	$("#deleteAdmin-AdminNames").html("");
});


/***************修改管理员******************/
//修改管理员模态框的显示
$("#modify-admin").click(function(){
	for(let i=0; i<userList.length; ++i){
		console.log(userList[i].userId);
		if(userList[i].userId == getSelectUserIds()){
			$("#admin-name-modify").val(userList[i].userName);
			$("#admin-code-modify").val(userList[i].userCode);
			selectedOrganizationForModify=userList[i].organizationName;
			selectedOrganizationId=userList[i].organizationId;
		    $("#choseOrganizationButtonForModify").html(selectedOrganizationForModify);
		    console.log(userList[i].userGender);
			if(userList[i].userGender == "男") $("input[name='admin-gender-modify']").get(0).checked=true; 
			else $("input[name='admin-gender-modify']").get(1).checked=true; 
			break;
		}
	}
})
//点击确认修改管理员
$("#modifyAdminCommit").click(function () {
	if($("#admin-name-modify").val() == ""){
		$("#admin-name-modify").addClass("is-invalid");
	}else{
		$("#admin-name-modify").addClass("is-valid");
	}
	if($("#admin-name-modify").hasClass("is-valid")){
		$.post(postSrc+"updateAdmin.action",{adminName:$("#admin-name-modify").val(),adminCode:$("#admin-code-modify").val(),adminGender:$("input[name='admin-gender-modify']:checked").val(),organizationId:selectedOrganizationId},function (result) {
			console.log(result);
			if(result[0].result == "修改成功"){
				$("#modifyAdminCancel").click();
				alert("修改成功");
				init();
				clearModel();
			}else{
				$("#modifyAdminCancel").click();
				alert("修改失败");
				clearModel();
			}
	    });
	}
});
//点击取消修改管理员
$("#modifyAdminCancel").click(function(){
	console.log("fndsjknijahji");
	selectedOrganizationForModify='杭州思帝姆教育科技有限公司';
    $("#choseOrganizationButtonForModify").html(selectedOrganizationForModify);
	selectedOrganizationId=1;
	clearModel();
});




/***************查询******************/
//获取查询的人员名称
$("#titleSearch").change(function () {
	userName = $("#titleSearch").val();
});
$("#titleSearchCode").change(function () {
	userCode = $("#titleSearchCode").val();
});
//点击查询
$("#query").click(function(){
    setQueryConditions();
});

/***************重置密码******************/
$("#sureToReset").click(function () {
    $.post(postSrc+"resetPWD.action",{ids:getSelectUserIds()},function(result){
        alert(result[0].result);
    });
    $("#CheckAll").prop("checked", false);
    $("[name=items]:checkbox").prop("checked", false);
    selectChanged();
});

/***************切换管理员和教师界面******************/
$("#admin").click(function () {
	personType="admin";
	selectedOrganization = "全部机构";
    $("#choseOrganizationButton").html("全部机构");
	document.getElementById("titleSearch").value="";
	document.getElementById("titleSearchCode").value="";
    $("#admin").addClass("active");
    $("#teacher").removeClass("active");
    $("#student").removeClass("active");
    $("#add-admin").css("display","inline");
    $("#modify-admin").css("display","inline");
    $("#delete-admin").css("display","inline");
    init();
});

$("#teacher").click(function () {
	personType="teacher";
	selectedOrganization = "全部机构";
    $("#choseOrganizationButton").html("全部机构");
	document.getElementById("titleSearch").value="";
	document.getElementById("titleSearchCode").value="";
    $("#admin").removeClass("active");
    $("#teacher").addClass("active");
    $("#student").removeClass("active");
    $("#add-admin").css("display","none");
    $("#modify-admin").css("display","none");
    $("#delete-admin").css("display","none");
    init();
});

$("#student").click(function () {
	personType="student";
	selectedOrganization = "全部机构";
    $("#choseOrganizationButton").html("全部机构");
	document.getElementById("titleSearch").value="";
	document.getElementById("titleSearchCode").value="";
    $("#admin").removeClass("active");
    $("#teacher").removeClass("active");
    $("#student").addClass("active");
    $("#add-admin").css("display","none");
    $("#modify-admin").css("display","none");
    $("#delete-admin").css("display","none");
    init();
});

/***************获取******************/
//获取选择的机构
$("#drapdown-menu").on("click", "a", function(){
    selectedOrganization = $(this).text();
    $("#choseOrganizationButton").html(selectedOrganization);
    setQueryConditions();
});
//获取选择的机构-添加管理员时
$("#drapdown-menu-addAdmin").on("click", "a", function(){
	selectedOrganizationForAdmin = $(this).text();
    $("#choseOrganizationButtonForAdmin").html(selectedOrganizationForAdmin);
    for(let i=0; i<organizationList.length; i++){
    	if(organizationList[i].organizationName==selectedOrganizationForAdmin){
    		selectedOrganizationId=organizationList[i].organizationId;
    		break;
    	}else{
    		selectedOrganizationId=1;
    	}
    }
});
//获取选择的机构-修改管理员时
$("#drapdown-menu-modifyAdmin").on("click", "a", function(){
	selectedOrganizationForModify = $(this).text();
    $("#choseOrganizationButtonForModify").html(selectedOrganizationForModify);
    for(let i=0; i<organizationList.length; i++){
    	if(organizationList[i].organizationName==selectedOrganizationForModify){
    		selectedOrganizationId=organizationList[i].organizationId;
    		break;
    	}else{
    		selectedOrganizationId=1;
    	}
    }
});


//获取选中的用户名称
function getSelectUserNames(){
    let names = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0;i<items.length;++i){
        if(items[i].checked===true){
            names += userList[i].userName+",";
        }
    }
    return names.substring(0,names.length-1);
}

//获取选中的用户code
function getSelectUserIds(){
    let ids = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0; i<items.length; ++i){
        if(items[i].checked===true){
        	ids += userList[i].userId+",";
        }
    }
    return ids.substring(0,ids.length-1);
}

//初始化
function init() {
	$("#CheckAll").prop("checked", false);
	$("#table-body").html("");
	$('#drapdown-menu').html("<a class='dropdown-item'>全部机构</a>");
	$('#drapdown-menu-addAdmin').html("");
	pageNo=1;
	filters="{}";
	if(personType=="admin"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryAdminByAdminName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}else if(personType=="teacher"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryTeachersByTeacherName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}else if(personType=="student"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryStudentsByStudentName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}
	
	$.post(postSrc2+"queryOrganization.action", function(result){
		organizationList=result;
		console.log(organizationList);
        $('#dropdownTitle').html(result[0].organizationName);
        for(let i=0; i<result.length; ++i){
            addmessage = '<a class="dropdown-item">' + result[i].organizationName + '</a>';
            $('#drapdown-menu').append(addmessage);
            $('#drapdown-menu-addAdmin').append(addmessage);
            $('#drapdown-menu-modifyAdmin').append(addmessage);
        }
    });
}
//将用户查询结果赋值给userList
function setList(result){
	if (result.length - 1 >= pageSize) {
		userList=result.slice(0,pageSize);
    }
    else {
    	userList=result.slice(0,result.length-1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    updateTable();
    updateFooter();
    selectChanged();
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
    for (let i = 0, index = firstIndex;i<userList.length; i++, index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(userList[i].userName));
        tr.append($("<td></td>").text(userList[i].userGender));
        tr.append($("<td></td>").text(userList[i].userCode));
        tr.append($("<td></td>").text(userList[i].creationTime.replace("T"," ")));
        tr.append($("<td></td>").text(userList[i].organizationName));
        
        $("#table-body").append(tr);
    }
}

function selectChanged(){
	//判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
    let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
    if(statue.checked)
        selectNum-=1;
    if(selectNum===0){
    	$("#modify-admin").attr("disabled", true).addClass("disabled");
    	$("#delete-admin").attr("disabled", true).addClass("disabled");
    	$("#resetPassword").attr("disabled", true).addClass("disabled");
    }
    else if(selectNum===1){
        $("#modify-admin").removeAttr("disabled").removeClass("disabled");
        $("#delete-admin").removeAttr("disabled").removeClass("disabled");
        $("#resetPassword").removeAttr("disabled").removeClass("disabled");
    }
    else{
    	$("#modify-admin").attr("disabled", true).addClass("disabled");
        $("#delete-admin").removeAttr("disabled").removeClass("disabled");
        $("#resetPassword").removeAttr("disabled").removeClass("disabled");
    }
}

//调用分页action
function getInfo(){
	$("#table-body").html("");
	if(personType=="admin"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryAdminByAdminName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}else if(personType=="teacher"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryTeachersByTeacherName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}else if(personType=="student"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryStudentsByStudentName.action",sendMessage,function(result){//需要修改action
	        setList(result);
	        console.log(result);
	    });
	}
}

//清空管理员模态框内的input
function clearModel(){
	$("#admin-name").val("");
	$("#admin-name").removeClass("is-invalid");
	$("#admin-name").removeClass("is-valid");
	$("#admin-code").val("");
	$("#admin-code").removeClass("is-invalid");
	$("#admin-code").removeClass("is-valid");
	$("input[name='admin-gender']").get(0).checked=true; 
	console.log("nannv");

	$("#admin-name-modify").val("");
	$("#admin-name-modify").removeClass("is-invalid");
	$("#admin-name-modify").removeClass("is-valid");
	$("input[name='admin-gender-modify']").get(0).checked=true; 
}


//修改级联查询条件
function updateCascadingQueryConditions(){
  filters = "";
  
  if(selectedOrganization === "全部机构" && userName !== ""){
      if(userCode === "") filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"userName\",\"op\":\"bw\", \"data\":\""+"%"+userName+"%\"}]}";
      else filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"userCode\",\"op\":\"bw\", \"data\":\"%"+userCode+"%\"}, "+"{\"field\": \"userName\",\"op\":\"bw\", \"data\":\"%"+userName+"%\"}]}";
  }
  else if(selectedOrganization === "全部机构"&&userName === ""){
	  if(userCode === "") filters = "{}";
	  else filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"userCode\",\"op\":\"bw\", \"data\":\""+"%"+userCode+"%\"}]}";
  }
  else if(selectedOrganization !== "全部机构"&&userName === ""){
	  if(userCode === "") filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"organizationName\",\"op\":\"bw\", \"data\":\"%"+selectedOrganization+"%\"}]}";
	  else filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"organizationName\",\"op\":\"bw\", \"data\":\"%"+selectedOrganization+"%\"}, "+"{\"field\": \"userCode\",\"op\":\"bw\", \"data\":\"%"+userCode+"%\"}]}";
  }
  else {
	  if(userCode === "") filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"organizationName\",\"op\":\"bw\", \"data\":\"%"+selectedOrganization+"%\"}, "+"{\"field\": \"userName\",\"op\":\"bw\", \"data\":\"%"+userName+"%\"}]}";
	  else filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"organizationName\",\"op\":\"bw\", \"data\":\"%"+selectedOrganization+"%\"}, "+"{\"field\": \"userName\",\"op\":\"bw\", \"data\":\"%"+userName+"%\"}, "+"{\"field\": \"userCode\",\"op\":\"bw\", \"data\":\"%"+userCode+"%\"}]}";
  }
}

function setQueryConditions(){
    pageSize = 20; //页面数据条数
    pageNo = 1; //页面
    currentPage = 1;
    userName = $("#titleSearch").val();
    userCode = $("#titleSearchCode").val();
    updateCascadingQueryConditions();
    $("#table-body").html("");
    if(personType=="admin"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryAdminByAdminName.action",sendMessage,function(result){
	        setList(result);
	    });
	}else if(personType=="teacher"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryTeachersByTeacherName.action",sendMessage,function(result){
	        setList(result);
	    });
	}else if(personType=="student"){
		sendMessage = {filters: filters, search: true, userType:personType, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	    $.post(postSrc+"queryStudentsByStudentName.action",sendMessage,function(result){
	        setList(result);
	    });
	}
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
}

