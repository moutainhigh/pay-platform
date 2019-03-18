package com.baseframe.generate;

import com.pay.platform.common.util.AESUtil;
import org.json.JSONObject;
import org.junit.Test;

import java.util.UUID;

/**
 * User:
 * DateTime: 2019/1/14 15:55
 */
public class TestMerchantNotify {

    @Test
    public void testMerchantNotify() {

        JSONObject json = new JSONObject();
        json.put("merchantNo", "12345");              //商家编号
        json.put("merchantOrderNo", UUID.randomUUID().toString());             //商户订单号
        json.put("platformOrderNo", UUID.randomUUID().toString());             //平台订单号

        String merchantSecret = UUID.randomUUID().toString().substring(0, 16);          //商户密钥
        System.out.println(" --> " + merchantSecret);

        try {
            String responseJson = AESUtil.encrypt(json.toString(), merchantSecret);
            System.out.println(" 加密结果: " + responseJson);

            String sourceJson = AESUtil.decrypt(responseJson, merchantSecret);
            System.out.println(" 解密结果: " + sourceJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}