package com.pay.platform.api.payCharge.controller;

import com.google.gson.JsonObject;
import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.payCharge.service.PayChargeService;
import com.pay.platform.api.payCharge.util.AESOperator;
import com.pay.platform.api.payCharge.util.PayUtil;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/6 19:38
 * <p>
 * 充值下单
 */
@Controller
public class PayChargeController extends BaseController {

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private PayChargeService payChargeService;

    @Autowired
    private OrderService orderServicel;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 充值下单接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/createOrderByCharge", method = RequestMethod.POST)
    public void createOrderByCharge(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String merchantNo = reqJson.getString("merchantNo");
            String merchantOrderNo = reqJson.getString("merchantOrderNo");
            String orderAmount = reqJson.getString("orderAmount");
            String payWay = reqJson.getString("payWay");
            String notifyUrl = reqJson.getString("notifyUrl");
            String clientIp = reqJson.getString("clientIp");

            //创建订单
            String baseUrl = IpUtil.getBaseURL(request);
            String result = payChargeService.createOrderByCharge(merchantNo, merchantOrderNo, orderAmount, payWay, notifyUrl, clientIp, baseUrl);
            if (StringUtil.isEmpty(result)) {
                json.put("status", "0");
                json.put("msg", "下单失败");
                writeJson(response, json.toString());
                return;
            }

            JSONObject resultJson = new JSONObject(result);
            if (resultJson.has("resultCode") && 200 == resultJson.getInt("resultCode")) {
                json.put("status", "1");
                json.put("msg", "下单成功");
                json.put("data", resultJson.getString("data"));
                writeJson(response, json.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage(), e);
            json.put("status", "0");
            json.put("msg", "服务器内部错误：" + e.getMessage());
            writeJson(response, json.toString());
        } finally {
            getCurrentLogger().info("响应报文：{}", json.toString());
        }

    }

    /**
     * 充值结果回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/payNotifyOfCharge", method = {RequestMethod.POST , RequestMethod.GET})
    public void payNotifyOfCharge(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            String secretData = IOUtils.toString(request.getInputStream(), "utf-8");
            String sign = URLDecoder.decode(new JSONObject(secretData).getString("sign"),"UTF-8");

            //解密获取报文
            AESOperator aes = new AESOperator(PayUtil.AES_SECRET);
            String text = aes.decrypt(sign);
            getCurrentLogger().info("充值结果回调：" + text);
            JSONObject reqJson = new JSONObject(text);

            if (200 == reqJson.getInt("resultCode")) {
                JSONObject data = reqJson.getJSONObject("data");

                //支付成功
                if ("SUCCESS".equalsIgnoreCase(data.getString("status"))) {
                    String platformOrderNo = data.getString("merchantOrderNo");
                    String payTime = data.getString("payTime");
                    String channelActuatAmount = data.getString("amount");

                    //相关业务处理：更新订单状态等
                    boolean flag = orderServicel.paySuccessBusinessHandle(platformOrderNo, null, payTime, channelActuatAmount);

                    //推送支付回调给商家
                    if (flag) {
                        merchantNotifyService.pushPaySuccessInfoByRetry(platformOrderNo);
                    }

                }

            }

            response.getWriter().write("ok");
            response.getWriter().flush();

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage(), e);
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
            String merchantOrderNo = reqJson.getString("merchantOrderNo");
            OrderModel orderModel = orderServicel.queryOrderByOrderNo(merchantOrderNo);
            if (orderModel == null) {
                json.put("status", "0");
                json.put("msg", "订单不存在");
                writeJson(response, json.toString());
                return;
            }

            //待支付状态: 则主动查询通道接口，获取最新状态
            if (PayStatusEnum.waitPay.getCode().equalsIgnoreCase(orderModel.getPayStatus())) {

                try {
                    String platformOrderNo = orderModel.getPlatformOrderNo();
                    String result = PayUtil.findOrder(platformOrderNo);
                    if (StringUtil.isNotEmpty(result)) {
                        JSONObject resultJson = new JSONObject(result);
                        if (resultJson.has("resultCode") && 200 == resultJson.getInt("resultCode")) {
                            JSONObject data = resultJson.getJSONObject("data");
                            //支付成功
                            if ("SUCCESS".equalsIgnoreCase(data.getString("status"))) {
                                String payTime = data.getString("finishedDate");
                                String channelActuatAmount = data.getString("actualPayment");

                                //相关业务处理：更新订单状态等
                                orderServicel.paySuccessBusinessHandle(platformOrderNo, null, payTime, channelActuatAmount);
                                orderModel = orderServicel.queryOrderByOrderNo(platformOrderNo);

                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            Map<String, Object> data = new HashMap();
            data.put("merchantOrderNo", orderModel.getMerchantOrderNo());
            data.put("platformOrderNo", orderModel.getPlatformOrderNo());
            data.put("payStatus", orderModel.getPayStatus());
            data.put("payWay", orderModel.getPayWay());
            data.put("payTime", orderModel.getPayTime());
            data.put("orderAmount", orderModel.getOrderAmount());
            data.put("actualAmount", orderModel.getActualAmount());
            data.put("handlingFee", orderModel.getHandlingFee());

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