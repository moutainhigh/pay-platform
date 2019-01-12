package com.pay.platform.modules.merchant.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

import com.pay.platform.modules.merchant.model.MerchantModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface MerchantService {

    /**
     * 分页查询商家
     *
     * @param merchant
     * @return
     */
    PageInfo<MerchantModel> queryMerchantList(MerchantModel merchant);

    /**
     * 根据id查询商家
     *
     * @param id
     * @return
     */
    MerchantModel queryMerchantById(String id);

    /**
     * 新增商家
     *
     * @param merchant
     * @return
     */
    Integer addMerchant(MerchantModel merchant);

    /**
     * 删除商家
     *
     * @param ids
     * @return
     */
    Integer deleteMerchant(String[] ids);

    /**
     * 逻辑删除商家
     *
     * @param ids
     * @return
     */
    Integer deleteMerchantByLogic(String[] ids);

    /**
     * 修改商家
     *
     * @param merchant
     * @return
     */
    Integer updateMerchant(MerchantModel merchant);

}