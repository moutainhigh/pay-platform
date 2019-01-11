/**
 * User: zjt
 * DateTime: 16/10/3 17:02
 *
 * 扩展jquery,提示框操作,调用方式$.msg
 */
$.extend({

    msg:{

        /**
         * 成功 - 提示消息
         * @param title
         * @param msg
         */
        success:function(title,msg){

            swal({
                title: title || "操作成功"
                ,text: msg || "该提示框将会在一秒后自动关闭。"
                ,timer:1200                                            //一秒后自动取消
                ,showConfirmButton:false                               //不显示确认按钮
                ,allowOutsideClick:true                                //点击外面时,自动关闭
                ,type:"success"                                        //类型
            });

        },

        /**
         * 失败 - 提示消息
         * @param title
         * @param msg
         */
        error:function(title,msg){

            swal({
                title: title || "操作失败"
                ,text: msg || "该提示框将会在一秒后自动关闭。"
                ,timer: 1200
                ,showConfirmButton:false
                ,allowOutsideClick:true
                ,type:"error"
            });

        },

        /**
         * 仿土司 - 纯文字,不带任何图标
         * @param title
         * @param msg
         */
        toast:function(title,msg){

            swal({
                title: title || "温馨提示"
                ,text: msg || "该提示框将会在一秒后自动关闭。"
                ,timer: 1200
                ,showConfirmButton:false
                ,allowOutsideClick:true
            });

        },

        /**
         * 对话框 - 普通
         * @param title
         * @param msg
         */
        alert:function(title,msg){

            swal({
                title: title || "温馨提示"
                ,text: msg  || ""
                ,confirmButtonText: "确认"
                ,allowOutsideClick:true
            });

        },

        /**
         * 对话框 - 确认/取消
         * @param callback:回调函数
         * @param title
         * @param msg
         */
        confirm:function(callback,title,msg){

            swal({
                title: title || "确认删除所选数据?"
                ,text:msg || "该操作不能撤销!"
                ,type: "warning"
                ,showCancelButton: true
                ,confirmButtonColor: "#DD6B55"
                ,confirmButtonText: "确认"
                ,cancelButtonText: "取消"
                ,allowOutsideClick:true
                ,closeOnConfirm: false
            },callback);

        },

        /**
         * 对话框 - 输入
         * @param callback
         * @param title
         * @param msg
         */
        prompt:function(callback,title,msg){

            swal({
                title: title || "请输入",
                text: msg,
                type: "input",
                showCancelButton: true,
                closeOnConfirm: false,
                animation: "slide-from-top",
                inputPlaceholder: "请输入"
                ,confirmButtonText: "确认"
                ,cancelButtonText: "取消"
                ,allowOutsideClick:true
            }, function (inputValue) {

                if (inputValue === false){
                    return false;
                }

                if (inputValue === "") {
                    swal.showInputError("输入框不能为空，请输入！");
                    return false;
                }

                callback && callback.call(window,inputValue);

            });

        }

    }

});