var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.payForTable = $('#payForTable').initBootstrapTable({
        url: baseURL + '/finance/payFor/queryPayForList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#payForPager',
        queryParams: function (params) {
            $.extend(params, $('#searchpayForForm').serializeObject());
            return params;
        },
        onClickRow: function (row, tr) {
            pageScope.currentrow = row;
        },
        responseHandler: function (response) {
            var griddata = {};
            try {
                griddata.rows = response.list || [];
                griddata.total = response.total || 0;
            } catch (e) {
            }
            return griddata;
        },
        columns: [
            {title: '全选', checkbox: true},
            {
                title: '序号',
                align: 'center',
                width: 46,
                formatter: function () {
                    return arguments[2] + 1;
                }
            },
            {title: '提现单号', field: 'orderNo', align: 'center', sortable: true},
            {title: '提现金额', field: 'payForAmount', align: 'center', sortable: true},
            {title: '真实姓名', field: 'realName', align: 'center', sortable: true},
            {title: '银行卡号', field: 'bankCard', align: 'center', sortable: true},
            {
                title: '审核状态',
                field: 'checkStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.checkStatusDictDesc;
                }
            },
            {
                title: '提现状态',
                field: 'payForStatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return row.payForStatusDictDesc;
                }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.editpayFor()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showpayForDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    // html += "<button type='button' class='btn btn-link' onclick='pageScope.deletepayFor(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    return html;
                }
            }
        ], onLoadSuccess: function () {

            if (roleCode == "ROLE_MERCHANT") {
                queryMerchantAmountOfNotifypayFor();
            }

        }
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.payForTable.bootstrapTable('refresh');
    };

})();