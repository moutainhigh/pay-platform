package com.pay.platform.modules.merchantRate.dao;

import com.pay.platform.modules.merchantRate.model.MerchantRateListModel;
import com.pay.platform.modules.merchantRate.model.MerchantRateModel;
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
    Integer add(MerchantRateModel merchantRateModel);


    /**
     * 查询商家的费率列表
     * @param merchantId
     * @return
     */
    List<MerchantRateListModel> queryMerchantRateList(@Param("merchantId") String merchantId);

}
