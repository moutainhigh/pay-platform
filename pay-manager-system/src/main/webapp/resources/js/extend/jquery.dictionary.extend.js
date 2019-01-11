/**
 * User: zjt
 * DateTime: 2017/1/7 23:42
 */
$.extend({

    /**
     * 获取字典信息
     * @param dictType
     * @param succesCallback
     */
   getDictionaryInfo: function(dictType , succesCallback){

       $.ajax({
           url: baseURL + "/sysmgr/dictionary/queryDictionaryListByDictType",
           type: "post",
           dataType: "json",
           data: {"_csrf": token, "dictType": dictType},
           success: function (response) {
               if (response && response.success) {
                   succesCallback(response.data);
               }
           }
       })

   }

});