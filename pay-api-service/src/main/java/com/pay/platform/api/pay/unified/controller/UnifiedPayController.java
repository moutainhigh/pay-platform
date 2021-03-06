package com.pay.platform.api.pay.unified.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.socket.data.ClientSocketList;
import com.pay.platform.common.socket.server.ServerSocketThread;
import com.pay.platform.common.websocket.service.AppWebSocketService;
import com.pay.platform.common.util.*;
import com.pay.platform.common.util.encrypt.Base64Util;
import com.pay.platform.security.util.AppSignUtil;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 统一支付接口：
 * <p>
 * 1、所有的支付接口,根据payWay转发到对应的支付通道接口
 * 2、公共接口也在此处调用,例如订单状态查询...
 */
@Controller
public class UnifiedPayController extends BaseController {

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AppWebSocketService appWebSocketService;

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
            //柳行聚合码（支付宝与微信）
            else if (PayChannelEnum.lzyhZfb.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.lzyhWechat.getCode().equalsIgnoreCase(payWay)) {
                return new ModelAndView("forward:/api/createOrderByLzyh");
            }
            //电商模式
            else if (PayChannelEnum.ds_hj_zfb_wap.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.ds_hj_wx_wap.getCode().equalsIgnoreCase(payWay)
                    || PayChannelEnum.ds_zl_wx_wap.getCode().equalsIgnoreCase(payWay)) {
                return new ModelAndView("forward:/api/createOrderByMall");
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
     * 统一获取支付链接
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/getPayLink", method = RequestMethod.POST)
    public ModelAndView getPayLink(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            //根据支付方式,转发到对应的支付接口
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            if (!reqJson.has("tradeId")) {
                ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
                modelAndView.addObject("status", "0");
                modelAndView.addObject("msg", "tradeId不可为空！");
                return modelAndView;
            }
            String tradeId = reqJson.getString("tradeId");

            Map<String, Object> orderInfo = orderService.queryOrderById(tradeId);
            if (orderInfo == null) {
                ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
                modelAndView.addObject("status", "0");
                modelAndView.addObject("msg", "订单不存在！");
                return modelAndView;
            }
            String payWay = orderInfo.get("pay_way").toString();

            //话冲：直接返回支付链接
            if (PayChannelEnum.hcZfb.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.hcWechat.getCode().equalsIgnoreCase(payWay)) {
                ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
                modelAndView.addObject("status", "1");
                modelAndView.addObject("msg", "获取支付链接成功！");
                modelAndView.addObject("data", orderInfo.get("pay_qr_code_link").toString());
                return modelAndView;
            }
            //柳行聚合码：转发到对应接口
            else if (PayChannelEnum.lzyhZfb.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.lzyhWechat.getCode().equalsIgnoreCase(payWay)) {
                return new ModelAndView("forward:/api/getPayLinkByLzyh");
            }
            //电商模式
            else if (PayChannelEnum.ds_hj_zfb_wap.getCode().equalsIgnoreCase(payWay) || PayChannelEnum.ds_hj_wx_wap.getCode().equalsIgnoreCase(payWay)
                    || PayChannelEnum.ds_zl_wx_wap.getCode().equalsIgnoreCase(payWay)) {
                ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
                if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
                    modelAndView.addObject("status", "1");
                    modelAndView.addObject("msg", "获取支付链接成功！");
                    modelAndView.addObject("data", orderInfo.get("pay_qr_code_link").toString());
                } else {
                    modelAndView.addObject("status", "0");
                    modelAndView.addObject("msg", "获取支付链接失败！");
                }
                return modelAndView;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("status", "0");
            modelAndView.addObject("msg", "服务器内部错误：" + e.getMessage());
            return modelAndView;
        }

    }

    /**
     * 根据不同通道,跳转到不同的h5支付页面
     * <p>
     * <p>
     * openApi：开放接口,可通过浏览器直接打开,无需签名
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

            String payStatus = payPageData.get("payStatus").toString();
            if (PayStatusEnum.payed.getCode().equalsIgnoreCase(payStatus)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("msg", "订单已支付成功！");
                modelAndView.setViewName("error");
                return modelAndView;
            }

            String createTime = payPageData.get("createTime").toString();
            long orderTimeStamp = DateUtil.getTimeStamp(createTime);
            long nowTimeStamp = DateUtil.getTimeStamp(new Date());
            if (Math.abs(nowTimeStamp - orderTimeStamp) > (5 * 60)) {      //有效期5分钟,单位秒
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("msg", "订单已超时！");
                modelAndView.setViewName("error");
                return modelAndView;
            }

            //计算支付倒计时（单位秒）5分钟支付时效 - 已过去的时间
            long payCountDownTime = (5 * 60) - (nowTimeStamp - orderTimeStamp);
            payPageData.put("payCountDownTime", payCountDownTime);

            String payWay = payPageData.get("payWay").toString();

            //柳行-zfb
            if (PayChannelEnum.lzyhZfb.getCode().equalsIgnoreCase(payWay)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("baseURL", IpUtil.getBaseURL(request));
                modelAndView.addObject("payPageData", payPageData);
                modelAndView.setViewName("pay/lzyh/lzyh_zfb");
                return modelAndView;
            }
            //柳行-微信
            if (PayChannelEnum.lzyhWechat.getCode().equalsIgnoreCase(payWay)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("baseURL", IpUtil.getBaseURL(request));
                modelAndView.addObject("payPageData", payPageData);
                modelAndView.setViewName("pay/lzyh/lzyh_wechat");
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

    /**
     * 订单查询
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/findOrder", method = RequestMethod.POST)
    public void findOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String merchantNo = reqJson.getString("merchantNo");
            String merchantOrderNo = reqJson.getString("merchantOrderNo");
            OrderModel orderModel = orderService.queryOrderByOrderNo(merchantOrderNo);
            if (orderModel == null) {
                json.put("status", "0");
                json.put("msg", "订单不存在");
                writeJson(response, json.toString());
                return;
            }

            //禁止查询其它商家的订单
            if(!merchantNo.equals(orderModel.getMerchantNo())){
                json.put("status", "0");
                json.put("msg", "订单不存在");
                writeJson(response, json.toString());
                return;
            }

            Map<String, Object> data = new HashMap();
            data.put("merchantOrderNo", orderModel.getMerchantOrderNo());
            data.put("platformOrderNo", orderModel.getPlatformOrderNo());
            data.put("orderAmount", orderModel.getOrderAmount());
            data.put("payStatus", orderModel.getPayStatus());
            data.put("payWay", orderModel.getPayWay());
            data.put("payTime", orderModel.getPayTime());

            json.put("status", "1");
            json.put("msg", "查询成功");
            json.put("data", data);

            writeJson(response, json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage(), e);
            json.put("status", "0");
            json.put("msg", "服务器内部错误：" + e.getMessage());
        } finally {
            getCurrentLogger().info("响应报文：{}", json.toString());
        }

    }

}