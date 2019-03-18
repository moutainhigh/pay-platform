package com.pay.platform.common.util.pay;

import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.encrypt.Md5Util;
import org.json.JSONObject;

import java.util.*;

/**
 * User:
 * DateTime: 2018/8/16 09:03
 * <p>
 * 派派支付工具类
 */
public class PaiPaiPayUtil {

    /**
     * 支付相关配置信息
     */
    private static final String PAY_URL = "https://www.n9py.cn/pay";                                      //收款接口
    private static final String GET_ORDER_STATUS_URL = "https://www.n9py.cn/api/getStatusByOrderId";      //查询订单状态地址
    public static final String UID = "10430";                                                  //必填。您的商户唯一标识，注册后在设置里获得。
    private static final String TOKEN = "1a9b42a6b6bf6af1f2940dd23d95aa06";                    //加密token,不可存储在客户端

    /**
     * 获取支付二维码
     *
     * @return
     */
    public static String getPayQrCode(String price, String istype, String notify_url, String return_url, String orderid, String orderuid, String goodsname) throws Exception {

        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("uid", UID);
        reqJson.put("price", price);
        reqJson.put("istype", istype);
        reqJson.put("notify_url", notify_url);
        reqJson.put("return_url", return_url);
        reqJson.put("orderid", orderid);
        reqJson.put("orderuid", orderuid);
        reqJson.put("goodsname", goodsname);

        String key = buildPaySign(reqJson);
        reqJson.put("key", key);

        return HttpClientUtil.doPostByHttps(PAY_URL + "?format=json", new JSONObject(reqJson).toString());

    }

    /**
     * 查询订单支付状态
     *
     * @param orderNo
     * @return
     */
    public static String getStatusByOrderId(String orderNo) throws Exception {

        Map<String, String> reqJson = new HashMap<>();
        reqJson.put("uid", UID);
        reqJson.put("orderid", orderNo);
        String r = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        reqJson.put("r", r);

        //注意此接口签名方式是按：uid + orderid + r + token的顺序
        String key = Md5Util.md5_32(UID + orderNo + r + TOKEN).toLowerCase();
        reqJson.put("key", key);

        return HttpClientUtil.doPostByHttps(GET_ORDER_STATUS_URL, new JSONObject(reqJson).toString());

    }

    /**
     * 生成付款签名
     *
     * @return
     */
    public static String buildPaySign(Map<String, String> params) {

        Map<String, String> signMap = new HashMap();
        signMap.putAll(params);
        signMap.put("token", TOKEN);                    //需要一个新的map,生成不能直接在原参数中携带token

        //1, 对参数进行排序（注意需要包含token）
        List<String> keys = new ArrayList<String>(signMap.keySet());
        Collections.sort(keys);

        //2, 拼接字符串，md5 32加密完成签名生成
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String paramKey = keys.get(i);
            String paramValue = signMap.get(paramKey);
            key.append(paramValue);
        }

        return Md5Util.md5_32(key.toString()).toLowerCase();

    }

}