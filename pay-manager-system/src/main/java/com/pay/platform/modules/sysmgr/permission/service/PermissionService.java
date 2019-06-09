package com.pay.platform.modules.sysmgr.permission.service;

import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;

import java.util.List;

/**
 * User:
 * DateTime: 16/10/5 14:07
 */
public interface PermissionService {

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
     * 修改权限(菜单) - 位置
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @return
     */
    Integer updatePosition(String id1,Integer seq1,String id2,Integer seq2);

    /**
     * 查询权限列表
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