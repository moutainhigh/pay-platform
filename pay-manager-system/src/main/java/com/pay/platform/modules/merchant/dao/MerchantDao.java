package com.pay.platform.modules.merchant.dao;

import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.merchant.model.MerchantModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface MerchantDao {

    /**
     * 分页查询商家列表
     *
     * @param merchant
     * @return
     */
    List<MerchantModel> queryMerchantList(MerchantModel merchant);

    /**
     * 根据id查询商家信息
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