let sendMessage = { filters:null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let organizationList;//表格中使用的列表
let postSrc=_URL+"organization/";
let filters="";
let selectedOrganization="";

init();

/***************添加机构******************/
//点击确认添加机构
$("#addOrganizationCommit").click(function () {
	if($("#addOrganizationName").val() == ""){
		$("#addOrganizationName").addClass("is-invalid");
	}
	if($("#addOrganizationSname").val() == ""){
		$("#addOrganizationSname").addClass("is-invalid");
	}
	if($("#addOrganizationName").hasClass("is-valid") && $("#addOrganizationSname").hasClass("is-valid")){
		$.post(postSrc+"addOrganization.action",{organizationName:$("#addOrganizationName").val(), organizationSname:$("#addOrganizationSname").val()},function (result) {
			console.log(result);
			if(result[0].result == "添加机构成功"){
				$("#addOrganizationCancel").click();
				alert("添加机构成功");
				init();
			}else{
				$("#addOrganizationCancel").click();
				alert("添加机构失败");
			}
	    });
	}
});
//点击取消添加机构
$("#addOrganizationCancel").click(function(){
	$("#addOrganizationName").val("");
	$("#addOrganizationSname").val("");
	$("#addOrganizationName").removeClass("is-invalid");
	$("#addOrganizationName").removeClass("is-valid");
	$("#addOrganizationSname").removeClass("is-invalid");
	$("#addOrganizationSname").removeClass("is-valid");
});

/***************删除机构******************/
//点击删除机构
$("#delete-organization").click(function(){
	$("#deleteOrganization-OrganizationNames").html(getSelectOrganizationNames());
});
//点击确认删除机构
$("#deleteOrganizationCommit").click(function(){
	$.post(postSrc+"deleteOrganizationByOrganizationId.action",{organizationIds:getSelectOrganizationIds()},function(result){
		if(result[0].result == "删除成功"){
			init();
			$("#deleteOrganizationCancel").click();
			alert("删除成功");
		}else{
			$("#deleteOrganizationCancel").click();
			alert("删除失败，请先删除机构下的用户！");
		}
	});
});
//点击取消删除机构
$("#addOrganizationCancel").click(function(){
	$("#deleteOrganization-OrganizationNames").html("");
});

/***************修改机构******************/
//修改机构模态框的显示
$("#modify-organization").click(function(){
	for(let i=0; i<organizationList.length; ++i){
		if(organizationList[i].organizationId==getSelectOrganizationIds()){
			$("#modifyOrganizationName").val(organizationList[i].organizationName);
			$("#modifyOrganizationSname").val(organizationList[i].organizationSname);
			break;
		}
	}
})
//点击确认修改机构
$("#modifyOrganizationCommit").click(function () {
	if($("#modifyOrganizationName").val() == ""){
		$("#modifyOrganizationName").addClass("is-invalid");
	}else{
		$("#modifyOrganizationName").addClass("is-valid");
	}
	if($("#modifyOrganizationSname").val() == ""){
		$("#modifyOrganizationSname").addClass("is-invalid");
	}else{
		$("#modifyOrganizationSname").addClass("is-valid");
	}
	if($("#modifyOrganizationName").hasClass("is-valid") && $("#modifyOrganizationSname").hasClass("is-valid")){
		$.post(postSrc+"updateOrganization.action",{organizationName:$("#modifyOrganizationName").val(), organizationSname:$("#modifyOrganizationSname").val(), organizationId:getSelectOrganizationIds()},function (result) {
			console.log(result);
			if(result[0].result == "修改成功"){
				$("#modifyOrganizationCancel").click();
				alert("修改成功");
				init();
			}else{
				$("#modifyOrganizationCancel").click();
				alert("修改失败");	
			}
	    });
	}
});
//点击取消修改机构
$("#modifyOrganizationCancel").click(function(){
	$("#modifyOrganizationName").val("");
	$("#modifyOrganizationSname").val("");
	$("#modifyOrganizationName").removeClass("is-invalid");
	$("#modifyOrganizationName").removeClass("is-valid");
	$("#modifyOrganizationSname").removeClass("is-invalid");
	$("#modifyOrganizationSname").removeClass("is-valid");
});


/***************查询******************/
$("#titleSearch").change(function(){
	selectedOrganization=$(this).val();
});

//点击查询
$("#query").click(function(){
    setQueryConditions();
});




//获取选中的机构名称
function getSelectOrganizationNames(){
    let names = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0;i<items.length;++i){
        if(items[i].checked===true){
            names += organizationList[i].organizationName+",";
        }
    }
    return names.substring(0,names.length-1);
}

//获取选中的机构ID
function getSelectOrganizationIds(){
    let ids = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0; i<items.length; ++i){
        if(items[i].checked===true){
        	ids += organizationList[i].organizationId+",";
        }
    }
    return ids.substring(0,ids.length-1);
}

//初始化
function init() {
	$("#CheckAll").prop("checked", false);
	$("#table-body").html("");
	pageNo=1;
	filters="{}";
    sendMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
    $.post(postSrc+"searchAllOrganization.action",sendMessage,function(result){//需要修改action
        setList(result);
        console.log(result);
    });
}

//将班级查询结果赋值给organizationList
function setList(result){
	if (result.length - 1 >= pageSize) {
		organizationList=result.slice(0,pageSize);
    }
    else {
    	organizationList=result.slice(0,result.length-1);
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
    for (let i = 0, index = firstIndex;i<organizationList.length; i++, index++) {
        let tr = $("<tr></tr>"); 
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(organizationList[i].organizationName));
        tr.append($("<td></td>").text(organizationList[i].organizationSname));
        tr.append($("<td></td>").text(organizationList[i].adminName));
        tr.append($("<td></td>").text(organizationList[i].creationTime.replace("T", " ")));
        $("#table-body").append(tr);
    }
}

//调用分页action
function getInfo(){
	$("#table-body").html("");
	sendMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
	$.post(postSrc+"searchAllOrganization.action",sendMessage,function(result){//需要修改action
        setList(result);
        console.log(result);
    });
}

function selectChanged(){
    //判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
	let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
	if(statue.checked)
		selectNum-=1;
	    if(selectNum===0){
	    $("#add-organization").removeAttr("disabled").removeClass("disabled");
	    $("#modify-organization").attr("disabled", true).addClass("disabled");
	    $("#delete-organization").attr("disabled", true).addClass("disabled");
	}else if(selectNum===1){
	    $("#add-organization").removeAttr("disabled").removeClass("disabled");
	    $("#modify-organization").removeAttr("disabled").removeClass("disabled");
        $("#delete-organization").removeAttr("disabled").removeClass("disabled");
	}
	else{
		$("#add-organization").removeAttr("disabled").removeClass("disabled");
		$("#modify-organization").attr("disabled", true).addClass("disabled");
	    $("#delete-organization").removeAttr("disabled").removeClass("disabled");
	}
}

//修改级联查询条件
function updateCascadingQueryConditions(){
  filters = "{}";
  
  if(searchOrganization == ""){
      filters = "{}";
  }
  else {
      filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"organizationName\",\"op\":\"bw\", \"data\":\"%"+searchOrganization+"%\"}]}";
  }
}

function setQueryConditions(){
    pageSize = 20; //页面数据条数
    pageNo = 1; //页面
    currentPage = 1;
    searchOrganization = $("#titleSearch").val();
    updateCascadingQueryConditions();
    sendMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
    
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
}