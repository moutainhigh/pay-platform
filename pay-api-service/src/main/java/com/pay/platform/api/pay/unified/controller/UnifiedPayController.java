package com.pay.platform.api.pay.unified.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.common.enums.PayChannelEnum;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一下单接口：所有的支付接口经过这里进行转发
 */
@Controller
public class UnifiedPayController extends BaseController {

    /**
     * 统一下单接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/unifiedCreateOrder", method = RequestMethod.POST)
    public ModelAndView unifiedCreateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            //根据支付方式,转发到对应的支付接口
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String payWay = reqJson.getString("payWay");

            //话冲
            if (PayChannelEnum.hcZfb.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.hcWechat.getCode().equalsIgnoreCase(payWay)) {
                return new ModelAndView("forward:/api/createOrderByCharge");
            }
            //拉卡拉固码
            else if (PayChannelEnum.lklZfbFixed.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.lklWeChatFixed.getCode().equalsIgnoreCase(payWay)) {
                return new ModelAndView("forward:/api/createOrderByLklFixed");
            }

            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("status", "0");
            modelAndView.addObject("msg", "无效的支付方式！");
            return modelAndView;

        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("status", "0");
            modelAndView.addObject("msg", "服务器内部错误：" + e.getMessage());
            return modelAndView;
        }

    }

}