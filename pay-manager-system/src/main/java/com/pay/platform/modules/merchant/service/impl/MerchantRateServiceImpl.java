package com.pay.platform.modules.merchant.service.impl;

import com.pay.platform.modules.merchant.dao.MerchantRateDao;
import com.pay.platform.modules.merchant.model.MerchantRateListModel;
import com.pay.platform.modules.merchant.model.MerchantRateModel;
import com.pay.platform.modules.merchant.service.MerchantRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantRateServiceImpl implements MerchantRateService {


    @Autowired
    private MerchantRateDao dao;


    /**
     * 新增
     * @param model
     * @return
     */
    @Override
    public Integer add(MerchantRateModel model) {
        Integer add = dao.add(model);
        return add;
    }

    /**
     * 查询商家的费率列表
     * @param merchantId
     * @return
     */
    @Override
    public List<MerchantRateListModel> queryMerchantRateList(String merchantId){
        List<MerchantRateListModel> merchantRateListModels = dao.queryMerchantRateList(merchantId);
        return merchantRateListModels;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Integer delete(String id){
        Integer count = dao.delete(id);
        return count;
    }


}