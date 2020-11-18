let workName = ""; //作品名称
let AuthorName = ""; //作品作者
let fromDate = ""; //开始时间
let toDate = ""; //截止时间
let isPublished = 1; //是否发布
let selectedClass = '全部班级';
let workSrc = _URL;
let workList = [];
let postSrc = _URL+"work/";
let currentSize = 0; //当前页面数据数
let sendMessage = {className: null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let filters;//定义查询使用的filters
let teacherWorkMessage = {filters: "{}",search: null, pageNo: null, pageSize: null, sorters: null};
let otherWorkMessage = {filters: "{}", search: null, pageNo: null, pageSize: null, className: null ,sorters: null};
//let selectedWorkId = "";

/********************删除作品*********************/
$("#work-context").on("click","#deleteScratch",function(){
    let index=$("#work-context .btn-danger").index(this);
    if(workList[index].courseWorkName == null)
    	$("#delete-work-name").html(workList[index].workName);
    else
    	$("#delete-work-name").html(workList[index].courseWorkName);
    $("#sureToDelete").unbind('click').click(function () {
        $("#delete-work-modal").modal("hide");
        $.post(postSrc+"deleteWork.action",{workId:workList[index].workId},function (result) {
            if(result[0].result === "删除成功"){
                alert("删除成功！");
                initPage();
                $("#sureToDelete_cancel").click();
            }
            else{
            	$("#sureToDelete_cancel").click();
               alert("删除失败，请联系系统管理员！");
               initPage();
            }
        });
    });
});

/********************撤销发布*********************/
$("#work-context").on("click","#cancelPublish",function(){
    let index=$("#work-context .btn-warning").index(this);
    if(workList[index].courseWorkName == null)
    	$("#cancel-work-name").html(workList[index].workName);
    else
    	$("#cancel-work-name").html(workList[index].courseWorkName);
    $("#sureToCancel").unbind('click').click(function () {
        $("#cancel-publish-modal").modal("hide");
	    $.post(postSrc+"recallPublish.action",{workId:workList[index].workId},function (result) {
	        if(result[0].result === "撤销成功"){
	        	alert("撤销成功！");
	        	initPage();
	        	$("#sureToCancel_cancel").click();
	        }
	        else{
	        	$("#sureToCancel_cancel").click();
                alert("撤销失败，请联系系统管理员！");
                initPage();
            }	
	    });
    });
});

/********************发布作品*********************/
$("#work-context").on("click","#publishScratch",function(){
	let index=$("#work-context .btn-warning").index(this);
    console.log(workList[index]);
	if(workList[index].courseWorkName == null){
		$("#workName").val(workList[index].workName);
    }
	else{
		$("#workName").val(workList[index].courseWorkName);
    }
	       
	$("#workDescription").val((workList[index].workDescription==null?"":workList[index].workDescription));
	$("#count").html($("#workDescription").val().length);
	
	$("#sureToPublish").unbind('click').click(function () {
		if($("#workName").val() == "")
			$("#workName").addClass("is-invalid");
		else{
			$("#workName").addClass("is-valid");
		}
		if($("#count").html() > 150){
			$("#workDescription").addClass("is-invalid");
		}
        //$("#publish-work-modal").hide();
		if($("#workName").hasClass("is-valid") && !$("#workDescription").hasClass("is-invalid")){
			$("#publish-work-modal").modal("hide");
			$("cancelToPublish").click();
			$.post(postSrc+"publishWork.action",{workId: workList[index].workId, workName: $("#workName").val(), workDescription: $("#workDescription").val()},function (result) {
		        if(result[0].result === "发布成功"){
		        	alert("发布成功！");
		        	$("cancelToPublish").click();
		        	initPage();
		        }
		        else{
		        	$("cancelToPublish").click();
		        	alert("发布失败，请联系系统管理员！");	
		        }
		    }); 
		}
	});
});

$("#workDescription").change(function(){
	$("#count").html($(this).val().length);
});

$("#cancelToPublish").click(function(){
	$("#workName").val("");
	$("#workName").removeClass("is-valid");
	$("#workName").removeClass("is-invalid");
    $("#workDescription").val("");
    $("#workDescription").removeClass("is-valid");
    $("#workDescription").removeClass("is-invalid");
    $("#count").text("0");
});

/********************推荐作品（仅管理员端）*********************/
$("#work-context").on("click","#recommendScratch",function(){
    let index=$("#work-context .btn-success").index(this);
    if($(this).text() === "推荐作品"){
        $.post(postSrc+"recommendWork.action", {workId: workList[index].workId}, function (result) {
            if(result[0].result === "推荐成功"){
            	document.getElementsByClassName("btn-success")[index].innerHTML = "取消推荐";
            	alert("推荐成功！");
            }
            else{
            	alert("推荐失败，请联系系统管理员！");
            }
        });
    }
    else{
        $.post(postSrc+"recallWork.action", {workId: workList[index].workId}, function (result) {
            if(result[0].result === "撤回成功"){
                document.getElementsByClassName("btn-success")[index].innerHTML = "推荐作品";
                alert("撤回成功！");
            }
            else{
                alert("撤回失败，请联系系统管理员！");
            }
        });
    }
});



function initServer() {
    updateCascadingQueryConditions();
    initPage();
}

function Post(_actionName,sendMessage,func) {
    $.post(postSrc+_actionName,sendMessage,function (result) {
        //判断是否查询到数据
        if(result.length === 0){
        	currentPage = 1;
            pageNo = 1;
            totalItem = 0;
            currentSize = 0;
            workList = [];
        }
        else{
        	console.log(result);
            let array;
            if (result.length - 1 >= pageSize) {
                array = result.slice(0, pageSize);
            }
            else {
                array = result.slice(0, result.length - 1);
            }

            if(result.length !== 0){
                totalItem = result[result.length - 1].totalCount;
            }

            currentSize = result.length-1;

            for(let i=0; i<currentSize; ++i){
                if(array[i].likeCount === null) {
                    array[i].likeCount = 0;
                }
                array[i].createTime = array[i].createTime.replace("T"," ");
            }

            workList = array;
        }
        func();
    });
}

//点击开始创作
$("#add-work").click(function () {
    // window.open("../../../build/index.html", "","");
    location.href = '../../../build/tmp/index.html';
});


//点击发布的作品
$("#publish-work").click(function () {
    isPublished = 1;
    $('#publish-work').attr("disabled","disabled");
    $('#save-work').removeAttr("disabled");
    $('#searchBarWorkName').val("");
    filters = "{\"field\": \"isPublished\",\"op\":\"eq\", \"data\":"+isPublished+"},";
    filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
    filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
    initPage();
});


//点击保存的作品
$("#save-work").click(function () {
    isPublished = 0;
    $('#publish-work').removeAttr("disabled");
    $('#save-work').attr("disabled","disabled");
    $('#searchBarWorkName').val("");
    filters = "{\"field\": \"isPublished\",\"op\":\"eq\", \"data\":"+isPublished+"},";
    filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
    filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
    initPage();
});


//获取输入的作品名
$("#searchBarWorkName").change(function () {
    workName =  $("#searchBarWorkName").val();
});


//获取输入的作者姓名
$("#searchBarAuthorName").change(function () {
    AuthorName =  $("#searchBarAuthorName").val();
});

//获取查询起始时间//获取查询起始时间
$("#from_date").change(function () {
	var date = new Date();
	let currentYear = date .getFullYear(); //获取完整的年份(4位)
	let currentMonth = date .getMonth()+1; //获取当前月份(0-11,0代表1月)
	let currentDate = date .getDate(); //获取当前日(1-31)
    let getDate =  $("#from_date").val();
    console.log(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate))
    if(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate)){
    	fromDate =  $("#from_date").val();
    }
    else{
    	$("#from_date").val("");
    	alert("请选择正确的时间段！");
    }
    
});


//获取查询截止时间
$("#to_date").change(function () {
	if(fromDate === ""){
		toDate =  $("#to_date").val();
	}
	else{
		console.log(fromDate);
		if(checkTime($("#to_date").val(),fromDate)){
			toDate =  $("#to_date").val();
	    }
	    else{
	    	$("#to_date").val("");
	    	alert("请选择正确的时间段！");
	    }
	}
});

function checkTime(time0, time1){
	
    let arr0 = time0.split("-");
    let arr1 = time1.split("-");
    
    if(Number(arr0[0]) < Number(arr1[1])){
    	return false;
    }
    else{
    	if(Number(arr0[1]) < Number(arr1[1])){
    		return false;
        }
        else{
        	if(Number(arr0[1]) > Number(arr1[1])){
        		return true;
            }
        	else{
        		if(Number(arr0[2])<Number(arr1[2])){
        			return false;
                }
                else{
                	return true;
                }
        	}
        }
    }
}
$("#from_date").change(function () {
	var date = new Date();
	let currentYear = date .getFullYear(); //获取完整的年份(4位)
	let currentMonth = date .getMonth()+1; //获取当前月份(0-11,0代表1月)
	let currentDate = date .getDate(); //获取当前日(1-31)
    let getDate =  $("#from_date").val();
    console.log(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate))
    if(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate)){
    	fromDate =  $("#from_date").val();
    }
    else{
    	$("#from_date").val("");
    	alert("请选择正确的时间段！");
    }
    
});


//获取查询截止时间
$("#to_date").change(function () {
	if(fromDate === ""){
		toDate =  $("#to_date").val();
	}
	else{
		console.log(fromDate);
		if(checkTime($("#to_date").val(),fromDate)){
			toDate =  $("#to_date").val();
	    }
	    else{
	    	$("#to_date").val("");
	    	alert("请选择正确的时间段！");
	    }
	}
});

function checkTime(time0, time1){
	
    let arr0 = time0.split("-");
    let arr1 = time1.split("-");
    
    if(Number(arr0[0]) < Number(arr1[1])){
    	return false;
    }
    else{
    	if(Number(arr0[1]) < Number(arr1[1])){
    		return false;
        }
        else{
        	if(Number(arr0[1]) > Number(arr1[1])){
        		return true;
            }
        	else{
        		if(Number(arr0[2])<Number(arr1[2])){
        			return false;
                }
                else{
                	return true;
                }
        	}
        }
    }
}


//修改查询级联查询条件
function updateCascadingQueryConditions(){

    //如果是我的作品
    if(workOwner === 'I'){
        filters = "";
        if(workName !== ""){
            filters = filters + "{\"field\": \"courseWorkName\",\"op\":\"bw\", \"data\":\""+"%"+workName+"%\"},";
        }
        if(fromDate !== ""){
            filters = filters + "{\"field\": \"createTime\",\"op\":\"ge\", \"data\":\""+fromDate+"\"},";
        }
        if(toDate !== ""){
            filters = filters + "{\"field\": \"createTime\",\"op\":\"le\", \"data\":\""+toDate+"\"},";
        }

        filters = filters + "{\"field\": \"isPublished\",\"op\":\"eq\", \"data\":"+isPublished+"},";

        //若不为空
        if(filters !== ""){
            filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
            filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
        }else{
        	filters = "{}";
        }
    }

    //如果是其他作品
    else if(workOwner === 'others'){
        filters = "";
        if(workName !== ""){
            filters = filters + "{\"field\": \"courseWorkName\",\"op\":\"bw\", \"data\":\""+"%"+workName+"%\"},";
        }
        if(AuthorName !== ""){
            filters = filters + "{\"field\": \"userName\",\"op\":\"bw\", \"data\":\""+"%"+AuthorName+"%\"},";
        }
        if(fromDate !== ""){
            filters = filters + "{\"field\": \"createTime\",\"op\":\"ge\", \"data\":\""+fromDate+"\"},";
        }
        if(toDate !== ""){
            filters = filters + "{\"field\": \"createTime\",\"op\":\"le\", \"data\":\""+toDate+"\"},";
        }

        //若不为空
        if(filters !== ""){
            filters = filters.substr(0,filters.length-1); //吃掉最后一个逗号
            filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
        }else{
        	filters = "{}";
        } 
    }
}


//加载页面
function initPage() {
    currentPage = pageNo;
    $("#work-context").html("");
    if(workOwner === 'I'){
        $("#dropdown").css("display","none");
        $("#save-work").css("display","inline");
        $("#publish-work").css("display","inline");
        $("#add-work").css("display","inline");
        $("#authorName").css("display","none");
        teacherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        Post("queryMyWorks.action", teacherWorkMessage, updateInfo);
    }

    else if(workOwner === 'others'){
        $("#dropdown").css("display","block");
        $("#save-work").css("display","none");
        $("#publish-work").css("display","none");
        $("#add-work").css("display","none");
        $("#authorName").css("display","block");
        $.get(_URL+"student/queryAllClasses.action", function(result){
            for(let i=0; i<result.length; ++i){
                addmessage = '<a class="dropdown-item">' + result[i].className + '</a>';
                $('#drapdown-menu').append(addmessage);
            }
        });
        if(selectedClass === "全部班级"){
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize,sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }
        else{
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, className:selectedClass, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }

        Post("queryOtherWorks.action", otherWorkMessage, updateInfo);
    }
}

$("#query").click(function () {
    pageNo = 1;
    updateCascadingQueryConditions();
    if(workOwner === 'I'){
        teacherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        Post("queryMyWorks.action", teacherWorkMessage, updateInfo);
    }

    else if(workOwner === 'others'){
        if(selectedClass === "全部班级"){
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize,sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }
        else{
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, className:selectedClass, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }
        Post("queryOtherWorks.action", otherWorkMessage, updateInfo);
    }
});

//更新页面信息
function updateInfo() {
    updateTable();
    updateFooter();
    // initWork();
}

//获取元素的小标并播放相应的sb3文件
$("#work-context").on("click",".card-img-top",function(){
    //注意：这里的下标从零开始
    let index=$("#work-context .card-img-top").index(this);
    // let workUrl = workSrc + workList[index].workLink + workList[index].workName; 
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + workList[index].workLink + workList[index].workName + '.sb3');
    localStorage.removeItem("workId");
    localStorage.setItem('workId', workList[index].workId);
    goWorkInfo();
});

//修改作品
$("#work-context").on("click","#editScratch",function(){
    let index=$("#work-context .btn-success").index(this);
    const id = workList[index]['workId'];
    location.href = '../../../build/tmp/index.html' + '?id=' + id;
});

//查看代码
$("#work-context").on("click","#checkCode",function(){
    let index=$("#work-context .btn-light").index(this);
    const id = workList[index]['workId'];
    location.href = '../../../build/tmp/index.html' + '?id=' + id;

});

//跳转至player.html页面
function goPlayer(workUrl, isClick){
	console.log(workUrl);
    localStorage.setItem('workUrl',workUrl+'.sb3');
    localStorage.setItem('isClick',isClick);
    window.open("../../../build/tmp/index.html", "",""); //第二个参数：_self，在当前窗口打开窗口；_blank（默认值），在另外的新建窗口打开新窗口；
}

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


//清空查询内容
function clearQuery(){
    $("#searchBarWorkName").val("");
    $("#searchBarAuthorName").val("");
    $("#from_date").val("");
    $("#to_date").val("");
    workName = "";
    AuthorName = "";
    fromDate = "";
    toDate = "";
}


//重置内容，清空文本框和班级选择
$("#reset").click(function (e) {
    clearQuery();
});


//选择班级
$("#drapdown-menu").on("click", "a", function(){
    selectedClass = $(this).text();
    $("#choseClassButton").html(selectedClass);
    
    //选择时同时可进行筛选
    pageNo = 1;
    updateCascadingQueryConditions();
    if(workOwner === 'I'){
        teacherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        Post("queryMyWorks.action", teacherWorkMessage, updateInfo);
    }

    else if(workOwner === 'others'){
        if(selectedClass === "全部班级"){
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize,sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }
        else{
            otherWorkMessage = {filters: filters, search: true, pageNo: pageNo, pageSize: pageSize, className:selectedClass, sorters: "[{\"property\":\"createTime\",\"direction\":\"DESC\"}]"};
        }
        Post("queryOtherWorks.action", otherWorkMessage, updateInfo);
    }
});



//切换我的作品和其他作品
$("#myWorks").click(function () {
    workOwner = 'I';
    $("#otherWorks").removeClass("active");
    $("#myWorks").addClass("active");
    $("#work-context").html("");
    clearQuery();
    pageNo = 1;
    pageSize = 10;
    currentPage = 1;
    updateCascadingQueryConditions();
    console.log(pageNo,pageSize);
    initPage();
});

$("#otherWorks").click(function () {
    workOwner = 'others';
    $("#myWorks").removeClass("active");
    $("#otherWorks").addClass("active");
    clearQuery();
    pageNo = 1;
    pageSize = 10;
    currentPage = 1;
    updateCascadingQueryConditions();
    console.log(pageNo,pageSize);
    initPage();
});





function goWorkInfo() {
    window.open("../../../pages/ProductionContent/ProductionContent.jsp", "","");
}

