package com.pay.platform.api.pay.lzyh.service;

import com.pay.platform.api.order.model.OrderModel;

import java.util.List;
import java.util.Map;

/**
 *
 * DateTime: 2019/5/22 16:02
 */
public interface LzyhPayService {

    /**
     * 轮询查询可用的交易码
     *
     * @param merchantId
     * @param payChannelId
     * @param orderAmount
     * @return
     */
    List<Map<String, Object>> queryLooperTradeCodeByLzyh(String codeNum , String merchantId, String payChannelId, String orderAmount);

    /**
     * 柳行下单接口
     *
     * @param merchantNo
     * @param merchantOrderNo
     * @param orderAmount
     * @param payWay
     * @param notifyUrl
     * @param returnUrl
     * @param tradeCodeId
     * @return
     */
    OrderModel createOrderByLzyh(String merchantNo, String merchantOrderNo, String orderAmount, String payWay, String notifyUrl, String returnUrl, String tradeCodeId, String tradeCodeNum) throws Exception;

    /**
     * 根据柳行回调信息 - 查询匹配的订单
     * 条件：5分钟内 + 未支付 + 浮动金额
     *
     * @param codeNum
     * @param amount
     * @param payCode
     * @return
     */
    Map<String,Object> queryOrderInfoPyLzyhAppNotify(String codeNum, String amount, String payCode);

    /**
     * 查询支付单号是否存在
     * @param payCode
     * @return
     */
    int queryPayCodeExists(String payCode);

    /**
     * 发送获取收款码消息
     * @param tradeCodeNum
     * @param secret
     * @param payFloatAmount
     */
    void sendGetQrCodeMessage(String orderId , String tradeCodeNum, String secret, String payFloatAmount);

    List<Map<String,Object>> getWaitQrCodeData(String codeNum);

}