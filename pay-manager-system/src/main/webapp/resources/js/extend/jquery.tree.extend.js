/**
 * User: zjt
 * DateTime: 16/10/3 17:05
 *
 * 扩展jquery,对常见树操作的封装,调用方式$.tree
 */
$.extend({

    tree:{

        /**
         * 初始化当前用户组织机构树
         * option.treeId: 树组件的id
         * option.callback: 各类回调,例如点击,新增,删除,修改等...., 此处只有点击
         */
        initCurrentUserOrgTree:function(option){

            var setting = {
                data : {
                    simpleData : {
                        enable : true,
                        idKey : "id",
                        pIdKey : "parentId"
                    },
                    key : {
                        name : "name"
                    }
                },
                view: {
                    showIcon : true,
                    selectedMulti: false,
                },
                callback: {
                    onClick :option.callback.onClick,
                }
            };


            $.ajax({
                url : baseURL + "/sysmgr/user/queryCurrentUserOrgList",
                type : "post",
                dataType : "json",
                data : {"_csrf" : token},
                success : function(response) {

                    //初始化树
                    var orgNodes = response;
                    $.fn.zTree.init($('#' + option.treeId), setting, orgNodes);

                    //展开所有节点
                    var organizationTree = $.fn.zTree.getZTreeObj(option.treeId);
                    organizationTree.expandAll(true);

                }
            });

        }

    }

});