let sendMessage = {className: null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let queryStudentAndClass = {filter:null, search: null, pageNo: null, pageSize: null, sorters: null, className: null}; //定义查询班级和学生的数据
let filters;//定义查询使用的filters
let selectedClass = '所有学生';
let studentList;//表格中使用的列表
let postSrc = _URL+"student/";
let searchStudent = ""; //被查询的学生姓名
let searchAccount = ""; //被查询的学生账号
let isDownloadTemplate = false;
init();

/***************重置密码******************/
$("#sureToReset").click(function (e) {
    $.post(postSrc+"resetPWD.action",{Ids:getSelectStudentId()},function(result){
        //重新获取页面信息
        getInfo();
    });
    $("#CheckAll").prop("checked", false);
    $("[name=items]:checkbox").prop("checked", false);
    selectChanged();
});

/***************导出学生名单******************/
$("#export-stu-list").click(function () {
    updateCascadingQueryConditions();
    searchStudent = $("#titleSearch").val();
    updateCascadingQueryConditionsForValue();
    $.download(postSrc+"exportExcel.action","post",filters);
});

$("#titleSearch").change(function () {
	$("#table-body").html("");
    setQueryConditions();
});

//查询班级或者学生
$("#query").click(function () {
	$("#table-body").html("");
    setQueryConditions();
});

//选择班级
$("#drapdown-menu").on("click", "a", function(){
    selectedClass = $(this).text();
    $("#choseClassButton").html(selectedClass);
	$("#table-body").html("");
    setQueryConditions();
});

//重置内容，清空文本框和班级选择
$("#reset").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    $("[name=items]:checkbox").prop("checked", false);
    $("#choseClassButton").html("所有学生 ");
    selectedClass = "所有学生"; //重置班级
    $("#titleSearch").val("");
    $("#accountSearch").val("");
    //document.getElementById("titleSearch").value="";
    //document.getElementById("accountSearch").value="";
});

//定义到jQuery全局变量下-文件下载
jQuery.download = function (url, method, filters) {
  if(!isDownloadTemplate){
      if(selectNum === 0){
          if(selectedClass === "未分配"){
              jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
                  '<input type="text" name="filters" value="'+filters+'"/>' +
                  '<input type="text" name="columnNames" value="学生名,性别,学生账号,班级名"/>' +
                  '<input type="text" name="propertyNames" value="studentName,studentGender,studentCode,className"/>' +
                  '<input type="text" name="className" value="未分配"/>' +
                  '</form>')
                  .appendTo('body').submit().remove();
          }
          else{
              jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
                  '<input type="text" name="filters" value="'+filters+'"/>' +
                  '<input type="text" name="columnNames" value="学生名,性别,学生账号,班级名"/>' +
                  '<input type="text" name="propertyNames" value="studentName,studentGender,studentCode,className"/>' +
                  '<input type="text" name="className" value=""/>' +
                  '</form>')
                  .appendTo('body').submit().remove();
          }

      }
      else {
          jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
              '<input type="text" name="ids" value="('+getSelectStudentId()+')"/>' +
              '<input type="text" name="columnNames" value="学生名,性别,学生账号,班级名"/>' +
              '<input type="text" name="propertyNames" value="studentName,studentGender,studentCode,className"/>' +
              '</form>')
              .appendTo('body').submit().remove();
      }
  }
  else {
      jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
          '<input type="text" name="columnNames" value="学生名,性别,学生账号"/>' +
          '</form>')
          .appendTo('body').submit().remove();
      isDownloadTemplate = false;
  }
};

//将班级查询结果赋值给studentList
function setList(result){
	if (result.length - 1 >= pageSize) {
    	studentList=result.slice(0,pageSize);
    }
    else {
    	studentList=result.slice(0,result.length-1);
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
    for (let i = 0, index = firstIndex;i<studentList.length; i++, index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(studentList[i].studentName));
        tr.append($("<td></td>").text(studentList[i].studentGender));
        tr.append($("<td></td>").text(studentList[i].studentCode));
        tr.append($("<td></td>").text(studentList[i].studentCreationTime.replace("T", " ")));
        tr.append($("<td></td>").text(studentList[i].className));
        $("#table-body").append(tr);
    }
}

//调用分页action
function getInfo(){
	$("#table-body").html("");
    sendMessage = {className:selectedClass, search: true, pageNo: pageNo, pageSize: pageSize,sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]"};
    updateCascadingQueryConditions();
    if(selectedClass === "未分配"){
        queryStudentAndClass = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]", className: "未分配"};
    }
    else {
        queryStudentAndClass = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]", className: null};
    }
    $.post(postSrc+"queryStudentsByStudentName.action",queryStudentAndClass,function(result){
    	setList(result);
    });
}

// //修改查询级联查询条件
// function updateCascadingQueryConditions(){
//     if(selectedClass === "所有学生" && searchStudent !== ""){
//         filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"studentName\",\"op\":\"bw\", \"data\":\""+"%"+searchStudent+"%\"}]}";
//     }

//     else if(selectedClass === "所有学生"&&searchStudent === ""){
//         filters = "{}";
//     }

//     else if(selectedClass !== "所有学生"&&searchStudent === ""){
//         if(selectedClass === "未分配"){
//             filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"className\",\"op\":\"bw\", \"data\":null}]}";
//         }
//         else {
//             filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"className\",\"op\":\"bw\", \"data\":\"%"+selectedClass+"%\"}]}";
//         }

//     }
//     else {
//         if(selectedClass === "未分配"){
//             filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"studentName\",\"op\":\"bw\", \"data\":\"%"+searchStudent+"%\"}]}";
//         }

//         else {
//             filters = "{\"groupOp\":\"and\",\"rules\": [{\"field\": \"className\",\"op\":\"bw\", \"data\":\"%"+selectedClass+"%\"}, "+"{\"field\": \"studentName\",\"op\":\"bw\", \"data\":\"%"+searchStudent+"%\"}]}";
//         }
//     }
// }
function updateCascadingQueryConditions(){
  filters = "";
  if(selectedClass !== "所有学生"){
  	  filters = filters + "{\"field\": \"className\",\"op\":\"bw\", \"data\":\""+"%"+selectedClass+"%\"},";
  }
  if(searchStudent !== ""){
	filters = filters + "{\"field\": \"studentName\",\"op\":\"bw\", \"data\":\""+"%"+searchStudent+"%\"},";
  }
  if(searchAccount !== ""){
	filters = filters + "{\"field\": \"studentCode\",\"op\":\"bw\", \"data\":\""+"%"+searchAccount+"%\"},";
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
    if(selectedClass === "所有学生" && searchStudent !== ""){
        filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34studentName&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+searchStudent+'%&#34}]}';
    }

    else if(selectedClass === "所有学生"&&searchStudent === ""){
        filters = "{}";
    }

    else if(selectedClass !== "所有学生"&&searchStudent === ""){
        if(selectedClass === "未分配"){
            filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34className&#34,&#34op&#34:&#34bw&#34, &#34data&#34:null}]}';
        }
        else {
            filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34className&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+selectedClass+'%&#34}]}';
        }
    }
    else {
        if(selectedClass === "未分配"){
            filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34className&#34,&#34op&#34:&#34bw&#34, &#34data&#34:null}, '+'{&#34field&#34: &#34studentName&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+searchStudent+'%&#34}]}';
        }
        else {
            filters = '{&#34groupOp&#34:&#34and&#34,&#34rules&#34: [{&#34field&#34: &#34className&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+selectedClass+'%&#34}, '+'{&#34field&#34: &#34studentName&#34,&#34op&#34:&#34bw&#34, &#34data&#34:&#34%'+searchStudent+'%&#34}]}';
        }
    }
}

function setQueryConditions(){
    pageSize = 20; //页面数据条数
    pageNo = 1; //页面
    currentPage = 1;
    searchStudent = $("#titleSearch").val();
    searchAccount = $("#accountSearch").val();
    updateCascadingQueryConditions();
    if(selectedClass === "未分配"){
        queryStudentAndClass = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]", className: "未分配"};
    }
    else {
        queryStudentAndClass = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]", className: null};
    }
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
}

function selectChanged(){
	//判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
	let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
	if(statue.checked)
        selectNum-=1;
	if(selectNum == 0){
		$("#reset-password").attr("disabled", true).addClass("disabled");
	}
	else{
		$("#reset-password").removeAttr("disabled").removeClass("disabled");
	}
}

//初始化
function init(){
	$("#table-body").html("");
	pageNo=1;
	updateCascadingQueryConditions();
	sendMessage = {filters: filters,className:"所有学生",search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"studentId\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryStudentsByStudentName.action",sendMessage,function(result){
    	setList(result);
    });
    $.post(postSrc+"queryAllClasses.action", function(result){
        for(let i=0; i<result.length; ++i){
            addmessage = '<a class="dropdown-item">' + result[i].className + '</a>';
            $('#drapdown-menu').append(addmessage);
        }
    });
}

//获取选中的学生ID
function getSelectStudentId(){
	let studentIds="";
	let items = document.getElementById("table-context-body").getElementsByTagName("input");
	for(let i=0; i<items.length; ++i){
        //添加选中的学生
        if(items[i].checked===true){
        	selectItemIndex=i;
        	studentIds += studentList[i].studentId+",";
        }
    }
    return studentIds.substring(0,studentIds.length-1);
}

