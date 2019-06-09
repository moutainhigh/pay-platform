package com.pay.platform.modules.sysmgr.organization.dao;
import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User:
 * DateTime: 2016/10/19 20:15
 */
@MapperScan
public interface OrganizationDao {

    /**
     * 添加组织机构
     * @param model
     * @return
     */
    Integer addOrganization(OrganizationModel model);

    /**
     * 删除组织机构
     * @param idList
     * @return
     */
    Integer deleteOrganization(List<String> idList);

    /**
     * 更新机构信息
     * @param model
     * @return
     */
    Integer updateOrganization(OrganizationModel model);

    /**
     * 位置修改
     * @param model
     * @return
     */
    Integer updatePosition(OrganizationModel model);

    /**
     * 查询组织机构列表
     * @return
     */
    List<OrganizationModel> queryOrganizationList();

    /**
     * 获取下一个排序字段
     * @param parentId
     * @return
     */
    Integer queryNextSeq(String parentId);

    /**
     * 根据id查询机构信息
     * @param id
     * @return
     */
    OrganizationModel queryOrganizationById(String id);

    /**
     * 查询子节点
     * @param parentId
     * @return
     */
    List<OrganizationModel> queryOrgListByParentId(@Param("parentId") String parentId);

}