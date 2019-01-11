/**
 * User: zjt
 * DateTime: 2016/10/13 19:41
 *
 * 扩展juqery对象方法 , 与字符串相关
 */
$.fn.extend({

    /**
     * 获取表格选中的i按,号拼接后返回
     * @returns {string}
     */
    getCheckedIds:function(id){

        //id不为空: 代表单选,直接返回该id即可
        if($.validate.isNotEmpty(id)){
            return id;
        }
        //id为空: 代表通过多选操作触发, 遍历自身, 拼接id
        else{

            if($(this).length > 0){

                var ids = "";

                $(this).each(function(index,obj){
                    ids += $(obj).val() + ",";
                });

                return ids.substring(0,ids.length - 1);

            }
            else{
                return "";
            }

        }

    },

    /**
     * 获取val不到时 , 获取text
     * @returns {*|jQuery}
     */
    valText:function(){
        return $(this).val() || $(this).text();
    } ,

    /**
     * 获取字节长度
     * @param includetxt
     * @returns {number}
     */
    getByteLength:function(includetxt){
        includetxt = includetxt || true;
        var self = $(this);
        var str = includetxt ? self.valText() : self.val();
        str = $.trim(str);

        if(!str) {
            return 0;
        }

        var byteLen = 0, len = str.length;
        for( var i = 0; i < len; i++ ){
            byteLen += str.charCodeAt(i) > 255 ? 2 : 1;
        }

        return byteLen;
    }

});

