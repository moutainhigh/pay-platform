package com.pay.platform.api.pay.controller;

import com.pay.platform.api.base.controller.BaseController;
import org.json.JSONObject;
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

    @RequestMapping(value = "/api/test01", method = {RequestMethod.POST, RequestMethod.GET})
    public void test01(HttpServletRequest request, HttpServletResponse response , String data) throws Exception {

        JSONObject respJson = new JSONObject();
        respJson.put("aaaa","123");
        respJson.put("success" , true);
        respJson.put("success" , false);

        System.out.println(" ---> 提交请求成功 ");

        writeJson(response , respJson.toString());


    }


    @RequestMapping(value = "/openApi/test02", method = {RequestMethod.POST, RequestMethod.GET})
    public void test02(HttpServletRequest request, HttpServletResponse response , String data) throws Exception {

        JSONObject respJson = new JSONObject();
        respJson.put("aaaa","123");
        respJson.put("success" , true);
        respJson.put("success" , false);

        System.out.println(" ---> 提交请求成功 ");

        writeJson(response , respJson.toString());


    }

}