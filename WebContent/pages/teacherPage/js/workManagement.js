let workOwner = 'I'; //'I'表示我的作品'others'表示其他作品

//更新作品
function updateTable() {
    $("#work-context").html("");
  //判断是否查询到数据
    if(workList.length === 0){
        $("#work-context").append("<p>无作品信息</p>");
    }
    let count =0;
    let rowNum = Math.ceil(workList.length/5);
    let workSum = workList.length;
    for(let i=0; i<rowNum; ++i){
        let row = '<div class="row work-row">';
        for(let j=0; j<5; ++j){
            let workUrl = workSrc + workList[5*i+j].workLink + workList[5*i+j].workCover;
            row = row+
                '<div class="col-md-1-5 col-sm-1-5 work-col" >'+
                '<div class="card card-style"  id="card'+i+'">'+
                '<img class="card-img-top" id="canvas'+ (5*i+j) +'" src="'+ workUrl + '" style="width: 100%; height: 150px"/>'+
                '<hr style="padding: unset; margin: unset">'+
                '<div class="card-body" style="background-color: #eae9eb; padding: 3%">'+
                '<h5 class="card-title titleStyle" style="text-overflow:ellipsis; overflow:hidden;" title="'+(workList[5*i+j]["courseWorkName"] == null ? workList[5*i+j]["workName"] : workList[5*i+j]["courseWorkName"].split(".")[0])+'">'+ (workList[5*i+j]["courseWorkName"] == null ? workList[5*i+j]["workName"] : workList[5*i+j]["courseWorkName"].split(".")[0]) +'</h5>'+
                '<h6 class="card-title titleStyle" title="'+workList[5*i+j]["createTime"].split(".")[0]+'">'+ workList[5*i+j]["createTime"].split(".")[0] +'</h6>'+
                '<div class="card-text">'+
                '<div style="display:inline-block;width:70%"><label><i class="fa fa-user"></i>'+ workList[5*i+j].userName+'</label></div>'+
                '<div style="display:inline-block;width:30%;text-align:right"><label><i class="fa fa-thumbs-up"></i>'+ workList[5*i+j].likeCount+'</label></div>'+
                '</div>';
            if(workOwner === 'I'){
                if(isPublished === 1){
                    row = row +  '<div class="card-text row editAndDelete">'+
                        '<div class="col-4 delete-work-btn"><button type="button" data-toggle="modal" data-target="#delete-work-modal" class="btn btn-danger btn-sm btnStyle" id="deleteScratch">删除</button></div>'+
                        '<div class="col-4 edit-work-btn"><button type="button" class="btn btn-success btn-sm btnStyle" id="editScratch">修改</button></div>'+
                        '<div class="col-4 cancel-publish-btn"><button  type="button" class="btn btn-warning btn-sm btnStyle" id="cancelPublish" data-toggle="modal" data-target="#cancel-publish-modal">撤销</button></div>'+
                        '</div>';
                }

                else if (isPublished === 0){
                    row = row +  '<div class="card-text row editAndDelete">'+
                        '<div class="col-4 delete-work-btn"><button type="button" data-toggle="modal" data-target="#delete-work-modal" class="btn btn-danger btn-sm btnStyle" id="deleteScratch">删除</button></div>'+
                        '<div class="col-4 edit-work-btn"><button type="button" class="btn btn-success btn-sm btnStyle" id="editScratch">修改</button></div>'+
                        '<div class="col-4 publish-work-btn"><button  type="button" class="btn btn-warning btn-sm btnStyle" id="publishScratch" data-toggle="modal" data-target="#publish-work-modal">发布</button></div>'+
                        '</div>';
                }
            }
            else if(workOwner == 'others'){
            	row = row +  '<div class="card-text row editAndDelete">'+
                '<div style="margin: 0 auto"><button type="button" class="btn btn-light btn-sm" style="width: 10rem" id="checkCode">查看代码</button></div>'+
                '</div>';
            }
            row = row+
                '</div>'+
                '</div>'+
                '</div>';
            count++;
            if(count === workSum){
                break;
            }


        }
        row = row+'</div>';
        $("#work-context").append(row);
    }
}

//加载
initServer();

$('#workDescription').bind('input propertychange',function(){
    $('#count').html($(this).val().length);
});