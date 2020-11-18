let sendMessage = { filters:{}, search: false, pageNo: 1, pageSize: 20, sorters: {}}; //定义发送的数据
let postSrc=_URL+"parameter/";
let postSrc2=_URL+"organization/";

let defaultOrganizationId = "1";
let selectedOrganizationId = "1";
let selectedOrganization;

let prameterValue=1;

let parameterId;
let parameterList;
let organizationList;


init();


/**************添加参数**************/
//确认添加参数
$("#addParameterCommit").click(function(){
	if($("#parameter-name").val() && $("#parameter-comment").val() && $("#parameter-sequence").val() && $("#parameter-value").val()){
		$.post(postSrc+"addParameter.action", {
			"parameterName":$("#parameter-name").val(),
			"parameterValue":$("#parameter-value").val(),
			"parameterComment":$("#parameter-comment").val(),
			"parameterSequence":$("#parameter-sequence").val(),
			"parameterSerial":$("#parameter-serial").val(),
			"parameterUrl":$("#parameter-url").val(),
			"parameterTypeName":$("#parameter-typeName").val(),
			"parameterTypeCode":$("#parameter-typeCode").val(),
			}, function(result){
			if(result[0].result=="添加成功"){
				alert(result[0].result);
				$("#addParameterCancel").click();
				window.location.reload();
				$(':input').val("");
				$(':input').removeClass('is-valid is-invalid');
			}else{
				alert(result[0].result);
			}
		});
	}
});

//取消添加参数
$("#addParameterCancel").click(function(){
	$(':input').val("");
	$(':input').removeClass('is-valid is-invalid');
});


/**************删除参数**************/
//删除参数
$("#delete-parameter").click(function(){
	$("#deletePameter-ParameterNames").html(getSelectParameterNames());
})
//确认删除参数
$("#sureToDelete").click(function(){
	$.post(postSrc+"deleteParameters.action", {"parameterIds":getSelectParameterIds()}, function(result){
		alert(result[0].result);
		$("#cancelToDelete").click();
		window.location.reload();
	});
});


/**************修改参数**************/
//修改参数模态框的显示
$("#modify-parameter").click(function(){
	for(let i=0; i<parameterList.length; ++i){
		if(parameterList[i].parameterId==getSelectParameterIds()){
			$("#parameter-name-modify").val(parameterList[i].parameterName);
			$("#parameter-value-modify").val(parameterList[i].parameterValue),
			$("#parameter-comment-modify").val(parameterList[i].parameterComment);
			$("#parameter-sequence-modify").val(parameterList[i].parameterSequence);
			$("#parameter-serial-modify").val(parameterList[i].parameterSerialNo);
			$("#parameter-typeCode-modify").val(parameterList[i].parameterTypeCode);
			$("#parameter-typeName-modify").val(parameterList[i].parameterTypeName);
			$("#parameter-url-modify").val(parameterList[i].parameterUrl);
			break;
		}
	}
})


//确认修改参数
$("#modifyParameterCommit").click(function(){
	console.log($("#parameter-name-modify").val(),$("#parameter-comment-modify").val(),$("#parameter-sequence-modify").val());
	if($("#parameter-name-modify").val() && $("#parameter-comment-modify").val() && $("#parameter-sequence-modify").val()){
		$.post(postSrc+"updateParameter.action",{
			"parameterId":getSelectParameterIds(),
			"parameterName":$("#parameter-name-modify").val(),
			"parameterValue":$("#parameter-value-modify").val(),
			"parameterComment":$("#parameter-comment-modify").val(),
			"parameterSequence":$("#parameter-sequence-modify").val(),
			"parameterSerialNo":$("#parameter-serial-modify").val(),
			"parameterTypeCode":$("#parameter-typeCode-modify").val(),
			"parameterTypeName":$("#parameter-typeName-modify").val(),
			"parameterUrl":$("#parameter-url-modify").val()
			}, function(result){
			if(result[0].result=="修改成功"){
				alert(result[0].result);
				window.location.reload();
				$("#modifyParameterCancel").click();
				$(':input').val("");
				$(':input').removeClass('is-valid is-invalid');
			}else{
				alert("修改失败");
			}
		});
	}
});


$('#modifyParameterCancel').click(function(){
	$('#drapdown-menu-parameterValue').html("");
})

//调用分页action
function getInfo(){
	$("#table-body").html("");
	sendMessage = {filters: {}, search: false, pageNo: pageNo, pageSize: pageSize, sorters: {}};
	$.post(postSrc+"queryParameter.action",sendMessage,function(result){//需要修改action
        setList(result);
        console.log(result);
    });
}

function init(){
	$("#CheckAll").prop("checked", false);
	$("#table-body").html("");
	pageNo=1;
	$.post(postSrc+"queryParameter.action", sendMessage, function(result){
		setList(result);
		console.log("123frt");
	});
	console.log(parameterList);
	$.post(postSrc2+"queryOrganization.action", {}, function(result){
		organizationList=result;
        for(let i=0; i<result.length; ++i){
        	if(result[i].organizationId.toString() == defaultOrganizationId.toString()){
        		$('#dropdownTitle').html(result[i].organizationName);
        	}
            addmessage = '<a class="dropdown-item">' + result[i].organizationName + '</a>';
            $('#drapdown-menu-organization').append(addmessage);
        }
    });
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
    for (let i = 0, index = firstIndex;i<parameterList.length; i++, index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(parameterList[i].parameterName));
        tr.append($("<td></td>").text(parameterList[i].parameterValue));
        tr.append($("<td></td>").text(parameterList[i].parameterComment));
        tr.append($("<td></td>").text(parameterList[i].parameterSequence));
        
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
    	$("#delete-parameter").attr("disabled", true).addClass("disabled");
    	$("#modify-parameter").attr("disabled", true).addClass("disabled");
    }
    else if(selectNum===1){
    	$("#delete-parameter").removeAttr("disabled").removeClass("disabled");
        $("#modify-parameter").removeAttr("disabled").removeClass("disabled");
    }
    else{
        $("#delete-parameter").removeAttr("disabled").removeClass("disabled");
        $("#modify-parameter").attr("disabled", true).addClass("disabled");
    }
}

//获取选中的参数名称
function getSelectParameterNames(){
    let names = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0;i<items.length;++i){
        if(items[i].checked===true){
            names += parameterList[i].parameterName+",";
        }
    }
    return names.substring(0,names.length-1);
}

//获取选中的参数id
function getSelectParameterIds(){
    let ids = "";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0; i<items.length; ++i){
        if(items[i].checked===true){
        	ids += parameterList[i].parameterId+",";
        }
    }
    return ids.substring(0,ids.length-1);
}
//将用户查询结果赋值给parameterList
function setList(result){
	if (result.length - 1 >= pageSize) {
		parameterList=result.slice(0,pageSize);
    }
    else {
    	parameterList=result.slice(0,result.length-1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    updateTable();
    updateFooter();
    selectChanged();
}

function setQueryConditions(){
    pageSize = 20; //页面数据条数
    pageNo = 1; //页面
    currentPage = 1;
    sendMessage = {filters: {}, search: false, pageNo: pageNo, pageSize: pageSize, sorters: {}};
    
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
}