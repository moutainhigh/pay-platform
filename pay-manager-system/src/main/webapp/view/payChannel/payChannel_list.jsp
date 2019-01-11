<%@page language="java" contentType="text/html; charset=UTF-8" %>


<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">通道管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchPayChannelForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">通道编码：</td>
                            <td width="150">
                                <input type="text" name="channelCode" id="queryChannelCode" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>

                            <td width="80" align="right">通道名称：</td>
                            <td width="150">
                                <input type="text" name="channelName" id="queryChannelName" class="form-control btn-block" aria-describedby="basic-addon1">
                            </td>


                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchPayChannelForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

                    <button id="add" class="btn btn-success" onclick="pageScope.addPayChannel()">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>

                    <button id="remove" class="btn btn-danger" onclick="pageScope.deletePayChannelByLogic()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>

                </div>

                <!-- 数据表格 -->
                <div class="data-table-wrapper">
                    <table id="payChannelTable"></table>
                    <div id="payChannelPager"></div>
                </div>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${baseURL}/view/payChannel/payChannel.js"></script>
<script type="text/javascript">

    $(function () {


    });

</script>