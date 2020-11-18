let sendMessage = { homeworkId: null, search: null, pageNo: null, pageSize: null, sorters: null, homeworkId: null, filters: "{}", username: null }; //定义发送的数据
let homeworkList;//表格中使用的列表
let postSrc = _URL + "homework/";
let userName = '';
let toDate = '';
let filters = '{}';
let operate = -1;
let searchClassName = "所有班级";
init();

/***************下载作业******************/
$("#downloadWork").click(function () {
    window.open(_URL + homeworkList[getSelectedIndex()].sourceLink);
});

$('#homeworkEvaluation').bind('input propertychange', function () {
    $('#count').html($(this).val().length);
});

//跳转至player.html页面
function goPlayer(workUrl, isClick) {
    localStorage.setItem('workUrl', workUrl + '.sb3');
    localStorage.setItem('isClick', isClick);
    window.open("../../../build/tmp/index.html", "", "");  //第二个参数：_self，在当前窗口打开窗口；_blank（默认值），在另外的新建窗口打开新窗口；
}

$("#viewWork").click(function () {
    // goPlayer(_URL+homeworkList[getSelectedIndex()].workLink, true);
    const workId = homeworkList[getSelectedIndex()].workId;
    window.open('../../../build/tmp/index.html' + '?id=' + workId, '', "");
});

/***************作业批改******************/
$("#evaluateHomework").click(function () {
    operate = 1;
});

/***************修改批改******************/
$("#checkEvaluation").click(function () {
    operate = 2;
});

$("#homeworkEvaluation").change(function () {
    $("#count").html($(this).val().length);
});

$('#evaluateHomework-modal').on('show.bs.modal', function () {
    if (getSelectedNum() === 1) {
        if (homeworkList[getSelectedIndex()].workLink)
            $("#viewWork").removeAttr("disabled").removeClass("disabled");
        else
            $("#viewWork").attr("disabled", true).addClass("disabled");
        if (homeworkList[getSelectedIndex()].sourceLink)
            $("#downloadWork").removeAttr("disabled").removeClass("disabled");
        else
            $("#downloadWork").attr("disabled", true).addClass("disabled");
    }
    else {
        $("#viewWork").attr("disabled", true).addClass("disabled");
        $("#downloadWork").attr("disabled", true).addClass("disabled");
    }
    if (operate === 2) {
        $("#homeworkEvaluation").val(homeworkList[getSelectedIndex()].evaluation);
        $("#homeworkScore").val(homeworkList[getSelectedIndex()].score);
        $("#count").html($("#homeworkEvaluation").val().length);
    }
    else if (operate === 1) {
        $("#homeworkEvaluation").val("");
        $("#homeworkScore").val("");
        $("#count").text("0");
    }
});

// $("#sureToEvaluateHomework").click(function(){
// 	let homeworkEvaluation = $("#homeworkEvaluation").val();
// 	let homeworkScore =  $("#homeworkScore").val();
// 	if(homeworkEvaluation === ""){
// 		alert("请填写作业评价!");
// 	}
// 	else if(homeworkScore === ""){
// 		alert("请填写作业分数!")
// 	}
// 	else{
// 		$.post(postSrc+"evaluateSubmitedHomework.action",{studentIds: getSudentIds(), homeworkId: window.location.href.split("&")[1], evaluation: homeworkEvaluation, score: homeworkScore},function(result){
// 			if(result[0].result == "批阅成功"){
// 				alert(result[0].result);
// 				$('#evaluateHomework-modal').modal('hide');
// 			}
//             currentPage = 1;
//             $("#current-page").attr("placeholder", currentPage);
// 			init();
// 		});
// 	}	
// });

$("#sureToEvaluateHomework").click(function () {
    let homeworkEvaluation = $("#homeworkEvaluation").val();
    let homeworkScore = $("#homeworkScore").val();
    if (homeworkScore === "") {
        // alert("请填写作业评价!");
        $("#homeworkScore").addClass("is-invalid");
    }
    else {
        $("#homeworkScore").addClass("is-valid");
    }
    if (homeworkEvaluation === "" || $("#count").html() > 500) {
        // alert("请填写作业评价!");
        $("#homeworkEvaluation").addclass("is-invalid");
    }
    else {
        $("#homeworkEvaluation").addClass("is-valid");
    }
    if ($("#homeworkEvaluation").hasClass("is-valid") && $("#homeworkScore").hasClass("is-valid")) {
        $.post(postSrc + "evaluateSubmitedHomework.action", { studentIds: getSudentIds(), homeworkId: window.location.href.split("&")[1], evaluation: homeworkEvaluation, score: homeworkScore }, function (result) {
            if (result[0].result == "批阅成功") {
                alert(result[0].result);
                $('#evaluateHomework-modal').modal('hide');
                $("#homeworkEvaluation").removeClass("is-valid");
                $("#homeworkEvaluation").removeClass("is-invalid");
                $("#homeworkScore").removeClass("is-valid");
                $("#homeworkScore").removeClass("is-invalid");
            }
            currentPage = 1;
            $("#current-page").attr("placeholder", currentPage);
            init();
        });
    }
});

$("#dismissEvaluateHomeworkModal").click(function () {
    $("#homeworkEvaluation").removeClass("is-valid");
    $("#homeworkEvaluation").removeClass("is-invalid");
    $("#homeworkScore").removeClass("is-valid");
    $("#homeworkScore").removeClass("is-invalid");
});


//初始化
function init() {
    let str = window.location.href;
    let attStr = window.location.href.split("&");
    $("#table-body").html("");
    pageNo = 1;
    sendMessage = {filters: filters,className:"所有班级",homeworkId: window.location.href.split("&")[1], userName: userName, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    $.post(postSrc + "querySubmitedHomework.action", sendMessage, function (result) {
        setList(result);
    });
    addClassInfo();
}

function setList(result) {
    if (result.length - 1 >= pageSize) {
        homeworkList = result.slice(0, pageSize);
    }
    else {
        homeworkList = result.slice(0, result.length - 1);
    }
    totalPage = result[result.length - 1].totalPage;//设置页面总数
    totalItem = result[result.length - 1].totalCount;//设置数据总数
    updateTable();
    updateFooter();
    selectChanged();
}

//更新表格
function updateTable() {
    firstIndex = (currentPage - 1) * pageSize + 1;
    for (let i = 0, index = firstIndex; i < homeworkList.length; i++ , index++) {
        let tr = $("<tr></tr>");
        tr.append($("<td><input type='checkbox' name='items' class='selectAll'></td>"));
        tr.append($("<td></td>").text(index));
        tr.append($("<td class='homeworkInfo'></td>").text(homeworkList[i].userName));
        tr.append($("<td class='homeworkInfo'></td>").text(homeworkList[i].className));
        tr.append($("<td class='homeworkInfo'></td>").text(homeworkList[i].creationTime.replace("T", " ")));
        if (homeworkList[i].score === null) {
            tr.append($("<td class='homeworkInfo'></td>").text("未批改"));
        }
        else {
            tr.append($("<td class='homeworkInfo'></td>").text(homeworkList[i].score));
        }
        $("#table-body").append(tr);
    }
}

//获取当前页的内容(适用于分页查询)
function getInfo() {
    $("#table-body").html("");
    sendMessage = { homeworkId: window.location.href.split("&")[1], userName: userName, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]", filters: filters };
    $.post(postSrc + "querySubmitedHomework.action", sendMessage, function (result) {
        setList(result);
    });
}

//获取选中学生的ID
function getSudentIds() {
    let userIds = "";
    let items = document.getElementById("table-body").getElementsByTagName("input");
    for (let i = 0; i < items.length; ++i) {
        if (items[i].checked === true)
            userIds += homeworkList[i].userId + ",";
    }
    return userIds.substring(0, userIds.length - 1);
}

//获取列表中选中的个数
function getSelectedNum() {
    let count = 0;
    let items = document.getElementById("table-body").getElementsByTagName("input");
    for (let i = 0; i < items.length; ++i) {
        if (items[i].checked === true)
            count++;
    }
    return count;
}


//获取选中的下标(只在选中项为1时适用)
function getSelectedIndex() {
    let items = document.getElementById("table-body").getElementsByTagName("input");
    for (let i = 0; i < items.length; ++i) {
        if (items[i].checked === true)
            return i;
    }
}

function selectChanged() {
    //判断check_item被选中的个数
    selectNum = $(".selectAll:checked").length;
    let statue = document.getElementById("CheckAll");//控制全选的复选框不纳入selectNum
    if (statue.checked)
        selectNum -= 1;
    $("#evaluateHomework").removeAttr("disabled").removeClass("disabled");
    if (selectNum === 0) {
        //    	$("#downloadHomework").attr("disabled", true).addClass("disabled");
        $("#evaluateHomework").attr("disabled", true).addClass("disabled");
        $("#checkEvaluation").attr("disabled", true).addClass("disabled");
    }
    else if (selectNum === 1) {
        //  	$("#downloadHomework").removeAttr("disabled").removeClass("disabled");
        if (homeworkList[getSelectedIndex()].evaluation !== null && homeworkList[getSelectedIndex()].score !== null) {
            $("#evaluateHomework").attr("disabled", true).addClass("disabled");
        }
        else {
            $("#evaluateHomework").removeAttr("disabled").removeClass("disabled");
        }
        if (homeworkList[getSelectedIndex()].evaluation !== null && homeworkList[getSelectedIndex()].score !== null) {
            $("#checkEvaluation").removeAttr("disabled").removeClass("disabled");
        }
    }
    else {
        //	$("#downloadHomework").removeAttr("disabled").removeClass("disabled");
        $("#checkEvaluation").attr("disabled", true).addClass("disabled");
        if (homeworkList[getSelectedIndex()].evaluation !== null && homeworkList[getSelectedIndex()].score !== null) {
            $("#evaluateHomework").attr("disabled", true).addClass("disabled");
        }
        else {
            $("#evaluateHomework").removeAttr("disabled").removeClass("disabled");
        }
    }
}

//设置datepicker
$('.form_date').datetimepicker({
    language: 'cn',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0,
    format: "yyyy-mm-dd"
});

//查询作业
$("#query").click(function () {
    updateCascadingQueryConditions();
    init();
});

$("#reset").click(function () {
    $("#search-student").val("");
    $("#from_date").val("");
    $("#to_date").val("");
    $("#choseClassButton").html("所有班级");
    searchClassName = "所有班级";
    userName = "";
    fromDate = "";
    toDate = "";
});

//获取查询的学生名称
$("#search-student").change(function () {
    userName = $("#search-student").val();
});

//修改级联查询条件
function updateCascadingQueryConditions() {
    filters = "";
    if (searchClassName !== "所有班级") {
        filters = filters + "{\"field\": \"className\",\"op\":\"bw\", \"data\":\"" + "%" + searchClassName + "%\"},";
    }
    if (userName !== "") {
        filters = filters + "{\"field\": \"userName\",\"op\":\"bw\", \"data\":\"" + "%" + userName + "%\"},";
    }
    if (fromDate !== "") {
        filters = filters + "{\"field\": \"creationTime\",\"op\":\"ge\", \"data\":\"" + fromDate + "\"},";
    }
    if (toDate !== "") {
        filters = filters + "{\"field\": \"creationTime\",\"op\":\"le\", \"data\":\"" + toDate + "\"},";
    }
    
    //若不为空
    if (filters !== "") {
        filters = filters.substr(0, filters.length - 1); //吃掉最后一个逗号
        filters = "{\"groupOp\":\"and\",\"rules\": [" + filters + "]}";
    } else {
        filters = "{}";
    }
}
//添加班级至下拉列表
function addClassInfo() {
    $.post(postSrc + "queryClassesByUserId.action", function (result) {
        $("#drapdown-menu").html("");
        $('#drapdown-menu').append('<a class="dropdown-item">所有班级</a>');
        for (let i = 0; i < result.length; ++i) {
            addmessage = '<a class="dropdown-item">' + result[i].className + '</a>';
            $('#drapdown-menu').append(addmessage);
        }
    });
}
//选择班级
$("#drapdown-menu").on("click", "a", function () {
    searchClassName = $(this).text();
    $("#choseClassButton").html(searchClassName);
});




