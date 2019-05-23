package com.pay.platform.api.pay.unified.service;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:20
 */
public interface UnifiedPayService {

    Map<String,Object> queryChannelEnabledStatus(String merchantNo, String payWay);

    /**
     * 查询可用的交易码
     * @param merchantId
     * @param payChannelId
     * @return
     */
    Map<String,Object> queryAvaiabledTradeCode(String merchantId, String payChannelId , String orderAmount);

    /**
     * 查询支付页面所需数据
     * @param tradeId
     * @return
     */
    Map<String,Object> queryPayPageData(String tradeId);

}