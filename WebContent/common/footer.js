let pageSize = 10; //页面数据条数
let pageNo = 1; //页面
let currentPage = 0;
let firstIndex = 0;
let lastIndex = 0;
let totalPage = 0;//页面总数
let totalItem = 0;//数据总数

//更新表格底栏
function updateFooter() {
    if (currentPage < 1 || currentPage > totalPage) currentPage = 1;
    
    firstIndex = (currentPage - 1) * pageSize + 1;
    lastIndex = currentPage === totalPage ? (totalItem): firstIndex + (pageSize-1);
    totalPage = Math.floor(totalItem / pageSize) + (totalItem % pageSize === 0 ? 0 : 1);
    
    if(totalItem === 0){
    	lastIndex = pageSize;
    }
    
    $("#current-page").attr("placeholder", currentPage);
    $("#total-page").text(totalPage);
    $("#first-index").text(firstIndex);
    $("#last-index").text(lastIndex);
    $("#total-item").text(totalItem);
    $("#per-page").attr("placeholder",pageSize);

    if (currentPage === "1") {
        $("#pre-page").addClass("disabled").attr("disabled", "disabled");
        $("#first-page").addClass("disabled").attr("disabled", "disabled");
    }
    if (currentPage !== "1") {
        $("#pre-page").removeClass("disabled").removeAttr("disabled");
        $("#first-page").removeClass("disabled").removeAttr("disabled");
    }
    if (currentPage.toString() === totalPage.toString()) {
        $("#next-page").addClass("disabled").attr("disabled", "disabled");
        $("#last-page").addClass("disabled").attr("disabled", "disabled");
    }
    if (currentPage.toString() !== totalPage.toString()) {
        $("#next-page").removeClass("disabled").removeAttr("disabled");
        $("#last-page").removeClass("disabled").removeAttr("disabled");
    }
}



/***TMD一定给我自定义initPage()方法来更新页面**/


//查找第一页
$("#first-page").click(function (e) {
    currentPage = 1;
    pageNo = currentPage;
    initPage();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//查找最后一页
$("#last-page").click(function (e) {
    currentPage = totalPage;
    pageNo = currentPage;
    initPage();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//查找上一页
$("#pre-page").click(function (e) {
    if (currentPage > 1) {
        currentPage--;
        pageNo = currentPage;
        initPage();
        document.getElementById("current-page").value="";
        document.getElementById("per-page").value="";
    }
});

//查找下一页
$("#next-page").click(function (e) {
    if (currentPage < totalPage) {
        currentPage++;
        pageNo = currentPage;
        initPage();
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
    refreshPageInfo();
    currentPage = pageNo;
    initPage();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});

//刷新页面
$("#refresh-page").click(function () {
    initPage();
});

//修改页面显示的作品数量
$("#per-page").change(function (e) {
    refreshPageInfo();
    initPage();
    document.getElementById("current-page").value="";
    document.getElementById("per-page").value="";
});