package com.pay.platform.modules.finance.payFor.service;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/3/12 20:39
 */
public interface PayForService {

    /**
     * 分页查询提现申请
     *
     * @param withdraw
     * @return
     */
    PageInfo<WithdrawModel> queryPayForList(WithdrawModel withdraw, String agentId, String merchantId, String beginTime, String endTime , String[] merchantIdList);

    /**
     * 代付审核
     *
     * @param id
     * @param checkStatus
     * @param checkDesc
     * @return
     */
    Integer payForReview(String id, String checkStatus, String checkDesc);

    /**
     * 设置为提现成功
     *
     * @param id
     * @param code
     * @return
     */
    boolean updateWithdrawStatusToSuccess(String id, String withdrawStatus) throws Exception;

    /**
     * 设置为提现失败
     *
     * @param id
     * @param code
     * @return
     */
    boolean updateWithdrawStatusToFail(String id, String code) throws Exception;

}