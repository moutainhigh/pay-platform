package com.pay.platform.api.merchant.dao;

import com.pay.platform.api.merchant.model.MerchantModel;
import org.apache.ibatis.annotations.Param;
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
     *
     * @return
     */
    List<MerchantModel> queryMerchantList();

    /**
     * 增加商家账户余额
     *
     * @param merchantId
     * @param amount
     * @return
     */
    int addAccountAmount(@Param("merchantId") String merchantId, @Param("amount") Double amount);

    /**
     * 增加商家账户余额-流水记录
     *
     * @param merchantId
     * @param sourceOrderNo
     * @param type
     * @param amount
     * @return
     */
    int addAccountAmountBillLog(@Param("merchantId") String merchantId, @Param("sourceOrderNo") String sourceOrderNo,
                                @Param("type") String type, @Param("amount") Double amount);

}