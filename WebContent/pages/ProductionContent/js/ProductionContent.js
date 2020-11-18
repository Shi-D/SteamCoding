let postSrc=_URL+"index/showWorkDetail.action";
let userName;
let userIntroduction;
let workDescription;
let userPhotoName;
let userInfo;
let isLike = false;
let status;
function getCommentInfo(workId){
//    $.get(_URL+"user/queryUserInfo.action", function (result) {
//        userPhoto = result.userPhotoName;
//        currentUser = result.userName;
//        currentUserId = result.id;
//    });
    $.post(_URL+"index/queryComment.action", {"workId":workId}, function (result) {
        commentInfo = result;
        getWorkId(workId);
        addCommentInfo();
    });
}


$(document).ready(function(){
    $('#logIn').click(function () {
        document.getElementById('logIn').style.display = "none";
        document.getElementById('logOut').style.display = "block";
    });
    $('#logOut').click(function () {
        document.getElementById('logIn').style.display = "block";
        document.getElementById('logOut').style.display = "none";
    });
    addInfo();
});

function addInfo() {
    let workId = localStorage.getItem("workId");
    $.post(postSrc, {workId: workId}, function (result) {
        userInfo = result;
        userName = result[0].userName;
        userIntroduction = result[0].userIntroduction;
        workDescription = result[0].workDescription;
        if(workDescription == null)
        	workDescription = "欢迎来看我的作品，我没什么要讲的，谢谢大家。";
        userPhotoName = result[0].userPhotoName;
        let addUserName = '<label>'+userName+'</label>';
        let addUserIntroduction = '<p>'+userIntroduction+''+'</p>';
        let addWorkDescription = '<p class="newLine">'+workDescription+'</p>';
        $('#userInfo').append(addUserName+addUserIntroduction);
        $('#productionIntroduction').append(addWorkDescription);
        $('#userPhotoName').attr("src",_URL+'account/'+userPhotoName);
        workLikeCount = result[0].likeCount;
        $('#goodNum').text(workLikeCount);
        changeStyleOfGoodBtn(result[1].result);
        isLikeWork = result[1].result;
        status = result[1].status;
    });

    getCommentInfo(workId);
}

function goWorkContentExpand() {
    window.open("../../build/workContent.html", "","");
}

//点赞作品
$("#likeBtn").click(function () {
	if(status == false){
		alert("您尚未登录，请前往登录");
	}
	else{
		if(isLikeWork === true){
	        $.post(_URL+"index/dropWorkLike.action", {"workId":workId}, function (result) {
	            isLikeWork = false;
	            changeStyleOfGoodBtn(isLikeWork);
	            workLikeCount = workLikeCount - 1;
	            $('#goodNum').text(workLikeCount);
	           
	        });
	    }
		else {
	        $.post(_URL+"index/clickWorkLike.action", {"workId":workId}, function (result) {
	            isLikeWork = true;
	            changeStyleOfGoodBtn(isLikeWork);
	            workLikeCount = workLikeCount + 1;
	            $('#goodNum').text(workLikeCount);
	            
	        });
	    }
	} 
});
$("#commentBtn").click(function () {
	if(status == false){
		alert("您尚未登录，请前往登录");
	}
});

//改变点赞按钮样式
function changeStyleOfGoodBtn(isLike) {
    if(isLike === true){
        $("#likeBtn").removeClass("btn-success");
        $("#likeBtn").addClass("btn-secondary");
        $("#btn-content").text('取消');
    }
    else {
        $("#likeBtn").removeClass("btn-secondary");
        $("#likeBtn").addClass("btn-success");
        $("#btn-content").text('点赞');
    }
}




