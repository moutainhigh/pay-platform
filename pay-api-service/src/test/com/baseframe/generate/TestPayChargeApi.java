package com.baseframe.generate;

import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.ApiSignUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/3/3 11:33
 */
public class TestPayChargeApi {

    String merchantNo = "17818991616";
    String merchantSecret = "9ae482f4380b412d8554018f7bf2d023";
    String serverURL = "http://jintaotest.frpgz1.idcfengye.com";

    /**
     * 测试充值下单
     *
     * @throws Exception
     */
    @Test
    public void testCreateOrderByCharge() throws Exception {

        Map<String,String> params = new HashMap();
        params.put("merchantNo" , merchantNo);
        params.put("merchantOrderNo" , System.currentTimeMillis() + "");
        params.put("orderAmount" , "100");
        params.put("payWay" , "2");
        params.put("notifyUrl" , serverURL + "/openApi/testMerchantNotify");
        params.put("clientIp" , "58.249.126.142");
        params.put("timestamp" , System.currentTimeMillis() + "");
        params.put("sign" , ApiSignUtil.buildSignByMd5(params , merchantSecret) );

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/createOrderByCharge" , jsonStr);

        System.out.println(" ---> " + result);


    }


}