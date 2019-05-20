package com.pay.platform.modules.loopMgr.service;

import com.github.pagehelper.PageInfo;

import com.pay.platform.modules.loopMgr.model.TradeCodeModel;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface LoopMgrService {

    /**
     * 分页查询交易码
     *
     * @param tradeCode
     * @return
     */
    PageInfo<TradeCodeModel> queryTradeCodeList(TradeCodeModel tradeCode);

    /**
     * 根据id查询交易码
     *
     * @param id
     * @return
     */
    TradeCodeModel queryTradeCodeById(String id);

    /**
     * 新增交易码
     *
     * @param tradeCode
     * @return
     */
    Integer addTradeCode(TradeCodeModel tradeCode);

    /**
     * 删除交易码
     *
     * @param ids
     * @return
     */
    Integer deleteTradeCode(String[] ids);

    /**
     * 逻辑删除交易码
     *
     * @param ids
     * @return
     */
    Integer deleteTradeCodeByLogic(String[] ids);

    /**
     * 修改交易码
     *
     * @param tradeCode
     * @return
     */
    Integer updateTradeCode(TradeCodeModel tradeCode);

    TradeCodeModel queryExistsByCode(TradeCodeModel tradeCode);

    Integer batchAddTradeCode(List<TradeCodeModel> tradeCodeList );

    PageInfo<Map<String,Object>> queryTradeCodeSuccessRateList(TradeCodeModel tradeCode, String beginTime, String endTime);

    Integer updateTradeCodeEnabled(String[] ids, String enabled);
}