package com.baseframe.generate;

import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.ApiSignUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestDsApi {

    private static final String HLX_PAY_API_SECRET = "452631d32c844c3282e7647ae5e1e30e";

    private static final String  serverURL = "http://localhost:8080";                //生产环境请求地址

    @Test
    public void createOrder() throws Exception {

        Map<String, String> params = new HashMap();
        params.put("hlxPlatformOrderNo", System.currentTimeMillis() + "");
        params.put("orderAmount", "1300");
        params.put("notifyUrl", serverURL + "/openApi/testMerchantNotify");
        params.put("payWay", "hjzfbH5");
        params.put("returnUrl", "");
        params.put("clientIp", "58.249.126.142");

        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, HLX_PAY_API_SECRET));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/hlxPay/createOrder.htm", jsonStr);

        System.out.println(" ---> " + result);

    }

}