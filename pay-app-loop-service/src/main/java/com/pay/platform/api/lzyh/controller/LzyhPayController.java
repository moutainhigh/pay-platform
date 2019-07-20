package com.pay.platform.api.lzyh.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.lzyh.service.LzyhPayService;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 柳行-支付接口
 */
@Controller
public class LzyhPayController extends BaseController {

    @Autowired
    private LzyhPayService lzyhPayService;

    /**
     * 获取待生成收款码的记录
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/app/getWaitQrCodeData", method = RequestMethod.POST)
    public void getWaitQrCodeData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String codeNum = reqJson.getString("codeNum");

            json.put("status","1");
            json.put("msg","请求成功");
            json.put("data",lzyhPayService.getWaitQrCodeData(codeNum));
            writeJson(response, json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "0");
            json.put("msg", "服务器内部错误：" + e.getMessage());
            writeJson(response, json.toString());
        } finally {
            getCurrentLogger().info("响应报文：{}", json.toString());
        }

    }

}