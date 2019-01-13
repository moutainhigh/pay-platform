package com.pay.platform.api.merchant.service.impl;

import com.pay.platform.api.merchant.dao.MerchantDao;
import com.pay.platform.api.merchant.model.MerchantModel;
import com.pay.platform.api.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/1/13 20:53
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public MerchantModel queryMerchantByIMerchantNo(String merchantNo) {
        return merchantDao.queryMerchantByIMerchantNo(merchantNo);
    }

    @Override
    public List<MerchantModel> queryMerchantList() {
        return merchantDao.queryMerchantList();
    }

}