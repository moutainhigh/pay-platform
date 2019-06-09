package com.pay.platform.modules.sysmgr.role.service;

import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * User:
 * DateTime: 2016/10/7 20:15
 */
public interface RoleService {

    /**
     * 新增角色
     * @param role
     * @return
     */
    Integer addRole(RoleModel role);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    Integer deleteRole(String[] ids);

    /**
     * 修改角色
     * @param role
     * @return
     */
    Integer updateRole(RoleModel role);

    /**
     * 分页查询角色
     * @param name
     * @param code
     * @return
     */
    PageInfo<RoleModel> queryRoleList(String name,String code);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    RoleModel queryRoleById(String id);

    /**
     * 授予权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    Integer grantPermission(String roleId, String[] permissionIds);

    /**
     * 查询权限信息 - 授予权限时回显
     * @param roleId
     * @return
     */
    List<PermissionModel> queryPermissionListForGrant(String roleId);

}