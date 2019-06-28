<%@ page import="com.pay.platform.modules.sysmgr.user.model.UserModel" %>
<%@ page import="com.pay.platform.common.context.AppContext" %>
<%@ page import="com.pay.platform.common.util.SysUserUtil" %>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
    UserModel userModel = AppContext.getCurrentUser();
    session.putValue("roleCode", SysUserUtil.getRoleCode(userModel));
%>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">${param.channelName}</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchTradeCodeForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>

                            <td width="80" align="right">所属商户：</td>
                            <td width="180">
                                <select name="merchantId" id="queryMerchantId" class="form-control btn-block">
                                </select>
                            </td>

                            <td width="80" align="right">支付通道：</td>
                            <td width="180">
                                <select name="channelCode" id="queryChannelCode" class="form-control btn-block">
                                    <option value="">请选择</option>
                                    <option value="lzyhZfb">柳行-支付宝</option>
                                    <option value="lzyhWechat">柳行-微信</option>
                                </select>
                            </td>

                            <td width="80" align="right">码编号：</td>
                            <td width="150">
                                <input type="text" name="codeNum" id="queryCodeNum" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>


                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchTradeCodeForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                        <tr>

                            <td width="80" align="right">登录账号：</td>
                            <td width="150">
                                <input type="text" name="loginAccount" id="queryLoginAccount" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">开始时间：</td>
                            <td width="180">
                                <div class='input-group date' id='beginTime_div'>
                                    <input name="beginTime" id="beginTime" type="text" class="form-control" data-date-format="YYYY-MM-DD HH:mm:ss" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td width="80" align="right">结束时间：</td>
                            <td width="180">
                                <div class='input-group date' id='endTime_div'>
                                    <input name="endTime" id="endTime" type="text" class="form-control" data-date-format="YYYY-MM-DD HH:mm:ss" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td></td>
                            <td></td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <button id="open" class="btn btn-primary" onclick="pageScope.updateTradeCodeEnabled(1)">
                        <i class="glyphicon glyphicon-plus"></i> 批量开启
                    </button>

                    <button id="close" class="btn btn-success" onclick="pageScope.updateTradeCodeEnabled(0)">
                        <i class="glyphicon glyphicon-cloud"></i> 批量关闭
                    </button>

                    <button id="remove" class="btn btn-danger" onclick="pageScope.deleteTradeCode()">
                        <i class="glyphicon glyphicon-remove"></i> 批量删除
                    </button>

                    <button id="getOnLineDevice" class="btn btn-primary" onclick="pageScope.getOnLineDevice()">
                        <i class="glyphicon glyphicon-plus"></i> 获取连接设备
                    </button>

                </div>

                <div style="margin-top: 70px;margin-bottom: -50px;font-size: 14px;">
                    交易金额合计：<span id="totalOrderAmount" style="color: green;"></span>&nbsp;&nbsp;&nbsp;
                    交易笔数：<span id="totalOrderCount" style="color: green;"></span>&nbsp;&nbsp;&nbsp;
                    成功金额合计：<span id="successAmount" style="color: green;"></span>&nbsp;&nbsp;&nbsp;
                    成功笔数：<span id="scuccessCount" style="color: green;"></span>&nbsp;&nbsp;&nbsp;
                    成功率：<span id="successRate" style="color: red;"></span>&nbsp;&nbsp;&nbsp;
                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="tradeCodeTable"></table>
                    <div id="tradeCodePager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/loopMgr/successRate/successRate.js"></script>
<script type="text/javascript">

    $(function () {

        $("#queryMerchantId").loadMerchantIdAndNameList();

        $('#beginTime_div').datetimepicker();
        $('#endTime_div').datetimepicker();



    });

</script>