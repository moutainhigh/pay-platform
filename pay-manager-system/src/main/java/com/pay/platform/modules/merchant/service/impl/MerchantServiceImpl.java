package com.pay.platform.modules.merchant.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import com.pay.platform.modules.merchant.dao.MerchantDao;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private UserDao userDao;

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

        //1, 添加商家信息
        int count = merchantDao.addMerchant(merchant);

        //2, 为商户添加账号,并绑定商家ID
        UserModel userModel = new UserModel();


        count += userDao.addUser(userModel);

        //3, 为账号授予商家管理员角色

        return count;

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

    @Override
    public MerchantModel queryMerchantByIMerchantNo(String merchantNo) {
        return merchantDao.queryMerchantByIMerchantNo(merchantNo);
    }

    @Override
    public List<Map<String, Object>> queryMerchantIdAndNameList(String merchantId) {
        return merchantDao.queryMerchantIdAndNameList(merchantId);
    }

}