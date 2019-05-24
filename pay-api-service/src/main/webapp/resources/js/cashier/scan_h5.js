var ws = null;
function startWebSocket() {
    try{
        if ('WebSocket' in window)
            ws = new WebSocket(wsuri);
        else if ('MozWebSocket' in window)
            ws = new MozWebSocket(wsuri);
        else{
            alert("该浏览器不支持，请更改浏览器再试！");
            window.opener=null;
            window.open('','_self');
            window.close();
        }
    }catch (e){
        console.log("错误信息：" + e.name + "，" + e.message);
        alert("系统维护中，请稍后再试！");
        window.opener=null;
        window.open('','_self');
        window.close();
    }
    ws.onmessage = function(evt) {
        var obj = JSON.parse(evt.data);
        switch (obj.type){
            case "login":
                if(obj.success){
                    socketId = obj.id;
                }else{
                    alert("连接socket失败");
                    window.opener=null;
                    window.open('','_self');
                    window.close();
                }
                break;
            case "payResult":
                if(obj.orderId == orderId){
                    if(obj.status + "" == "true"){
                        if(obj.url + "" != "")
                            window.location.href = obj.url;
                        else window.location.href = "http://zhiq.zx26m.cn/register/cashier_msg.jsp?msg=支付成功&color=#04BE01";
                    }else{
                        if(obj.remark == "下单失败")
                            obj.remark = "支付失败，授权码过期或无效";
                        window.location.href = "http://zhiq.zx26m.cn/register/cashier_msg.jsp?msg=" + obj.remark;
                    }
                }
                break;
            default:
                break;
        }
    };
    ws.onclose = function(evt) {
//                alert("close");
    };
    ws.onopen = function(evt) {
        var obj = JSON.parse(JSON.stringify(evt));
        if(!obj.isTrusted){
            $.msg.alert("温馨提示", "连接Socket失败，请联系客服！");
            setTimeout(function () {
                window.opener=null;
                window.open('','_self');
                window.close();
            },2000);
        }
    };
};
function init(){
    startWebSocket();
};
init();