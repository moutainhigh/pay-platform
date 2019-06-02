package com.pay.platform.api.pay.lzyh.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.merchant.model.MerchantModel;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.merchant.service.MerchantService;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.lzyh.service.LzyhPayService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.util.DateUtil;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.IpUtil;
import com.pay.platform.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:01
 * <p>
 * 柳行-支付接口
 */
@Controller
public class LzyhPayController extends BaseController {

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LzyhPayService lzyhPayService;

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/createOrderByLzyh", method = RequestMethod.POST)
    public void createOrderByLzyh(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
            String returnUrl = reqJson.has("returnUrl") ? reqJson.getString("returnUrl") : "";
            String clientIp = reqJson.has("clientIp") ? reqJson.getString("clientIp") : "";

            //校验通道是否开启
            Map<String, Object> merchantChannelInfo = unifiedPayService.queryChannelEnabledStatus(merchantNo, payWay);
            if (merchantChannelInfo == null || 0 == Integer.parseInt(merchantChannelInfo.get("enabled").toString())) {
                json.put("status", "0");
                json.put("msg", "通道已关闭!");
                writeJson(response, json.toString());
                return;
            }

            //判断订单金额是否为数字
            if (!DecimalCalculateUtil.isNumeric(orderAmount) || orderAmount.contains(".")) {
                json.put("status", "0");
                json.put("msg", "订单金额不可包含小数！");
                writeJson(response, json.toString());
                return;
            }

            //查询可用的交易码
            MerchantModel merchantModel = merchantService.queryMerchantByIMerchantNo(merchantNo);
            Map<String, Object> payChannelInfo = orderService.queryPayChannelByCode(payWay);
            String merchantId = merchantModel.getId();
            String payChannelId = payChannelInfo.get("id").toString();
            Map<String, Object> tradeCode = lzyhPayService.queryLooperTradeCodeByLzyh(merchantId, payChannelId, orderAmount);
            if (tradeCode == null) {
                json.put("status", "0");
                json.put("msg", "暂无可用收款通道,请联系客服！");
                writeJson(response, json.toString());
                return;
            }

            //校验最小金额、最大金额
            double minAmount = Double.parseDouble(tradeCode.get("min_amount").toString());
            double maxAmount = Double.parseDouble(tradeCode.get("max_amount").toString());
            if (Double.parseDouble(orderAmount) < minAmount || Double.parseDouble(orderAmount) > maxAmount) {
                json.put("status", "0");
                json.put("msg", "无效的订单金额,请联系客服！");
                writeJson(response, json.toString());
                return;
            }

            //完成创建订单
            String tradeCodeId = tradeCode.get("id").toString();
            String tradeCodeNum = tradeCode.get("code_num").toString();
            String id = lzyhPayService.createOrderByLzyh(merchantNo, merchantOrderNo, orderAmount, payWay, notifyUrl, returnUrl, tradeCodeId, tradeCodeNum);
            if (StringUtil.isEmpty(id)) {
                json.put("status", "0");
                json.put("msg", "下单失败,当前支付人数过多,请稍后再试！");
                writeJson(response, json.toString());
                return;
            }

            json.put("status", "1");
            json.put("msg", "下单成功");
            json.put("data", id);
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

    /**
     * 获取支付链接
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/getPayLinkByLzyh", method = RequestMethod.POST)
    public void getPayLinkByLzyh(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String tradeId = reqJson.getString("tradeId");

            Map<String, Object> orderInfo = orderService.queryOrderById(tradeId);
            if (orderInfo == null) {
                json.put("status", "0");
                json.put("msg", "订单不存在！");
                writeJson(response, json.toString());
                return;
            }

            String payQrCodeLink = null;

            if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
                payQrCodeLink = IpUtil.getBaseURL(request) + "/openApi/toH5PayPage?tradeId=" + tradeId;
            } else {

                //与app进行socket通信,生成二维码可能需要等待时间;此处休眠一会再进行查询;
                //每隔2秒查询一次,最多3次
                for (int i = 0; i < 3; i++) {

                    try {
                        Thread.sleep(2000);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    //再次查询
                    orderInfo = orderService.queryOrderById(tradeId);
                    if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
                        payQrCodeLink = IpUtil.getBaseURL(request) + "/openApi/toH5PayPage?tradeId=" + tradeId;
                        break;
                    }

                }

            }

            if (StringUtil.isNotEmpty(payQrCodeLink)) {
                json.put("status", "1");
                json.put("msg", "获取支付链接成功");
                json.put("data", payQrCodeLink);
                writeJson(response, json.toString());
            } else {
                json.put("status", "0");
                json.put("msg", "获取支付链接失败");
                writeJson(response, json.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "0");
            json.put("msg", "服务器内部错误：" + e.getMessage());
            writeJson(response, json.toString());
        } finally {
            getCurrentLogger().info("响应报文：{}", json.toString());
        }

    }

    /**
     * 拉卡拉固码 - 支付回调
     * <p>
     * app回调接口,需经过AppSecurityFilter; 进行签名认证,防止恶意回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/app/payNotifyByLklFixed", method = RequestMethod.POST)
    public void payNotifyByLklFixed(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String orderNo = null;      //reqJson.getString("orderNo");     //暂时无法传递单号,只能根据设备、金额、时间匹配订单
            String codeNum = reqJson.getString("codeNum");
            String amount = reqJson.getString("amount");

            //查询数据库5分钟内的一笔订单,是否与app回调匹配
            Map<String, Object> orderInfo = orderService.queryOrderInfoPyAppNotifyAmount(codeNum, amount, orderNo);
            if (orderInfo != null) {

                String platformOrderNo = orderInfo.get("platform_order_no").toString();
                String payTime = DateUtil.getCurrentDateTime();

                //支付成功业务处理：更新订单状态，增加代理、商家账户余额等.
                boolean flag = orderService.paySuccessBusinessHandle(platformOrderNo, null, payTime, null);

                //推送支付回调给商家
                if (flag) {
                    merchantNotifyService.pushPaySuccessInfoByRetry(platformOrderNo);
                }

            }

            json.put("status", "1");
            json.put("msg", "回调成功！");
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