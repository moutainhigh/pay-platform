package com.pay.platform.api.pay.unified.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.DateUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 统一支付接口：
 * <p>
 * 1、所有的支付接口经过这里进行转发
 * 2、公共接口也在此处调用
 */
@Controller
public class UnifiedPayController extends BaseController {

    @Autowired
    private UnifiedPayService unifiedPayService;

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

    /**
     * 跳转到h5支付页面
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/toH5PayPage", method = RequestMethod.GET)
    public ModelAndView toH5PayPage(HttpServletRequest request, HttpServletResponse response, String tradeId) throws Exception {

        try {

            //查询支付页面所需数据
            Map<String, Object> payPageData = unifiedPayService.queryPayPageData(tradeId);
            if (payPageData == null) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("msg", "订单不存在！");
                modelAndView.setViewName("error");
                return modelAndView;
            }

            String createTime = payPageData.get("createTime").toString();
            long orderTimeStamp = DateUtil.getTimeStamp(createTime);
            long nowTimeStamp = DateUtil.getTimeStamp(new Date());
            if (Math.abs(nowTimeStamp - orderTimeStamp) > (5 * 60)){      //有效期5分钟,单位秒
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("msg", "订单已超时！");
                modelAndView.setViewName("error");
                return modelAndView;
            }

            String payWay = payPageData.get("payWay").toString();
            //拉卡拉固码界面
            if (PayChannelEnum.lklZfbFixed.getCode().equalsIgnoreCase(payWay)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("payPageData", payPageData);
                modelAndView.setViewName("lakala/lkl_zfb_fixed");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("msg", "暂不支持该通道！");
                modelAndView.setViewName("error");
                return modelAndView;
            }

        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", "服务器内部错误！：" + e.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }

    }

}