/**
 * User: zjt
 * DateTime: 16/10/3 17:05
 *
 * 扩展jquery,提供验证相关的操作,调用方式$.validate
 */
$.extend({

    validate: {

        /**
         * 非空验证
         * @param str
         * @returns {boolean}
         */
        isEmpty: function (str) {
            return (str == null || str == undefined || str == '');
        }

        , isNotEmpty: function (str) {
            return (str != null && str != undefined && str != '');
        }

        /**
         * 判断是否大写开头
         */
        , isUpperCaseStart: function (str) {

            if ($.validate.isNotEmpty(str)) {

                //获取第一个字符
                var c = str.substring(0, 1);
                return (str >= 'A' && str <= 'Z');
            }

            return false;

        }

        /**
         * 判断是否为合法的包名
         */
        , isJavaPackageName: function (str) {

            if ($.validate.isNotEmpty(str)) {
                var regExp = new RegExp(/^[A-Za-z\d.]+$/);
                return (regExp.test(str) && "." != str.charAt(str.length - 1));
            }

            return false;

        }
        /**
         * 判断是否为数字：包含正负整数，0以及正负浮点数
         * @param val
         * @returns {boolean}
         */
        , isNumber: function (val) {

            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var regNeg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/; //负浮点数
            if (regPos.test(val) || regNeg.test(val)) {
                return true;
            } else {
                return false;
            }

        }

        /**
         * 校验密码：//必须为字母加数字且长度不小于8位
         * @param password
         * @returns {boolean}
         * @constructor
         */
        , checkPassWord: function (password) {
            var str = password;
            if (str == null || str.length < 8) {
                return false;
            }
            var reg1 = new RegExp(/^[0-9A-Za-z]+$/);
            if (!reg1.test(str)) {
                return false;
            }
            var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
            if (reg.test(str)) {
                return true;
            } else {
                return false;
            }
        }

    }

});