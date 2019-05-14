package com.pay.platform.modules.merchant.dao;

import com.pay.platform.modules.merchant.model.MerchantRateListModel;
import com.pay.platform.modules.merchant.model.MerchantRateModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;


@MapperScan
public interface MerchantRateDao {

    /**
     * 添加
     * @param merchantRateModel
     * @return
     */
    Integer addMerchantRate(MerchantRateModel merchantRateModel);


    /**
     * 查询商家的费率列表
     * @param merchantId
     * @return
     */
    List<MerchantRateListModel> queryMerchantRateList(@Param("merchantId") String merchantId);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteMerchantRate(@Param("id") String id);

    Integer updateMerchantChannelEnabledStatus(@Param("id") String id , @Param("enabled")  String enabled);

}
