let isSelectAll = false; //是否全选
let modalIsSelectAll=false; //模态框中是否全选
let selectNum = 0; //选中个数
let pageSize = 20; //页面数据条数
let pageNo = 1;//页面
let currentPage = 1;//当前页数
let firstIndex = 1;//第一页下标
let lastIndex = 0;//最后一页下标
let totalPage = 0;//页面总数
let totalItem = 0;//数据总数
let selectItemIndex=-1;//当前选中的班级下标


//更新表格底栏
function updateFooter() {
    if (currentPage < 1 || currentPage > totalPage) currentPage = 1;
    //if (pageSize > 100) pageSize = 20;
    firstIndex = (currentPage - 1) * pageSize + 1;
    if(pageSize>totalItem)
        lastIndex=totalItem;
    else
        lastIndex = currentPage == totalPage ? (totalItem): (parseInt(firstIndex) + parseInt((pageSize-1)));
        
    console.log("currnetPage"+currentPage);
    console.log("totalPage"+totalPage);
    console.log("totalItem",totalItem);
    console.log("firstIndex",firstIndex);
    console.log("pageSize",pageSize);
    
    totalPage = Math.floor(totalItem / pageSize) + (totalItem % pageSize === 0 ? 0 : 1);
    $("#current-page").attr("placeholder", currentPage);
    $("#total-page").text(totalPage);
    $("#first-index").text(firstIndex);
    $("#last-index").text(lastIndex);
    $("#total-item").text(totalItem);
    $("#per-page").attr("placeholder",pageSize);
    if (currentPage === 1) {
        $("#pre-page").addClass("disabled").attr("disabled", "disabled");
        $("#first-page").addClass("disabled").attr("disabled", "disabled");
    }
    if (currentPage !== 1) {
        $("#pre-page").removeClass("disabled").removeAttr("disabled");
        $("#first-page").removeClass("disabled").removeAttr("disabled");
    }
    if (currentPage === totalPage) {
        $("#next-page").addClass("disabled").attr("disabled", "disabled");
        $("#last-page").addClass("disabled").attr("disabled", "disabled");
    }
    if (currentPage !== totalPage) {
        $("#next-page").removeClass("disabled").removeAttr("disabled");
        $("#last-page").removeClass("disabled").removeAttr("disabled");
    }
}

//查找第一页
$("#first-page").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    currentPage = 1;
    pageNo = currentPage;
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//查找最后一页
$("#last-page").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    currentPage = totalPage;
    pageNo = currentPage;
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//查找上一页
$("#pre-page").click(function (e) {
	 $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    if (currentPage > 1) {
        currentPage--;
        pageNo = currentPage;
        getInfo();
        document.getElementById("current-page").value="";
        document.getElementById("per-page").value="";
    }
});

//查找下一页
$("#next-page").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    if (currentPage < totalPage) {
        currentPage++;
        pageNo = currentPage;
        getInfo();
        document.getElementById("current-page").value="";
        document.getElementById("per-page").value="";
    }
});

//更新页面条数和当前页面
function refreshPageInfo(){
    if($("#current-page").val()!==""){
        pageNo = $("#current-page").val();
    }
    if($("#per-page").val()!==""){
        pageSize = $("#per-page").val();
    }
}

//页面跳转
$("#current-page").change(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    refreshPageInfo();
    currentPage = pageNo;
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//改变页面显示数目
$("#per-page").change(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    refreshPageInfo();
    getInfo();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

$("#refresh-page").click(function () {
    getInfo();
	updateFooter();
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
});

//重置页面内容
$("#reset").click(function (e) {
    $("#CheckAll").prop("checked", false);
    isSelectAll = false;
    $("[name=items]:checkbox").prop("checked", false);
    if($("#titleSearch").val() !== undefined){
    	document.getElementById("titleSearch").value="";
    }
    if($("#titleSearchCode").val() !== undefined){
    	document.getElementById("titleSearchCode").value="";
    }
    if($("#search-homeworkName").val() !== undefined){
    	$("#search-homeworkName").val("");
    }
    
    
    if($("#from_date").val() !== undefined){
    	$("#from_date").val("");
    }
    
    
    if($("#to_date").val() !== undefined){
    	$("#to_date").val("");
    }
    
    if($("#searchBarWorkName").val() !== undefined){
    	$("#searchBarWorkName").val("");
    }
});


//点击全选全选当前页面td;再次点击取消
$("#table-header").on('click','input',function () {
	console.log(11);
  if(isSelectAll === false){
      isSelectAll = true;
      $("[name=items]:checkbox").prop("checked", true);
  }
  else{
      isSelectAll = false;
      $("[name=items]:checkbox").prop("checked", false);
  }
});

//模态框中的全选和全不选
$(".selectAllModalItem").click(function () {
    modalIsSelectAll = true;
    $("[name=modalItems]:checkbox").prop("checked", true);
});
$(".notSelectAllModalItem").click(function () {
    modalIsSelectAll = false;
    $("[name=modalItems]:checkbox").prop("checked", false);
});

//主列表有选中项目时触发
$(document).on("click",".selectAll",function(){
	selectChanged();
});

