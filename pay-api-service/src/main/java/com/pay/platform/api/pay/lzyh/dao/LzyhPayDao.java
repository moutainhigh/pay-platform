package com.pay.platform.api.pay.lzyh.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface LzyhPayDao {

    /**
     * 轮询查询可用的交易码
     *
     * @param merchantId
     * @param payChannelId
     * @param orderAmount
     * @return
     */
    List<Map<String, Object>> queryLooperTradeCodeByLzyh(@Param("merchantId") String merchantId, @Param("payChannelId") String payChannelId, @Param("orderAmount") String orderAmount);

    /**
     * 判断1个小时内是否有重复整数金额的订单：
     *
     * @param tradeCodeId
     * @param orderAmount
     * @return
     */
    int queryPayFloatAmountIsExists(@Param("tradeCodeId") String tradeCodeId, @Param("orderAmount") String orderAmount);

    /**
     * 查询指定整数金额，在1个小时内使用过的支付浮动金额
     *
     * @param tradeCodeId
     * @param orderAmount
     * @return
     */
    List<Double> queryTradeCodeUsedPayFloatAmount(@Param("tradeCodeId") String tradeCodeId, @Param("orderAmount") String orderAmount);

    /**
     * 根据回调金额查询匹配的订单
     *
     * @param codeNum
     * @param amount
     * @param payCode
     * @return
     */
    Map<String, Object> queryOrderInfoPyLzyhAppNotify(@Param("codeNum") String codeNum, @Param("amount") String amount, @Param("payCode") String payCode);

    /**
     * 查询支付单号是否存在
     *
     * @param payCode
     * @return
     */
    int queryPayCodeExists(@Param("payCode") String payCode);

    List<Map<String, Object>> getWaitQrCodeData(@Param("codeNum") String codeNum);

}