/**
 * User: zjt
 * DateTime: 16/10/3 17:05
 *
 * 扩展jquery,提供验证相关的操作,调用方式$.validate
 */
$.extend({

    validate : {

        /**
         * 非空验证
         * @param str
         * @returns {boolean}
         */
        isEmpty:function(str){
            return (str == null || str == undefined || str == '');
        }

        , isNotEmpty:function(str){
            return (str != null && str != undefined && str != '');
        }

        /**
         * 判断是否大写开头
         */
        , isUpperCaseStart:function (str) {

            if($.validate.isNotEmpty(str)){

                //获取第一个字符
                var c = str.substring(0,1);
                return (str >= 'A' && str <= 'Z');
            }

            return false;

        }

        /**
         * 判断是否为合法的包名
         */
        , isJavaPackageName:function (str) {

            if($.validate.isNotEmpty(str)){
                var regExp = new RegExp(/^[A-Za-z\d.]+$/);
                return (regExp.test(str) && "." != str.charAt(str.length - 1));
            }

            return false;

        }

    }

});