let scrolled = false;//防止充重入开关
let off_on = true; //下拉加载开关
let pageSize = 16; //页面数据条数
let pageNo = 1; //页面
let totalCount = 0; //action中获取的作品总数
let workNum = 0; //当前action获取的页面作品数量
let all_pageNo = 1;
let all_totalCount = -1;
let all_currentCount = 0;
let new_pageNo = 1;
let new_totalCount = -1;
let new_currentCount = 0;
let hot_pageNo = 1;
let hot_totalCount = -1;
let hot_currentCount = 0;
let good_pageNo = 1;
let good_totalCount = -1;
let good_currentCount = 0;

let allWorkList = [];
let newWorkList = [];
let hotWorkList = [];
let goodWorkList = [];

let workList;
let postSrc=_URL+"index/queryPopularWorks.action";
let type = 'creationTime'; //creationTime表示最新发布，num表示最热评论，likeCount表示点赞优先, all表示进行筛选
//let sendMessage = {filters: null, search: true, pageNo: pageNo, pageSize: pageSize, sorters: [{"property":"creationTime","direction":"DESC"}]};
let sendMessage = {filters: null, search: true, pageNo: pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};

let workSrc = _URL;

let browserHeight;
let windowHeight;
let documentHeight;

let currentTime;
let isQuery=false;
let condition="";

$(document).ready(function() {
    $('#logIn').click(function () {
        document.getElementById('logIn').style.display = "none";
        document.getElementById('logOut').style.display = "block";
    });
    $('#logOut').click(function () {
        document.getElementById('logIn').style.display = "block";
        document.getElementById('logOut').style.display = "none";
    });

    currentTime = getCurrentTime();

    $("#productionTabs div").idTabs("selectScratchProgrammingNew");
    //sendMessage = {filters: getFilters(), search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: [{"property":"creationTime","direction":"DESC"}]};
    sendMessage = {filters: getFilters(), search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};

    getWorkInfo();

    scopeLoad(); //进行下拉加载


    //给页面绑定滑轮滚动事件
    if (document.addEventListener) {
        document.addEventListener('DOMMouseScroll', scrollFunc, false);
    }
    //滚动滑轮触发scrollFunc方法
    window.onmousewheel = document.onmousewheel = scrollFunc;
    
});


function getFilters(){
    let filter = "{\"field\": \"createTime\",\"op\":\"le\", \"data\":\""+currentTime+"\"}";
    return "{\"groupOp\":\"and\",\"rules\": [" + filter + "]}";
}
function setFilters(f){
	let filter = "{\"field\": \""+f+"\",\"op\":\"le\", \"data\":\""+currentTime+"\"}";
    return "{\"groupOp\":\"and\",\"rules\": [" + filter + "]}";
}

//切换idtabs并更新下拉加载参数
$("#selectScratchProgrammingNew").click(function () {
    isQuery=false;
	$("#scratchProgrammingAll").empty();
	$("#scratchProgrammingNew").empty();
	$("#scratchProgrammingHot").empty();
	$("#scratchProgrammingGood").empty();
	new_pageNo = 1;
	new_totalCount = -1;
	new_currentCount = 0;
    type = 'creationTime';
    off_on = new_currentCount !== new_totalCount;
    sendMessage = {filters: getFilters(), search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
    getWorkInfo();
});

$("#selectScratchProgrammingHot").click(function () {
    isQuery=false;
	$("#scratchProgrammingAll").empty();
	$("#scratchProgrammingNew").empty();
	$("#scratchProgrammingHot").empty();
	$("#scratchProgrammingGood").empty();
	hot_pageNo = 1;
	hot_totalCount = -1;
	hot_currentCount = 0;
    type = 'num';
    off_on = hot_currentCount !== hot_totalCount;
    sendMessage = {filters: getFilters(), search: true, pageNo: hot_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"num\",\"direction\":\"DESC\"}]"};
    getWorkInfo();
});

$("#selectScratchProgrammingGood").click(function () {
    isQuery=false;
	$("#scratchProgrammingAll").empty();
	$("#scratchProgrammingNew").empty();
	$("#scratchProgrammingHot").empty();
	$("#scratchProgrammingGood").empty();
	good_pageNo = 1;
	good_totalCount = -1;
	good_currentCount = 0;
    type = 'likeCount';
    off_on = good_currentCount !== good_totalCount;
    sendMessage = {filters: getFilters(), search: true, pageNo: good_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"likeCount\",\"direction\":\"DESC\"}]"};
    getWorkInfo();
});

$("#query").click(function(){
    isQuery=true;
	let searchWork=$("#searchBarWorkName").val(),
		searchAuthor=$("#searchBarAuthorName").val();
	$("#scratchProgrammingAll").empty();
	$("#scratchProgrammingNew").empty();
	$("#scratchProgrammingHot").empty();
	$("#scratchProgrammingGood").empty();
	all_pageNo = 1;
	all_totalCount = -1;
	all_currentCount = 0;
	
	condition = "";
	if(searchWork !== ""){
        condition = condition + "{\"field\": \"courseWorkName\",\"op\":\"bw\", \"data\":\""+"%"+searchWork+"%\"},";
	}
	if(searchAuthor !== ""){
	    condition = condition + "{\"field\": \"authorName\",\"op\":\"bw\", \"data\":\""+"%"+searchAuthor+"%\"},";
	}
	//若不为空
	if(condition !== ""){
	    condition = condition.substr(0,condition.length-1);
	    condition = "{\"groupOp\":\"and\",\"rules\": [" + condition + "]}";
	}else{
		condition=getFilters();
	}
	off_on = all_currentCount !== all_totalCount;
    //sendMessage = {filters: filters, search: true, pageNo: all_pageNo, pageSize: pageSize, sorters: [{"property":"creationTime","direction":"DESC"}]};
	if(type=="creationTime") sendMessage = {filters: condition, search: true, pageNo: all_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
	else if(type=="num") sendMessage = {filters: condition, search: true, pageNo: all_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"num\",\"direction\":\"DESC\"}]"};
    else if(type=="likeCount") sendMessage = {filters: condition, search: true, pageNo: all_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"likeCount\",\"direction\":\"DESC\"}]"};
     
	getWorkInfo();
})
$("#reset").click(function(){
	$("#searchBarWorkName").val("");
	$("#searchBarAuthorName").val("");
})

function scrollFunc (e) {
    e = e || window.event;
    if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件
        if (e.wheelDelta < 0) { //当滑轮向下滚动时
            isLoad();
        }
    }
    else if (e.detail) {  //Firefox滑轮事件
        if (e.detail< 0) { //当滑轮向下滚动时
            isLoad();
        }
    }
}

//判断加载
function isLoad() {
    browserHeight = jQuery(window).scrollTop();
    windowHeight = jQuery(window).height();
    documentHeight = jQuery(document).height();
    if ((browserHeight+ windowHeight + 10)>= documentHeight) {

        if (scrolled)        //防止重入
            return false;
        scrolled = true;     //锁定函数
        if (off_on) {
            if(new_currentCount === new_totalCount){
                off_on = false;
            }
            else if(hot_currentCount === hot_totalCount){
                off_on = false;
            }
            else if(good_currentCount === good_totalCount){
                off_on = false;
            }
            getWorkInfo();
            $("#loadInfo").modal({backdrop: 'static', keyboard: false, show: true});
        }
        //延时解锁，不能直接解锁的原因是此时第二个函数调用
        //还在消息队列里，此处直接解锁等于没有上锁
        setTimeout(releaseLockScopeLoad, 1000);
    }
}


//下拉加载
function scopeLoad() {
    jQuery(document).scroll(function () {
        isLoad();
    });
}

function releaseLockScopeLoad() {
    scrolled=false;
    $("#loadInfo").modal('hide');
}


//获取后端作品信息
function getWorkInfo() {
    $.post(postSrc,sendMessage,function (result){
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

        workNum = result.length-1;

        for(let i=0; i<workNum; ++i){
            if(array[i].likeCount === null) {
                array[i].likeCount = 0;
            }
            if(array[i].num === null){
                array[i].num = 0;
            }
            array[i].workName = array[i].workName.replace("T"," ");
        }



        workList = array;

        if(type === 'creationTime'){

            addNode(array);
            new_totalCount = totalItem;
            new_currentCount = new_currentCount+workNum;

            off_on = new_currentCount !== new_totalCount;

            new_pageNo++;
            if(!isQuery) sendMessage = {filters: getFilters(), search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
            else sendMessage = {filters: condition, search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"creationTime\",\"direction\":\"DESC\"}]"};
            
            addProductionMessage(workList, '#scratchProgrammingNew');
        }
        else if(type === 'num'){

            addNode(array);
            hot_totalCount = totalItem;
            hot_currentCount = hot_currentCount+workNum;

            off_on = hot_currentCount !== hot_totalCount;

            hot_pageNo++;
            if(!isQuery) sendMessage = {filters: getFilters(), search: true, pageNo: hot_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"num\",\"direction\":\"DESC\"}]"};
            else sendMessage = {filters: condition, search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"num\",\"direction\":\"DESC\"}]"};
            
            addProductionMessage(workList, '#scratchProgrammingHot');
        }
        else if(type === 'likeCount'){
        	
        	addNode(array);
            console.log(goodWorkList);
            good_totalCount = totalItem;
            good_currentCount = good_currentCount+workNum;

            off_on = good_currentCount !== good_totalCount;

            good_pageNo++;
            if(!isQuery) sendMessage = {filters: getFilters(), search: true, pageNo: good_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"likeCount\",\"direction\":\"DESC\"}]"};
            else sendMessage = {filters: condition, search: true, pageNo: new_pageNo, pageSize: pageSize, sorters: "[{\"property\":\"likeCount\",\"direction\":\"DESC\"}]"};
            
            addProductionMessage(workList, '#scratchProgrammingGood');
        }

    });
}


//添加节点
//function addNode(workArray, array) {
//	workArray.length = 0;
//    let length = array.length;
//    for(let i=0; i<length; ++i){
//        workArray.push(array[i]);
//    }
//}

function addNode(array) {
	if(type === 'creationTime'){
		let length = array.length;
	    for(let i=0; i<length; ++i){
	    	newWorkList.push(array[i]);
	    }
	}else if(type === 'num'){
	    let length = array.length;
	    for(let i=0; i<length; ++i){
	    	hotWorkList.push(array[i]);
	    }
	}else if(type === 'likeCount'){
	    let length = array.length;
	    for(let i=0; i<length; ++i){
	    	goodWorkList.push(array[i]);
	    }
	}
}



function addProductionMessage(ProductionMessage, id) {

    let numOfProduction = ProductionMessage.length;
    let rowNum = Math.ceil(numOfProduction/4);
    let count = 0;
    
    for(let i = 0; i <= rowNum; ++i){
        let addmessage = '<div class="row">';
        for(let j = count; j<numOfProduction; ++j){
            let workUrl = workSrc + workList[4*i+j].workLink + workList[4*i+j].workName + ".png";
            addmessage = addmessage + '<div class="card courseCrad col-3 ">'
                + '<img class="card-img-top" src="'+workUrl+'" alt="Card image cap">'
                + '<div class="card-body">'
                + '<h5 class="card-title" style="text-overflow:ellipsis; overflow:hidden;">'+(ProductionMessage[4*i+j].courseWorkName!==null?ProductionMessage[4*i+j].courseWorkName:ProductionMessage[4*i+j].workName)+'</h5>'
                + '<span>'
                + '<img src="../../resources/ico/discuss.png" height="24" width="24"/>'
                + '<span>'+ProductionMessage[4*i+j].num+'</span>'
                + '<img src="../../resources/ico/thumbsUp.png" height="24" width="24"/>'
                + '<span>'+ProductionMessage[4*i+j].likeCount+'</span>'
                + '</span>'
                + '<p class="card-text">'+ProductionMessage[4*i+j].authorName+'</p>'
                + '</div>'
                + '</div>';
            count++;

        }
        addmessage = addmessage+'</div>';
        $(id).append(addmessage);
    }

}

function getNow(s) {
    return s < 10 ? '0' + s: s;
}

function getCurrentTime(){
    let myDate = new Date();
    //获取当前年
    let year=myDate.getFullYear();
    //获取当前月
    let month=myDate.getMonth()+1;
    //获取当前日
    let date=myDate.getDate();
    let h=myDate.getHours();       //获取当前小时数(0-23)
    let m=myDate.getMinutes();     //获取当前分钟数(0-59)
    let s=myDate.getSeconds();

    let now=year+'-'+getNow(month)+"-"+getNow(date)+" "+getNow(h)+':'+getNow(m)+":"+getNow(s);
    return now;
}


$("#scratchProgrammingNew").on("click",".courseCrad",function(){
    //注意：这里的下标从零开始
    let index=$("#scratchProgrammingNew .courseCrad").index(this);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + newWorkList[index].workLink + newWorkList[index].workName + '.sb3');
    goWorkInfo(newWorkList[index].workId);
});

$("#scratchProgrammingHot").on("click",".courseCrad",function(){
    //注意：这里的下标从零开始
    let index=$("#scratchProgrammingHot .courseCrad").index(this);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + hotWorkList[index].workLink + hotWorkList[index].workName + '.sb3');
    goWorkInfo(hotWorkList[index].workId);
});

$("#scratchProgrammingGood").on("click",".courseCrad",function(){
    //注意：这里的下标从零开始
    let index=$("#scratchProgrammingGood .courseCrad").index(this);
    console.log(index);
    console.log(goodWorkList);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + goodWorkList[index].workLink + goodWorkList[index].workName + '.sb3');
    goWorkInfo(goodWorkList[index].workId);
});



//跳转至作品详情界面
function goWorkInfo(workId) {
    localStorage.removeItem("workId");
    localStorage.setItem('workId', workId);
    window.open("../ProductionContent/ProductionContent.jsp", "_self","");
}




























