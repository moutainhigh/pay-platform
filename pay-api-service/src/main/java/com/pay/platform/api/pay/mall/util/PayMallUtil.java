package com.pay.platform.api.pay.mall.util;

import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.ApiSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PayMallUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayMallUtil.class);

    //电商平台密钥
    public static final String MALL_SYSTEM_API_SECRET = "452631d32c844c3282e7647ae5e1e30e";

    public static String createOrderByMall(String platformOrderNo, String orderAmount, String payWay
            , String platformNotifyUrl, String returnUrl, String clientIp, String mallServerUrl) {

        try {

            Map<String, String> params = new HashMap();
            params.put("hlxPlatformOrderNo", platformOrderNo);
            params.put("orderAmount", orderAmount);
            params.put("notifyUrl", platformNotifyUrl);
            params.put("payWay", payWay);
            params.put("returnUrl", returnUrl);
            params.put("clientIp", clientIp);

            params.put("timestamp", System.currentTimeMillis() + "");
            params.put("sign", ApiSignUtil.buildSignByMd5(params, MALL_SYSTEM_API_SECRET));

            String jsonStr = JsonUtil.parseToJsonStr(params);
            logger.info("电商下单-请求报文:" + jsonStr);
            String result = HttpClientUtil.doPost(mallServerUrl + "/hlxPay/createOrder.htm", jsonStr);
            logger.info("电商下单-响应报文:" + platformOrderNo + "->" + result);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}