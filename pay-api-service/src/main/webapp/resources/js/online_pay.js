/**
 * Created by 何振杰 on 2016/5/4.
 */
var a
$(function(){
    $.ajax({
        type: "post",
        url: baseURL+"/remotePay/getOrderNoInfo.mvc",
        data: {'appid':$("#appid").val(),'orderNo':$("#orderNo").val()},
        dataType: "json",
        success: function(rs) {
            if(rs.success==true){
                $("#amount").html(rs.data.amount);
                a=rs.data.pay_params;
            }else{
               alert(rs.msg);
            }
        }
    });

})



function onBridgeReady(b){
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "package":   b.package,
            "timeStamp": b.timeStamp,
            "signType":  b.signType,
            "paySign":   b.paySign,
            "appId":     b.appId,
            "nonceStr":  b.nonceStr
},
function(res){
    if(res.err_msg == "get_brand_wcpay_request：ok" ) {
        alert(支付成功);
    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
}
);
}
if (typeof WeixinJSBridge == "undefined"){
    if( document.addEventListener ){
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    }else if (document.attachEvent){
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
}else{
    onBridgeReady();
}
