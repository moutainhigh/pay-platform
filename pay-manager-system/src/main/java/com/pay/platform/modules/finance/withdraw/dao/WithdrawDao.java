package com.pay.platform.modules.finance.withdraw.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface WithdrawDao {

    /**
     * 分页查询提现申请列表
     *
     * @param withdraw
     * @param beginTime
     * @param endTime
     * @return
     */
    List<WithdrawModel> queryWithdrawList(@Param("withdraw") WithdrawModel withdraw, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 根据id查询提现申请信息
     *
     * @param id
     * @return
     */
    WithdrawModel queryWithdrawById(String id);

    /**
     * 新增提现申请
     *
     * @param withdraw
     * @return
     */
    Integer addWithdraw(WithdrawModel withdraw);

    /**
     * 删除提现申请
     *
     * @param ids
     * @return
     */
    Integer deleteWithdraw(String[] ids);

    /**
     * 逻辑删除提现申请
     *
     * @param ids
     * @return
     */
    Integer deleteWithdrawByLogic(String[] ids);

    /**
     * 修改提现申请
     *
     * @param withdraw
     * @return
     */
    Integer updateWithdraw(WithdrawModel withdraw);

    /**
     * 初始化提现密码
     *
     * @param userId
     * @param withdrawPassword
     * @return
     */
    Integer initWithdrawPassword(@Param("userId") String userId, @Param("withdrawPassword") String withdrawPassword);

}