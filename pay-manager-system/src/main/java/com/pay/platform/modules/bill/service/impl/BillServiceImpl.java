package com.pay.platform.modules.bill.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.bill.dao.BillDao;
import com.pay.platform.modules.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/18 21:01
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Override
    public PageInfo<Map<String, Object>> queryAgentEveryDayBill(String agentId, String beginTime, String endTime) {
        return new PageInfo(billDao.queryAgentEveryDayBill(agentId, beginTime, endTime));
    }

    @Override
    public PageInfo<Map<String, Object>> queryMerchantEveryDayBill(String merchantId, String beginTime, String endTime, String agentId) {
        return new PageInfo(billDao.queryMerchantEveryDayBill(merchantId, beginTime, endTime, agentId));
    }

    @Override
    public PageInfo<Map<String, Object>> queryBillByDateTime(String agentId, String merchantId, String beginTime, String endTime) {
        return new PageInfo(billDao.queryBillByDateTime(agentId, merchantId, beginTime, endTime));
    }

    @Override
    public PageInfo<Map<String, Object>> queryAgentProfit(String agentId, String beginTime, String endTime, String merchantName, String platformOrderNo, String merchantOrderNo) {
        return new PageInfo(billDao.queryAgentProfit(agentId, beginTime, endTime , merchantName , platformOrderNo , merchantOrderNo));
    }

    @Override
    public Map<String, Object> queryTotalAgentProfit(String agentId, String beginTime, String endTime, String merchantName, String platformOrderNo, String merchantOrderNo) {

        Map<String,Object> result = new HashMap<>();

        Map<String,Object> totalProfitAmountMap = billDao.queryTotalProfitAmount(agentId, beginTime, endTime, merchantName , platformOrderNo , merchantOrderNo);
        Map<String,Object> totalOrderAmountMap = billDao.queryAgentTotalOrderAmount(agentId, beginTime, endTime, merchantName , platformOrderNo , merchantOrderNo);

        if(totalProfitAmountMap != null && totalProfitAmountMap.get("totalProfitAmount") != null){
            result.put("totalProfitAmount" , totalProfitAmountMap.get("totalProfitAmount").toString());
        }

        if(totalOrderAmountMap != null && totalOrderAmountMap.get("totalOrderAmount") != null){
            result.put("totalOrderAmount" , totalOrderAmountMap.get("totalOrderAmount").toString());
        }

        return result;
    }

}