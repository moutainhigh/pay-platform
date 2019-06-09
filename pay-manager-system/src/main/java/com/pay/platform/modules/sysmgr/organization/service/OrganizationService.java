package com.pay.platform.modules.sysmgr.organization.service;

import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;

import java.util.List;

/**
 * User:
 * DateTime: 2016/10/19 20:14
 */
public interface OrganizationService {

    /**
     * 添加组织机构
     * @param model
     * @return
     */
    Integer addOrganization(OrganizationModel model);

    /**
     * 删除组织机构
     * @param id
     * @return
     */
    Integer deleteOrganization(String id);

    /**
     * 更新机构信息
     * @param model
     * @return
     */
    Integer updateOrganization(OrganizationModel model);

    /**
     * 位置修改
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @return
     */
    Integer updatePosition(String id1, Integer seq1, String id2, Integer seq2);

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
     * 递归查询子节点,并将id加到List中
     * @param parentId
     * @param idList
     */
    void treeById(String parentId, List<String> idList);

    /**
     * 递归查询子节点,并将对象加入list中
     * @param parentId
     * @param orgList
     */
    void treeByModel(String parentId, List<OrganizationModel> orgList);

}