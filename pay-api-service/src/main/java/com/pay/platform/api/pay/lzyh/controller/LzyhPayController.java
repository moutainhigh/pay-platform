package com.pay.platform.api.pay.lzyh.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.merchant.model.MerchantModel;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.merchant.service.MerchantService;
import com.pay.platform.api.order.model.OrderModel;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.lzyh.service.LzyhPayService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.websocket.config.SocketMessageType;
import com.pay.platform.common.websocket.service.AppWebSocketService;
import com.pay.platform.common.util.*;
import com.pay.platform.security.util.AppSignUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private AppWebSocketService appWebSocketService;

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
            String codeNum = reqJson.has("codeNum") ? reqJson.getString("codeNum") : "";

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
            Map<String, Object> tradeCode = lzyhPayService.queryLooperTradeCodeByLzyh(codeNum, merchantId, payChannelId, orderAmount);
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
            OrderModel orderModel = lzyhPayService.createOrderByLzyh(merchantNo, merchantOrderNo, orderAmount, payWay, notifyUrl, returnUrl, tradeCodeId, tradeCodeNum);
            if (orderModel == null) {
                json.put("status", "0");
                json.put("msg", "下单失败,当前支付人数过多,请稍后再试！");
                writeJson(response, json.toString());
                return;
            }

            //发送获取收款码消息
            lzyhPayService.sendGetQrCodeMessage(orderModel.getId(), tradeCodeNum, tradeCode.get("secret").toString(), orderModel.getPayFloatAmount());

            json.put("status", "1");
            json.put("msg", "下单成功");
            json.put("data", orderModel.getId());
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
            long beginTime = System.currentTimeMillis();
            JSONObject reqJson = new JSONObject(text);

            String tradeId = reqJson.getString("tradeId");

            Map<String, Object> orderInfo = orderService.queryOrderById(tradeId);
            if (orderInfo == null) {
                json.put("status", "0");
                json.put("msg", "订单不存在！");
                writeJson(response, json.toString());
                return;
            }

            //首次获取收款码
            String payQrCodeLink = null;
            if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
                payQrCodeLink = IpUtil.getBaseURL(request) + "/openApi/toH5PayPage?tradeId=" + tradeId;
                getCurrentLogger().info("首次获取收款码" + orderInfo.get("platform_order_no").toString());
            }

            //还没有生成完二维码,则每隔500毫秒查询一次; 等待生成完
            if (StringUtil.isEmpty(payQrCodeLink)) {
                for (int i = 0; i < 5; i++) {

                    try {
                        Thread.sleep(500);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    orderInfo = orderService.queryOrderById(tradeId);
                    if (orderInfo.get("pay_qr_code_link") != null && StringUtil.isNotEmpty(orderInfo.get("pay_qr_code_link").toString())) {
                        payQrCodeLink = IpUtil.getBaseURL(request) + "/openApi/toH5PayPage?tradeId=" + tradeId;
                        break;
                    }

                }

            }

            long endTime = System.currentTimeMillis();
            getCurrentLogger().info(orderInfo.get("platform_order_no").toString() + "收款码获取结果:" + payQrCodeLink + " 耗时:" + (endTime - beginTime) + "毫秒");

            //无论如何都返回h5页面; 在页面进行二次等待； 例如3秒、5秒的倒计时
            if (StringUtil.isEmpty(payQrCodeLink)) {
                payQrCodeLink = IpUtil.getBaseURL(request) + "/openApi/toH5PayPage?tradeId=" + tradeId;
            }

            json.put("status", "1");
            json.put("msg", "获取支付链接成功");
            json.put("data", payQrCodeLink);
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
     * 柳州银行 - 支付回调
     * <p>
     * app回调接口,需经过AppSecurityFilter; 进行签名认证,防止恶意回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/app/payNotifyByLzyh", method = RequestMethod.POST)
    public void payNotifyByLzyh(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String codeNum = reqJson.getString("codeNum");          //设备编号
            String amount = reqJson.getString("amount");            //支付金额
            String payTime = reqJson.getString("payTime");          //支付时间
            String payCode = reqJson.getString("payCode");          //三方单号

            long payTimeStamp = DateUtil.getTimeStamp(payTime);
            long nowTimeStamp = DateUtil.getTimeStamp(new Date());
            if (payTimeStamp > nowTimeStamp) {
                json.put("status", "0");
                json.put("msg", "参数错误,支付时间不可能大于当前时间！");
                writeJson(response, json.toString());
                return;
            }

            //支付时间与当前时间间隔超过5分钟; 不进行处理; 按人工掉单流程处理
            if (nowTimeStamp - payTimeStamp > (5 * 60)) {
                json.put("status", "0");
                json.put("msg", "支付时间超过5分钟,请联系客服进行掉单处理！");
                writeJson(response, json.toString());
                return;
            }

            int existsPayCode = lzyhPayService.queryPayCodeExists(payCode);
            if (existsPayCode > 0) {
                json.put("status", "2");
                json.put("msg", "该订单已成功回调过,请勿重复发起回调！");
                writeJson(response, json.toString());
                return;
            }

            //查询数据库相匹配的订单（5分钟内 + 未支付 + 浮动金额）;
            //因为订单金额向下浮动，短期内不会出现重复金额的订单;
            Map<String, Object> orderInfo = lzyhPayService.queryOrderInfoPyLzyhAppNotify(codeNum, amount, payCode);
            if (orderInfo != null) {

                String platformOrderNo = orderInfo.get("platform_order_no").toString();

                //支付成功业务处理：更新订单状态，增加代理、商家账户余额等.
                boolean flag = orderService.paySuccessBusinessHandle(platformOrderNo, payCode, payTime);

                //推送支付回调给商家
                if (flag) {
                    //开启异步线程回调商家;避s免响应给app的请求阻塞;
                    AppContext.getExecutorService().submit(new Runnable() {
                        @Override
                        public void run() {
                            merchantNotifyService.pushPaySuccessInfoByRetry(platformOrderNo);
                        }
                    });
                }

                getCurrentLogger().info("收到柳行支付回调请求:" + platformOrderNo + " -> 设备编号:" + codeNum + " -> 支付金额:" + amount + " -> 支付时间:" + payTime + " -> 支付流水号:" + payCode);

                json.put("status", "1");
                json.put("msg", "回调后台成功！");
                writeJson(response, json.toString());

            } else {
                json.put("status", "0");
                json.put("msg", "回调失败,无匹配的订单,可能是已超时！");
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
     * 上传收款码
     * app回调接口,需经过AppSecurityFilter; 进行签名认证,防止恶意回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/app/uploadQrCode", method = RequestMethod.POST)
    public void uploadQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        try {

            //获取请求提交数据
            String text = IOUtils.toString(request.getInputStream(), "utf-8");
            JSONObject reqJson = new JSONObject(text);
            String codeNum = reqJson.getString("codeNum");
            String amount = reqJson.getString("amount");
            String remarks = reqJson.getString("remarks");
            String codeUrl = reqJson.getString("codeUrl");

            //查询匹配的订单,并更新收款链接
            Map<String, Object> orderInfo = unifiedPayService.queryOrderByQrCodeInfo(codeNum, amount, codeUrl);
            if (orderInfo != null) {
                String id = orderInfo.get("id").toString();
                orderService.updateOrderPayQrCodeLink(id, codeUrl);

                json.put("status", "1");
                json.put("msg", "上传成功");
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