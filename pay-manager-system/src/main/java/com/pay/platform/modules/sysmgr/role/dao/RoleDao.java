package com.pay.platform.modules.sysmgr.role.dao;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface RoleDao {

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
     * 分页查询角色列表
     * @param params
     * @return
     */
    List<RoleModel> queryRoleList(Map<String,Object> params);

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    RoleModel queryRoleById(String id);

    /**
     * 删除角色权限
     * @param roleId
     * @return
     */
    Integer deletePermissionByRoleId(String roleId);

    /**
     * 为角色添加权限
     * @param params
     * @return
     */
    Integer addPermissionToRole(Map<String, Object> params);

    /**
     * 查询角色的权限列表
     * @param roleId
     * @return
     */
    List<PermissionModel> queryPermissionListByRoleId(String roleId);

    /**
     * 查询权限 - 分配给了那些角色
     * @param id
     * @return
     */
    List<RoleModel> queryRoleListByPermissionId(String id);

    /**
     * 根据角色码查询角色
     * @param code
     * @return
     */
    RoleModel queryRoleByRoleCode(String code);

}