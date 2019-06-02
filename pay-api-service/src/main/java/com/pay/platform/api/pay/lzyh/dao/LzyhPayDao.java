package com.pay.platform.api.pay.lzyh.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

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
    Map<String,Object> queryLooperTradeCodeByLzyh(@Param("merchantId") String merchantId, @Param("payChannelId") String payChannelId, @Param("orderAmount") String orderAmount);

    /**
     * 获取向下浮动金额,避免重复（同一个整数金额,最后一笔浮动金额,1小时重新开始浮动）;
     *
     * @param tradeCodeId
     * @param orderAmount
     * @return
     */
    double queryTradeCodeLastPayFloatAmount(@Param("tradeCodeId") String tradeCodeId, @Param("orderAmount") String orderAmount);

    /**
     * 根据回调金额查询匹配的订单
     *
     * @param codeNum
     * @param amount
     * @param payCode
     * @return
     */
    Map<String, Object> queryOrderInfoPyLzyhAppNotify(@Param("codeNum") String codeNum, @Param("amount") String amount, @Param("payCode") String payCode);

}