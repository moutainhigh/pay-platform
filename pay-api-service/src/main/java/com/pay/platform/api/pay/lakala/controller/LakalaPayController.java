package com.pay.platform.api.pay.lakala.controller;

import com.pay.platform.api.base.controller.BaseController;
import com.pay.platform.api.merchant.model.MerchantModel;
import com.pay.platform.api.merchant.service.MerchantNotifyService;
import com.pay.platform.api.merchant.service.MerchantService;
import com.pay.platform.api.order.service.OrderService;
import com.pay.platform.api.pay.lakala.service.LakalaPayService;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
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
 *
 * 拉卡拉支付接口
 */
@Controller
public class LakalaPayController extends BaseController {

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LakalaPayService lakalaPayService;

    @Autowired
    private UnifiedPayService unifiedPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 拉卡拉固码（支付宝、微信）
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/api/createOrderByLklFixed", method = RequestMethod.POST)
    public void createOrderByLklFixed(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
            Map<String,Object> merchantChannelInfo = unifiedPayService.queryChannelEnabledStatus(merchantNo , payWay);
            if(merchantChannelInfo == null || 0 == Integer.parseInt(merchantChannelInfo.get("enabled").toString())){
                json.put("status", "0");
                json.put("msg", "通道已关闭!");
                writeJson(response, json.toString());
                return;
            }

            //查询可用的交易码
            MerchantModel merchantModel = merchantService.queryMerchantByIMerchantNo(merchantNo);
            Map<String, Object> payChannelInfo = orderService.queryPayChannelByCode(payWay);
            String merchantId = merchantModel.getId();
            String payChannelId = payChannelInfo.get("id").toString();
            Map<String,Object> tradeCode = unifiedPayService.queryAvaiabledTradeCode(merchantId , payChannelId , orderAmount);
            if(tradeCode == null){
                json.put("status", "0");
                json.put("msg", "暂无可用收款通道,请联系客服！");
                writeJson(response, json.toString());
                return;
            }

            //校验最小金额、最大金额
            double minAmount = Double.parseDouble(tradeCode.get("min_amount").toString());
            double maxAmount = Double.parseDouble(tradeCode.get("max_amount").toString());
            if(Double.parseDouble(orderAmount) < minAmount || Double.parseDouble(orderAmount) > maxAmount){
                json.put("status", "0");
                json.put("msg", "无效的订单金额,请联系客服！");
                writeJson(response, json.toString());
                return;
            }

            //完成创建订单
            String tradeCodeId = tradeCode.get("id").toString();
            String platformOrderNo = lakalaPayService.createOrderByLklFixed(merchantNo, merchantOrderNo, orderAmount, payWay, notifyUrl, returnUrl , tradeCodeId);
            if (StringUtil.isEmpty(platformOrderNo)) {
                json.put("status", "0");
                json.put("msg", "下单失败,当前支付人数过多,请稍后再试！");
                writeJson(response, json.toString());
                return;
            }

            json.put("status", "1");
            json.put("msg", "下单成功");
//                json.put("data", resultJson.getString("data"));       //此处返回支付链接; 后台接口转发到H5页面
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