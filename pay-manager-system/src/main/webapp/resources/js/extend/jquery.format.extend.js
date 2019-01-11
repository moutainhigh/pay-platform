/**
 * User: zjt
 * DateTime: 2016/12/24 13:14
 */
$.extend({

    format:{

        /**
         * 格式化金钱
         * @param str
         */
        formatMoney:function(number){
            return accounting.formatMoney(number , "￥");
        },

    }

});