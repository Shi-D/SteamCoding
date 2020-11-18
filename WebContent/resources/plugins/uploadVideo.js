var xhr = new XMLHttpRequest();
var percentComplete = 0; 
//监听选择文件信息
function fileSelected() {
    //HTML5文件API操作
      var file = document.getElementById('file').files[0];
      if (file) {
        var fileSize = 0;
        if (file.size > 1024 * 1024)
          fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
        else
          fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
        $(".el-upload-list").css("display","block");
        $(".el-upload-list li").css("border","1px solid #20a0ff");
        $("#videoName").text(file.name+" "+fileSize);
        $("#progressBar").css("display","none");
        $(".progress-bar").css("width","0");
        $(".progress-bar").text("0%");
      }
      else{
          alert("请选择文件");
      }
    }
 
function uploadMaxFile(){
    var testxhr = new XMLHttpRequest();
    var file = document.getElementById('file').files[0];
    var formdata = new FormData();
    percentComplete = 0;
    if ($("#title").val() == ""){
    	$("#title").addClass("is-invalid");
    }
    if ($("#title").hasClass("is-valid")&&file){
    	//$("#uploadVideoCommit").attr("disabled","true");
    	//$("#uploadVideoCancel").removeAttr("disabled");
        formdata.append("fileName", file.name);
        formdata.append("fileSize", file.size);
        testxhr.onload = function(evt){
            var start = parseInt(JSON.parse(evt.target.responseText).uploadSize);
            uploadFile(file,start);
            
        };
        testxhr.open("POST", postSrc+"uploadifyTest_getUploadedSize.action");
        testxhr.send(formdata);
    }
}

//上传文件
function uploadFile(file,start) {
      var formdata = new FormData();
      //关联表单数据,可以是自定义参数
      formdata.append("fileName", file.name);
      formdata.append("fileSize", file.size);
      var end = start + 1024*1024;//文件分片上传，每片1M
      var endFlag = false;
      if(end>=file.size){
          end = file.size;
          endFlag = true;
      }
      formdata.append("file", file.slice(start,end));
      formdata.append("uploadSize", end);
      formdata.append("videoTitle",$("#title").val());
      //监听事件
      //xhr.upload.addEventListener("progress", uploadProgress, false);
      xhr.addEventListener("load", uploadComplete, false);
      xhr.addEventListener("error", uploadFailed, false);
      xhr.addEventListener("abort", uploadCanceled, false);
      xhr.onload = function(evt){
          if(!endFlag){
                  uploadFile(file,end);
          }
      };
      xhr.upload.addEventListener("progress", function(evt){
          if (evt.lengthComputable) {
        	  console.log("percentComplete"+percentComplete)
                //var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        	  	percentComplete = Math.round((evt.loaded+start) * 100 / file.size);      
                if(percentComplete>100)
                	percentComplete=100;                
                $("#progressBar").css("display","block");
                $(".progress-bar").css("width", percentComplete + "%");
                $(".progress-bar").html(percentComplete + "%"); 
                if(percentComplete == 100){
            		$("#uploadVideoCommit").removeAttr("disabled");
//            		/$("#uploadVideoCancel").attr("disabled","true");
                }
            } else {
                //document.getElementById('progressNumber').innerHTML = 'unable to compute';
            }
      }, false);
      //发送文件和表单自定义参数
      xhr.open("POST", postSrc+"uploadifyTest_doUpload.action");
      xhr.send(formdata);
}

//取消上传
function cancleUploadFile() {
    xhr.abort();
}
 
//上传进度
function uploadProgress(evt) {
    if (evt.lengthComputable) {
    	 var percentComplete = Math.round((evt.loaded+start) * 100 / file.size);
        document.getElementById('progressNumber').innerHTML = "FILE"+percentComplete.toString()+ '%';
        
        $("#progressBar").css("display","block");
        $(".progress-bar").css("width", percentComplete + "%");
        $(".progress-bar").html(percentComplete + "%"); 
        	
    } else {
        document.getElementById('progressNumber').innerHTML = 'unable to compute';
    }
}

$("#uploadVideoCancel").click(function(){
	 $('#upload-video-modal').modal('hide');
	 $("#title").val("");
	 $("#title").removeClass("is-invalid");
	 $("#title").removeClass("is-valid");
	 percentComplete=0;
	 $("#videoName").text("");
	 $("#videoList").css("display","none");
	 $("#file").val('');
	 $("#progressBar").css("display","none");
	 $(".progress-bar").css("width","0");
	 $(".progress-bar").text("0%");
});
 
    //上传成功响应
function uploadComplete(evt) {
    //服务断接收完文件返回的结果
    //alert(evt.target.responseText);
}
 
    //上传失败
function uploadFailed(evt) {
    alert("上传失败");
}
//取消上传
function uploadCanceled(evt) {
    alert("您取消了本次上传.");
}
/*function uploadProgress(evt) {

var time = document.getElementById("time");
var nt = new Date().getTime(); //获取当前时间
var pertime = (nt - ot) / 1000; //计算出上次调用该方法时到现在的时间差，单位为s
ot = new Date().getTime(); //重新赋值时间，用于下次计算

var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b 
oloaded = evt.loaded; //重新赋值已上传文件大小，用以下次计算

//上传速度计算
var speed = perload / pertime; //单位b/s
var bspeed = speed;
var units = 'b/s'; //单位名称
if(speed / 1024 > 1) {
    speed = speed / 1024;
    units = 'k/s';
}
if(speed / 1024 > 1) {
    speed = speed / 1024;
    units = 'M/s';
}
speed = speed.toFixed(1);
//剩余时间
var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1);
time.innerHTML = '上传速度：' + speed + units + ',剩余时间：' + resttime + 's';
if(bspeed == 0)
    time.innerHTML = '上传已取消';
}*/