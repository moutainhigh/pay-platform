package com.pay.platform.api.payCharge.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.pay.platform.common.listener.AppContextListener;
import com.pay.platform.common.util.JsonUtil;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 第三方话冲接口
 */
public class PayUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayUtil.class);

    public static final String MERCHANT_ID = "155153962946401";                        //商家号
    public static final String AES_SECRET = "9b2692335f958a94";                        //密钥
    public static final String CHARGE_BASE_URL = "http://www.muLanqing.com.cn:8080";          //服务器地址

    /**
     * 充值接口
     *
     * @param orderNo
     * @param amount
     * @param payType
     * @param notifyUrl
     * @param clientIp
     * @return
     * @throws Exception
     */
    public static String charge(String orderNo, String amount, String payType, String notifyUrl, String clientIp) throws Exception {

        String url = CHARGE_BASE_URL + "/mobile/charge";

        Map<String, String> map = new HashMap<>();
        map.put("merchantOrderNo", orderNo);
        map.put("amount", amount);
        map.put("payType", payType);
        map.put("notifyUrl", notifyUrl);
        map.put("clientIp", clientIp);

        AESOperator aes = new AESOperator(AES_SECRET);
        String reqStr = aes.encrypt(JsonUtil.parseToJsonStr(map));
        reqStr = URLEncoder.encode(reqStr);

        JSONObject param = new JSONObject();
        param.put("merchantId", MERCHANT_ID);
        param.put("sign", reqStr);

        logger.info("话冲请求报文:" + JsonUtil.parseToJsonStr(map));
        String resStr = HttpClientPost.reqPost(url, param.toString());
        logger.info("话冲响应报文:" + orderNo + "->" + resStr);

        return resStr;
    }

    /**
     * 订单查询
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    public static String findOrder(String orderNo) throws Exception {

        String url = CHARGE_BASE_URL + "/mobile/findOrder";

        JSONObject param = new JSONObject();
        param.put("merchantId", MERCHANT_ID);
        param.put("merchantOrderId", orderNo);

        String resStr = HttpClientPost.reqPost(url, param.toString());
        logger.info("话冲响应数据:" + orderNo + "->" + resStr);

        return resStr;
    }

}
