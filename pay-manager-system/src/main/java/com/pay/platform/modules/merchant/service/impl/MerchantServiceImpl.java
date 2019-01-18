package com.pay.platform.modules.merchant.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.config.BizConstants;
import com.pay.platform.common.util.BuildNumberUtils;
import com.pay.platform.common.util.PropertyUtils;
import com.pay.platform.common.util.encrypt.AESEncryptUtil;
import com.pay.platform.modules.merchant.dao.MerchantDao;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;


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
    public Integer addMerchant(MerchantModel merchant) throws Exception {
        //生产商家编号
        String merchantNo = BuildNumberUtils.getMerchantNo();
        merchant.setMerchantNo(merchantNo);
        //设置商家的密钥
        merchant.setMerchantSecret(UUID.randomUUID().toString().replaceAll("-", ""));
        //设置商家的回调密钥
        String notifySecret = UUID.randomUUID().toString().replaceAll("-", "").substring(16);
        merchant.setNotifySecret(notifySecret);

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