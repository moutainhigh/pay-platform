var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */
(function () {

    pageScope.agentTable = $('#agentTable').initBootstrapTable({
        url: baseURL + '/agent/queryAgentList?_csrf=' + token,
        method: 'post',
        dataType: "json",
        toolbar: '#agentPager',
        queryParams: function (params) {
            $.extend(params, $('#searchAgentForm').serializeObject());
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
            {title: '代理编号', field: 'agentNo', align: 'center', sortable: true},
            {title: '代理名称', field: 'agentName', align: 'center', sortable: true},
            {title: '手机号', field: 'phone', align: 'center', sortable: true},
            {title: '真实姓名', field: 'realName', align: 'center', sortable: true},
            {title: '身份证号码', field: 'identityCode', align: 'center', sortable: true},
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
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.editAgent()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.showAgentDetail()' ><i class='glyphicon glyphicon-file'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteAgentByLogic(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                    html += "<button type='button' class='btn btn-link' onclick='pageScope.rate()' >设置费率</button>";
                    return html;
                }
            }
        ]
    });


    /**
     * 查询
     */
    pageScope.search = function () {
        pageScope.agentTable.bootstrapTable('refresh');
    };

    /**
     * 新增代理
     */
    pageScope.addAgent = function () {

        var dialog = $.dialog.show({
            url: baseURL + "/view/agent/agent_add.jsp?" + _csrf + "=" + token,
            buttonEvents: {
                success: function () {

                    if (!$("#addAgentForm").valid()) {                   //表单验证
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#addAgentForm').ajaxSubmit({
                        dataType: 'json',
                        type: "post",
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                pageScope.agentTable.bootstrapTable('refresh');
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
     * 删除代理
     */
    pageScope.deleteAgent = function (id) {

        var ids = $("#agentTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/agent/deleteAgent",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.agentTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 逻辑删除代理
     */
    pageScope.deleteAgentByLogic = function (id) {

        var ids = $("#agentTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/agent/deleteAgentByLogic",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.agentTable.bootstrapTable('refresh');
                    } else {
                        $.msg.fail(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑代理
     */
    pageScope.editAgent = function () {

        $.dialog.show({
            url: baseURL + "/view/agent/agent_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editId").val(pageScope.currentrow.id);
                $("#editAgentNo").val(pageScope.currentrow.agentNo);
                $("#editAgentName").val(pageScope.currentrow.agentName);
                $("#editPhone").val(pageScope.currentrow.phone);
                $("#editRealName").val(pageScope.currentrow.realName);
                $("#editIdentityCode").val(pageScope.currentrow.identityCode);
                //单图片上传 - 身份证-正面
                $("#fileIdCardImg1").singleImageUpload({
                    uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIdCardImg1",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.idCardImg1       //图片路径,编辑时回显(可选)
                });

                //单图片上传 - 身份证-反面
                $("#fileIdCardImg2").singleImageUpload({
                    uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIdCardImg2",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.idCardImg2       //图片路径,编辑时回显(可选)
                });

                //单图片上传 - icp证件
                $("#fileIcpImg").singleImageUpload({
                    uploadExtraData: {"module": "/agent"},                     //上传图片扩展参数,指定所属模块
                    hiddenField: "#editIcpImg",                 //返回隐藏域路径
                    imgUrl: pageScope.currentrow.icpImg       //图片路径,编辑时回显(可选)
                });

                $("#editIsDel").val(pageScope.currentrow.isDel);
                $("#editCreateTime").val(pageScope.currentrow.createTime);
            },
            buttonEvents: {
                success: function () {

                    if (!$('#editAgentForm').valid()) {
                        return false;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#editAgentForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                $.msg.success(response.msg);
                                $(".modal-footer .btn-danger").trigger("click");
                                pageScope.agentTable.bootstrapTable('refresh');
                            } else {
                                $.msg.fail(response.msg);
                                return false;
                            }

                        },
                        error: function () {
                            $.msg.fail('修改失败，可能是由网络原因引起的，请稍候再试');
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
     * 查看代理详情
     * @param id
     */
    pageScope.showAgentDetail = function () {

        $.dialog.show({
            url: baseURL + "/view/agent/agent_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {

                $("#detailId").val(pageScope.currentrow.id);
                $("#detailAgentNo").val(pageScope.currentrow.agentNo);
                $("#detailAgentName").val(pageScope.currentrow.agentName);
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
                $("#detailIsDel").val(pageScope.currentrow.isDel);
                $("#detailCreateTime").val(pageScope.currentrow.createTime);

            }
        });

    };

    /**
     * 設置費率
     * @param id
     */
    pageScope.rate = function () {

        $.dialog.show({
            url: baseURL + "/view/agent/agent_rate.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#agentId").val(pageScope.currentrow.id);

                queryAllPayChannelList();           //查看所有支付通道

                selectAgentRate(pageScope.currentrow.id);               //加载代理费率信息
            },
            buttonEvents: {
                success: function () {

                    var rate = $("#costRate").val();
                    var nowRate = $("#rate").val();
                    if (rate > nowRate) {
                        $.msg.error('不得低于成本费率!');
                        return;
                    }

                    var btn = $(".modal-footer .btn-success");        //防止重复提交
                    btn.attr("disabled", "disabled");

                    $('#reteAgentForm').ajaxSubmit({
                        dataType: 'json',
                        success: function (response) {
                            btn.removeAttr("disabled");

                            if (response && response.success) {
                                selectAgentRate(pageScope.currentrow.id);
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
     * 读取所有通道
     * @param channelCode
     */
    function queryAllPayChannelList() {

        $.ajax({
            url: baseURL + "/payChannel/queryAllPayChannelList",
            type: "post",
            dataType: "json",
            data: {"_csrf": token},
            success: function (response) {
                if (response && response.success == true) {
                    var data = response.data;
                    var str = "";
                    for (var i = 0; i < data.length; i++) {
                        str += "<option  rate='" + data[i].costRate + "' value='" + data[i].id + "'>" + data[i].channelName + "（" + data[i].costRate + "）</option>";
                    }
                    $("#costRate").val(data[0].costRate);
                    $("#channel").html(str);
                } else {
                    $.msg.error('读取费率失败，可能是由网络原因引起的，请稍候再试');
                }

            }
        });
    };

    /**
     * 读取代理的费率列表
     */
    function selectAgentRate(agentId) {

        $.ajax({
            url: baseURL + "/agent/queryAgentRateList",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "agentId": agentId},
            success: function (response) {
                if (response && response.success == true) {
                    var data = response.data;
                    var str = "";
                    for (var i = 0; i < data.length; i++) {
                        str += ' <tr class="active" id="' + data[i].id + '" >';
                        str += '<td>' + data[i].channelName + '</td>' +
                            '<td>' + data[i].rate + '</td>' +
                            '<td><button   onclick="deleteAgentRate(\'' + data[i].id + '\')" type="button" class="btn btn-danger btn-xs" >删 除</button></td>';
                        str += ' </tr>';
                    }
                    $("#rateList").html(str);
                } else {
                    $.msg.error('读取费率失败');
                }

            }
        });
    };

})();