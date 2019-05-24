$(function () {
        $('#startTime').datetimepicker({
            locale: 'zh_cn',
//         	format: 'YYYY-MM-DD HH:mm:ss'
            format: 'YYYY-MM-DD'
        });
        $('#endTime').datetimepicker({
            locale: 'zh_cn',
            format: 'YYYY-MM-DD',
            useCurrent: false //Important! See issue #1075
        });
        $("#startTime").on("dp.change", function (e) {
            $('#endTime').data("DateTimePicker").minDate(e.date);
        });
        $("#endTime").on("dp.change", function (e) {
            $('#startTime').data("DateTimePicker").maxDate(e.date);
        });

        var myDate = new Date();//获取当前系统日期
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        var month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var date = myDate.getDate();        //获取当前日(1-31)
        var endDate = date+1;
        if(date<10){
            var strDate = year+"-"+month+"-0"+date;
        }else{
            var strDate = year+"-"+month+"-"+date;
        }
        if(endDate<10){
            var strEndDate = year+"-"+month+"-0"+endDate;
        }else {
            var strEndDate = year+"-"+month+"-"+endDate;
        }

        $('#startDate').val(strDate);
        $('#endDate').val(strEndDate);
    })