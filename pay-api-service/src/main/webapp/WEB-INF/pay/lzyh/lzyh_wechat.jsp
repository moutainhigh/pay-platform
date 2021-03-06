<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2019/5/23
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<script type="text/javascript">
    window.baseURL = "${baseURL}";
</script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>微信安全支付</title>
    <link href="${baseURL}/resources/css/wechat_phone.css" rel="stylesheet" media="screen">
    <script src="${baseURL}/resources/js/jquery.min.js"></script>
    <script src="${baseURL}/resources/js/cashier/base64.js"></script>
    <style>
        .btn-danger:hover {
            color: #fff;
            background-color: #3AB035;
            border-color: #3AB035;
        }

        .btn-dangeRr {
            color: #fff;
            background-color: #d9534
        }

        .btn {
            display: inline-block;
            padding: 6px 12px;
            background-color: #3AB035;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: normal;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 10px;
            width: 170px;
            height: 46px;
            font-size: 18px;
        }

        .btn:hover {
            color: #fff;
            text-decoration: none;
            cursor: default;
        }
    </style>
</head>
<body ontouchstart style="">
<header class="m-head" style="display: none"><h1>支付订单</h1></header>
<section class="m-zfb">
    <div style="background: #FFFFFF;vertical-align:middle; ">
        <img src="${baseURL}/resources/images/wechat_logo.png" style="width: 36px;height: 36px;vertical-align:middle; "/>
        <div class="logo" style="line-height: 56px;font-size: 20px; padding:0;display: inline;vertical-align:middle; color:red;font-weight: bold;">&nbsp;微信安全支付</div>
    </div>
    <dl style="padding: 0; margin: 0;">
        <h1 style="color:red;font-weight: bold;">￥${payPageData.payFloatAmount}</h1>
        <div>
            <img id="code" src="" style="width: 150px; height: 150px;margin-top: -10px;display: none;"/>
            <input id="btnWaitPay" type="button" value="支付授权中（5秒）" class="btn btn-danger" style="display: none;"/>
        </div>
        <div class="tip" style="text-align: center;margin-top: 30px;font-size:18px;color:red;padding-left:0px;">
            <%--<span style="color: red; font-weight: 800" id="tipsRed">若启动微信无法支付，请使用以下步骤：<br/><br/></span>--%>
            1、请截屏保存到相册<br/>
            2、打开微信扫一扫<br/>
            3、点击相册>选择二维码图片>完成支付<br/>
            <p style="font-weight: bold;margin-top:6px;">金额随机浮动,系统将按实际支付金额上分</p>
            <p style="font-weight: bold;margin-top:6px;">如遇到FK提示,请点击继续完成支付。</p>
            <img src="${baseURL}/resources/images/pay/xiangce.jpg" style="margin-top:10px;"/>
        </div>
        <h1 id="time" style="margin: 0; padding: 0; line-height: 50px; height: 50px;"></h1>
        <p style="border: 1px rgb(222,222,222) dashed"></p>
        <p style="margin-top: 10px;">支付即时到账，未到账请与我们联系</p>
        <p style="padding-bottom: 10px;">订单号：${payPageData.merchantOrderNo}</p>
    </dl>
</section>

<script type="text/javascript">

    $(function () {

        var time = parseInt("${payPageData.payCountDownTime}");         //支付倒计时,剩余秒数
        var orderId = "${payPageData.id}";
        var returnUrl = "${payPageData.returnUrl}";
        var payQrCodeLink = "${payPageData.payQrCodeLink}";

        //未生成完二维码,等待3秒,引导用户等待;
        var payWaitTime = 5;        //支付等待时间
        if (payQrCodeLink == null || payQrCodeLink == undefined || payQrCodeLink == '') {
            $("#btnWaitPay").show();

            //倒计时
            var payWaitInterval = setInterval(function () {
                payWaitTime--;
                if (payWaitTime >= 0) {
                    $("#btnWaitPay").val("支付授权中（" + payWaitTime + "秒）");
                } else {
                    clearInterval(payWaitInterval);

                    //请求服务器,获取最新的二维码
                    $.ajax({
                        url: baseURL + "/openApi/queryQrCodeByOrderId",
                        type: "post",
                        dataType: "json",
                        data: {"orderId": orderId},
                        success: function (response) {
                            if (response.status == "1") {
                                $("#btnWaitPay").hide();

                                //显示二维码
                                $("#code").show();
                                $("#code")[0].src = "${baseURL}/openApi/getPayQrcode?payUrl=" + encodeURIComponent(response.data);
                            } else {
                                $("#btnWaitPay").val("授权失败,请重试!");
                                alert("授权失败,可能是网络原因,请重新进行充值！");
                            }
                        }
                    });


                }
            }, 1250);

        }
        //已生成完了二维码; 直接显示;
        else {
            $("#code").show();
            $("#code")[0].src = "${baseURL}/openApi/getPayQrcode?payUrl=" + encodeURIComponent(payQrCodeLink);
        }

        /**
         * 支付倒计时
         */
        var interval = setInterval(function () {
            time--;
            if (time >= 0) {
                $("#time").text("0" + Math.floor(time / 60) + ":" + (time % 60 < 10 ? "0" : "") + time % 60);
            } else {
                clearInterval(interval);
                location.href = (baseURL + "/view/error.jsp?msg=订单已超时,请重新下单!");
            }
        }, 1000);

        /**
         * 3秒钟轮询一次后台接口,查看订单是否支付成功
         */
        setInterval(function () {

            $.ajax({
                url: baseURL + "/openApi/queryOrderStatus",
                type: "post",
                dataType: "json",
                data: {"orderId": orderId},
                success: function (response) {

                    //订单超时或者不存在
                    if (response.status == "0") {
                        location.href = (baseURL + "/view/error.jsp?msg=" + response.msg);
                    }
                    //支付成功
                    else if (response.status == "1") {

                        //客户有默认返回页面则用returnUrl; 没有则使用默认的成功跳转页面;
                        if (returnUrl !== null && returnUrl !== undefined && returnUrl !== '') {
                            if (returnUrl.indexOf("http") != -1 || returnUrl.indexOf("https") != -1) {
                                location.href = returnUrl;
                            } else {
                                location.href = "http://" + returnUrl;
                            }
                        } else {
                            location.href = (baseURL + "/view/success.jsp?msg=" + response.msg);
                        }

                    }

                }
            });

        }, 3000);

    });

</script>
</body>
</html>