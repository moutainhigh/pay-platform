package com.pay.platform.modules.bill.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.bill.dao.BillDao;
import com.pay.platform.modules.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/18 21:01
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Override
    public PageInfo<Map<String, Object>> queryEveryDayBill(String merchantId, String beginTime, String endTime) {
        return new PageInfo(billDao.queryEveryDayBill(merchantId, beginTime, endTime));
    }

}