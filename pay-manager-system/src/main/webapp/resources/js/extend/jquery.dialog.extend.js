/**
 * User: zjt
 * DateTime: 16/10/3 17:06
 *
 * 模态化窗体操作,调用方式$.dialog
 */
$.extend({

    dialog: {

        /**
         * 显示模态化窗体
         * @param option
         * options.url：url
         * options.size：大小（可选）
         * options.onLoad：加载页面成功事件，可以用于填充页面数据
         * options.buttonEvent：可在此处配置按钮事件，key为按钮的data-bb-handler="success"; 也可通过onClick=事件名称，直接在页面指定
             *  buttonEvent:{
             *      success:function(){
             *
             *      }
             *  }
         *
         * @returns
         */
        show: function (options) {

            var dialog = bootbox.dialog({
                url: options.url,
                size: options.size || "",
                closeButton: true,
                className: options.className || "default-bootbox-modal",        //自定义宽度
                buttonEvents: options.buttonEvents || {}                 //自定义底部按钮回调事件
            });

            options.onLoad && dialog.on('loaded.bs.modal', options.onLoad);

            return dialog;

        },

    }
});