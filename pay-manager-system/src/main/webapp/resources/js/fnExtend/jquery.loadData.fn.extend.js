/**
 * User:
 * DateTime: 2016/10/13 19:41
 *
 *  扩展jquery实例对象方法 , 主要用于加载常见数据的简化操作
 */
$.fn.extend({

    /**
     * 初始化bootstrapTable
     */
    initBootstrapTable: function (option) {

        return $(this).bootstrapTable({
            url: option.url,
            method: option.method || "post",
            dataType: option.dataType || "json",
            cache: false,
            classes: 'table table-hover',
            striped: true,
            pagination: true,
            onClickRow: option.onClickRow || function (item, $element) {
            },
            "queryParamsType": "limit",
            singleSelect: false,
            contentType: "application/x-www-form-urlencoded",
            pageSize: option.pageSize || 10,
            pageNumber: option.pageNumber || 1,
            pageList: option.pageList || [10, 30, 50],
            search: false,          //不显示 搜索框
            showColumns: true,      //不显拉框（选择显示的列示下）
            showRefresh: true,
            showPaginationSwitch: false,
            smartDisplay: false,
            maintainSelected: true,
            showToggle: false,
            idField: option.idField || 'id',
            sidePagination: option.sidePagination || "server",
            toolbar: option.toolbar,
            queryParams: option.queryParams,
            sortable: true,                                            //启用排序
            sortName: option.sortName || "create_time",                //默认排序方式
            sortOrder: option.sortOrder || "desc",
            onLoadSuccess: option.onLoadSuccess || function () {

            },
            onPostBody: option.onPostBody || function () {
                return false;
            },
            responseHandler: option.responseHandler || function (response) {
                var griddata = {};
                try {
                    griddata.rows = response.list || [];
                    griddata.total = response.total || 0;
                } catch (e) {

                }
                return griddata;
            },
            columns: option.columns
        });

    },

    /**
     * 加载字典 - 下拉框
     * @param option.dictType: 字典类型
     * @param option.value: 字典的值 , 根据其决定是否回显
     */
    loadDictionaryForSelect: function (option) {

        var self = $(this);

        $.ajax({
            url: baseURL + "/sysmgr/dictionary/queryDictionaryListByDictType",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "dictType": option.dictType},
            success: function (response) {

                if (response && response.success) {

                    var html = "<option value='' >请选择</option>";

                    for (var i = 0; i < response.data.length; i++) {
                        var dictionary = response.data[i];

                        if (option.value && option.value == dictionary.itemCode) {
                            html += "<option value='" + dictionary.itemCode + "' selected >" + dictionary.itemDesc + "</option>";
                        } else {
                            html += "<option value='" + dictionary.itemCode + "' >" + dictionary.itemDesc + "</option>";
                        }

                    }

                    self.html(html);

                }

            }
        });

    },

    /**
     * 加载字典 - 单选按钮
     * @param option.inputName: 表单组件
     * @param option.dictType: 字典类型
     * @param option.value: 字典的值 , 根据其决定是否回显
     */
    loadDictionaryForRadio: function (option) {

        var self = $(this);

        $.ajax({
            url: baseURL + "/sysmgr/dictionary/queryDictionaryListByDictType",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "dictType": option.dictType},
            success: function (response) {

                if (response && response.success) {

                    var html = "";

                    for (var i = 0; i < response.data.length; i++) {
                        var dictionary = response.data[i];

                        if (option.value && option.value == dictionary.itemCode) {
                            html += "<label class='checkbox-inline'>";
                            html += "<input type='radio' name='" + option.inputName + "' value='" + dictionary.itemCode + "' checked>&nbsp;&nbsp;" + dictionary.itemDesc;
                            html += "</label>";
                        } else {
                            html += "<label class='checkbox-inline'>";
                            html += "<input type='radio' name='" + option.inputName + "' value='" + dictionary.itemCode + "' >&nbsp;&nbsp;" + dictionary.itemDesc;
                            html += "</label>";
                        }

                    }

                    self.html(html);

                }

            }
        });

    },

    /**
     * 加载字典 - 复选框
     * @param option.inputName: 表单组件
     * @param option.dictType: 字典类型
     * @param option.value: 字典的值 , 根据其决定是否回显
     */
    loadDictionaryForCheckbox: function (option) {

        var inputName = option.inputName;
        var dictType = option.dictType;

        var self = $(this);

        $.ajax({
            url: baseURL + "/sysmgr/dictionary/queryDictionaryListByDictType",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "dictType": dictType},
            success: function (response) {

                if (response && response.success) {

                    var html = "";

                    for (var i = 0; i < response.data.length; i++) {
                        var dictionary = response.data[i];

                        if (option.value && option.value.indexOf(dictionary.itemCode) != -1) {
                            html += "<label class='checkbox-inline'>";
                            html += "<input type='checkbox' name='" + inputName + "' value='" + dictionary.itemCode + "' checked>" + dictionary.itemDesc;
                            html += "</label>";
                        } else {
                            html += "<label class='checkbox-inline'>";
                            html += "<input type='checkbox' name='" + inputName + "' value='" + dictionary.itemCode + "' >" + dictionary.itemDesc;
                            html += "</label>";
                        }

                    }

                    self.html(html);

                }

            }
        });

    },

    /**
     * 加载字典 - 用于界面显示
     * @param option.dictType: 字典类型
     * @param option.itemCode: 字典的值 , 根据其决定是否回显
     */
    loadDictionaryForDisplay: function (option) {

        var dictType = option.dictType;
        var itemCode = option.itemCode;

        var self = $(this);

        $.ajax({
            url: baseURL + "/sysmgr/dictionary/queryDictionaryItermDesc",
            type: "post",
            dataType: "json",
            data: {"_csrf": token, "dictType": dictType, "itemCode": itemCode},
            success: function (response) {

                if (response && response.success) {
                    self.val(response.data);
                    self.html(response.data);
                }

            }
        });

    },

    /**
     * 加载字典 - 用于界面显示
     * @param option.agentId: 代理id,回显用
     */
    loadAgentIdAndNameList: function (option) {

        var self = $(this);

        $.ajax({
            type: "post",
            url: baseURL + "/agent/queryAgentIdAndNameList?_csrf=" + token,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {
                    var str = "<option value=''>请选择代理</option>";

                    if(option && option.isDelDefaultOption){
                        str = ""
                    }

                    for (var i = 0; i < response.agentIdList.length; i++) {
                        if (option && response.agentIdList[i].id == option.agentId) {
                            str += "  <option selected  value='" + response.agentIdList[i].id + "'>" + response.agentIdList[i].agentName + " </option> ";
                        } else {
                            str += "  <option  value='" + response.agentIdList[i].id + "'>" + response.agentIdList[i].agentName + " </option> ";
                        }
                    }
                    self.html(str);

                    if(option && option.onSuccess != undefined){
                        option.onSuccess();
                    }

                } else {
                    btn.removeAttr("disabled");
                    $.msg.fail(response.msg);
                    return false;
                }
            },
            error: function () {
                return false;
            }
        });

    },

    /**
     * 加载商家 信息下拉框
     * @param option
     *  agentId：根据上级代理级联
     *  merchantId：回显用
     */
    loadMerchantIdAndNameList: function (option) {

        var self = $(this);

        var agentId = "";
        if (option && option.agentId) {
            agentId = option.agentId;
        }

        $.ajax({
            type: "post",
            url: baseURL + "/merchant/queryMerchantIdAndNameList?_csrf=" + token + "&agentId=" + agentId,
            dataType: "json",
            success: function (response) {
                if (response && response.success == true) {

                    var str = "<option value=''>请选择商家</option>";
                    if(option && option.isDelDefaultOption){
                        str = "";
                    }

                    for (var i = 0; i < response.merchantIdList.length; i++) {
                        if (option && option.merchantId && response.merchantIdList[i].id == option.merchantId) {
                            str += "  <option selected='selected'  value='" + response.merchantIdList[i].id + "'>" + response.merchantIdList[i].merchant_name + " </option> ";
                        } else {
                            str += "  <option  value='" + response.merchantIdList[i].id + "'>" + response.merchantIdList[i].merchant_name + " </option> ";
                        }

                    }
                    self.html(str);

                    if(option && option.onSuccess != undefined){
                        option.onSuccess();
                    }

                } else {
                    $.msg.fail(response.msg);
                    return false;
                }
            },
            error: function () {
                return false;
            }
        });

    }


});

