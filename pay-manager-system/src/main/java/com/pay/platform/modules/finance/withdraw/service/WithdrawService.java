package com.pay.platform.modules.finance.withdraw.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
public interface WithdrawService {

    /**
     * 分页查询提现申请
     *
     * @param withdraw
     * @return
     */
    PageInfo<WithdrawModel> queryWithdrawList(WithdrawModel withdraw , String merchantId , String beginTime, String endTime);

    /**
     * 根据id查询提现申请
     *
     * @param id
     * @return
     */
    WithdrawModel queryWithdrawById(String id);

    /**
     * 新增提现申请
     *
     * @param userId ,
     * @param withdraw
     * @return
     */
    Integer addWithdraw(String userId , WithdrawModel withdraw) throws Exception ;

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
     * @param userId
     * @param withdrawPassword
     * @return
     */
    Integer initWithdrawPassword(String userId, String withdrawPassword);

    /**
     * 查询账户资金情况
     * @param userId
     * @return
     */
    Map<String,Object> queryAccountAmountInfo(String userId);
}