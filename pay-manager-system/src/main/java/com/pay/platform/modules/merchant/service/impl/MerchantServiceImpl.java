package com.pay.platform.modules.merchant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import com.pay.platform.modules.merchant.dao.MerchantDao;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public PageInfo<MerchantModel> queryMerchantList(MerchantModel merchant) {
        return new PageInfo(merchantDao.queryMerchantList(merchant));
    }

    @Override
    public MerchantModel queryMerchantById(String id) {
        return merchantDao.queryMerchantById(id);
    }

    @Override
    public Integer addMerchant(MerchantModel merchant) {
        return merchantDao.addMerchant(merchant);
    }

    @Override
    public Integer deleteMerchant(String[] ids) {
        return merchantDao.deleteMerchant(ids);
    }

    @Override
    public Integer deleteMerchantByLogic(String[] ids) {
        return merchantDao.deleteMerchantByLogic(ids);
    }

    @Override
    public Integer updateMerchant(MerchantModel merchant) {
        return merchantDao.updateMerchant(merchant);
    }

}