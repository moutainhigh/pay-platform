package com.pay.platform.api.pay.lzyh.service;

import java.util.Map;

/**
 * User: zjt
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
    Map<String, Object> queryLooperTradeCodeByLzyh(String merchantId, String payChannelId, String orderAmount);

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
    String createOrderByLzyh(String merchantNo, String merchantOrderNo, String orderAmount, String payWay, String notifyUrl, String returnUrl, String tradeCodeId, String tradeCodeNum) throws Exception;

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

}