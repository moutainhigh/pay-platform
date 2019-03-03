package com.baseframe.generate;

import com.pay.platform.api.payCharge.util.AESOperator;
import com.pay.platform.api.payCharge.util.PayUtil;
import com.pay.platform.common.util.AESUtil;
import com.pay.platform.common.util.HttpClientUtil;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.ApiSignUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/3/3 11:33
 */
public class TestPayChargeApi {

    String merchantNo = "17818991616";                                  //商家编号
    String merchantSecret = "9ae482f4380b412d8554018f7bf2d023";         //商家密钥：调接口签名用
    String notifySecret = "b7874e246f916b97";                           //回调密钥：接收支付回调,解密数据用

    String serverURL = "http://jintaotest.frpgz1.idcfengye.com";        //请求地址

    /**
     * 测试充值下单
     *
     * @throws Exception
     */
    @Test
    public void testCreateOrderByCharge() throws Exception {

        Map<String, String> params = new HashMap();
        params.put("merchantNo", merchantNo);
        params.put("merchantOrderNo", System.currentTimeMillis() + "");
        params.put("orderAmount", "10");
        params.put("payWay", "1");
        params.put("notifyUrl", serverURL + "/openApi/testMerchantNotify");
        params.put("clientIp", "58.249.126.142");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/createOrderByCharge", jsonStr);

        System.out.println(" ---> " + result);


    }

    /**
     * 测试支付回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void testMerchantNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String text = IOUtils.toString(request.getInputStream(), "utf-8");
        System.out.println(" 回调报文 ---> " + text);

        String params = AESUtil.decrypt(text, notifySecret);
        System.out.println(" 解密后 ---> " + params);

        //业务操作...

        response.getWriter().write("SUCCESS");          //需返回SUCCESS给平台
        response.getWriter().flush();

    }

    /**
     * 测试查询订单
     *
     * @throws Exception
     */
    @Test
    public void testFindOrder() throws Exception {

        Map<String, String> params = new HashMap();
        params.put("merchantNo", merchantNo);
        params.put("merchantOrderNo", "2019030313003200421978260");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/findOrder", jsonStr);

        System.out.println(" ---> " + result);


    }


}