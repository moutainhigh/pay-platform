package com.pay.platform.api.pay.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.notify.service.MerchantNotifyService;
import com.pay.platform.common.util.AESUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: zjt
 * DateTime: 2019/1/6 19:38
 */
@Controller
public class PayController extends BaseController {

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @RequestMapping(value = "/api/test01", method = {RequestMethod.POST, RequestMethod.GET})
    public void test01(HttpServletRequest request, HttpServletResponse response, String data) throws Exception {

        //获取请求提交数据
        String text = IOUtils.toString(request.getInputStream(), "utf-8");
        JSONObject reqJson = new JSONObject(text);

        System.out.println(" ---> " + reqJson.toString());

        JSONObject respJson = new JSONObject();
        respJson.put("aaaa", "123");
        respJson.put("success", true);
        respJson.put("success", false);

        System.out.println(" ---> 提交请求成功 ");

        writeJson(response, respJson.toString());

    }

    /**
     * 模拟回调商户
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/pushPaySuccessInfoToMerchant", method = {RequestMethod.POST, RequestMethod.GET})
    public void testPushPaySuccessInfoToMerchant(HttpServletRequest request, HttpServletResponse response, String orderNo) throws Exception {

        boolean result = merchantNotifyService.pushPaySuccessInfoToMerchant(orderNo);
        System.out.println(" ---> " + result);

        response.getWriter().write("回调结果: " + result);
        response.getWriter().flush();

    }

    /**
     * 模拟商户接收回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/testMerchantNotify", method = {RequestMethod.POST, RequestMethod.GET})
    public void testMerchantNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String text = IOUtils.toString(request.getInputStream(), "utf-8");
        System.out.println(" 回调报文 ---> " + text);

        String params = AESUtil.decrypt(text, "6625484sd5czx6aw");
        System.out.println(" 解密后 ---> " + params);

        response.getWriter().write("SUCCESS");
        response.getWriter().flush();

    }

}