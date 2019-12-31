package com.pay.platform.modules.phoneConfig.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

import com.pay.platform.modules.phoneConfig.model.PhoneConfigModel;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
public interface PhoneConfigService {

    /**
     * 分页查询手机校验配置
     *
     * @param phoneConfig
     * @return
     */
    PageInfo<PhoneConfigModel> queryPhoneConfigList(PhoneConfigModel phoneConfig);

    /**
     * 根据id查询手机校验配置
     *
     * @param id
     * @return
     */
    PhoneConfigModel queryPhoneConfigById(String id);

    /**
     * 新增手机校验配置
     *
     * @param phoneConfig
     * @return
     */
    Integer addPhoneConfig(PhoneConfigModel phoneConfig);

    /**
     * 删除手机校验配置
     *
     * @param ids
     * @return
     */
    Integer deletePhoneConfig(String[] ids);

    /**
     * 逻辑删除手机校验配置
     *
     * @param ids
     * @return
     */
    Integer deletePhoneConfigByLogic(String[] ids);

    /**
     * 修改手机校验配置
     *
     * @param phoneConfig
     * @return
     */
    Integer updatePhoneConfig(PhoneConfigModel phoneConfig);

}