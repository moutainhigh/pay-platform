/**
 * User: zjt
 * DateTime: 2018/2/28 16:14
 */
var pageScope = {};         //页面作用域,每次进入列表页面置为{},避免全局变量都挂载在window下,无法释放或冲突

/**
 *  函数自执行,只会执行一遍
 */

(function () {

    /**
     * 初始化用户组织机构树
     */
    pageScope.initCurrentUserOrgTree = function () {

        $.tree.initCurrentUserOrgTree({
            treeId: "organizationTree",
            callback: {
                onClick: pageScope.onClick,             //点击事件
            }
        });

    };

    /**
     *  初始化表格
     */
    pageScope.initUserTable = function () {

        pageScope.userTable = $("#userTable").initBootstrapTable({
            url: baseURL + '/sysmgr/user/queryUserList?_csrf=' + token,
            method: "post",
            dataType: "json",
            toolbar: '#userPager',
            queryParams: function (params) {
                $.extend(params, $('#searchUserForm').serializeObject());
                return params;
            },
            onClickRow: function (row, tr) {
                pageScope.currentrow = row;
            },
            columns: [
                {title: '全选', checkbox: true},                //正确
                {
                    title: '序号',
                    width: 46,
                    formatter: function () {
                        return arguments[2] + 1;
                    }
                },
                {title: '用户昵称', field: 'nickname', align: 'center', sortable: true},
                {title: '登录账号', field: 'account', align: 'center', sortable: true},
                {title: '手机号码', field: 'phone', align: 'center', sortable: true},
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
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.toEditUser()' ><i class='glyphicon glyphicon-pencil'></i></button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.toUserDetail(\"" + row.id + "\")' ><i class='glyphicon glyphicon-file'></i></button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.toGrantRole(\"" + row.id + "\")' >授予角色</button>";
                        html += "<button type='button' class='btn btn-link' onclick='pageScope.deleteUser(\"" + row.id + "\")' ><i class='glyphicon glyphicon-remove'></i></button>";
                        return html;
                    }
                }
            ]
        });

    };

    /**
     *  查询
     */
    pageScope.search = function () {
        pageScope.userTable.bootstrapTable('refresh');
    };

    /**
     * 新增用户
     */
    pageScope.toAddUser = function () {

        $.dialog.show({
            url: baseURL + "/view/sysmgr/user/user_add.jsp?" + _csrf + "=" + token,
            onLoad: function () {

            }
        });

    };

    /**
     * 删除用户
     */
    pageScope.deleteUser = function (id) {

        var ids = $("#userTable input[name='btSelectItem']:checked").getCheckedIds(id);

        if ($.validate.isEmpty(ids)) {
            $.msg.toast("请选中一条记录!");
            return;
        }

        $.msg.confirm(function () {

            $.ajax({
                url: baseURL + "/sysmgr/user/deleteUser",
                type: "post",
                dataType: "json",
                data: {"ids": ids, "_csrf": token},
                success: function (response) {

                    if (response && response.success == true) {
                        $.msg.success(response.msg);
                        pageScope.userTable.bootstrapTable('refresh');
                    } else {
                        $.msg.error(response.msg);
                    }

                }
            });

        });

    };

    /**
     * 编辑用户
     */
    pageScope.toEditUser = function () {

        $.dialog.show({
            url: baseURL + "/view/sysmgr/user/user_edit.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#editUserId").val(pageScope.currentrow.id);
                $("#editNickName").val(pageScope.currentrow.nickname);
                $("#editAccount").val(pageScope.currentrow.account);
                $("#editPhone").val(pageScope.currentrow.phone);
                $("#editOrgId").val(pageScope.currentrow.orgId);
                $("#editOrgName").val(pageScope.currentrow.orgName);
                $("#editAvatar").val(pageScope.currentrow.avatar);

                $('#editUserForm').validation();

                /**
                 * 上传头像 - 编辑/回显示
                 */
                $("#file").singleImageUpload({
                    uploadExtraData: {"module": "user"},               //上传图片扩展参数,指定所属模块
                    hiddenField: "#editAvatar",                             //返回隐藏域路径
                    defaultPreviewContent: '<img src="' + baseURL + '/resources/img/default/default_avatar_male.jpg" style="width:160px" >',        //默认显示图片
                    imgUrl: pageScope.currentrow.avatar           //图片路径,编辑时回显(可选)
                });

            },
            success: function (response) {

            }
        });

    };

    /**
     * 用户详情
     */
    pageScope.toUserDetail = function (id) {

        $.dialog.show({
            url: baseURL + "/view/sysmgr/user/user_detail.jsp?" + _csrf + "=" + token,
            onLoad: function () {
                $("#detailId").val(pageScope.currentrow.id);
                $("#detailNickName").html(pageScope.currentrow.nickname);
                $("#detailAccount").html(pageScope.currentrow.account);
                $("#detailPhone").html(pageScope.currentrow.phone);
                $("#detailOrgName").html(pageScope.currentrow.orgName);

                //上传头像 - 显示
                $("#file").showImage({
                    imgUrl: pageScope.currentrow.avatar
                });

            },
            success: function (response) {

            }
        });

    };

    /**
     * 授予角色
     * @param id
     */
    pageScope.toGrantRole = function (id) {
        $.dialog.show({
            url: baseURL + "/view/sysmgr/user/user_grant_role.jsp?_csrf=" + token + "&userId=" + id,
            size: "small"
        });
    };

    /**
     * 查看节点信息
     * @param event
     * @param treeId
     * @param treeNode
     */
    pageScope.onClick = function (event, treeId, treeNode) {
        console.log(treeNode.id);
        $("#orgId").val(treeNode.id);
        pageScope.search();
    };


})();