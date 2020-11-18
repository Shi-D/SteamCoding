let postSrc=_URL+"index/";
let classId;
let courseId;

$(document).ready(function(){
    getClassId();
    getCourseInfo();
});

//获取当前的课程id
function getClassId(){
    let str=window.location.href;
    let  attStr=window.location.href.split("&");
    classId=attStr[1];
    courseId=attStr[2];
}

function getCourseInfo() {
    $.get(postSrc+"getCourseInfo.action",{courseId:courseId},function (result) {
        let courseCover =  '<img  src="'+_URL+"account/"+result[0].CourseInfo.courseFolderName+result[0].CourseInfo.courseCover+ '" style="width:100%;height: auto;">';
        $('#courseCover').append(courseCover);
        $('#organizationIntroduction').html(result[0].OrganizationName);
        $('#teacherIntroduction').html(result[0].OwnerName);
        $('#courseIntroduction').html(result[0].CourseInfo.courseIntroduction);
    })
    
    if(classId==-1){
    	$.get(postSrc+"queryChapterInPublishedCourse.action",{courseId:courseId},function (result) {
            for(let i=0;i<result.length;i++){
                let chapterItem=
                    '<div class="row mt-2 ">'+
                        '<p class="col-8 ml-3 ">'+result[i].chapterName+'</p>'+
                        '<input type="button" class="btn btn-info btn-sm col-2" value="开始学习" onclick="window.location.href=\'../chapterContent/chapterContent.jsp?chapterId='+result[i].chapterId+'\'" />'+
                    '</div>';
               $("#courseChapter").append(chapterItem);
            }
        });
    }else{
    	$.get(postSrc+"queryChatperInCourse.action",{classId:classId,courseId:courseId},function (result) {
            for(let i=0;i<result.length;i++){
                let chapterItem=
                    '<div class="row mt-2 ">'+
                        '<p class="col-8 ml-3 ">'+result[i].chapterName+'</p>'+
                        '<input type="button" class="btn btn-info btn-sm col-2" value="开始学习" onclick="window.location.href=\'../chapterContent/chapterContent.jsp?chapterId='+result[i].chapterId+'\'" />'+
                    '</div>';
               $("#courseChapter").append(chapterItem);
            }
        });
    }
}




