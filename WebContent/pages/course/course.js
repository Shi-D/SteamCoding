let sendMessage = {className: null, search: null, pageNo: null, pageSize: null, sorters: null}; //定义发送的数据
let postSrc = _URL+"index/";
function initPage() {
    $.post(postSrc+"queryAllIsPublishedCourse.action",function (result) {
        for(let i=0;i<result.length;++i){
            let row = '<div class="col-3">';
                row = row+
                    '<div class="col-md-1-5 col-sm-1-5"  style="padding: 1%">'+
                    '<div class="card card-style">'+
                    '<img class="card-img-top" style="width: 100%; height: 150px" src="'+_URL+"account/"+result[i].courseFolderName+result[i].courseCover+ '" alt="Card image cap" name="courseCover">'+
                    '<div class="card-body">'+
                    '<h5 class="card-title" id="CourseName" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap" title="'+result[i].courseName+'">'+result[i].courseName+'</h5>'+
                    '<p class="card-text" style="-webkit-line-clamp: 1;-webkit-box-orient: vertical;font-size:14px;color:gray;overflow: hidden;display: -webkit-box;" title="'+result[i].courseIntroduction+'">'+result[i].courseIntroduction+'</p>'+
                    '<a href="../courseContent/courseContent.jsp?&'+(-1)+"&"+result[i].courseId+'" class="btn btn-primary" >'+"开始学习"+'</a>'+
                    '</div>'+
                    '</div>'+
                    '</div>';
            row = row+'</div>';
            $("#highQualityCourses").append(row);
        }
    })
}
//加载
initPage();