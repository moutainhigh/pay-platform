<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pay.platform.modules.sysmgr.user.model.UserModel" %>
<%@ page import="com.pay.platform.common.context.AppContext" %>
<%@ page import="com.pay.platform.common.util.SysUserUtil" %>

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
                <h3 class="panel-title">提现申请管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchWithdrawForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">提现单号：</td>
                            <td width="150">
                                <input type="text" name="orderNo" id="queryOrderNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">真实姓名：</td>
                            <td width="150">
                                <input type="text" name="realName" id="queryRealName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">银行卡号：</td>
                            <td width="150">
                                <input type="text" name="bankCard" id="queryBankCard" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>


                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchWithdrawForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <div style="height: 30px;margin-left: 15px;color:red;font: 14px bold;">
                    <span width="80" align="right">账户余额：</span>
                    <span width="150" id="accountAmount" >0.00</span>

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span width="80" align="right">冻结资金：</span>
                    <span width="150" id="freezeAmount">0.00</span>

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span width="80" align="right">可提现余额：</span>
                    <span width="150" id="withdrawableAmount" >0.00</span>
                </div>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <button id="add" class="btn btn-success" onclick="pageScope.addWithdraw()">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>

                    <%--<button id="remove" class="btn btn-danger" onclick="pageScope.deleteWithdraw()">--%>
                        <%--<i class="glyphicon glyphicon-remove"></i> 删除--%>
                    <%--</button>--%>

                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="withdrawTable"></table>
                    <div id="withdrawPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/finance/withdraw/withdraw.js"></script>
<script type="text/javascript">

    $(function () {

        pageScope.queryAccountAmount();

    });

</script>