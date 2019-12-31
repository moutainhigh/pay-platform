package com.pay.platform.modules.phoneConfig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.phoneConfig.model.PhoneConfigModel;
import com.pay.platform.modules.phoneConfig.service.PhoneConfigService;
import com.pay.platform.modules.phoneConfig.dao.PhoneConfigDao;


/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
@Service
public class PhoneConfigServiceImpl implements PhoneConfigService {

    @Autowired
    private PhoneConfigDao phoneConfigDao;

    @Override
    public PageInfo<PhoneConfigModel> queryPhoneConfigList(PhoneConfigModel phoneConfig) {
        return new PageInfo(phoneConfigDao.queryPhoneConfigList(phoneConfig));
    }

    @Override
    public PhoneConfigModel queryPhoneConfigById(String id) {
        return phoneConfigDao.queryPhoneConfigById(id);
    }

    @Override
    public Integer addPhoneConfig(PhoneConfigModel phoneConfig) {
        return phoneConfigDao.addPhoneConfig(phoneConfig);
    }

    @Override
    public Integer deletePhoneConfig(String[] ids) {
        return phoneConfigDao.deletePhoneConfig(ids);
    }

    @Override
    public Integer deletePhoneConfigByLogic(String[] ids) {
        return phoneConfigDao.deletePhoneConfigByLogic(ids);
    }

    @Override
    public Integer updatePhoneConfig(PhoneConfigModel phoneConfig) {
        return phoneConfigDao.updatePhoneConfig(phoneConfig);
    }

}