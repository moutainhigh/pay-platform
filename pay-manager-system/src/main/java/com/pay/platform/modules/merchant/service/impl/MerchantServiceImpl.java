package com.pay.platform.modules.merchant.service.impl;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.RoleCodeEnum;
import com.pay.platform.common.util.BuildNumberUtils;
import com.pay.platform.common.util.encrypt.Md5Util;
import com.pay.platform.modules.merchant.dao.MerchantDao;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.role.service.RoleService;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.modules.sysmgr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

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
        merchant.setId(UUID.randomUUID().toString());
        merchant.setMerchantNo(merchantNo);

        //设置商家的密钥
        merchant.setMerchantSecret(UUID.randomUUID().toString().replaceAll("-", ""));

        //设置商家的回调密钥
        String notifySecret = UUID.randomUUID().toString().replaceAll("-", "").substring(16);
        merchant.setNotifySecret(notifySecret);

        //1, 添加商家信息
        int count = merchantDao.addMerchant(merchant);

        //2, 为商户添加账号,并绑定商家ID
        UserModel userModel = new UserModel();
        userModel.setAccount(merchantNo);
        userModel.setPassword(merchantNo);      //初始化密码,跟账号一样,商家首次登陆后续配置,并修改提现密码
        userModel.setMerchantId(merchant.getId());
        userModel.setPhone(merchant.getPhone());
        userModel.setNickname(merchant.getMerchantName());
        count += userService.addUser(userModel);

        //3, 为账号授予商家管理员角色
        String userId = userModel.getId();
        RoleModel roleModel = roleDao.queryRoleByRoleCode(RoleCodeEnum.ROLE_MERCHANT.getCode());
        count += userService.grantRole(userId, roleModel.getId().split(","));

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