package com.pay.platform.modules.finance.payFor.dao;

import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/3/12 20:39
 */
@MapperScan
public interface PayForDao {

    /**
     * 分页查询提现申请列表
     *
     * @param withdraw
     * @param beginTime
     * @param endTime
     * @return
     */
    List<WithdrawModel> queryPayForList(@Param("withdraw") WithdrawModel withdraw, @Param("agentId") String agentId, @Param("merchantId") String merchantId
            , @Param("beginTime") String beginTime, @Param("endTime") String endTime);

}