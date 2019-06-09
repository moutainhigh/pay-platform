package com.pay.platform.modules.sysmgr.organization.service.impl;

import com.pay.platform.modules.sysmgr.organization.dao.OrganizationDao;
import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
import com.pay.platform.modules.sysmgr.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User:
 * DateTime: 2016/10/19 20:14
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public Integer addOrganization(OrganizationModel model) {
        return organizationDao.addOrganization(model);
    }

    @Override
    public Integer deleteOrganization(String id) {

        List<String> idList = new ArrayList<String>();
        idList.add(id);

        //递归查询子节点,并将id添加到List中
        treeById(id,idList);

        return organizationDao.deleteOrganization(idList);
    }

    @Override
    public Integer updateOrganization(OrganizationModel model) {
        return organizationDao.updateOrganization(model);
    }

    @Override
    public Integer updatePosition(String id1, Integer seq1, String id2, Integer seq2) {

        Integer count = 0;

        OrganizationModel organization1 = new OrganizationModel();
        organization1.setId(id1);
        organization1.setSeq(seq2);

        OrganizationModel organization2 = new OrganizationModel();
        organization2.setId(id2);
        organization2.setSeq(seq1);

        count += organizationDao.updatePosition(organization1);
        count += organizationDao.updatePosition(organization2);

        return count;

    }

    @Override
    public List<OrganizationModel>  queryOrganizationList() {
        return organizationDao.queryOrganizationList();
    }

    @Override
    public Integer queryNextSeq(String parentId) {
        return organizationDao.queryNextSeq(parentId);
    }

    @Override
    public OrganizationModel queryOrganizationById(String id) {
        return organizationDao.queryOrganizationById(id);
    }

    /**
     * 递归查询子节点,并将id加到List中
     * @param parentId
     * @param idList
     */
    @Override
    public void treeById(String parentId, List<String> idList){

        //查询子节点
        List<OrganizationModel> childrenList = organizationDao.queryOrgListByParentId(parentId);

        if (!CollectionUtils.isEmpty(childrenList)) {

            //迭代查询出来的子节点
            for (OrganizationModel organizationModel : childrenList) {

                idList.add(organizationModel.getId());

                //如果发现还有子节点，则继续递归查询,并添加id到list中
                if(organizationModel.getChildCount() > 0){
                    treeById(organizationModel.getId(),idList);
                }

            }

        }

    }

    /**
     * 递归查询子节点,并将对象加入list中
     * @param parentId
     * @param orgList
     */
    @Override
    public void treeByModel(String parentId, List<OrganizationModel> orgList) {

        //查询子节点
        List<OrganizationModel> childrenList = organizationDao.queryOrgListByParentId(parentId);

        if (!CollectionUtils.isEmpty(childrenList)) {

            //迭代查询出来的子节点
            for (OrganizationModel organizationModel : childrenList) {

                orgList.add(organizationModel);

                //如果发现还有子节点，则继续递归查询,并添加对象到list中
                if(organizationModel.getChildCount() > 0){
                    treeByModel(organizationModel.getId() , orgList);
                }

            }

        }

    }

}