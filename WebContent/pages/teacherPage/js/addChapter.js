let chapterId;
let chapterName;
let courseId;
let courseName;
let chapterDetials;
let formData = new FormData();
let postSrc=_URL+"course/";

$(document).ready(function(){
	courseId = GetUrlParam("courseId");
	courseName = decodeURI(GetUrlParam("courseName"));
	getChapters();
});

//获取参数方法
function GetUrlParam(name){
 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
 var r = encodeURI(window.location.search).substr(1).match(reg);
 if(r!=null)return unescape(r[2]); return null;
}

function getChapters() {
    $.post(postSrc + "queryChapterInCourse.action", {"courseId": courseId}, function (result) {
        $("#chapterList").children().remove();
        chapterDetials=result;
        for(let i=0;i<result.length;i++){
            let chapterItem="";
            if(!i)
                chapterItem ='<li  class="curr">';
            else
                chapterItem='<li  class="">';
            chapterItem = chapterItem +
                '<div class="bor">' +
                '<div class="tit W_f14">' + result[i].chapterName + '</div>' +
                '<div class="time S_txt2">' +
                '<span class="time">' + result[i].createTime.replace("T"," ") + '</span></div>' +
                '</div>' +
                '</li>'
            $("#chapterList").append(chapterItem);
        }
        let list=document.getElementById("chapterList").getElementsByTagName("li");
        for(let i=0;i<list.length;i++){
            list[i].index=i;
            list[i].onclick=function () {
            }
        }

        chapterId = result[0].chapterId;
        chapterName = result[0].chapterName;
        showChapterDetail(chapterDetials[0]);
        $("#chapterList li").click(function () {
            $(this).siblings('li').removeClass('curr');
            $(this).addClass('curr');
            showChapterDetail(chapterDetials[this.index])
            chapterId = chapterDetials[this.index].chapterId;
            chapterName = chapterDetials[this.index].chapterName; 
        });
    })
}

//新建章节
$("#newChapter").click(function () {
    $.post(postSrc+"addNewChapter.action",{"courseId":courseId},function (result) {
        getChapters();
    })
})

//在右侧显示章节的内容
function showChapterDetail(obj) {
    $("#chapterName").val(obj.chapterName);
    $("#chapterContent").summernote('code',obj.chapterContent);
}

//预览
$("#shutView").click(function(){
    let chapterName=$("#chapterName").val();
    let chapterContent=$("#chapterContent").summernote('code');
});

//更新章节
$("#updateChapterCommit").click(function () {
    updateChapter();
    getChapters();
});

//删除章节
$("#deleteChapter").click(function () {
    let deleteWindow=window.confirm("确定删除该章节: " + chapterName + " ?");
    if(deleteWindow){
        $.post(postSrc+"deleteChapterByChapterId.action",{"chapterId":chapterId},function (result) {
            getChapters();
        });
    }
});


function updateChapter(){
    let chapterName=$("#chapterName").val();
    let chapterContent=$("#chapterContent").summernote('code');
    if(formData.has("chapterName"))
        formData.delete("chapterName");
    formData.append("chapterName",chapterName);
    if(formData.has("chapterContent"))
        formData.delete("chapterContent");
    formData.append("chapterContent",chapterContent);
    if(formData.has("courseId"))
        formData.delete("courseId");
    formData.append("courseId",courseId);
    if(formData.has("chapterId"))
        formData.delete("chapterId");
    formData.append("chapterId",chapterId);

    $.ajax({
        url : postSrc+'updateChapterForCourse.action',
        type : 'POST',
        async : false,
        data : formData,
        processData : false,
        contentType : false,
        success:function (data) {
        }
    });
}

$(function(){
    d=$('.summernote').summernote({
        height: 400,
        tabsize: 2,
        lang: 'zh-CN',
    });
})

let timer = setInterval(function(){ countdown() },1000); 
let count = 0;
function countdown () {
    if(count < 600 )
    	count++;
    else if(count >= 600){
    	count = 0;
    	updateChapter();
        getChapters();
    }
};
