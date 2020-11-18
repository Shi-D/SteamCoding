let sendMessage = { search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let formData = new FormData();
let list = [];
let postSrc=_URL+"course/";
let courseList;
let searchCourseName="";
let rowNum;
let courseSum;
init();

function init() {
	$("#course-context").html("");
	pageSize=10;
	pageNo=1;
	sendMessage = {search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
	$.post(postSrc+"searchAllInfoCourses.action",function(result){
		console.log(result)
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
    rowNum = Math.ceil(result.length/5);
    courseSum = result.length;
    updateTable();
    updateFooter();
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
    for (let i = 0;i<courseList.length; i++) {
        let row = '<div class="">';
            row = row+
                '<div class="col-md-1-5 col-sm-1-5 work-col"  style="padding: 1%">'+
                '<div class="card card-style">'+
                '<img class="card-img-top" style="width: 100%; height: 150px" src="'+_URL+"account/"+courseList[i].courseFolderName+courseList[i].courseCover+ '" alt="Card image cap" name="courseCover">'+
                '<div class="card-body">'+
                '<h5 class="card-title" id="CourseName" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap" title="'+courseList[i].courseName+'">'+courseList[i].courseName+'</h5>'
                if(courseList[i].courseIntroduction != "")
                	row += '<p class="card-text" style="-webkit-line-clamp: 1;-webkit-box-orient: vertical;font-size:14px;color:gray;overflow: hidden;display: -webkit-box;" title="'+courseList[i].courseIntroduction+'">'+courseList[i].courseIntroduction+'</p>';
                else
                	row += '<p class="card-text" style="-webkit-line-clamp: 1;-webkit-box-orient: vertical;font-size:14px;color:gray;overflow: hidden;display: -webkit-box;" title="暂无课程介绍">暂无课程介绍</p>';
                row += '<p class="card-text" style="-webkit-line-clamp: 1;-webkit-box-orient: vertical;font-size:14px;color:gray;overflow: hidden;display: -webkit-box;" title="暂无课程介绍">'+courseList[i].className+'</p>';
            	row  +='<a href="../../courseContent/courseContent.jsp?&'+courseList[i].classId+"&"+courseList[i].courseId+'" class="btn btn-primary" >'+"开始学习"+'</a>'+
                '</div>'+
                '</div>'+
                '</div>';
            if(i === courseSum){
                break;
            }
        row = row+'</div>';
        $("#course-context").append(row);
    }
}

function getInfo(){
	$("#course-context").html("");
	sendMessage = {courseName:searchCourseName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
    $.post(postSrc+"searchAllInfoCourses.action",sendMessage,function(result){
        setList(result);
    });
}

$("#query").click(function (e) {
	searchCourseName=$("#titleSearch").val();
	pageNo=1;//重置当前页
    pageSize = 10; //页面数据条数
    currentPage=1;//重置当前页数
	$("#course-context").html("");
    sendMessage = {courseName:searchCourseName, search: true, pageNo: pageNo, pageSize: pageSize,  sorters: "[{\"property\":\"creationTime\",\"direction\":\"ASC\"}]"};
    $.post(postSrc+"queryAllInfoCoursesByCourseName.action",sendMessage,function(result){
    	setList(result);
    });
});

//重置页面内容
$("#reset").click(function (e) {
	document.getElementById("titleSearch").value="";
});
