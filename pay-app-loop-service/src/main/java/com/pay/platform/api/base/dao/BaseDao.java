package com.pay.platform.api.base.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/7/20 16:47
 */
@MapperScan
public interface BaseDao {

    /**
     * 根据编号查询交易码
     *
     * @param codeNum
     * @return
     */
    Map<String, Object> queryTradeCodeByCudeNum(@Param("codeNum") String codeNum);

}