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
     * 查询每日流水
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> queryEveryDayBill(@Param("merchantId") String merchantId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

}