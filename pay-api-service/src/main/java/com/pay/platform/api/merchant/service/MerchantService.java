package com.pay.platform.api.merchant.service;

import com.pay.platform.api.merchant.model.MerchantModel;

import java.util.List;

/**
 * User:
 * DateTime: 2019/1/13 20:53
 */
public interface MerchantService {

    /**
     * 根据商家编号查询商家信息
     *
     * @param merchantNo
     * @return
     */
    MerchantModel queryMerchantByIMerchantNo(String merchantNo);

    /**
     * 查询商家编号
     * @return
     */
    List<MerchantModel> queryMerchantList();

}