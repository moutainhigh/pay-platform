package com.pay.platform.modules.bill.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/18 21:00
 */
public interface BillService {

    /**
     * 查询每日流水
     *
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    PageInfo<Map<String, Object>> queryEveryDayBill(String merchantId, String beginTime, String endTime);

}