package com.pay.platform.modules.bill.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/18 21:00
 */
public interface BillService {

    /**
     * 查看代理每日流水
     * @param agentId
     * @param beginTime
     * @param endTime
     * @return
     */
    PageInfo<Map<String,Object>> queryAgentEveryDayBill(String agentId, String beginTime, String endTime);

    /**
     * 查询商家每日流水
     *
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    PageInfo<Map<String, Object>> queryMerchantEveryDayBill(String merchantId, String beginTime, String endTime ,  String agentId);

    /**
     * 根据时间段查询流水
     * @param agentId
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    PageInfo<Map<String,Object>> queryBillByDateTime(String agentId, String merchantId, String beginTime, String endTime);

    /**
     * 查询代理分润
     * @param agentId
     * @param beginTime
     * @param endTime
     * @param merchantName
     * @param platformOrderNo
     * @param merchantOrderNo
     * @return
     */
    PageInfo<Map<String,Object>> queryAgentProfit(String agentId, String beginTime, String endTime, String merchantName, String platformOrderNo, String merchantOrderNo);

    /**
     * 查询代理分润流水（统计总数）
     * @param agentId
     * @param beginTime
     * @param endTime
     * @param merchantName
     * @return
     */
    Map<String,Object> queryTotalAgentProfit(String agentId, String beginTime, String endTime, String merchantName, String platformOrderNo, String merchantOrderNo);

}