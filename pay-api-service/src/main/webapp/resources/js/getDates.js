//查看昨天数据
function getYesterDayDemo() {
    //昨天的时间
    var day1 = new Date();
    day1.setTime(day1.getTime()-24*60*60*1000);

    var yesterDay = day1.getFullYear();
    var today = day1.getFullYear();
    if(day1.getMonth()+1<10){
        yesterDay = yesterDay + "-0" + (day1.getMonth()+1);
        today = today + "-0" + (day1.getMonth()+1);
    }else {
        yesterDay = yesterDay + "-" + (day1.getMonth()+1);
        today = today + "-" + (day1.getMonth()+1);
    }
    if(day1.getDate()<10){
        yesterDay = yesterDay  + "-0" + day1.getDate();
    }else {
        yesterDay = yesterDay  + "-" + day1.getDate();
    }
    if(day1.getDate()+1<10){
        today = today  + "-0" + (day1.getDate()+1);
    }else {
        today = today  + "-" + (day1.getDate()+1);
    }



    $("#startDate").val(yesterDay);
    $("#endDate").val(yesterDay);
}

//查看今天数据
function getToDayDemo() {
    //今天的时间
    var day1 = new Date();
    day1.setTime(day1.getTime());

    var yesterDay = day1.getFullYear();
    var today = day1.getFullYear();
    if(day1.getMonth()+1<10){
        yesterDay = yesterDay + "-0" + (day1.getMonth()+1);
        today = today + "-0" + (day1.getMonth()+1);
    }else {
        yesterDay = yesterDay + "-" + (day1.getMonth()+1);
        today = today + "-" + (day1.getMonth()+1);
    }
    if(day1.getDate()<10){
        yesterDay = yesterDay  + "-0" + day1.getDate();
    }else {
        yesterDay = yesterDay  + "-" + day1.getDate();
    }
    if(day1.getDate()+1<10){
        today = today  + "-0" + (day1.getDate()+1);
    }else {
        today = today  + "-" + (day1.getDate()+1);
    }



    $("#startDate").val(yesterDay);
    $("#endDate").val(yesterDay);
}
