let sendMessage = { search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let list = [];//模态框中共用的数组容器
let classList;//表格中使用的列表
let courseId;//定义全局的courseId
let courseList;
let postSrc=_URL+"classes/";
let searchClassName="";
init();

/***************给班级添加学生******************/
//点击添加学生按钮,初始化模态框
$("#add-student").click(function(){
    getNotAddedStudentList();
});
//搜索学生
$("#addStudentInputInfo").bind('input propertychange',"studentName&studentCode",searchModal);
//点击添加学生按钮
$("#addStudentCommit").click(function () {
    let studentIds= getSelectModalItems(list,"modal_studentNotInClass_list","studentId");
    $.post(postSrc+"addStudentsToClass.action",{classId:getSelectClassId(),studentIds:studentIds},function (result) {
        getNotAddedStudentList();
    });
});
//模态框结束后调用getInfo 防止在添加学生过程中多次调用Action
$("#add-student-modal").on('hide.bs.modal',function(){
	getInfoCopy();
});


/****************从班级中移除学生****************/
//点击移除学生按钮，初始化模态框
$("#remove-student").click(function(){
    getAddedStudentList();
});
//搜索学生
$("#removeStudentInputInfo").bind('input propertychange',"studentName&studentCode",searchModal);
//点击移除学生按钮
$("#removeStudentCommit").click(function () {
    let studentIds=getSelectModalItems(list,"modal_studentInClass_list","studentId");
    $.post(postSrc+"deleteStudentInClass.action",{classId:getSelectClassId(),studentIds:studentIds},function (result) {
        getAddedStudentList();
    });
});
//模态框结束后调用getInfo 防止在移除学生过程中拿不到选中的班级id
$("#remove-student-modal").on('hide.bs.modal',function(){
	getInfoCopy();
});


/******************添加班级**********************/
$("#addClassCommit").click(function (e) {
	if($("#addClassName").val()!=""){
	    $.post(postSrc+"addClass.action",{className:$("#addClassName").val()},function(result){
	    	init();
	    	$("#add-class-modal").modal('hide');
	    })
	}
	else{
		alert("请输入班级名");
	}
    $("#addClassName").val("");//清空输入框
})


/******************删除班级**********************/
$("#delete-class").click(function (e) {
    $("#deleteClass_className").html(getSelectClassName());
})
$("#deleteClassCommit").click(function (e) {
	$.post(postSrc+"deleteClassesByClassIds.action",{classIds:getSelectClassId()},function(result){
    	init();
    })
})

/****************给班级添加课程****************/

//点击添加课程按钮
$("#add-course").click(function(){
    $("#modal_courseNotInClass_list").html("");
    $("#addCourseInputInfo").val("");
    $("#addCourse_className").html(getSelectClassName());
    getNotAddedCourseList();
})

//点击确认添加课程按钮
$("#addCourseCommit").click(function () {
	$("#addCourseInputInfo").val("");
	let courseIds=getSelectModalItems(list,"modal_courseNotInClass_list","courseId");
    $.post(postSrc+"addCoursesToClass.action",{classId:getSelectClassId(),courseIds:courseIds},function (result) {
    	$("#modal_courseNotInClass_list").html("");
        getNotAddedCourseList();
    });
})

//模态框结束后调用getInfo 防止在添加课程过程中多次调用Action
$("#add-course-modal").on('hide.bs.modal',function(){
	getInfoCopy();
});

/****************给班级移除课程****************/
$("#remove-course").click(function(){
    $("#modal_courseInClass_list").html("");
    $("#removeCourseInputInfo").val("");
    $("#removeCourse_className").html(getSelectClassName());
    getAddedCourseList();
})

//点击确认移除课程按钮
$("#removeCourseCommit").click(function () {
	$("#removeCourseInputInfo").val("");
	let courseIds=getSelectModalItems(list,"modal_courseInClass_list","courseId");
    $.post(postSrc+"deleteCoursesInClass.action",{classId:getSelectClassId(),courseIds:courseIds},function (result) {
    	$("#modal_courseInClass_list").html("");
        getAddedCourseList();
    });
})

//模态框结束后调用getInfo 防止在添加课程过程中多次调用Action
$("#remove-course-modal").on('hide.bs.modal',function(){
	getInfoCopy();
});

//搜索课程
$("#addCourseInputInfo").bind('input propertychange',"courseName&courseId",searchModal);

//搜索课程
$("#removeCourseInputInfo").bind('input propertychange',"courseName&courseId",searchModal);

/****************给班级更新课程进度****************/
//点击添加课程按钮
$("#update-courseProgress").click(function(e){
	$("#choseCourseButton").html("请选择课程");
	$("#drapdown-menu").html("");//课程下拉框清空
    $("#modal_chapterInCourse_list").html("");//清空章节列表
    $("#addCourseChapter_className").html(getSelectClassName());//模态框显示当前的班级
    
    //获取该班级下的课程
    $.post(postSrc+"queryCoursesByClassId.action",{classId:getSelectClassId()},function(result){
    	courseList=result;
    	 //显示该班级下的课程列表
         for (let i=0;i<result.length;++i){
             $("#drapdown-menu").append('<li class="dropdown-item">'+courseList[i].courseName+"</li>")
         }
         //给课程列表加上index下标
         let _list=document.getElementById("drapdown-menu").getElementsByTagName("li");
         for(let i=0;i<_list.length;i++){
             _list[i].index=i;
         }
    })
})

//选择课程获取章节
$("#drapdown-menu").on('click','li',function () {
 	let selectCourseName=courseList[this.index].courseName;
    courseId = courseList[this.index].courseId;
    $("#modal_chapterInCourse_list").html("");//清空章节列表
    $("#choseCourseButton").html(selectCourseName);
    getChapterInCourse();
})
$("#updateChapterCommit").click(function () {
    let chapterIds=getSelectModalItems(list,"modal_chapterInCourse_list","chapterId");
    $.post(postSrc+"updateChapterSchedule.action",{classId:getSelectClassId(),chapterIds:chapterIds},function (result) {
    	$("#modal_chapterInCourse_list").html("");
    	getChapterInCourse();
    })
});

//查询班级
$("#query").click(function (e) {
	searchClassName=$("#titleSearch").val();
	pageNo=1;//重置当前页
    pageSize = 20; //页面数据条数
    currentPage=1;//重置当前页数
	$("#table-body").html("");
    sendMessage = {className:searchClassName, search: true, pageNo: pageNo, pageSize: pageSize,  sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoClassesByClassName.action",sendMessage,function(result){
    	setList(result);
    });
});

//在模态框中显示不在该班中的学生
function getNotAddedStudentList(){
	$("#addStudentInputInfo").val("");
    $("#modal_studentNotInClass_list").html("");
    $("#addStudent_className").html(getSelectClassName());
    $.post(postSrc+"queryStudentNotInClass.action",{classId:getSelectClassId()},function(result) {
    	list = result;//将result放入全局的list中用于其他操作
        for (let i = 0, index = 1;i<list.length; i++, index++) {
            let tr = $("<tr></tr>");
            tr.append($("<td><input type='checkbox' name='modalItems' val='"+index+"' ></td>"));
            tr.append($("<td></td>").text(index));
            tr.append($("<td></td>").text(list[i].studentName));
            tr.append($("<td></td>").text(list[i].studentCode));
            $("#modal_studentNotInClass_list").append(tr);
        }
    });
}

function setSelectedIndex(){
	if(selectItemIndex!=-1)
		$("input[name='items']").eq(selectItemIndex).prop("checked", true);
}

//在模态框中显示在该班中的学生
function getAddedStudentList(){
	$("#removeStudentInputInfo").val("");
    $("#modal_studentInClass_list").html("");
    $("#removeStudent_className").html(getSelectClassName());
    $.post(postSrc+"queryStudentInClass.action",{classId:getSelectClassId()},function(result){
        list = result;
        for (let i = 0, index = 1;i<list.length; i++, index++) {
            let tr = $("<tr></tr>");
            tr.append($("<td><input type='checkbox' name='modalItems' val='"+index+"' ></td>"));
            tr.append($("<td></td>").text(index));
            tr.append($("<td></td>").text(list[i].studentName));
            tr.append($("<td></td>").text(list[i].studentCode));
            $("#modal_studentInClass_list").append(tr);
        }
    });
}

//在模态框中显示不在该班中的课程
function getNotAddedCourseList(){
    $.post(postSrc+"queryCourseNotInClass.action",{classId:getSelectClassId()},function(result){
    	list=result;
    	console.log(list)
    	for (let i = 0, index = 1;i<list.length; i++, index++) {
            let tr = $("<tr></tr>");
            tr.append($("<td><input type='checkbox' name='modalItems' val='"+index+"' ></td>"));
            tr.append($("<td></td>").text(index));
            tr.append($("<td></td>").text(list[i].courseName));
            tr.append($("<td></td>").text(list[i].courseId));
            $("#modal_courseNotInClass_list").append(tr);
        }
    });
}

//在模态框中显示在该班中的课程
function getAddedCourseList(){
    $.post(postSrc+"queryCourseInClass.action",{classId:getSelectClassId()},function(result){
    	list=result;
    	for (let i = 0, index = 1;i<list.length; i++, index++) {
            let tr = $("<tr></tr>");
            tr.append($("<td><input type='checkbox' name='modalItems' val='"+index+"' ></td>"));
            tr.append($("<td></td>").text(index));
            tr.append($("<td></td>").text(list[i].courseName));
            tr.append($("<td></td>").text(list[i].courseId));
            $("#modal_courseInClass_list").append(tr);
        }
    });
}

//获取当前课程下的章节信息
function getChapterInCourse(){
	$.post(postSrc+"queryChapterInCourse.action",{classId:getSelectClassId(),courseId:courseId},function (result) {
     	list=result;
        for (let i = 0, index = 1;i<list.length; i++, index++) {
            let tr = $("<tr></tr>");
            
            if(list[i].isFinished===1){
            	tr.append($("<td><input type='checkbox' name='modalItems' disabled></td>"));
            	tr.append($("<td></td>").text(index));
            	tr.append($("<td style='color:red;'></td>").text("已发布"));
                }
            else{
            	tr.append($("<td><input type='checkbox' name='modalItems'></td>"));
            	tr.append($("<td></td>").text(index));
            	tr.append($("<td style='color:green;'></td>").text("未发布"));
            }
            tr.append($("<td></td>").text(list[i].chapterName));
            $("#modal_chapterInCourse_list").append(tr);
        }
  });
};

//获取选中的班级ID
function getSelectClassId(){
    let classIds="";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0; i<items.length; ++i){
        //添加选中的班级
        if(items[i].checked===true){
        	selectItemIndex=i;
        	classIds += classList[i].classId+",";
        }
    }
    return classIds.substring(0,classIds.length-1);
}

//获取选中的班级名称
function getSelectClassName(){
    let classNames="";
    let items = document.getElementById("table-context-body").getElementsByTagName("input");
    for(let i=0;i<items.length;++i){
        //添加选中的班级
        if(items[i].checked===true){
            classNames +=classList[i].className+",";
        }
    }
    return classNames.substring(0,classNames.length-1);
}

//更新表格
function updateTable() {
	firstIndex=(currentPage-1)*pageSize+1;
    for (let i = 0, index = firstIndex;i<classList.length; i++, index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td></td>").text(classList[i].className));
        tr.append($("<td></td>").text(classList[i].teacherName));
        if(classList[i].studentNumber == null)
        	tr.append($("<td></td>").text("0"));
        else
        	tr.append($("<td></td>").text(classList[i].studentNumber));
        tr.append($("<td></td>").text(classList[i].creationTime.substring(0,19)));
        $("#table-body").append(tr);
    }
}

//获取当前页的内容(适用于分页查询)
function getInfo(){
	$("#table-body").html("");
	sendMessage = {className:searchClassName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoClassesByClassName.action",sendMessage,function(result){
        if (result.length - 1 >= pageSize) {
        	classList=result.slice(0,pageSize);
        }
        else {
        	classList=result.slice(0,result.length-1);
        }
        updateTable();
        updateFooter();
        selectChanged();
    });
}
//获取当前页面的内容（不适用于分页）
function getInfoCopy(){
	$("#table-body").html("");
	sendMessage = {className:searchClassName,search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"queryAllInfoClassesByClassName.action",sendMessage,function(result){
        if (result.length - 1 >= pageSize) {
        	classList=result.slice(0,pageSize);
        }
        else {
        	classList=result.slice(0,result.length-1);
        }
        updateTable();
        updateFooter();
        setSelectedIndex();//多了此处，还原选中的班级
        selectChanged();
    });
}

function selectChanged(){
	//判断check_item被选中的个数
	selectNum = $(".selectAll:checked").length;
    let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
    if(statue.checked)
        selectNum-=1;
    if(selectNum===0){
        $("#add-student").attr("disabled", true).addClass("disabled");
        $("#update-courseProgress").attr("disabled", true).addClass("disabled");
        $("#add-course").attr("disabled", true).addClass("disabled");
        $("#remove-student").attr("disabled", true).addClass("disabled");
        $("#remove-course").attr("disabled", true).addClass("disabled");
        $("#delete-class").attr("disabled", true).addClass("disabled");
    }
    else if(selectNum===1){
        $("#add-student").removeAttr("disabled").removeClass("disabled");
        $("#update-courseProgress").removeAttr("disabled").removeClass("disabled");
        $("#add-course").removeAttr("disabled").removeClass("disabled");
        $("#remove-student").removeAttr("disabled").removeClass("disabled");
        $("#remove-course").removeAttr("disabled").removeClass("disabled");
        $("#delete-class").removeAttr("disabled").removeClass("disabled");
    }
    else{
        $("#add-student").attr("disabled", true).addClass("disabled");
        $("#update-courseProgress").attr("disabled", true).addClass("disabled");
        $("#add-course").attr("disabled", true).addClass("disabled");
        $("#remove-student").attr("disabled", true).addClass("disabled");
        $("#remove-course").attr("disabled", true).addClass("disabled");
        $("#delete-class").removeAttr("disabled").removeClass("disabled");
    }
}


//获取模态框中选中的Item
function getSelectModalItems(list,listAdress,itemType){
	//list为当前的列表 ,listAdress当前列表所在的ID,itemType所选择的字段
	let modalItems="";
	//获取当前页面item
	let items = document.getElementById(listAdress).getElementsByTagName("input");
	for(let i=0; i<items.length; ++i){
		//添加选中的项目
		if(items[i].checked===true){
			console.log($(items[i]));
			console.log($(items[i])[0].attributes.val.value);
			modalItems += list[$(items[i])[0].attributes.val.value-1][itemType]+",";
		}
	}
	//删去最后一个逗号
	return modalItems.substring(0,modalItems.length-1);
}

//模态框中进行搜索
function searchModal(itemType){
	let searchInfo=$("#"+this.id).val();    
	$(".modal_list").html("");
	let arr=itemType.data.split("&");
	let j = 0 ;
  	for (let i = 0, index = 1;i<list.length; i++, index++) {
  		//存在字符匹配项  Id可能不是String类型需要强制转换,需注意啊！！
  		if((list[i][arr[0]].toString().indexOf(searchInfo)!=-1)||(list[i][arr[1]].toString().indexOf(searchInfo)!=-1)){
  			let tr = $("<tr></tr>");
  			tr.append($("<td><input type='checkbox' name='modalItems' val='"+index+"'></td>"));
  			tr.append($("<td></td>").text(index));
  			tr.append($("<td></td>").text(list[i][arr[0]]));
  			tr.append($("<td></td>").text(list[i][arr[1]]));
  			$(".modal_list").append(tr);
  		}
  	}
}

//初始化
function init() {
	$("#table-body").html("");
	pageNo=1;
    sendMessage = {search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc+"searchAllInfoClasses.action",sendMessage,function(result){
    	console.log(result);
        setList(result);
    });
}

//将班级查询结果赋值给classList
function setList(result){
	if (result.length - 1 >= pageSize) {
    	classList=result.slice(0,pageSize);
    }
    else {
    	classList=result.slice(0,result.length-1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    updateTable();
    updateFooter();
    selectChanged();
}

