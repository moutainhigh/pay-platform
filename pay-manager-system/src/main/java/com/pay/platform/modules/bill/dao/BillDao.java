package com.pay.platform.modules.bill.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/18 21:01
 */
@MapperScan
public interface BillDao {

    /**
     * 查看代理每日流水
     *
     * @param agentId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> queryAgentEveryDayBill(@Param("agentId") String agentId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 查询商家每日流水
     *
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> queryMerchantEveryDayBill(@Param("merchantId") String merchantId, @Param("beginTime") String beginTime
            , @Param("endTime") String endTime, @Param("agentId") String agentId);

    /**
     * 根据时间段查询商家流水
     * @param agentId
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> queryMerchantBillByDateTime(@Param("agentId") String agentId, @Param("merchantId") String merchantId
            , @Param("beginTime") String beginTime, @Param("endTime") String endTime );

}