package com.pay.platform.modules.sysmgr.user.service.impl;

import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.modules.sysmgr.user.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * UserModel: zjt
 * DateTime: 16/9/25 15:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Integer queryUserExistsByAccount(String account) {
        return userDao.queryUserExistsByAccount(account);
    }

    @Override
    public Integer addUser(UserModel user) {
        user.setId(UUID.randomUUID().toString());
        return userDao.addUser(user);
    }

    @Override
    public Integer deleteUser(String[] ids) {
        return userDao.deleteUser(ids);
    }

    @Override
    public Integer updateUser(UserModel user) {
        return userDao.updateUser(user);
    }

    @Override
    public PageInfo<UserModel> queryUserList(String nickname, String account, List<String> orgIdList) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nickname", nickname);
        params.put("account", account);
        params.put("orgIdList", orgIdList);

        return new PageInfo(userDao.queryUserList(params));

    }

    @Override
    public UserModel queryUserById(String id) {
        return userDao.queryUserById(id);
    }

    @Override
    public List<RoleModel> queryRoleListForGrant(String userId) {

        //查询所有角色
        List<RoleModel> roleList = roleDao.queryRoleList(new HashMap<String, Object>());

        //查询用户拥有角色
        List<RoleModel> userRoleList = userDao.queryRoleListByUserId(userId);

        for (RoleModel role : roleList) {

            role.setChecked(false);

            for (RoleModel userRole : userRoleList) {

                if (role.getId().equals(userRole.getId())) {
                    role.setChecked(true);            //回显
                }

            }

        }

        return roleList;
    }

    @Override
    public Integer grantRole(String userId, String[] roleIds) {

        Integer count = 0;

        //删除用户的所有角色
        count += userDao.deleteRoleByUserId(userId);

        //重新授予角色
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("roleIds", roleIds);
        count += userDao.addRoleToUser(params);

        return count;

    }

    @Override
    public Integer updateUserOrganization(String userId, String orgId) {
        return userDao.updateUserOrganization(userId, orgId);
    }

    @Override
    public OrganizationModel queryOrgInfoByUserId(String userId) {
        return userDao.queryOrgInfoByUserId(userId);
    }

    @Override
    public Integer updateUserPassword(String userId, String oldPassword, String password) {
        return userDao.updateUserPassword(userId, oldPassword, password);
    }

    @Override
    public int queryUserByUserIdAndPassword(String id, String oldPassword) {
        return userDao.queryUserByUserIdAndPassword(id , oldPassword);
    }

}