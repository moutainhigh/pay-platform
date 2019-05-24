/**
 * Created by HZJ on 2016/11/3.
 */

var theValue=0;  //消费总额值
var payIlAmount=0;//不参与优惠金额
var isCheckbox =false;
var reg   = /^[0-9]{1}\d{0,4}$/;
var reg1  = /^[0-9]{1}\d{0,4}\.\d{0,2}$/;
var payIlAmountReg   = /^[0-9]{1}\d{0,4}$/;
var payIlAmountReg1  = /^[0-9]{1}\d{0,4}\.\d{0,2}$/;
var returnUrl;
$(function(){
    $("input").bind("copy cut paste",function(e){
        return false;
    });
    // log();
    // function log(){
    //     console.log("-----------------");
    //     console.log("消费总额值"+theValue);
    //     console.log("不参与优惠金额"+payIlAmount);
    //     console.log("integral:"+$('#integral').val());
    //     console.log("payCbIntegral:"+$('#payCbIntegral').val());
    //     console.log("cashBackRate:"+$('#cashBackRate').val());
    //     // console.log("payRate:"+$('#payRate').val());
    //     // console.log("balanceIntegral:"+$('#balanceIntegral').val());
    //     console.log("totalAmount:"+$('#totalAmount').val());
    //     console.log("payIlAmount:"+$('#payIlAmount').val());
    //     // console.log("btn is check:"+$('#btn'));
    //     console.log("view_IntegralAmount:"+$('#view_IntegralAmount').text());
    //     console.log("view_integral:"+$('#view_integral').text());
    //     console.log("view_payAmount:"+$('#view_payAmount').text());

    // }
    document.getElementById('totalAmount').addEventListener('input', function(e){  //消费总额值事件
        if($(this).val()==""){
            theValue="";
            $("#view_integral").text(0);
            $("#view_payAmount").text(0);
            return ;
        }
        if(payIlAmount == ""){
            payIlAmount = 0;
        }
        if(reg1.test($(this).val())||reg.test($(this).val())){
            theValue = $(this).val();
            if(true){
                console.log(payIlAmount);
                if(parseFloat($(this).val())>parseFloat(payIlAmount)){
                    //计算获得积分
                    $("#view_integral").text(toDecimal2Point((theValue-payIlAmount) * ($("#cashBackRate").val()/100)));
                    //计算实际付款金额
                    if(isCheckbox){ //算上积分抵消金额
                        if(theValue!="") {
                            if (parseFloat(theValue) < $("#view_IntegralAmount").text()) {
                                $("#integral").val(theValue*$("#payRate").val());
                                $("#view_payAmount").text(0);
                            } else {
                                //计算实际付款金额
                                $("#integral").val($("#balanceIntegral").val());
                                $("#view_payAmount").text(toDecimal2Point(theValue - $("#view_IntegralAmount").text()));
                            }
                        }
                    }else{//不算积分抵消金额
                        $("#view_payAmount").text(theValue);
                        $("#integral").val(0);
                    }
                }else{
                    $("#payIlAmount").val(0);
                    if(isCheckbox){ //算上积分抵消金额
                        if(theValue!="") {
                            if (parseFloat(theValue) < $("#view_IntegralAmount").text()) {
                                $("#integral").val(theValue*$("#payRate").val());
                                $("#view_payAmount").text(0);
                            } else {
                                //计算实际付款金额
                                $("#integral").val($("#balanceIntegral").val());
                                $("#view_payAmount").text(toDecimal2Point(theValue - $("#view_IntegralAmount").text()));
                            }
                        }
                    }else{//不算积分抵消金额
                        $("#view_payAmount").text(theValue);
                        $("#integral").val(0);
                    }
                }

            }
        }else{
            $(this).val(theValue) ;
        }
        // log();
    });

    document.getElementById('payIlAmount').addEventListener('input', function(){ //不参与消费金额事件
        if($(this).val()==""){
            payIlAmount="";
            $("#view_integral").text(toDecimal2Point((theValue) * ($("#cashBackRate").val()/100)));

            return ;
        }
        if(payIlAmountReg1.test($(this).val())||payIlAmountReg.test($(this).val())){
            if(theValue==""){
                mui.toast("请先输入消费金额");
                $(this).val("");
                return;
            }else{
                if(parseFloat($(this).val())>parseFloat(theValue)){
                    $(this).val(theValue);
                    payIlAmount= $(this).val();
                    $("#view_integral").text(toDecimal2Point((theValue-theValue) * ($("#cashBackRate").val()/100)));
                    //计算实际付款金额
                    if(isCheckbox){ //算上积分抵消金额
                        if(theValue!="") {
                            if (parseFloat(theValue) < $("#view_IntegralAmount").text()) {
                                $("#integral").val(theValue*$("#payRate").val());
                                $("#view_payAmount").text(0);
                            } else {
                                //计算实际付款金额
                                $("#integral").val($("#balanceIntegral").val());
                                $("#view_payAmount").text(toDecimal2Point(theValue - $("#view_IntegralAmount").text()));
                            }
                        }
                    }else{//不算积分抵消金额
                        $("#view_payAmount").text(theValue);
                        $("#integral").val(0);
                    }
                }else{
                    payIlAmount=$(this).val();
                    $("#view_integral").text(toDecimal2Point((theValue-payIlAmount) * ($("#cashBackRate").val()/100)));
                    if(isCheckbox){ //算上积分抵消金额
                        if(theValue!="") {
                            if (parseFloat(theValue) < $("#view_IntegralAmount").text()) {
                                $("#integral").val(theValue*$("#payRate").val());
                                $("#view_payAmount").text(0);
                            } else {
                                //计算实际付款金额
                                $("#integral").val($("#balanceIntegral").val());
                                $("#view_payAmount").text(toDecimal2Point(theValue - $("#view_IntegralAmount").text()));
                            }
                        }
                    }else{//不算积分抵消金额
                        $("#view_payAmount").text(theValue);
                        $("#integral").val(0);
                    }
                }

            }

        }else{
            $(this).val(payIlAmount) ;
        }
        // log();
    });

    //计算积分抵用金额
    if($("#balanceIntegral").val()>0){
        $("#view_IntegralAmount").text(toDecimal2Point($("#balanceIntegral").val() * ($("#payRate").val()/10000)));
    }

    //判断是否选中积分
    $('#btn').click(function(){
        if($("input[type='checkbox']").is(':checked')){
            isCheckbox=true;
            //判断积分余额和消费总额
            if(theValue!="") {
                if (parseFloat(theValue) < $("#view_IntegralAmount").text()) {
                    $("#integral").val(theValue*$("#payRate").val());
                    $("#view_payAmount").text(0);
                } else {
                    //计算实际付款金额
                    $("#integral").val($("#balanceIntegral").val());
                    $("#view_payAmount").text(toDecimal2Point(theValue - $("#view_IntegralAmount").text()));
                }
            }
        }else{
            isCheckbox =false;
            $("#integral").val(0);
            if(theValue!="") {
                $("#view_payAmount").text(theValue);
            }else{
                $("#view_payAmount").text(0);
            }
        }
    });


})


function sbm(obj){
    obj.disabled=true;
    if($("#totalAmount").val().trim()==""){
        mui.toast("请输入消费总额");
        obj.disabled=false;
        return;
    }
    if(reg1.test($("#totalAmount").val())||reg.test($("#totalAmount").val())){
    }else{
        mui.toast("请正确输入总额");
        obj.disabled=false;
        return;
    }

    if($("#payIlAmount").val().trim()==""){
        mui.toast("请输入不参与优惠金额");
        obj.disabled=false;
        return;
    }
    if(payIlAmountReg1.test($("#payIlAmount").val())||payIlAmountReg.test($("#payIlAmount").val())){
    }else{
        mui.toast("请正确输入不参与优惠金额");
        obj.disabled=false;
        return ;
    }
    $("#payCbIntegral").val($("#view_integral").text());
    $('#integral_pay_from').ajaxSubmit({
        dataType:'json',
        success:function (response) {
            if(response && response.success == true) {
                if(response.payType == "on_amount") {
                    if (response.code == '0001'){
                        mui.toast(response.resperr);
                    }
                    else if (response.code == '0000'){
                        wx_pay(response);        //微信线上支付
                    }
                    else if (response.code == '0004'){
                        al_pay(response);        //支付宝支付
                    }

                    //window.location = location.origin + "/register/offline_pay.mvc" + "?code_url=" + response.codeUrl + "&pay_name=微信支付";   //线下支付
                    obj.disabled = false;
                }else if(response.payType =="on_integral"){
                    window.location = response.data.pay_params.returnUrl;
                }else {
                    mui.toast("未知支付类型！！")
                }
            } else {
                $("#box2 h4").html(response.msg);
                $("#box2").show();
                obj.disabled=false;
            }
        },
        error:function (jqXHR) {
            $("#box2 h4").html("提交失败,可能是网络原因，请稍后重试！");
            $("#box2").show();
            //alert(jqXHR.responseText);
            obj.disabled=false;
        }
    });

}


function toDecimal2Point(str) {
    //先精确到小数点后3位,会四舍五入
    var num = str.toFixed(3);
    //再截取最后一位,即变成2位,并不会四舍五入VVW
    return num.substring(0, num.length - 1);
}




function wx_pay(a) {
    //alert(JSON.stringify(a));
    var b = a.data.pay_params;
    returnUrl= a.data.pay_params.returnUrl;
    WeixinJSBridge.invoke("getBrandWCPayRequest", {
        "package":   b.package,
        "timeStamp": b.timeStamp,
        "signType":  b.signType,
        "paySign":   b.paySign,
        "appId":     b.appId,
        "nonceStr":  b.nonceStr
    }, function (b) {
        if("get_brand_wcpay_request:ok" == b.err_msg){
            window.location = returnUrl;
        }else{
            WeixinJSBridge.invoke("closeWindow", {}, function (a) {
            });
        }

    })
}

function al_pay(a) {
    //直接跳转到支付宝支付
    window.location.href=a.codeUrl;
}



