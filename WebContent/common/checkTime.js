fromDate = ''

//获取查询起始时间
$("#from_date").change(function () {
	var date = new Date();
	let currentYear = date .getFullYear(); //获取完整的年份(4位)
	let currentMonth = date .getMonth()+1; //获取当前月份(0-11,0代表1月)
	let currentDate = date .getDate(); //获取当前日(1-31)
    let getDate =  $("#from_date").val();
    console.log(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate))
    if(checkTime(currentYear+"-"+currentMonth+"-"+currentDate, getDate)){
    	fromDate =  $("#from_date").val();
    }
    else{
    	$("#from_date").val("");
    	alert("请选择正确的时间段！");
    }
    
});


//获取查询截止时间
$("#to_date").change(function () {
	if(fromDate === ""){
		toDate =  $("#to_date").val();
	}
	else{
		console.log(fromDate);
		if(checkTime($("#to_date").val(),fromDate)){
			toDate =  $("#to_date").val();
	    }
	    else{
	    	$("#to_date").val("");
	    	alert("请选择正确的时间段！");
	    }
	}
});

function checkTime(time0, time1){
	
    let arr0 = time0.split("-");
    let arr1 = time1.split("-");
    
    if(Number(arr0[0]) < Number(arr1[1])){
    	return false;
    }
    else{
    	if(Number(arr0[1]) < Number(arr1[1])){
    		return false;
        }
        else{
        	if(Number(arr0[1]) > Number(arr1[1])){
        		return true;
            }
        	else{
        		if(Number(arr0[2])<Number(arr1[2])){
        			return false;
                }
                else{
                	return true;
                }
        	}
        }
    }
}