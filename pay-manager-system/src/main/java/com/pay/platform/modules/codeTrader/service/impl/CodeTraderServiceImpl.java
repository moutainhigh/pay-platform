package com.pay.platform.modules.codeTrader.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.RoleCodeEnum;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.modules.sysmgr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.codeTrader.model.CodeTraderModel;
import com.pay.platform.modules.codeTrader.service.CodeTraderService;
import com.pay.platform.modules.codeTrader.dao.CodeTraderDao;
import sun.management.resources.agent;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class CodeTraderServiceImpl implements CodeTraderService {

    @Autowired
    private CodeTraderDao codeTraderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo<CodeTraderModel> queryCodeTraderList(CodeTraderModel codeTrader) {
        return new PageInfo(codeTraderDao.queryCodeTraderList(codeTrader));
    }

    @Override
    public CodeTraderModel queryCodeTraderById(String id) {
        return codeTraderDao.queryCodeTraderById(id);
    }

    @Override
    public Integer addCodeTrader(String account, String password, CodeTraderModel codeTrader) {

        //1, 添加码商信息
        codeTrader.setId(UUID.randomUUID().toString());
        int count = codeTraderDao.addCodeTrader(codeTrader);

        //2, 为码商添加账号,并绑定码商ID
        UserModel userModel = new UserModel();
        userModel.setAccount(account);
        userModel.setPassword(password);
        userModel.setCodeTraderId(codeTrader.getId());
        userModel.setNickname(codeTrader.getName());
        count += userService.addUser(userModel);

        //3, 为账号授予码商管理员角色
        String userId = userModel.getId();
        RoleModel roleModel = roleDao.queryRoleByRoleCode(RoleCodeEnum.ROLE_CODE_TRADER.getCode());
        count += userService.grantRole(userId, roleModel.getId().split(","));

        return count;
    }

    @Override
    public Integer deleteCodeTrader(String[] ids) {

        int count = 0;

        count += codeTraderDao.deleteCodeTrader(ids);
        count += userDao.deleteUserByCodeTraderIdOfLogic(ids);

        return count;

    }

    @Override
    public Integer deleteCodeTraderByLogic(String[] ids) {
        return codeTraderDao.deleteCodeTraderByLogic(ids);
    }

    @Override
    public Integer updateCodeTrader(CodeTraderModel codeTrader) {
        return codeTraderDao.updateCodeTrader(codeTrader);
    }

    @Override
    public PageInfo<Map> queryAllCodeTraderByMerchantId(String merchantId) {
        return new PageInfo(codeTraderDao.queryAllCodeTraderByMerchantId(merchantId));
    }

    @Override
    public int addMerchantCodeTrader(String codeTraderId, String merchantId) {
        return codeTraderDao.addMerchantCodeTrader(codeTraderId , merchantId);
    }

    @Override
    public int deleteMerchantCodeTrader(String codeTraderId, String merchantId) {
        return codeTraderDao.deleteMerchantCodeTrader(codeTraderId , merchantId);
    }

}