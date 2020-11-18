let maxWidth = window.screen.availWidth;
let maxHeight = window.screen.availHeight;
let postSrc = _URL+"index/";
let workSrc = _URL;
let recommendedWorkList; //精品推荐
let hottestWorkList; //热门票王
let newPublishedWorkList; //新作赏析
let goodWorkList; // 点赞最多
let browseMostWorkList; //浏览最多

$(document).ready(function () {
    getWorkNum();
    queryPopularWork();
    queryLastestWorksByPublished();
    queryLastestWorksByrecommented();
    queryLikestWorks();
    queryPageviewsWorks();
});



//获取累计创作和今日创作
function getWorkNum() {
    $.get(postSrc+"countAllWorks.action",function (result) {
        $("#totolWork").text(result[0].NumberOfAllWorks);
        $("#todayWork").text(result[0].NumberOfTodayWorks);
    });
}


//查询精品推荐
function queryLastestWorksByrecommented() {
  $.get(postSrc+"queryLastestWorksByrecommented.action",function (result) {
      recommendedWorkList = result;
      addWorkCanvas(recommendedWorkList, "#recommended", "recommendedWork",1);
  });
}

//查询热门票王
function queryPopularWork() {
    $.get(postSrc+"queryHottestWorks.action",function (result) {
        hottestWorkList = result;
        addWorkCanvas(hottestWorkList, "#hotKing", "hottestWork",1);
    });
}

//查询新作赏析
function queryLastestWorksByPublished() {
    $.get(postSrc+"queryLastestWorksByPublished.action",function (result) {
    	newPublishedWorkList = result;
        addWorkCanvas(newPublishedWorkList, "#newAppreciation", "newPublishedWork",1);
    });
}

//查询点赞最多
function queryLikestWorks() {
    $.get(postSrc+"queryLikestWorks.action",function (result) {
        goodWorkList = result;
        addWorkCanvas(goodWorkList, "#mostPraise", "goodWork",2);
    });
}

//查询浏览最多
function queryPageviewsWorks() {
    $.get(postSrc+"queryPageviewsWorks.action",function (result) {
        browseMostWorkList = result;
        addWorkCanvas(browseMostWorkList, "#mostBrowse", "browseMostWork",2);
    });
}


//添加canvas
function addWorkCanvas(list, type, id_top, role) {
    let listLength = list.length;
    
    for(let i=0; i<listLength; ++i){
        let workUrl = workSrc + list[i].workLink + list[i].workName + ".png";
        let userPhotoUrl = _URL+"account/"+list[i].userPhotoName;
        let addList = "";
        if(list[i].commentNumber == null)
        	list[i].commentNumber = 0;
        if(role == 1){
        	addList = 
        	'<div class="item">'+
        		'<div class="workText mb-3">'+list[i].courseWorkName+'</div>'+
            	'<div><img id='+'"'+id_top+ i +'" src="'+workUrl+'" class="workCanvas"/></div>'+
            	'<div class="workText mt-3 row">'+
        			'<div class="col" style=""><span>'+list[i].likeCount+'</span>'+'<img class="ml-2" src="/SteamCoding/resources/img/web-15.png" style="width:20px;height:20px;"></div>'+
        			'<div class="col" style=""><span class="mr-2">'+list[i].commentNumber+'</span>'+'<img src="/SteamCoding/resources/img/web-12.png" style="width:20px;height:20px;position:absolute;"></div>'+
            	'</div>'+
            '</div>';	
        }else if(role == 2){
        	addList = 
            	'<div class="item">'+
            		'<div class="workText">'+list[i].authorName+'</div>'+
                	'<div><img src="'+userPhotoUrl+'" class="workCanvas"/></div>'+
                '</div>';
        }
        
        $(type).append(addList);
    }
    //补一个空的
    let addWork = '<div class="col"></div>';
    $(type).append(addWork);
}



//点击获取作品下标
$("#hotKing").on("click",".workCanvas",function(){
    //注意：这里的下标从零开始
    let index=$("#hotKing .workCanvas").index(this);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + hottestWorkList[index].workLink + hottestWorkList[index].workName + '.sb3');
    goWorkInfo(hottestWorkList[index].workId);
});

$("#newAppreciation").on("click",".workCanvas",function(){
    //注意：这里的下标从零开始
    let index=$("#newAppreciation .workCanvas").index(this);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + newPublishedWorkList[index].workLink + newPublishedWorkList[index].workName + '.sb3');
    goWorkInfo(newPublishedWorkList[index].workId);
});

$("#recommended").on("click",".workCanvas",function(){
    //注意：这里的下标从零开始
    let index=$("#recommended .workCanvas").index(this);
    localStorage.removeItem("productionUrl");
    localStorage.setItem("productionUrl", workSrc + recommendedWorkList[index].workLink + recommendedWorkList[index].workName + '.sb3');
    goWorkInfo(recommendedWorkList[index].workId);
});

//跳转至作品详情界面
function goWorkInfo(workId) {
    localStorage.removeItem("workId");
    localStorage.setItem('workId', workId);
    window.open("pages/ProductionContent/ProductionContent.jsp", "_self","");
}

$(".arrowLeft").click(function(e){
	let obj=$(this).parents('.workList').children('.container');
	let scorll = obj.scrollLeft();
	obj.scrollLeft(scorll-156);
});

$(".arrowRight").click(function(e){
	let obj=$(this).parents('.workList').children('.container');
	let scorll = obj.scrollLeft();
	obj.scrollLeft(scorll+156);
});

