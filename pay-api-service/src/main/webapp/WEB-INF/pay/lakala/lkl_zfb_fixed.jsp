<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2019/5/23
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    window.baseURL = "${baseURL}";
</script>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>Title</title>
</head>
<body>

<h1>拉卡拉支付宝固码</h1>

</body>

<!-- jQuery 2.1.4 -->
<script src="${baseURL}/resources/js/jquery/jquery2.1.4/jquery-2.1.4.js"></script>

<script type="text/javascript">

    var orderId = "${payPageData.id}";
    var returnUrl = "${payPageData.returnUrl}";

    $(function () {

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


</html>
