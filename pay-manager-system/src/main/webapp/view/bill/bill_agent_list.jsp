<%@page language="java" contentType="text/html; charset=UTF-8" %>


<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">代理流水管理</h3>
            </div>
            <div class="panel-body">

                <!-- 搜索条件 -->
                <form id="searchBillForm" method="post">
                    <table class="search" width="100%" border="0" cellspacing="0" cellpadding="0">

                        <tr>
                            <td width="80" align="right">所属代理：</td>
                            <td width="150">
                                <select name="agentId" id="agentId" class="form-control btn-block">
                                </select>
                            </td>

                            <td width="80" align="right">开始时间：</td>
                            <td width="150">
                                <div class='input-group date' id='beginTime_div'>
                                    <input name="beginTime" id="beginTime" type="text" class="form-control" data-date-format="YYYY-MM-DD" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td width="80" align="right">结束时间：</td>
                            <td width="150">
                                <div class='input-group date' id='endTime_div'>
                                    <input name="endTime" id="endTime" type="text" class="form-control" data-date-format="YYYY-MM-DD" aria-describedby="basic-addon1"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </td>

                            <td colspan="2">
                                <input class="btn btn-default btn-search" type="button" value="查 询" onclick="pageScope.search()">
                                <input class="btn btn-default btn-reset" type="button" value="重 置"
                                       onclick="javascript:document.getElementById('searchBillForm').reset(); pageScope.search();">
                            </td>

                        </tr>

                    </table>
                </form>

                <!-- 操作按钮 -->
                <div class="operation-button columns columns-left bars pull-left">

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

<script type="text/javascript" src="${baseURL}/view/bill/bill_agent.js"></script>
<script type="text/javascript">

    $(function () {
        $('#beginTime_div').datetimepicker();
        $('#endTime_div').datetimepicker();
    });

</script>