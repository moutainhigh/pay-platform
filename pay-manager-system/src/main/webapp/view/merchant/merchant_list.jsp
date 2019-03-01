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
                <h3 class="panel-title">商家管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchMerchantForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">商家编号：</td>
                            <td width="150">
                                <input type="text" name="merchantNo" id="queryMerchantNo" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">商户名称：</td>
                            <td width="150">
                                <input type="text" name="merchantName" id="queryMerchantName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">手机号：</td>
                            <td width="150">
                                <input type="text" name="phone" id="queryPhone" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">真实姓名：</td>
                            <td width="150">
                                <input type="text" name="realName" id="queryRealName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchMerchantForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <c:if test="${roleCode == 'ROLE_ADMIN'}">
                        <button id="add" class="btn btn-success" onclick="pageScope.addMerchant()">
                            <i class="glyphicon glyphicon-plus"></i> 新增
                        </button>

                        <button id="remove" class="btn btn-danger" onclick="pageScope.deleteMerchantByLogic()">
                            <i class="glyphicon glyphicon-remove"></i> 删除
                        </button>
                    </c:if>

                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="merchantTable"></table>
                    <div id="merchantPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/merchant/merchant.js"></script>