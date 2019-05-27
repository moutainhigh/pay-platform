package com.pay.platform.modules.loopMgr.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.loopMgr.model.TradeCodeModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface LoopMgrDao {

    /**
     * 分页查询交易码列表
     *
     * @param tradeCode
     * @return
     */
    List<TradeCodeModel> queryTradeCodeList(TradeCodeModel tradeCode);

    /**
     * 根据id查询交易码信息
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

    Integer batchAddTradeCode(@Param("tradeCodeList") List<TradeCodeModel> tradeCodeList);

    List<Map<String, Object>> queryTradeCodeSuccessRateList(@Param("tradeCode") TradeCodeModel tradeCode, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Integer updateTradeCodeEnabled(@Param("ids") String[] ids, @Param("enabled") String enabled);

    Map<String, Object> queryTradeSuccessRate(@Param("merchantId") String merchantId, @Param("channelId") String channelId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String,Object> queryMerchantInfoByTradeCode(@Param("codeNum") String codeNum);
}