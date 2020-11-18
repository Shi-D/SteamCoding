let sendMessage = { search: null, pageNo: null, pageSize: null, sorters: null, filters: null}; //定义发送的数据
let list = [];
let postSrc=_URL+"homework/";
let homeworkList;
let searchHomeWorkName="";
let rowNum;
let homeworkSum;
homeworkName = "";
pageSize = 9;
toDate = "";
init();

function init() {
	pageSize=9;
	pageNo=1;
	updateCascadingQueryConditions();
	sendMessage = {filters:filters,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
	$.post(postSrc+"queryHomework.action",sendMessage, function(result){
		$("#table-context").html("");
        setList(result);
    });
}

function setList(result){
	if (result.length - 1 >= pageSize) {
		homeworkList=result.slice(0,pageSize);
    }
    else {
    	homeworkList=result.slice(0,result.length-1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    rowNum = Math.ceil(result.length/5);
    courseSum = result.length;
    updateTable();
    updateFooter();
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
	
	let appendInfo = '<ul>'
	
    for (let i = 0;i<homeworkList.length; i++) {
    	
    	appendInfo = appendInfo + '<li class="homeWorkCardStyle">'
    	+ '<div class="cardContent">'
    	+ '<p title="'+homeworkList[i].homeworkName+'">'+homeworkList[i].homeworkName+'</p>'
    	+ '<span>发布老师: '+homeworkList[i].teacherName+'</span>'
    	+ '<span>开始时间: '+homeworkList[i].creationTime.replace("T"," ")+'</span>'
    	+ '<span>截止时间: '+homeworkList[i].deadline.replace("T"," ")+'</span>';
    	
    	if(!homeworkList[i].isSubmit){//false
        	appendInfo = appendInfo+
        	'<span>作业状态: 未提交</span>';
        }
        else if(homeworkList[i].isSubmit&&homeworkList[i].score==null){
        	appendInfo = appendInfo+
        	'<span>作业状态: 待批阅</span>';
        }
        else{
        	appendInfo = appendInfo+
        	'<span>作业状态: 已批阅</span>';	
        }
    	appendInfo = appendInfo +'<div class="homeworkDetail">';
    	
        if(homeworkList[i].score!= null)
        	appendInfo = appendInfo +'<label>'+homeworkList[i].score+'分</label>';
        
        if(homeworkList[i].submitable && !homeworkList[i].isSubmit){
        	appendInfo = appendInfo + '<a href="../html/homeworkContent.jsp?&'+homeworkList[i].homeworkId+'" >提交</a></div></div></li>';
        }
        else if(homeworkList[i].submitable && homeworkList[i].isSubmit){
            appendInfo = appendInfo + '<a href="../html/homeworkContent.jsp?&'+homeworkList[i].homeworkId+'" >编辑</a></div></div></li>';
        }
        else{
        	appendInfo = appendInfo + '<a href="../html/homeworkContent.jsp?&'+homeworkList[i].homeworkId+'" >查看</a></div></div></li>';
        }
        
    	
    }
	appendInfo = appendInfo + '</ul>'
	$("#table-context").append(appendInfo);
}

function uploadHomework(homeworkId){
	let formData=new FormData();
	
	formData.append("homeworkId",homeworkId);
	formData.append("homeworkFile",$("#"+homeworkId)[0].files[0]);
	formData.append("fileName",$("#"+homeworkId)[0].files[0].name);

	$.ajax({
        url : postSrc+'.action',
        type : 'POST',
        async : false,
        data : formData,
        processData : false,
        contentType : false,
        beforeSend:function(){
            //alert("正在上传作业,请稍候");
        },
        success : function(result) {
        	alert("作业上传成功");
        	init();
        }
    });
}



function getInfo(){
	sendMessage = {search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters};
    $.post(postSrc+"queryHomework.action",sendMessage,function(result){
    	$("#table-context").html("");	
        setList(result);
    });
}

$("#query").click(function (e) {
	searchHomeWorkName=$("#titleSearch").val();
	pageNo=1;//重置当前页
    pageSize = 9; //页面数据条数
    currentPage=1;//重置当前页数
	$("#table-context").html("");
	init();
});

//重置页面内容
$("#reset").click(function (e) {
	document.getElementById("titleSearch").value="";
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

//获取查询的作业名称
$("#searchBarWorkName").change(function () {
	homeworkName = $("#searchBarWorkName").val();
});


//修改级联查询条件
function updateCascadingQueryConditions(){
  filters = "";
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
