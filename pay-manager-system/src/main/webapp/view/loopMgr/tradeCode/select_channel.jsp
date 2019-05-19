<%--
  Created by IntelliJ IDEA.
  User: zjt
  Date: 2016/10/6
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("baseURL", pageContext.getServletContext().getContextPath());
%>

<style type="text/css">

    .divChannelRow{
        margin-top:4px;
    }

    .divChannelBlock {
        height: 200px;
        float: left;
        width: 28%;
        border: 1px solid #cad9ea;
        text-align: center;
        font-size: 30px;
        color: #666;
        padding: 60px 0;
        cursor: pointer;
        background: #ece9e9;
        margin-left:15px;
    }
</style>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">选择通道</h3>
            </div>
            <div class="panel-body">

                <input id="merchantId" type="hidden" value="${param.merchantId}" />

                <div class="divChannelRow">
                    <div class="divChannelBlock" onclick="toAddTroadeCodePage('lklZfbFixed' , '拉卡拉-支付宝固码');" >拉卡拉-支付宝固码</div>
                    <div class="divChannelBlock" onclick="toAddTroadeCodePage('lklWeChatFixed' , '拉卡拉-微信固码');" >拉卡拉-微信固码</div>
                </div>

            </div>
        </div>

    </div>
</div>
<script type="text/javascript">

    /**
     * 去设置交易码界面
     */
    function toAddTroadeCodePage(channelCode , channelName){
        var merchantId = $("#merchantId").val();
        loadMenuContent('/view/loopMgr/tradeCode/tradeCode_list.jsp?merchantId=' + merchantId + "&channelCode=" + channelCode + "&channelName=" + channelName);
    }


</script>