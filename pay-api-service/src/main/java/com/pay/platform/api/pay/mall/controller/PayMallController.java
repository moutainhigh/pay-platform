package com.pay.platform.api.pay.mall.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.mall.service.PayMallService;
import com.pay.platform.api.pay.mall.util.PayMallUtil;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.enums.PayStatusEnum;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.IpUtil;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.security.util.ApiSignUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 电商下单
 */
@Controller
public class PayMallController extends BaseController {

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayMallService payMallService;

    @Autowired
    private UnifiedPayService unifiedPayService;

    /**
     * 下单接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/createOrderByMall", method = RequestMethod.POST)
    public void createOrderByMall(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String merchantNo = reqJson.has("merchantNo") ? reqJson.getString("merchantNo") : "";
            String merchantOrderNo = reqJson.has("merchantOrderNo") ? reqJson.getString("merchantOrderNo") : "";
            String orderAmount = reqJson.has("orderAmount") ? reqJson.getString("orderAmount") : "";
            String payWay = reqJson.has("payWay") ? reqJson.getString("payWay") : "";
            String notifyUrl = reqJson.has("notifyUrl") ? reqJson.getString("notifyUrl") : "";
            String returnUrl = reqJson.has("returnUrl") ? reqJson.getString("returnUrl") : "";
            String clientIp = reqJson.has("clientIp") ? reqJson.getString("clientIp") : "";

            if (StringUtil.isEmpty(merchantNo) || StringUtil.isEmpty(merchantOrderNo) || StringUtil.isEmpty(orderAmount)
                    || StringUtil.isEmpty(payWay) || StringUtil.isEmpty(notifyUrl)) {
                json.put("status", "0");
                json.put("msg", "参数不完整！");
                writeJson(response, json.toString());
                return;
            }

            //判断订单金额是否为数字
            if (!DecimalCalculateUtil.isNumeric(orderAmount)) {
                json.put("status", "0");
                json.put("msg", "订单金额只能为数字！");
                writeJson(response, json.toString());
                return;
            }

            //校验通道是否开启
            Map<String, Object> merchantChannelInfo = unifiedPayService.queryChannelEnabledStatus(merchantNo, payWay);
            if (merchantChannelInfo == null || 0 == Integer.parseInt(merchantChannelInfo.get("enabled").toString())) {
                json.put("status", "0");
                json.put("msg", "通道已关闭!");
                writeJson(response, json.toString());
                return;
            }

            //创建订单
            String baseUrl = IpUtil.getBaseURL(request);
            String orderId = payMallService.createOrderByMall(merchantNo, merchantOrderNo, orderAmount, payWay, notifyUrl, clientIp, baseUrl, returnUrl);
            if (StringUtil.isEmpty(orderId)) {
                json.put("status", "0");
                json.put("msg", "下单失败");
                writeJson(response, json.toString());
                return;
            }

            json.put("status", "1");
            json.put("msg", "下单成功");
            json.put("data", orderId);
            writeJson(response, json.toString());

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
     * 电商回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/payNotifyOfMall", method = {RequestMethod.POST, RequestMethod.GET})
    public void payNotifyOfMall(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            if (StringUtil.isEmpty(text)) {
                getCurrentLogger().info("电商回调：报文为空");
                return;
            }
            getCurrentLogger().info("电商回调报文：" + text);
            JSONObject reqJson = new JSONObject(text);

            //1,判断是否包含timestamp,sign参数
            if (!reqJson.has("timestamp") || !reqJson.has("sign")) {
                getCurrentLogger().info("电商回调：参数错误");
                return;
            }

            //2,判断请求时间跟当前时间是否超过最大间隔,防止请求url盗用进行重放攻击 (当前限制为1分钟,即可6万毫秒)
            Long timestamp = reqJson.getLong("timestamp");
            Long currentMills = System.currentTimeMillis();
            if (Math.abs(currentMills - timestamp) > 60000) {
                getCurrentLogger().info("电商回调：请求已过期");
                return;
            }

            //3,对所有请求参数和时间戳进行排序  ->  并“参数=参数值”的模式用“&”字符拼接成字符串 + 加上商家密钥 -> MD5生成sign签名
            Map<String, String> params = new HashMap<String, String>();
            String[] names = JSONObject.getNames(reqJson);
            for (String key : names) {
                params.put(key, reqJson.getString(key));
            }
            String sign = reqJson.getString("sign").trim();
            String currentSign = ApiSignUtil.buildSignByMd5(params, PayMallUtil.MALL_SYSTEM_API_SECRET).trim();
            if (!sign.equalsIgnoreCase(currentSign)) {
                getCurrentLogger().info("电商回调：签名错误");
                return;
            }

            String hlxPlatformOrderNo = reqJson.getString("hlxPlatformOrderNo");
            String mallOrderNo = reqJson.getString("mallOrderNo");
            String payState = reqJson.getString("payState");
            String payTime = reqJson.getString("payTime");

            //支付成功
            if (PayStatusEnum.payed.getCode().equals(payState)) {

                //相关业务处理：更新订单状态等
                boolean flag = orderService.paySuccessBusinessHandle(hlxPlatformOrderNo, mallOrderNo, payTime);

                //推送支付回调给商家
                if (flag) {
                    merchantNotifyService.pushPaySuccessInfoByRetry(hlxPlatformOrderNo);
                }

            }

            response.getWriter().write("SUCCESS");
            response.getWriter().flush();

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage(), e);
        }

    }

}