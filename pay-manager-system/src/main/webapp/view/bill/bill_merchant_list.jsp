<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pay.platform.modules.sysmgr.user.model.UserModel" %>
<%@ page import="com.pay.platform.common.context.AppContext" %>
<%@ page import="com.pay.platform.common.util.SysUserUtil" %>
<%@page language="java" contentType="text/html; charset=UTF-8" %>

<%
    UserModel userModel = AppContext.getCurrentUser();
    session.putValue("roleCode", SysUserUtil.getRoleCode(userModel));
%>
<script type="text/javascript">
    var roleCode = "${roleCode}";
</script>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">商家流水管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchBillForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <%--<td width="80" align="right">所属代理：</td>--%>
                            <%--<td width="150">--%>
                            <%--<select name="agentId" id="queryAgentId" class="form-control btn-block">--%>
                            <%--</select>--%>
                            <%--</td>--%>

                            <td width="80" align="right">所属商户：</td>
                            <td width="150">
                                <select name="merchantId" id="queryMerchantId" class="form-control btn-block">
                                </select>
                            </td>

                            <td width="80" align="right">开始时间：</td>
                            <td width="200">
                                <div class='input-group date' id='beginTime_div'>
                                    <input name="beginTime" id="beginTime" type="text" class="form-control" data-date-format="YYYY-MM-DD HH:mm:ss" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td width="80" align="right">结束时间：</td>
                            <td width="200">
                                <div class='input-group date' id='endTime_div'>
                                    <input name="endTime" id="endTime" type="text" class="form-control" data-date-format="YYYY-MM-DD HH:mm:ss" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td width="80" align="right">统计方式：</td>
                            <td width="150">
                                <select name="statisticsWay" id="queryStatisticsWay" class="form-control btn-block">
                                    <option value="day">按天</option>
                                    <option value="timeLine">按时间段</option>
                                </select>
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="resetForm();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">
                    <button id="UpMonth" class="btn btn-success" onclick="getUpMonth();">
                        <i class="glyphicon"></i> 上月
                    </button>
                    <button id="TheMonth" class="btn btn-success" onclick="getCurrentMonth();">
                        <i class="glyphicon"></i> 当月
                    </button>
                    <button id="zhouDay" class="btn btn-success" onclick="getCurrentWeek();">
                        <i class="glyphicon"></i> 本周
                    </button>
                    <button id="qianDay" class="btn btn-success" onclick="getTheDayBeforeYesterday();">
                        <i class="glyphicon"></i> 前天
                    </button>
                    <button id="yesterDay" class="btn btn-success" onclick="getYesterDay();">
                        <i class="glyphicon"></i> 昨天
                    </button>
                    <button id="toDay" class="btn btn-success" onclick="getToDay();">
                        <i class="glyphicon"></i> 今天
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
                    <table id="billTable"></table>
                    <div id="billPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/bill/bill_merchant.js"></script>
<script type="text/javascript">

    $(function () {
        $('#beginTime_div').datetimepicker();
        $('#endTime_div').datetimepicker();

//        //加载代理,以供选择
//        $("#queryAgentId").loadAgentIdAndNameList({
//            isDelDefaultOption: true,
//            onSuccess: function () {
//
//                var defaultAgentId = $("#queryAgentId").find("option:selected").val();
//
//                //加载对应商家
//                $("#queryMerchantId").loadMerchantIdAndNameList({
//                    isDelDefaultOption: true,
//                    agentId: defaultAgentId,
//                    onSuccess: function () {
//                        pageScope.billTable.bootstrapTable('refresh');
//                    }
//                });
//
//            }
//        });

//        //级联操作
//        $("#queryAgentId").change(function () {
//            var value = $(this).val();
//            $("#queryMerchantId").loadMerchantIdAndNameList({agentId: value});
//        });

        //加载对应商家
        $("#queryMerchantId").loadMerchantIdAndNameList({
            isDelDefaultOption: true,
            onSuccess: function () {
                pageScope.billTable.bootstrapTable('refresh');
                pageScope.statisticsTradeInfo();
            }
        });

    });

    function resetForm() {
        $("#queryAgentId").find("option:selected").attr("selected", false);
        javascript:document.getElementById('searchBillForm').reset();
        pageScope.search();
    }


    function formatDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = month < 10 ? "0" + month : month + "";
        var day = date.getDate();
        day = day < 10 ? "0" + day : "" + day;
        return year + "-" + month + "-" + day;
    }

    /**
     * 获取今天日期
     */
    function getToDay() {
        var day1 = new Date();
        var day1Str = formatDate(day1);
        $("#beginTime").val(day1Str + " 00:00:00");
        $("#endTime").val(day1Str + " 23:59:59");
        pageScope.search();
    }

    /**
     * 获取昨天日期
     */
    function getYesterDay() {
        var day1 = new Date(new Date().getTime() - 24 * 1 * 60 * 60 * 1000);
        var day1Str = formatDate(day1);
        $("#beginTime").val(day1Str + " 00:00:00");
        $("#endTime").val(day1Str + " 23:59:59");
        pageScope.search();
    }

    /**
     * 获取前天日期
     */
    function getTheDayBeforeYesterday() {
        var day1 = new Date(new Date().getTime() - 24 * 2 * 60 * 60 * 1000);
        var day1Str = formatDate(day1);
        $("#beginTime").val(day1Str + " 00:00:00");
        $("#endTime").val(day1Str + " 23:59:59");
        pageScope.search();
    }

    /**
     * 获取本周
     */
    function getCurrentWeek() {
        var now = new Date();
        var nowTime = now.getTime();
        var day = now.getDay();
        var oneDayLong = 24 * 60 * 60 * 1000;
        var MondayTime = nowTime - (day - 1) * oneDayLong;
        var SundayTime = nowTime + (7 - day - 1) * oneDayLong;

        var monday = new Date(MondayTime);
        var sunday = new Date(SundayTime);
        $("#beginTime").val(formatDate(monday) + " 00:00:00");
        $("#endTime").val(formatDate(new Date()) + " 23:59:59");
        pageScope.search();
    }

    /**
     * 获取当前月份
     */
    function getCurrentMonth() {
        var day1 = new Date();
        day1.setDate(1);
        var day2 = new Date();
        var day1Str = formatDate(day1);
        var day2Str = formatDate(day2);
        $("#beginTime").val(day1Str + " 00:00:00");
        $("#endTime").val(day2Str + " 23:59:59");
        pageScope.search();
    }

    /**
     * 获取上个月
     */
    function getUpMonth() {
        var day1 = new Date();
        day1.setMonth(day1.getMonth() - 1);
        day1.setDate(1);
        var day2 = new Date();
        day2.setDate(1)
        day2 = new Date(day2.getTime() - 24 * 1 * 60 * 60 * 1000)
        var day1Str = formatDate(day1);
        var day2Str = formatDate(day2);
        $("#beginTime").val(day1Str + " 00:00:00");
        $("#endTime").val(day2Str + " 23:59:59");
        pageScope.search();
    }

</script>