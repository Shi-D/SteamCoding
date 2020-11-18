let sendMessage = {search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let queryteacher = {filter:null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义查询班级和教师的数据
let filters;//定义查询使用的filters
let postSrc = _URL+"teacher/";
let teacherList;//表格中使用的列表
let searchTeacher = ""; //被查询的教师姓名
let searchAccount = ""; //被查询的教师账号
let isDownloadTemplate = false;

init();

/***************删除老师******************/
$("#sureToDelete").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    $.post(postSrc+"deleteTeacher.action",{Ids:getSelectTeacherId()},function(result){
        //重新获取页面信息
        if(result[0].result === "删除成功"){
            alert("删除成功!");
        }
        else {
            alert("删除失败!");
        }
        getInfo();
    });
});

/***************重置密码******************/
$("#sureToReset").click(function (e) {
    $.post(postSrc+"resetPWD.action",{Ids:getSelectTeacherId()},function(result){
        //重新获取页面信息
        getInfo();
    });
    //若全选则取消
    $("#CheckAll").prop("checked", false);
    $("[name=items]:checkbox").prop("checked", false);
    selectChanged();
});

/***************导出教师名单******************/
$("#export-teacher-list").click(function () {
    updateCascadingQueryConditions();
    searchTeacher = $("#titleSearch").val();
    updateCascadingQueryConditionsForValue();
    $.download(postSrc+"exportExcel.action","post",filters);
});

/***************下载导入模板******************/
$("#download-import-template").click(function () {
    isDownloadTemplate = true;
    $.download(postSrc+"downloadTemplate.action","post");
});
/***************添加单个教师******************/
$("#sureToAddSingleteacher").click(function () {
	if($("#teacher-name").val() == "")
		$("#teacher-name").addClass("is-invalid");
	if($("#teacher-code").val() == "")
		$("#teacher-code").addClass("is-invalid");
	
	if($("#teacher-name").hasClass("is-valid")&&
		$("#teacher-code").hasClass("is-valid")){
		let teacherName = $("#teacher-name").val();
	    let teacherGender = $("input[name='teacher-gender']:checked").val();
	    let teacherCode =  $("#teacher-code").val();
	    let addSingleteacher = {teacherName: teacherName, teacherGender: teacherGender, teacherCode: teacherCode};
	    $.post(postSrc+"addTeacher.action", addSingleteacher, function(result){
	        if(result[0].result == "添加成功"){
	            alert("添加成功!");
	            $("#cancelToAddSingleteacher").click();
	        }
	        else {
	            alert(result[0].result);
	        }
	        init();
	    });  
	}
});

$("#cancelToAddSingleteacher").click(function(){
	$("#teacher-name").val("");
	$("#teacher-name").removeClass("is-valid");
	$("#teacher-name").removeClass("is-invalid");
	$("#teacher-code").val("");
	$("#teacher-code").removeClass("is-valid");
	$("#teacher-code").removeClass("is-invalid");
});

/*****************************************/

$("#titleSearch").change(function () {
	setQueryConditions();
});

//查询班级或者教师
$("#query").click(function () {
	setQueryConditions();
});

//重置内容，清空文本框和班级选择
$("#reset").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    $("[name=items]:checkbox").prop("checked", false);
    $("#titleSearch").val("");
    $("#accountSearch").val("");
});

// 定义到jQuery全局变量下-文件下载
jQuery.download = function (url, method, filters) {
    if(!isDownloadTemplate){
        if(selectNum === 0){
            jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
                '<input type="text" name="filters" value="'+filters+'"/>' +
                '<input type="text" name="columnNames" value="教师名,性别,教师账号,班级名"/>' +
                '<input type="text" name="propertyNames" value="teacherName,teacherGender,teacherCode,className"/>' +
                '<input type="text" name="className" value=""/>' +
                '</form>')
                .appendTo('body').submit().remove();
        }
        else {
            jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
                '<input type="text" name="ids" value="'+getExportCheckItem()+'"/>' +
                '<input type="text" name="columnNames" value="教师名,性别,教师账号,班级名"/>' +
                '<input type="text" name="propertyNames" value="teacherName,teacherGender,teacherCode,className"/>' +
                '</form>')
                .appendTo('body').submit().remove();
        }
    }
    else {
        jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
            '<input type="text" name="columnNames" value="教师名,性别,教师账号"/>' +
            '</form>')
            .appendTo('body').submit().remove();
        isDownloadTemplate = false;
    }
};

//将教师查询结果赋值给teacherList
function setList(result){
	if (result.length - 1 >= pageSize) {
    	teacherList=result.slice(0,pageSize);
    }
    else {
    	teacherList=result.slice(0,result.length-1);
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
    for (let i = 0, index = firstIndex;i<teacherList.length; i++, index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(teacherList[i].teacherName));
        tr.append($("<td></td>").text(teacherList[i].teacherGender));
        tr.append($("<td></td>").text(teacherList[i].teacherCode));
        tr.append($("<td></td>").text(teacherList[i].teacherCreationTime.replace("T", " ")));
        tr.append($("<td></td>").text(teacherList[i].className));
        $("#table-body").append(tr);
    }
}

//调用分页action
function getInfo(){
	$("#table-body").html("");
    sendMessage = {search: true, pageNo: pageNo, pageSize: pageSize,sorters: "[{\"property\":\"teacherId\",\"direction\":\"DESC\"}]"};
    updateCascadingQueryConditions();
    queryteacher = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"teacherId\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryTeachersByTeacherName.action", queryteacher,function(result){
    	setList(result);
    });
}

//修改查询级联查询条件
function updateCascadingQueryConditions(){
  filters = "";
  if(searchTeacher !== ""){
	filters = filters + "{\"field\": \"teacherName\",\"op\":\"bw\", \"data\":\""+"%"+searchTeacher+"%\"},";
  }
  if(searchAccount !== ""){
	filters = filters + "{\"field\": \"teacherCode\",\"op\":\"bw\", \"data\":\""+"%"+searchAccount+"%\"},";
  }
  //若不为空
  if(filters !== ""){
      filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
      filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
  }else{
	  filters = "{}";
  }
}
//value值中转义后的删选条件
function updateCascadingQueryConditionsForValue(){
	filters = "";
    if(searchTeacher === ""){
        filters = "{}";
    }
    else {
        filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34teacherName&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+searchTeacher+'%&#34}]}';
    }
}

function selectChanged(){
	//判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
	let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
	if(statue.checked)
        selectNum-=1;
	if(selectNum == 0){
		$("#reset-password").attr("disabled", true).addClass("disabled");
        $("#delete-teacher").attr("disabled", true).addClass("disabled");
	}
	else{
		 $("#reset-password").removeAttr("disabled").removeClass("disabled");
	        $("#delete-teacher").removeAttr("disabled").removeClass("disabled");
	}
}

function setQueryConditions(){
    pageSize = 20; //页面数据条数
    pageNo = 1; //页面
    currentPage = 1;
    searchTeacher = $("#titleSearch").val();
    searchAccount = $("#accountSearch").val();
    updateCascadingQueryConditions();
    queryteacher = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"teacherId\",\"direction\":\"DESC\"}]"};
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
}

function importExp() {
    var formData = new FormData();
    var name = $("#excel-file").val();
    formData.append("file",$("#excel-file")[0].files[0]);
    formData.append("name",name);
    $.ajax({
        url : postSrc+'teacherImport.action',
        type : 'POST',
        async : false,
        data : formData,
        processData : false,
        contentType : false,
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : function(responseStr) {
            if(responseStr.length === 0){
                alert("导入成功!");
            }
            else{
                let failedteacher = responseStr[0].教师账号;
                for(let i=1; i<responseStr[i]; ++i){
                    failedteacher = failedteacher+"、"+responseStr[i].教师账号;
                }
                alert("教师账号为"+failedteacher+"的教师导入失败!");
            }
            getInfo();
        }
    });
}

//初始化
function init(){
	$("#table-body").html("");
	pageNo=1;
	updateCascadingQueryConditions();
	sendMessage = {filters:filters,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"teacherId\",\"direction\":\"DESC\"}]"};
	$.post(postSrc+"queryTeachersByTeacherName.action",sendMessage,function(result){
        console.log(result);
    	setList(result);
    });
}

//获取选中的教师ID
function getSelectTeacherId(){
	let teacherIds="";
	let items = document.getElementById("table-context-body").getElementsByTagName("input");
	for(let i=0; i<items.length; ++i){
        //添加选中的教师
        if(items[i].checked===true){
        	selectItemIndex=i;
        	teacherIds += teacherList[i].teacherId+",";
        }
    }
    return teacherIds.substring(0,teacherIds.length-1);
}
