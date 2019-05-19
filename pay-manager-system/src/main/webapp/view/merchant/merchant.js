var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.merchantTable = $('#merchantTable').initBootstrapTable({
        url: baseURL + '/merchant/queryMerchantList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#merchantPager',
        queryParams: function (params) {
            $.extend(params, $('#searchMerchantForm').serializeObject());
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
            {title: '商家编号', field: 'merchantNo', align: 'center', sortable: true},
            {title: '商户名称', field: 'merchantName', align: 'center', sortable: true},
            {title: '手机号', field: 'phone', align: 'center', sortable: true},
            {title: '真实姓名', field: 'realName', align: 'center', sortable: true},
            {title: '身份证号码', field: 'identityCode', align: 'center', sortable: true},
            {
                title: '状态', field: 'checkStatus', align: 'center', sortable: true, formatter: function (value) {
                return value == "waitCheck" ? "待审核" : value == "success" ? "通过" : "失败";
            }
            },
            {
                title: '开启提现通知', field: 'needNotifyWithdraw', align: 'center', sortable: true, formatter: function (value) {
                return value == 1 ? "开启" : "关闭";
            }
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";

                    if (roleCode == "ROLE_ADMIN") {
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.editMerchant()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.setCodeTrader()' >设置码商</button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.showMerchantDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteMerchantByLogic(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.review()' >审核</button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.rate()' >设置费率</button>";
                    }

                    if (roleCode == "ROLE_CODE_TRADER" || roleCode == "ROLE_ADMIN") {
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.setTradeCode(\"" + row.id + "\")' >轮询配置</button>";
                    }

                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.merchantTable.bootstrapTable('refresh');
    };

    /**
     * 新增商家
     */
    pageScope.addMerchant = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/merchant/merchant_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addMerchantForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addMerchantForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.merchantTable.bootstrapTable('refresh');
                                $(".modal-footer .btn-danger").trigger("click");
                            }
                            else {
                                $.msg.error(response.msg);
                            }

                        }, error: function (e) {
                            btn.removeAttr("disabled");
                        }

                    });

                }
            }
        });

    };

    /**
     * 删除商家
     */
    pageScope.deleteMerchant = function (id) {

        var ids = $("#merchantTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/merchant/deleteMerchant",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.merchantTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除商家
     */
    pageScope.deleteMerchantByLogic = function (id) {

        var ids = $("#merchantTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/merchant/deleteMerchantByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.merchantTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑商家
     */
    pageScope.editMerchant = function () {

        $.dialog.show({
            url: baseURL + "/view/merchant/merchant_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editMerchantNo").val(pageScope.currentrow.merchantNo);
                $("#editMerchantName").val(pageScope.currentrow.merchantName);
                $("#editPhone").val(pageScope.currentrow.phone);
                $("#editRealName").val(pageScope.currentrow.realName);
                $("#editIdentityCode").val(pageScope.currentrow.identityCode);
                //单图片上传 - 身份证-正面
                $("#fileIdCardImg1").singleImageUpload({
                    uploadExtraData: {"module": "/merchant"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIdCardImg1",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.idCardImg1       //图片路径,编辑时回显(可选)
                });

                //单图片上传 - 身份证-反面
                $("#fileIdCardImg2").singleImageUpload({
                    uploadExtraData: {"module": "/merchant"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIdCardImg2",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.idCardImg2       //图片路径,编辑时回显(可选)
                });

                //单图片上传 - icp证件
                $("#fileIcpImg").singleImageUpload({
                    uploadExtraData: {"module": "/merchant"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIcpImg",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.icpImg       //图片路径,编辑时回显(可选)
                });

                $("#editMerchantSecret").val(pageScope.currentrow.merchantSecret);
                $("#editCheckStatus").val(pageScope.currentrow.checkStatus);
                $("#editCheckDesc").val(pageScope.currentrow.checkDesc);
                $("#editIsDel").val(pageScope.currentrow.isDel);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
                $("input[name='needNotifyWithdraw'][value='" + pageScope.currentrow.needNotifyWithdraw + "']").attr("checked", "checked");

                $("#editAgentId").loadAgentIdAndNameList({agentId: pageScope.currentrow.agentId});

            },
            buttonEvents: {
                success: function () {

                    if (!$('#editMerchantForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editMerchantForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.merchantTable.bootstrapTable('refresh');
                            } else {
                                $.msg.error(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.error('修改失败，可能是由网络原因引起的，请稍候再试');
                            btn.removeAttr("disabled");
                            return false;
                        }
                    });

                    return false;

                }
            }
        });


    };

    /**
     * 查看商家详情
     * @param id
     */
    pageScope.showMerchantDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/merchant/merchant_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailMerchantNo").val(pageScope.currentrow.merchantNo);
                $("#detailMerchantName").val(pageScope.currentrow.merchantName);
                $("#detailPhone").val(pageScope.currentrow.phone);
                $("#detailRealName").val(pageScope.currentrow.realName);
                $("#detailIdentityCode").val(pageScope.currentrow.identityCode);
                //图片显示 - 身份证-正面
                $("#fileIdCardImg1").showImage({
                    imgUrl: pageScope.currentrow.idCardImg1
                });
                //图片显示 - 身份证-反面
                $("#fileIdCardImg2").showImage({
                    imgUrl: pageScope.currentrow.idCardImg2
                });
                //图片显示 - icp证件
                $("#fileIcpImg").showImage({
                    imgUrl: pageScope.currentrow.icpImg
                });
                $("#detailMerchantSecret").val(pageScope.currentrow.merchantSecret);
                $("#detailCheckStatus").val(pageScope.currentrow.checkStatus);
                $("#detailCheckDesc").val(pageScope.currentrow.checkDesc);
                $("#detailIsDel").val(pageScope.currentrow.isDel);
                var checkStatus = pageScope.currentrow.checkStatus;
                $("#checkStatus").val(checkStatus == "waitCheck" ? "待审核" : checkStatus == "success" ? "审核通过" : "审核失败");
                $("#detailCreateTime").val(pageScope.currentrow.createTime);
                $("#checkDesc").text(pageScope.currentrow.checkDesc);

            }
        });

    };
    /**
     * 审核
     * @param id
     */
    pageScope.review = function () {

        $.dialog.show({
            url: baseURL + "/view/merchant/merchant_review.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#reviewId").val(pageScope.currentrow.id);


            },
            buttonEvents: {
                success: function () {

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#reviewMerchantForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.merchantTable.bootstrapTable('refresh');
                            } else {
                                $.msg.error(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.error('修改失败，可能是由网络原因引起的，请稍候再试');
                            btn.removeAttr("disabled");
                            return false;
                        }
                    });

                    return false;

                }
            }
        });

    };


    /**
     * 設置費率
     * @param id
     */
    pageScope.rate = function () {

        $.dialog.show({
            url: baseURL + "/view/merchant/merchant_rate.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#merchantId").val(pageScope.currentrow.id);

                queryAllPayChannelListAndAgentRate(pageScope.currentrow.agentId);           //查看所有支付通道

                selectMerchantRate(pageScope.currentrow.id);               //加载商家费率信息

            },
            buttonEvents: {
                success: function () {

                    var costRate = $("#channel").find(":selected").attr("costRate").replace("%", "");
                    var nowRate = $("#rate").val().replace("%", "");
                    if (!$.validate.isNumber(nowRate)) {
                        $.msg.error("请输入合法的费率");
                        return;
                    }

                    if (parseFloat(costRate) > parseFloat(nowRate)) {
                        $.msg.error('不得低于上级成本费率!');
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#reteMerchantForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                selectMerchantRate(pageScope.currentrow.id);
                            } else {
                                $.msg.error(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.error('修改失败，可能是由网络原因引起的，请稍候再试');
                            btn.removeAttr("disabled");
                            return false;
                        }
                    });

                    return false;

                }
            }
        });

    };

    /**
     * 设置码商
     * @param id
     */
    pageScope.setCodeTrader = function () {

        $.dialog.show({
            url: baseURL + "/view/merchant/merchant_code_trader.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#merchantId").val(pageScope.currentrow.id);
                //查询所有码商
                queryAllCodeTraderByMerchantId();
            }
        });

    };

    /**
     * 设置交易码 - 为商户上号
     */
    pageScope.setTradeCode = function (merchantId) {
        loadMenuContent('/view/loopMgr/tradeCode/select_channel.jsp?merchantId=' + merchantId);
    }

})();

/**
 * 读取所有通道
 * @param channelCode
 */
function queryAllPayChannelListAndAgentRate(agentId) {

    $.ajax({
        url: baseURL + "/payChannel/queryAllPayChannelListAndAgentRate",
        type: "post",
        dataType: "json",
        data: {"_csrf": token, "agentId": agentId},
        success: function (response) {

            if (response && response.success == true) {
                var data = response.data;
                var str = "";
                for (var i = 0; i < data.length; i++) {

                    if (data[i].agentCostRate) {
                        var showCostRate = (data[i].agentCostRate * 100).toFixed(2) + "%";
                        str += "<option costRate='" + showCostRate + "' value='" + data[i].id + "'>" + data[i].channelName + "（成本费率：" + showCostRate + "）</option>";
                    } else {
                        var showCostRate = (data[i].costRate * 100).toFixed(2) + "%";
                        str += "<option costRate='" + showCostRate + "' value='" + data[i].id + "'>" + data[i].channelName + "（成本费率：" + showCostRate + "）</option>";
                    }

                }

                $("#channel").html(str);
            } else {
                $.msg.error('读取费率失败，可能是由网络原因引起的，请稍候再试');
            }

        }
    });

};

/**
 * 读取商家的费率列表
 */
function selectMerchantRate(merchantId) {

    $.ajax({
        url: baseURL + "/merchant/queryMerchantRateList",
        type: "post",
        dataType: "json",
        data: {"_csrf": token, "merchantId": merchantId},
        success: function (response) {
            if (response && response.success == true) {
                var data = response.data;
                var str = "";
                for (var i = 0; i < data.length; i++) {
                    var showMerchantRate = (data[i].rate * 100).toFixed(2) + "%";

                    str += ' <tr class="active" id="' + data[i].id + '" >';
                    str += '<td>' + data[i].channelName + '</td>';
                    str += '<td>' + showMerchantRate + '</td>';

                    if (parseInt(data[i].enabled) == 1) {
                        str += '<td>已启用</td>';
                    } else {
                        str += '<td>已关闭</td>';
                    }

                    if (parseInt(data[i].enabled) == 1) {
                        str += '<td><button   onclick="updateMerchantChannelEnabledStatus(\'' + data[i].id + '\',0)" type="button" class="btn btn-danger btn-xs" >关 闭</button>';
                    } else {
                        str += '<td><button   onclick="updateMerchantChannelEnabledStatus(\'' + data[i].id + '\',1)" type="button" class="btn btn-danger btn-xs" >启 用</button>';
                    }

                    str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button   onclick="deleteMerchantRate(\'' + data[i].id + '\')" type="button" class="btn btn-danger btn-xs" >删 除</button></td>';
                    str += ' </tr>';

                }
                $("#rateList").html(str);
            } else {
                $.msg.error('读取费率失败');
            }

        }
    });
};
