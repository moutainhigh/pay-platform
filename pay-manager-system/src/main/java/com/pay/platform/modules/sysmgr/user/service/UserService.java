package com.pay.platform.modules.sysmgr.user.service;
import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * UserModel:
 * DateTime: 16/9/25 15:53
 */
public interface UserService {

    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    Integer queryUserExistsByAccount(String account);

    /**
     * 新增用户
     * @param user
     * @return
     */
    Integer addUser(UserModel user);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    Integer deleteUser(String[] ids);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer updateUser(UserModel user);

    /**
     * 分页查询用户列表
     * @param nickname
     * @param account
     * @param orgIdList
     * @return
     */
    PageInfo<UserModel> queryUserList(String nickname, String account,List<String> orgIdList);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserModel queryUserById(String id);

    /**
     * 查询角色列表 - 授予角色时回显
     * @param userId
     * @return
     */
    List<RoleModel> queryRoleListForGrant(String userId);

    /**
     * 授予角色
     * @param userId
     * @param roleIds
     * @return
     */
    Integer grantRole(String userId, String[] roleIds);

    /**
     * 更新用户组织机构
     * @param userId
     * @param orgId
     * @return
     */
    Integer updateUserOrganization(String userId, String orgId);

    /**
     * 查询用户的组织机构
     * @param userId
     * @return
     */
    OrganizationModel queryOrgInfoByUserId(String userId);

    /**
     * 修改用户密码
     * @param userId
     * @param oldPassword
     * @param password
     * @return
     */
    Integer updateUserPassword(String userId, String oldPassword, String password);

    int queryUserByUserIdAndPassword(String id, String oldPassword);

    /**
     * 查询商家密钥
     * @param userId
     * @return
     */
    Map<String,Object> queryMerchantSecretByUserId(String userId);

}