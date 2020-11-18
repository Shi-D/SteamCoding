let sendMessage = { search: null, pageNo: null, pageSize: null, sorters: null, filters: null}; //定义发送的数据
let homeworkList;//表格中使用的列表
let postSrc=_URL+"homework/";
let homeworkName = "";
let searchClassName = "所有班级";
let toDate = "";
init();

$(function(){
	var date_now = new Date();
	var year = date_now.getFullYear();
	var month = date_now.getMonth()+1 < 10 ? "0"+(date_now.getMonth()+1) : (date_now.getMonth()+1);
	var date = date_now.getDate() < 10 ? "0"+date_now.getDate() : date_now.getDate();
	$('.form_date').datetimepicker('setStartDate', year+"-"+month+"-"+date);
	console.log(year+"-"+month+"-"+date);
})

function fileSelectedModify() {
    var file = document.getElementById('modifyHomeworkFile').files[0];
    if (file) { 
       $("#modifyFileName").text(file.name);
    }
}

function fileSelected() {
    var file = document.getElementById('homeworkFile').files[0];
    if (file) {
        $("#fileName").text(file.name);
    }
}

function fileSelectedCopy() {
    var file = document.getElementById('copyHomeworkFile').files[0];
    if (file) { 
       $("#copyFileName").text(file.name);
    }
}

$('#homeworkContent').bind('input propertychange',function(){
    $('#count').html($(this).val().length);
});
$('#previousHomeworkContent').bind('input propertychange',function(){
    $('#count').html($(this).val().length);
});
$('#copyHomeworkContent').bind('input propertychange',function(){
    $('#count').html($(this).val().length);
});

$("#removeCopyHomeworkFile").click(function(){
	$("#copyFileName").text("");
	$("#copyHomeworkFile").val("");
});

$("#removeModifyHomeworkFile").click(function(){
	$("#modifyFileName").text("");
	$("#modifyHomeworkFile").val("");
});

/******************复制作业**********************/
$('#copyHomework-modal').on('shown.bs.modal', function () {
	$("#selectNameforCopy").html(getHomeworkNames());
	let index = getHomeworkIndex();
	$("#copyHomeworkName").val(homeworkList[index].homeworkName);
	$("#copyHomeworkContent").val(homeworkList[index].homeworkContent);
	$("#count_copy").html(homeworkList[index].homeworkContent.length);
	$("#copyClass").val(homeworkList[index].classesName);
	$("#copyEndtime").val(homeworkList[index].deadline.split("T")[0]);
	$("#copyHomeworkFile").val("");
	$("#copyFileName").text(homeworkList[index].fileName);
	$(':input').removeClass('is-valid is-invalid');
	
	$.post(postSrc+"queryClassesByUserId.action",function(result){
		$("#modal-context-list-copy").html("");
		let checkedClass=homeworkList[index].classId.split(",");
		for(let i=0;i<result.length;++i){
			let tr=$("<tr></tr>");
			if(checkedClass.indexOf(result[i].classId.toString()) != -1)
				tr.append($("<td><input type='checkbox' id="+result[i].classId+" name='classItem' value="+result[i].className+" checked></td>"));
			else
				tr.append($("<td><input type='checkbox' id="+result[i].classId+" name='classItem' value="+result[i].className+"></td>"));	
			tr.append($("<td></td>").text(result[i].className));
			$("#modal-context-list-copy").append(tr);
		}
		
		let classItems=getModalClassName("modal-context-list-copy").split(",");
		$("#copyClass").html("");
		for(let i=0;i<classItems.length;++i){
			$("#copyClass").append($("<button type='button' class='btn btn-outline-primary' disabled></button>").text(classItems[i]));	
		}
	});
});

$("#modal-context-list-copy").on("change","input",function(){
	let classItems=getModalClassName("modal-context-list-copy").split(",");
	$("#copyClass").html("");
	if(classItems[0]!=="")
	for(let i=0;i<classItems.length;++i){
		$("#copyClass").append($("<button type='button' class='btn btn-outline-primary' disabled></button>").text(classItems[i]));	
	}
	else{
		$("#copyClass").append($("<button type='button' class='btn btn-outline-secondary' disabled></button>").text("暂无选中班级"));
	}
});

$("#copyHomeworkCommit").click(function(){
	if($("#copyHomeworkName").val()=="")
		$("#copyHomeworkName").hasClass("is-invalid");
	else if($("#copyHomeworkContent").val()=="")
		$("#copyHomeworkContent").hasClass("is-invalid");
	else if(getModalClassId("modal-context-list-copy")=="")
		$("#modal-context-list-copy").hasClass("is-invalid");
	else if($("#copyEndtime").val()=="")
		$("#copyEndtime").hasClass("is-invalid");
	else{
		formData = new FormData();
		
		formData.append("homeworkName",$("#copyHomeworkName").val());
		formData.append("homeworkContent",$("#copyHomeworkContent").val());
		formData.append("classIds",getModalClassId("modal-context-list-copy"));
		formData.append("deadline",$("#copyEndtime").val());
		if($("#copyHomeworkFile")[0].files[0]){
			formData.append("homeworkFile",$("#copyHomeworkFile")[0].files[0]);
			formData.append("fileName",$("#copyHomeworkFile")[0].files[0].name);
		}
		else if($("#copyFileName").text() != ""){
			console.log(homeworkList[getHomeworkIndex()]);
			formData.append("copyHomeworkId",homeworkList[getHomeworkIndex()].homeworkId);
		}
	
		$.ajax({
	        url : postSrc+'addHomework.action',
	        type : 'POST',
	        async : false,
	        data : formData,
	        processData : false,
	        contentType : false,
	        success : function(result) {
	        	alert(result[0].result);
	        	$("#copyHomework-modal").modal('hide');
	        	init();
	        }
	    });
	}
});


/******************删除作业**********************/
$("#deleteHomework").click(function(){
	$("#deleteHomework_homeworkName").html(getHomeworkNames());
});

$("#deleteHomeworkCommit").click(function(){
	$.post(postSrc+"deleteHomework.action",{homeworkIds:getHomeworkIds()},function(result){
		init();
	});
});


/******************修改作业**********************/
//查询作业批改
$('#modifyHomework-modal').on('shown.bs.modal', function () {
	$("#selectNameforModify").html(getHomeworkNames());
	let index = getHomeworkIndex();
	$("#previousHomeworkName").val(homeworkList[index].homeworkName);
	$("#previousHomeworkContent").val(homeworkList[index].homeworkContent);
	$("#count_modify").html(homeworkList[index].homeworkContent.length);
	$("#previousClass").val(homeworkList[index].classesName);
	$("#previousEndtime").val(homeworkList[index].deadline.split("T")[0]);
	$("#modifyHomeworkFile").val("");
	$("#modifyFileName").text(homeworkList[index].fileName);
	$(':input').removeClass('is-valid is-invalid');
	
	$.post(postSrc+"queryClassesByUserId.action",function(result){
		$("#modal-context-list-modify").html("");
		let checkedClass=homeworkList[index].classId.split(",");
		for(let i=0;i<result.length;++i){
			let tr=$("<tr></tr>");
			if(checkedClass.indexOf(result[i].classId.toString()) != -1)
				tr.append($("<td><input type='checkbox' id="+result[i].classId+" name='classItem' value="+result[i].className+" checked></td>"));
			else
				tr.append($("<td><input type='checkbox' id="+result[i].classId+" name='classItem' value="+result[i].className+"></td>"));	
			tr.append($("<td></td>").text(result[i].className));
			$("#modal-context-list-modify").append(tr);
		}
		
		let classItems=getModalClassName("modal-context-list-modify").split(",");
		$("#previousClass").html("");
		for(let i=0;i<classItems.length;++i){
			$("#previousClass").append($("<button type='button' class='btn btn-outline-primary' disabled></button>").text(classItems[i]));	
		}
	});
});

$("#modal-context-list-modify").on("change","input",function(){
	let classItems=getModalClassName("modal-context-list-modify").split(",");
	console.log(classItems);
	$("#previousClass").html("");
	if(classItems[0]!=="")
	for(let i=0;i<classItems.length;++i){
		$("#previousClass").append($("<button type='button' class='btn btn-outline-primary' disabled></button>").text(classItems[i]));	
	}
	else{
		$("#previousClass").append($("<button type='button' class='btn btn-outline-secondary' disabled></button>").text("暂无选中班级"));
	}
});

//数组减法
var subSet = function(arr1, arr2) {
    var arr = [];
    for(var i=0;i<arr1.length;i++){
        if(arr2.indexOf(arr1[i]) < 0){
            arr.push(arr1[i]);
        }
    }
    return arr;
};

$("#modifyHomeworkCommit").click(function(){
	let oldClassIds = homeworkList[getHomeworkIndex()].classId;
	let newClassIds = getModalClassId("modal-context-list-modify");
	if($("#previousHomeworkName").val()=="")
		$("#previousHomeworkName").hasClass("is-invalid");
	else if($("#previousHomeworkContent").val()=="")
		$("#previousHomeworkContent").hasClass("is-invalid");
	else if(getModalClassId("modal-context-list-modify")=="")
		$("#modal-context-list-modify").hasClass("is-invalid");
	else if($("#previousEndtime").val()=="")
		$("#previousEndtime").hasClass("is-invalid");
	else{
		formData = new FormData();
		console.log("###"+getModalClassId("modal-context-list-modify"));
		formData.append("homeworkName",$("#previousHomeworkName").val());
		formData.append("homeworkContent",$("#previousHomeworkContent").val());
		//formData.append("classIds",getModalClassId("modal-context-list-modify"));
		formData.append("addClassIds",subSet(newClassIds.split(","),oldClassIds.split(",")));
		formData.append("deleteClassIds",subSet(oldClassIds.split(","),newClassIds.split(",")));
		formData.append("deadline",$("#previousEndtime").val());
		formData.append("homeworkId",homeworkList[getHomeworkIndex()].homeworkId);
		
		if($("#modifyHomeworkFile")[0].files[0]){
			formData.append("homeworkFile",$("#modifyHomeworkFile")[0].files[0]);
			formData.append("fileName",$("#modifyHomeworkFile")[0].files[0].name);
		}else if($("#modifyFileName").text()!= ""){
			formData.append("fileName",$("#modifyFileName").text());
		}
		
		$.ajax({
	        url : postSrc+'updateHomework.action',
	        type : 'POST',
	        async : false,
	        data : formData,
	        processData : false,
	        contentType : false,
	        success : function(result) {
	        	alert(result[0].result);
	        	$("#modifyHomework-modal").modal('hide');
	        	init();
	        }
	    });
	}
});

/******************添加作业**********************/
$("#publishHomework").click(function(){
	$("#homeworkName").val("");
	$("#homeworkContent").val("");
	$("input[name='classItems']").check=false;
	$("#selectedClassList").html("");
	$("#count_create").text("0");	
	$("#selectedClassList").append($("<button type='button' class='btn btn-outline-secondary' disabled></button>").text("暂无选中班级"));
	$("#deadLine").val("");
	$("#homeworkFile").val("");
	$("#fileName").text("");
	$(':input').removeClass('is-valid is-invalid');
	
	$.post(postSrc+"queryClassesByUserId.action",function(result){
		$("#modal-context-list").html("");
		for(let i=0;i<result.length;++i){
			let tr=$("<tr></tr>");
			tr.append($("<td><input type='checkbox' id="+result[i].classId+" name='classItem' value="+result[i].className+" ></td>"));
			tr.append($("<td></td>").text(result[i].className));
			$("#modal-context-list").append(tr);
		}
		
	});
});
$("#modal-context-list").on("change","input",function(){
	let classItems=getModalClassName("modal-context-list").split(",");
	$("#selectedClassList").html("");
	if(classItems[0]!=="")
	for(let i=0;i<classItems.length;++i){
		$("#selectedClassList").append($("<button type='button' class='btn btn-outline-primary' disabled></button>").text(classItems[i]));	
	}
	else{
		$("#selectedClassList").append($("<button type='button' class='btn btn-outline-secondary' disabled></button>").text("暂无选中班级"));
	}
});

$("#publishHomeworkCommit").click(function(){
	if($("#homeworkName").val()=="")
		$("#homeworkName").hasClass("is-invalid");
	else if($("#homeworkContent").val()=="")
		$("#homeworkContent").hasClass("is-invalid");
	else if(getModalClassId("modal-context-list")=="")
		$("#modal-context-list").hasClass("is-invalid");
	else if($("#deadLine").val()=="")
		$("#deadLine").hasClass("is-invalid");
	else{
		formData = new FormData();
		
		formData.append("homeworkName",$("#homeworkName").val());
		formData.append("homeworkContent",$("#homeworkContent").val());
		formData.append("classIds",getModalClassId("modal-context-list"));
		formData.append("deadline",$("#deadLine").val());
		
		if($("#homeworkFile")[0].files[0]){
			formData.append("homeworkFile",$("#homeworkFile")[0].files[0]);
			formData.append("fileName",$("#homeworkFile")[0].files[0].name);
		}
		$.ajax({
	        url : postSrc+'addHomework.action',
	        type : 'POST',
	        async : false,
	        data : formData,
	        processData : false,
	        contentType : false,
	        success : function(result) {
	        	alert(result[0].result);
	        	$("#publishHomework-modal").modal('hide');
	        	init();
	        }
	    });
	}
});

function getHomeworkIndex() {
	let homeworkIndex = "";
	let items = document.getElementById("table-body").getElementsByTagName("input");
	for (let i = 0; i < items.length; ++i)
		if (items[i].checked === true)
			return i;
}

// 获取选中的作业ID
function getHomeworkIds(){
	 let homeworkIds="";
	    let items = document.getElementById("table-body").getElementsByTagName("input");
	    for(let i=0; i<items.length; ++i){
	        //添加选中的班级
	        if(items[i].checked===true){
	        	//selectItemIndex=i;
	        	homeworkIds += homeworkList[i].homeworkId+",";
	        }
	    }
	    return homeworkIds.substring(0,homeworkIds.length-1);
}

//获取选中的作业名称
function getHomeworkNames(){
	 let homeworkNames="";
	    let items = document.getElementById("table-body").getElementsByTagName("input");
	    for(let i=0; i<items.length; ++i){
	        //添加选中的班级
	        if(items[i].checked===true){
	        	//selectItemIndex=i;
	        	homeworkNames += homeworkList[i].homeworkName+",";
	        }
	    }
	    return homeworkNames.substring(0,homeworkNames.length-1);
}

//获取模态框中选中的班级ID
function getModalClassId(list){
	 let classIds="";
	    let items = document.getElementById(list).getElementsByTagName("input");
	    for(let i=0; i<items.length; ++i){
	        //添加选中的班级
	        if(items[i].checked===true){
	        	classIds += items[i].id+",";
	        }
	    }
	    return classIds.substring(0,classIds.length-1);
}

//获取模态框中选中的班级名称
function getModalClassName(list){
	 let classNames="";
	    let items = document.getElementById(list).getElementsByTagName("input");
	    for(let i=0; i<items.length; ++i){
	        //添加选中的班级
	        if(items[i].checked===true){
	        	classNames += items[i].value+",";
	        }
	    }
	    return classNames.substring(0,classNames.length-1);
}


function setList(result){
	console.log(result);
	if (result.length - 1 >= pageSize) {
    	homeworkList=result.slice(0,pageSize);
    }
    else {
    	homeworkList=result.slice(0,result.length-1);
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
    for (let i = 0, index = firstIndex;i<homeworkList.length; i++, index++) {
        let tr = $("<tr id='"+homeworkList[i].homeworkId+"'></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(homeworkList[i].homeworkName));
        tr.append($("<td></td>").text(homeworkList[i].creationTime.replace("T", " ")));
        tr.append($("<td></td>").text(homeworkList[i].deadline.replace("T", " ")));
        tr.append($("<td></td>").text(homeworkList[i].submitedStudents+"/"+homeworkList[i].totalStudents));
        tr.append($("<td></td>").text(homeworkList[i].classesName));
        $("#table-body").append(tr);
    }
}

$("#table-body").on("dblclick","tr",function(){
	
	window.location.href = "../html/checkStudentHomework.jsp?&"+$(this)[0].id ;
});

//设置datepicker
$('.form_date').datetimepicker({
    language:  'cn',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0,
    format: "yyyy-mm-dd"
});

//初始化
function init() {
	$("#table-body").html("");
	pageNo=1;
	updateCascadingQueryConditions();
    sendMessage = {filters: filters,className:"所有班级",search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
    $.post(postSrc+"queryHomework.action",sendMessage,function(result){
    	console.log(result);
  	  setList(result);
    });
    addClassInfo();
}

//获取当前页的内容(适用于分页查询)
function getInfo(){
	$("#table-body").html("");
	updateCascadingQueryConditions();
	sendMessage = {className:searchClassName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
    $.post(postSrc+"queryHomework.action",sendMessage,function(result){
    	setList(result);
    });
}

//添加班级至下拉列表
function addClassInfo(){
	$.post(postSrc+"queryClassesByUserId.action", function(result){
		$("#drapdown-menu").html("");
		$('#drapdown-menu').append('<a class="dropdown-item">所有班级</a>');
        for(let i=0; i<result.length; ++i){
            addmessage = '<a class="dropdown-item">' + result[i].className + '</a>';
            $('#drapdown-menu').append(addmessage);
        }
    });
}

//选择班级
$("#drapdown-menu").on("click", "a", function(){
	searchClassName = $(this).text();
    $("#choseClassButton").html(searchClassName);
	updateCascadingQueryConditions();
	sendMessage = {className:searchClassName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
	init();
});

$("#reset").click(function () {
    $("#search-homeworkName").val("");
    $("#from_date").val("");
    $("#to_date").val("");
    $("#choseClassButton").html("所有班级");
    searchClassName = "所有班级";
    homeworkName = "";
    fromDate = "";
    toDate = "";
});

//查询作业
$("#query").click(function(){
	updateCascadingQueryConditions();
	sendMessage = {className:searchClassName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
	init();
   });

//$("#table-body").on("click", "[name=items]:checkbox", function(){
//	let index = $("#table-body [name=items]:checkbox").index(this);
//	if(index !== currentItemIndex){
//		if(homeworkList[index].evaluation !== null && homeworkList[index].score !== null){
//			currentItemIndex = index;
//    	}   		
//	}
//});

//$("#dissmissModel").click(function(){
//	$("#CheckAll").prop("checked", false);
//    $("[name=items]:checkbox").prop("checked", false);
//    $("#studentHomework").attr("disabled", true).addClass("disabled");
//	$("#checkHomework").attr("disabled", true).addClass("disabled");
//});



$("#studentHomework").click(function(){
	window.location.href = "../html/checkStudentHomework.jsp?&"+getHomeworkIds();
});

//获取查询的作业名称
$("#search-homeworkName").change(function () {
	homeworkName = $("#search-homeworkName").val();
});

function selectChanged(){
	//判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
    let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
    if(statue.checked)
        selectNum-=1;
    if(selectNum===0){
    	$("#copyHomework").attr("disabled", true).addClass("disabled");
        $("#deleteHomework").attr("disabled", true).addClass("disabled");
        $("#modifyHomework").attr("disabled", true).addClass("disabled");
        $("#studentHomework").attr("disabled", true).addClass("disabled");
    }
    else if(selectNum===1){
    	$("#copyHomework").removeAttr("disabled").removeClass("disabled");
    	$("#deleteHomework").removeAttr("disabled").removeClass("disabled");
        $("#modifyHomework").removeAttr("disabled").removeClass("disabled");
        $("#studentHomework").removeAttr("disabled").removeClass("disabled");
    }
    else{
    	$("#copyHomework").attr("disabled", true).addClass("disabled");
    	$("#deleteHomework").removeAttr("disabled").removeClass("disabled");
    	$("#modifyHomework").attr("disabled", true).addClass("disabled");
        $("#studentHomework").attr("disabled", true).addClass("disabled");
    }
}



//修改级联查询条件
function updateCascadingQueryConditions(){
  filters = "";
  if(searchClassName !== "所有班级"){
  	  filters = filters + "{\"field\": \"className\",\"op\":\"bw\", \"data\":\""+"%"+searchClassName+"%\"},";
  }
  if(homeworkName !== ""){
	filters = filters + "{\"field\": \"homeworkName\",\"op\":\"bw\", \"data\":\""+"%"+homeworkName+"%\"},";
  }
  if(fromDate !== ""){
	filters = filters + "{\"field\": \"creationTime\",\"op\":\"ge\", \"data\":\""+fromDate+"\"},";
  }
  if(toDate !== ""){
	filters = filters + "{\"field\": \"creationTime\",\"op\":\"le\", \"data\":\""+toDate+"\"},";
  }
  //若不为空
  if(filters !== ""){
      filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
      filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
  }else{
	  filters = "{}";
  }
}

$("#homeworkContent").bind('input propertychange',function(){
    $("#count_create").html($(this).val().length);
});

$("#previousHomeworkContent").bind('input propertychange',function(){
    $("#count_modify").html($(this).val().length);
});

$("#copyHomeworkContent").bind('input propertychange',function(){
    $("#count_copy").html($(this).val().length);
});

