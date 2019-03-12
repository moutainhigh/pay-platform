package com.pay.platform.modules.finance.payFor.service;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;

/**
 * User: zjt
 * DateTime: 2019/3/12 20:39
 */
public interface PayForService {

    /**
     * 分页查询提现申请
     *
     * @param withdraw
     * @return
     */
    PageInfo<WithdrawModel> queryPayForList(WithdrawModel withdraw , String agentId , String merchantId , String beginTime, String endTime);

}