package com.pay.platform.modules.loopMgr.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.loopMgr.service.LoopMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.loopMgr.model.TradeCodeModel;
import com.pay.platform.modules.loopMgr.dao.LoopMgrDao;

import java.util.List;
import java.util.Map;


/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
@Service
public class LoopMgrServiceImpl implements LoopMgrService {

    @Autowired
    private LoopMgrDao tradeCodeDao;

    @Override
    public PageInfo<TradeCodeModel> queryTradeCodeList(TradeCodeModel tradeCode) {
        return new PageInfo(tradeCodeDao.queryTradeCodeList(tradeCode));
    }

    @Override
    public TradeCodeModel queryTradeCodeById(String id) {
        return tradeCodeDao.queryTradeCodeById(id);
    }

    @Override
    public Integer addTradeCode(TradeCodeModel tradeCode) {
        return tradeCodeDao.addTradeCode(tradeCode);
    }

    @Override
    public Integer deleteTradeCode(String[] ids) {
        return tradeCodeDao.deleteTradeCode(ids);
    }

    @Override
    public Integer deleteTradeCodeByLogic(String[] ids) {
        return tradeCodeDao.deleteTradeCodeByLogic(ids);
    }

    @Override
    public Integer updateTradeCode(TradeCodeModel tradeCode) {
        return tradeCodeDao.updateTradeCode(tradeCode);
    }

    @Override
    public TradeCodeModel queryExistsByCode(TradeCodeModel tradeCode) {
        return tradeCodeDao.queryExistsByCode(tradeCode);
    }

    @Override
    public Integer batchAddTradeCode(List<TradeCodeModel> tradeCodeList) {
        return tradeCodeDao.batchAddTradeCode(tradeCodeList);
    }

    @Override
    public PageInfo<Map<String,Object>> queryTradeCodeSuccessRateList(TradeCodeModel tradeCode, String beginTime, String endTime) {
        return new PageInfo(tradeCodeDao.queryTradeCodeSuccessRateList(tradeCode , beginTime , endTime));
    }

    @Override
    public Integer updateTradeCodeEnabled(String[] ids, String enabled) {
        return tradeCodeDao.updateTradeCodeEnabled(ids , enabled);
    }

    @Override
    public Map<String, Object> queryTradeSuccessRate(String merchantId, String channelId, String beginTime, String endTime) {
        return tradeCodeDao.queryTradeSuccessRate(merchantId , channelId , beginTime , endTime);
    }

    @Override
    public Map<String, Object> queryMerchantInfoByTradeCode(String codeNum) {
        return tradeCodeDao.queryMerchantInfoByTradeCode(codeNum);
    }

}