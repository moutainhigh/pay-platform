package com.pay.platform.modules.merchant.service;

import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    Integer addMerchant(MerchantModel merchant) throws Exception;

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

    /**
     * 根据商家编号查询商家信息
     *
     * @param merchantNo
     * @return
     */
    MerchantModel queryMerchantByIMerchantNo(String merchantNo);

    /**
     * 查询商家id和名称
     * @param merchantId
     * @param agentId
     * @return
     */
    List<Map<String,Object>> queryMerchantIdAndNameList(String merchantId , String agentId);


    /**
     * 审核
     * @param merchant
     * @return
     */
    Integer review(MerchantModel merchant);

}