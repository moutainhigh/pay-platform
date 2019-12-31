package com.pay.platform.modules.merchant.dao;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.merchant.model.MerchantModel;

/**
 * User:
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
    List<MerchantModel> queryMerchantList(@Param("merchant") MerchantModel merchant , @Param("merchantIdList") String[] merchantIdList);

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

    /**
     * 根据商户编号查询商户信息
     *
     * @param merchantNo
     * @return
     */
    MerchantModel queryMerchantByIMerchantNo(String merchantNo);

    /**
     * 根据商户编号查询商户密钥信息
     *
     * @param merchantNo
     * @return
     */
    MerchantModel queryMerchantSecretByIMerchantNo(String merchantNo);

    /**
     * 查询商家id和名称
     *
     * @param merchantId
     * @param agentId
     * @return
     */
    List<Map<String, Object>> queryMerchantIdAndNameList(@Param("merchantId") String merchantId, @Param("agentId") String agentId, @Param("merchantIds") String[] merchantIds);


    /**
     * 审核
     *
     * @param merchant
     * @return
     */
    Integer review(MerchantModel merchant);

    Map<String, Object> queryMerchantAmountInfo(@Param("merchantId") String merchantId);

    int saveMerchantNotifyWithdrawAmount(@Param("merchantId") String merchantId, @Param("totalAmount") double totalAmount);

    List<String> queryMerchantIdByAgentId(@Param("agentId") String agentId, @Param("parentId") String parentId);

    List<Map<String,Object>> queryMerchanAccountBalancetList(@Param("merchant") MerchantModel merchant);

}