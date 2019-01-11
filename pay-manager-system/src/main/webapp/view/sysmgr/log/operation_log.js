/**
 * User: zjt
 * DateTime: 2018/2/28 15:18
 */
var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {


    pageScope.initOperationLogTable = function () {

        pageScope.operationLogTable = $('#operationLogTable').initBootstrapTable({
            url: baseURL + '/sysmgr/log/queryOperationLogList?_csrf=' + token,
            method: 'post',
            dataType: "json",
            toolbar: '#operationLogPager',
            queryParams: function (params) {
                $.extend(params, $('#searchOperationLogForm').serializeObject());
                return params;
            },
            onClickRow: function (row, tr) {
                pageScope.currentrow = row;
            },
            columns: [
                {
                    title: '序号',
                    width: 46,
                    formatter: function () {
                        return arguments[2] + 1;
                    }
                },
                {title: '操作用户', field: 'userName', align: 'center', sortable: true},
                {title: 'IP地址', field: 'ip', align: 'center', sortable: true},
                {title: '操作模块', field: 'module', align: 'center', sortable: true},
                {title: '操作动作', field: 'operation', align: 'center', sortable: true},
                {
                    title: '操作结果',
                    field: 'status',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 0 ? "失败" : "成功";
                    }
                },
                {
                    title: '创建时间',
                    field: 'createTime',
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        return $.date.formatToDateTime(value);
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var html = "";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.showLogDetail(\"" + row.id + "\")' ><i class='glyphicon glyphicon-file'></i></button>";
                        return html;
                    }
                }
            ]
        });

    };

    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.operationLogTable.bootstrapTable('refresh');
    };

    /**
     * 查看详情
     * @param id
     */
    pageScope.showLogDetail = function (id) {

        var dialog = $.dialog.show({
            url: baseURL + "/view/sysmgr/log/operation_log_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailUserName").html(pageScope.currentrow.userName);
                $("#detailIp").html(pageScope.currentrow.ip);
                $("#detailModule").html(pageScope.currentrow.module);
                $("#detailOperation").html(pageScope.currentrow.operation);
                $("#detailDescription").html(pageScope.currentrow.description);

                $("#detailStatus").html((pageScope.currentrow.status == "0" ? '失败' :'成功' ));
                $("#detailCreateTime").html(pageScope.currentrow.createTime);

            }
        });

    };

})();