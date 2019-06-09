package com.pay.platform.modules.finance.payFor.dao;

import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
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
            , @Param("beginTime") String beginTime, @Param("endTime") String endTime , @Param("merchantIdList") String[]  merchantIdList);

    /**
     * 代付审核
     *
     * @param id
     * @param checkStatus
     * @param checkDesc
     * @return
     */
    Integer payForReview(@Param("id") String id, @Param("checkStatus") String checkStatus, @Param("checkDesc") String checkDesc);

    /**
     * 修改提现状态
     * @param id
     * @param withdrawStatus
     * @return
     */
    int updateWithdrawStatus(@Param("id") String id, @Param("withdrawStatus") String withdrawStatus);

}