package com.pay.platform.modules.merchant.service;

import com.pay.platform.modules.merchant.model.MerchantRateListModel;
import com.pay.platform.modules.merchant.model.MerchantRateModel;

import java.util.List;

public interface MerchantRateService {

    /**
     * 新增
     * @param model
     * @return
     */
    Integer add(MerchantRateModel model);

    /**
     * 查询商家的费率列表
     * @param merchantId
     * @return
     */
    List<MerchantRateListModel> queryMerchantRateList(String merchantId);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(String id);
}
