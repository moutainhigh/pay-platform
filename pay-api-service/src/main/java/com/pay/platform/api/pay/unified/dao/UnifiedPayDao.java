package com.pay.platform.api.pay.unified.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 * <p>
 * 支付接口的一些通用操作
 */
@MapperScan
public interface UnifiedPayDao {

    Map<String, Object> queryChannelEnabledStatus(@Param("merchantNo") String merchantNo, @Param("payWay") String payWay);

    /**
     * 查询5分钟内,未被使用过的码;
     *
     * @param merchantId
     * @param payChannelId
     * @return
     */
    List<Map<String, Object>> queryTradeCodeBy5MinuteNotUse(@Param("merchantId") String merchantId, @Param("payChannelId") String payChannelId);

    /**
     * 查询列表中,可用的一个码
     * <p>
     * 1、去除已达到单日收款限制的号；
     * 2、并根据重复金额笔数、单日收款笔数、单日收款金额进行排序；返回最少使用的一个
     *
     * @param tradeCodeIds
     * @param orderAmount
     * @return
     */
    Map<String, Object> queryAvaiabledTradeCodeByIds(@Param("tradeCodeIds") String[] tradeCodeIds, @Param("orderAmount") String orderAmount);

    /**
     * 查询交易码5分钟内是否被使用过
     *
     * @param tradeCodeId
     * @return
     */
    Map<String, Object> queryTradeCode5MinuteNotUseById(@Param("tradeCodeId") String tradeCodeId);

    /**
     * 查询码 - 最后一笔的浮动金额
     *
     * @param tradeCodeId
     * @param orderAmount
     * @return
     */
    double queryTradeCodeLastPayFloatAmount(@Param("tradeCodeId") String tradeCodeId, @Param("orderAmount") String orderAmount);

    /**
     * 查询支付页面所需数据
     * @param tradeId
     * @return
     */
    Map<String,Object> queryPayPageData(@Param("tradeId") String tradeId);

}