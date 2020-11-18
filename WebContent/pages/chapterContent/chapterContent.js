let postSrc=_URL+"index/";
let chapterId;

$(document).ready(function(){
    getChapterId();
    getChapterContent();
});

function getChapterId() {
    let str=window.location.href;
    let  attStr=window.location.href.split("=");
    chapterId=attStr[1];
}

function getChapterContent() {
    $.get(postSrc+"getChapterInfo.action",{chapterId:chapterId},function (result) {
        $("#chapterTitle").append("<h1>"+result.chapterName+"</h1>");
        $("#chapterContent").append(result.chapterContent);
    });
}