
let commentInfo;
let workId;

let currentUser = '';

let currentUserId = '';

let userPhoto = '';

let sendMessage = {workId: null, content: '', replyUserId: '', replyCommentId: null};

let fhN; //回复的名字



/***获取当前时间***/
function getCurrentTime() {
    let myDate = new Date();
    //获取当前年
    let year=myDate.getFullYear();
    //获取当前月
    let month=myDate.getMonth()+1;
    //获取当前日
    let date=myDate.getDate();
    let h=myDate.getHours();       //获取当前小时数(0-23)
    let m=myDate.getMinutes();     //获取当前分钟数(0-59)
    if(m<10) m = '0' + m;
    let s=myDate.getSeconds();
    if(s<10) s = '0' + s;
    return year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
}



/***textarea高度自适应***/

    $(function () {
        $('.content').flexText();
    });

<!--textarea限制字数-->

function keyUP(t){
    let len = $(t).val().length;
    if(len > 139){
        $(t).val($(t).val().substring(0,140));
        alert("最多输入140字! 当前字数" + len);
    }
}


function fixTime(time) {
    return time.replace('T', ' ');
}



/***加载评论信息***/
function addCommentInfo(){
    let infoLength = commentInfo.length;
    let i;
    for (i=0; i<infoLength; ++i){
        let commentTime = fixTime(commentInfo[i].commentTime);
        let content = commentInfo[i].content;
        let reviewerName = commentInfo[i].reviewerName;
        let commentLikeCount = commentInfo[i].commentLikeCount;
        let reviewerPhotoName = commentInfo[i].reviewerPhotoName;
        let isLkeComment = commentInfo[i].isLike;

        let isAddRemove = false;
        isAddRemove = commentInfo[i].reviewerId === currentUserId;

        let oHtml = createComment(reviewerName, content, commentTime, commentLikeCount, isAddRemove, reviewerPhotoName, isLkeComment);
        if(content.replace(/(^\s*)|(\s*$)/g, "") != ''){
            $('.comment-show').append(oHtml);
        }

        //加载回复内容
        let replyContentLength = commentInfo[i].commentReply.length;
        if(replyContentLength > 0){
            for (let j=0; j<replyContentLength; ++j){
                let oAt;
                if (commentInfo[i].commentReply[j].replyUserName !== ''){
                    oAt = '回复<a href="#" class="atName">@' + commentInfo[i].commentReply[j].replyUserName + '</a> : ' + commentInfo[i].commentReply[j].content;
                }
                else{
                    oAt = commentInfo[i].commentReply[j].content;
                }

                let isRemove = false;
                let isLikeSubComment = commentInfo[i].commentReply[j].isLike;
                isRemove = commentInfo[i].commentReply[j].reviewerId === currentUserId;
                let oHtml = createReply(commentInfo[i].commentReply[j].reviewerName, oAt, fixTime(commentInfo[i].commentReply[j].commentTime), commentInfo[i].commentReply[j].commentLikeCount, isRemove, isLikeSubComment);
                $('.comment-show-con').eq(i).find('.comment-show-con-list').find('.hf-list-con').css('display','block').append(oHtml);
            }
        }
    }
}


function createComment(currentUser, content, commentTime, commentLikeCount, isAddRemove, reviewerPhotoName, isLike){
    let oHtml = '<div class="comment-show-con clearfix">' +
        '<div class="comment-show-con-img pull-left">' +
        '<img src="'+_URL+'account/'+reviewerPhotoName+'" alt="45 45">' +
        '</div> ' +
        '<div class="comment-show-con-list pull-left clearfix">' +
        '<div class="pl-text clearfix"> ' +
        '<a class="comment-size-name">'+currentUser+':'+'</a>' +
        ' <span class="my-pl-con">&nbsp;'+ content +'</span> ' +
        '</div> <div class="date-dz"> ' +
        '<span class="date-dz-left pull-left comment-time">'+commentTime+'</span> ' +
        '<div class="date-dz-right pull-right comment-pl-block">';
    if(isAddRemove) {
        oHtml = oHtml + '<a class="removeBlock">删除</a>'
    }
    oHtml = oHtml + '<a class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> ' +
    '<span class="pull-left date-dz-line">|</span>';
    if(isLike){
        oHtml = oHtml + '<a class="date-dz-z pull-left date-dz-z-click"><i class="date-dz-z-click-red red">' +
        '</i>赞 (<i class="z-num">' + commentLikeCount + '</i>)</a> </div>' +
        ' </div>' +
        '<div class="hf-list-con">' +
        '</div>' +
        '</div> ' +
        '</div>';
    }
    else{
        oHtml = oHtml + '<a class="date-dz-z pull-left"><i class="date-dz-z-click-red">' +
            '</i>赞 (<i class="z-num">' + commentLikeCount + '</i>)</a> </div>' +
            ' </div>' +
            '<div class="hf-list-con">' +
            '</div>' +
            '</div> ' +
            '</div>';
    }
    return oHtml;
}


function createReply(reviewerName, oAt, currentTime, commentLikeCount, isAddRemove, isLike){
     let oHtml =  '<div class="all-pl-con">' +
            '<div class="pl-text hfpl-text clearfix">' +
            '<a href="#" class="comment-size-name">'+reviewerName+' : </a>' +
            '<span class="my-pl-con">'+oAt+'</span>' +
            '</div><div class="date-dz">' +
            ' <span class="date-dz-left pull-left comment-time">'+currentTime+'</span> ' +
            '<div class="date-dz-right pull-right comment-pl-block">';
    if(isAddRemove) {
        oHtml = oHtml + '<a class="removeBlock">删除</a>'
    }

    oHtml = oHtml+'<a  class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>' +
            ' <span class="pull-left date-dz-line">|</span> ';
    if(isLike){
        oHtml = oHtml + '<a  class="date-dz-z pull-left date-dz-z-click">' + //href="javascript:;"
            '<i class="date-dz-z-click-red red"></i>赞 (<i class="z-num">'+commentLikeCount+'</i>)' +
            '</a>' +
            ' </div> ' +
            '</div>' +
            '</div>';
    }
    else {
        oHtml = oHtml + '<a  class="date-dz-z pull-left">' + //href="javascript:;"
            '<i class="date-dz-z-click-red"></i>赞 (<i class="z-num">'+commentLikeCount+'</i>)' +
            '</a>' +
            ' </div> ' +
            '</div>' +
            '</div>';
    }

    return oHtml;
}


function splitReply(oHfVal){
    let arr;
    let ohfNameArr;
    let data = [];
    if (oHfVal.indexOf("@") == -1) {
        data['replyUserName'] = '';
        data['content'] = oHfVal;
    } else {
        arr = oHfVal.split(':');
        ohfNameArr = arr[0].split('@');
        data['content'] = arr[1];
        data['replyUserName'] = ohfNameArr[1];
    }

    if (data.replyUserName == '') {
        return data.content;
    } else {
        return '回复<a href="#" class="atName">@' + data.replyUserName + '</a> : ' + data.content;
    }
}

function getAtNameAndContent(oHfVal){
    let arr;
    let ohfNameArr;
    let data = [];
    if (oHfVal.indexOf("@") == -1) {
        data['replyUserName'] = '';
        data['content'] = oHfVal;
    } else {
        arr = oHfVal.split(':');
        ohfNameArr = arr[0].split('@');
        data['content'] = arr[1];
        data['replyUserName'] = ohfNameArr[1];
    }
    return data;
}


Array.prototype.pushHead = function(){
    for(let i = 0 ;i<arguments.length;i++){
        this.splice(i,0,arguments[i]);
    }
};
Array.prototype.popHead = function(count){
    if(typeof count === "undefined"){
        this.splice(0,1);
    }
    if(typeof count === "number"){
        this.splice(0,count);
    }
};



/***点击评论创建评论条***/

$('.commentAll').on('click','.plBtn',function(){
    //获取输入内容
    let oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
    sendMessage = {workId: workId, content: oSize, replyUserId: '', commentId: ''};
    //注意要在头部添加以便找到下标来查找ID
    //动态创建评论模块
    $.post(_URL+"index/addComment.action", sendMessage, function (result) {
        commentInfo.pushHead({'reviewerName': currentUser, 'commentTime': fixTime(result.contentTme), 'content': oSize, 'commentLikeCount': result.likeCount, 'commentId': result.commentId, 'commentReply': null});
        let oHtml = createComment(currentUser, oSize, fixTime(result.contentTme), result.likeCount, true, userPhoto);
        if(oSize.replace(/(^\s*)|(\s*$)/g, "") != ''){
            $('.comment-show').append(oHtml);
            $('.comment-input').prop('value','').siblings('pre').find('span').text('');
        }
    });
});



/***取消回复***/
$('.comment-show').on('click','.cancel-hf',function() {
    $(this).parents('.hf-con').remove();
});


/***点击回复动态创建回复块***/

$('.comment-show').on('click','.pl-hf',function(){
	if(status == false){
		alert("您尚未登陆，请前往登陆");
	}
	else{
		//获取回复人的名字
	    let fhName = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
	    //回复@
	    fhN = '回复@'+fhName;
	    let fhHtml = '<div class="hf-con pull-left">' +
	        '<textarea class="content comment-input hf-input" placeholder="'+fhN+'" onkeyup="keyUP(this)"></textarea>' +
	        '<a class="cancel-hf">取消</a> ' +
	        '<a class="hf-pl">评论</a>' +
	        '</div>';

	    if(fhName === currentUser + ':'){
	        alert("不能回复自己!");
	    }
	    else {
	        //显示回复
	        if($(this).is('.hf-con-block')){
	            $(this).parents('.date-dz-right').parents('.date-dz').append(fhHtml);
	            $(this).removeClass('hf-con-block');
	            $('.content').flexText();
	            $(this).parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding','6px 15px');
	            $(this).parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus().val();
	        }else {
	            $(this).addClass('hf-con-block');
	            $(this).parents('.date-dz-right').siblings('.hf-con').remove();
	        }
	    }
	}
});

$('.comment-show').on('click','.hf-pl',function(){
    let oThis = $(this);
    //获取输入内容
    let oHfVal = fhN + $(this).siblings('.flex-text-wrap').find('.hf-input').val();
    let oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
    let i = $(".comment-show-con").index($(this).parents('.comment-show-con'));
    let j = $(this).parents('.all-pl-con').index();
    let replyUserId;
    let data = getAtNameAndContent(oHfVal);
    let index = $(this).parents('.comment-show-con').index();
    
    if(j === -1){
        replyUserId = commentInfo[i].reviewerId;
    }
    else{
        replyUserId = commentInfo[i].commentReply[j].reviewerId;
    }

    sendMessage = {workId: workId ,content: data['content'], replyUserId: replyUserId, replyCommentId: commentInfo[index].commentId};

    if(oHfName === currentUser+' : '){
        alert("不能回复自己!");
    }
    else{
        //向后端传送评论信息
        $.post(_URL+"index/addComment.action", sendMessage, function (result) {
            let oAt = splitReply(oHfVal);
            commentInfo[index].commentReply.pushHead({'reviewerName': currentUser, 'commentTime': fixTime(result.contentTme), 'content': oAt, 'commentLikeCount': result.likeCount, 'commentId': result.commentId});
                let oHtml = createReply(currentUser, oAt, fixTime(result.contentTme), result.likeCount, true);
                oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display','block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
        });
    }
});

/***删除评论块***/

$('.commentAll').on('click','.removeBlock',function(){
    let othis = $(this); // 在异步中使用$(this),很重要，测的时候发现一部的时候$(this)没有用,应该是子函数里的原因
    let i = $(".comment-show-con").index($(this).parents('.comment-show-con'));
    let j = $(this).parents('.all-pl-con').index();
    let id;
    if(j === -1){
        id = commentInfo[i].commentId;
    }
    else{
        id = commentInfo[i].commentReply[j].commentId;
    }

    $.post(_URL+"index/deleteComment.action", {commentId: id}, function (result) {
        if(j === -1){
            commentInfo.splice(i,1);
        }
        else{
            commentInfo[i].commentReply.splice(j,1);
        }
        let oT = othis.parents('.all-pl-con');
        if(oT.siblings('.all-pl-con').length >= 1){
            oT.remove();
        }else {
            othis.parents('.hf-list-con').css('display','none');
            oT.remove();
        }
        othis.parents('.comment-show-con').remove();
    });


});

/***点赞***/
$('.comment-show').on('click','.date-dz-z',function(){
	if(status == false){
		alert("您尚未登录，请前往登录");
	}
	else{
		let i = $(".comment-show-con").index($(this).parents('.comment-show-con'));
	    let j = $(this).parents('.all-pl-con').index();
	    let id;
	    let zNum = $(this).find('.z-num').html();
	    let that = $(this);
	    if(j === -1){
	        id = commentInfo[i].commentId;
	    }
	    else{
	        id = commentInfo[i].commentReply[j].commentId;
	    }

	    if($(this).is('.date-dz-z-click')){
	        $.post(_URL+"index/dropLikeComment.action", {commentId: id}, function (result) {
	            zNum--;
	            that.removeClass('date-dz-z-click red');
	            that.find('.z-num').html(zNum);
	            that.find('.date-dz-z-click-red').removeClass('red');
	        });
	    }else {
	        $.post(_URL+"index/clickLikeComment.action", {commentId: id}, function (result) {
	            zNum++;
	            that.addClass('date-dz-z-click');
	            that.find('.z-num').html(zNum);
	            that.find('.date-dz-z-click-red').addClass('red');
	        });
	    }
	}
    
});

function getWorkId(Id) {
    workId = Id;
}