package com.pay.platform.api.pay.unified.service.impl;

import com.pay.platform.api.pay.unified.dao.UnifiedPayDao;
import com.pay.platform.api.pay.unified.service.UnifiedPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:20
 * <p>
 * 支付接口的一些通用操作
 */
@Service
public class UnifiedPayServiceImpl implements UnifiedPayService {

    @Autowired
    private UnifiedPayDao unifiedPayDao;

    @Override
    public Map<String, Object> queryChannelEnabledStatus(String merchantNo, String payWay) {
        return unifiedPayDao.queryChannelEnabledStatus(merchantNo, payWay);
    }

    /**
     * 查询可用的码
     *
     * @param merchantId
     * @param payChannelId
     * @param orderAmount
     * @return
     */
    @Override
    public Map<String, Object> queryAvaiabledTradeCode(String merchantId, String payChannelId, String orderAmount) {

        //1、查询5分钟内,未被使用过的码;
        List<Map<String, Object>> tradeCodeList = unifiedPayDao.queryTradeCodeBy5MinuteNotUse(merchantId, payChannelId);
        if(tradeCodeList == null || tradeCodeList.size() == 0){
            return null;
        }

        //2、去除已达到单日收款限制的号；
        //3、并根据单日重复金额笔数、单日收款笔数、单日收款金额进行排序；返回最少使用的那个
        String[] tradeCodeIds = new String[tradeCodeList.size()];
        for (int i = 0; i < tradeCodeList.size(); i++) {
            tradeCodeIds[i] = tradeCodeList.get(i).get("id").toString();
        }

        return unifiedPayDao.queryAvaiabledTradeCodeByIds(tradeCodeIds , orderAmount);
    }

    @Override
    public Map<String, Object> queryPayPageData(String tradeId) {
        return unifiedPayDao.queryPayPageData(tradeId);
    }

}