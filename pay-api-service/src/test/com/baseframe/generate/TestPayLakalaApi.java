package com.baseframe.generate;

import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.AESUtil;
import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.ApiSignUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/3/3 11:33
 */
public class TestPayLakalaApi {

    String merchantNo = "17818991616";                                  //商家编号
    String merchantSecret = "9ae482f4380b412d8554018f7bf2d023";         //商家密钥：调接口签名用
    String notifySecret = "b7874e246f916b97";                           //回调密钥：接收支付回调,解密数据用

    String serverURL = "http://localhost:8080";        //系统请求地址

    /**
     * 测试充值下单
     *
     * @throws Exception
     */
    @Test
    public void createOrderByLklFixed() throws Exception {

//        for (int i = 0; i < 20; i++) {

            Map<String, String> params = new HashMap();
            params.put("merchantNo", merchantNo);
            params.put("merchantOrderNo", System.currentTimeMillis() + "");
            params.put("orderAmount", "100");
            params.put("payWay", PayChannelEnum.lklZfbFixed.getCode());
            params.put("notifyUrl", serverURL + "/openApi/testMerchantNotify");
            params.put("returnUrl", "www.qq.com");
            params.put("clientIp", "58.249.126.142");
            params.put("timestamp", System.currentTimeMillis() + "");
            params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

            String jsonStr = JsonUtil.parseToJsonStr(params);
            String result = HttpClientUtil.doPost(serverURL + "/api/unifiedCreateOrder", jsonStr);

            System.out.println(" ---> " + result);

//        }

    }

    /**
     *
     * 测试获取支付链接
     *
     * @throws Exception
     */
    @Test
    public void testGetPayLink() throws Exception {

        String tradeId = "656ee932-3f63-4178-ab2f-8a64a882e76b";

        Map<String, String> params = new HashMap();
        params.put("tradeId", tradeId);
        params.put("merchantNo", merchantNo);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/getPayLink", jsonStr);

        System.out.println(" ---> " + result);

    }


    /**
     * 测试支付回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/testMerchantNotify", method = {RequestMethod.POST, RequestMethod.GET})
    public void testMerchantNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String text = IOUtils.toString(request.getInputStream(), "utf-8");
        System.out.println(" 回调报文 ---> " + text);

        String params = AESUtil.decrypt(text, notifySecret);
        System.out.println(" 解密后 ---> " + params);

        //业务操作...

        response.getWriter().write("SUCCESS");          //需返回SUCCESS给平台
        response.getWriter().flush();

    }

}