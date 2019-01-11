package com.pay.platform.modules.sysmgr.permission.dao;

import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User: zjt
 * DateTime: 16/10/5 14:09
 */
@MapperScan
public interface PermissionDao {

    /**
     * 新增权限
     * @param model
     * @return
     */
    Integer addPermission(PermissionModel model);

    /**
     * 删除权限
     * @param ids
     * @return
     */
    Integer deletePermission(String[] ids);

    /**
     * 修改权限
     * @param model
     * @return
     */
    Integer updatePermission(PermissionModel model);

    /**
     * 修改位置
     * @param model
     * @return
     */
    Integer updatePosition(PermissionModel model);

    /**
     * 分页查询权限列表
     * @return
     */
    List<PermissionModel> queryPermissionList();

    /**
     * 获取下一个排序索引
     * @param parentId
     * @return
     */
    Integer queryNextSeq(String parentId);

    /**
     * 根据id查询权限信息
     * @param id
     * @return
     */
    PermissionModel queryPermissionById(String id);

}