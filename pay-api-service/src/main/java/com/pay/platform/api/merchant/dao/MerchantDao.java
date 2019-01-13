package com.pay.platform.api.merchant.dao;

import com.pay.platform.api.merchant.model.MerchantModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/1/13 20:54
 */
@MapperScan
public interface MerchantDao {

    /**
     * 根据商家办好查询商家信息
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