<%@page language="java" contentType="text/html; charset=UTF-8" %>


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

                        <%-- 商家id、通道编码 --%>
                        <input type="hidden" name="merchantId" id="merchantId" value="${param.merchantId}"/>
                        <input type="hidden" name="channelCode" id="channelCode" value="${param.channelCode}"/>

                        <tr>
                            <td width="80" align="right">码编号：</td>
                            <td width="150">
                                <input type="text" name="codeNum" id="queryCodeNum" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">登录账号：</td>
                            <td width="150">
                                <input type="text" name="loginAccount" id="queryLoginAccount" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">号主姓名：</td>
                            <td width="150">
                                <input type="text" name="realName" id="queryRealName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchTradeCodeForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <a class="btn btn-primary" href="${baseURL}/resources/excel/拉卡拉-支付宝固码-批量导入模板.xls">下载导入模板</a>

                    <button id="import" class="btn btn-success" onclick="pageScope.batchImportExcel();">
                        <i class="glyphicon glyphicon-plus"></i> 批量导入
                    </button>

                    <button id="add" class="btn btn-success" onclick="pageScope.addTradeCode()">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>

                    <button id="remove" class="btn btn-danger" onclick="pageScope.deleteTradeCode()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>

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

<script type="text/javascript" src="${baseURL}/view/loopMgr/tradeCode/tradeCode.js"></script>
<script type="text/javascript">

    $(function () {

    });

</script>