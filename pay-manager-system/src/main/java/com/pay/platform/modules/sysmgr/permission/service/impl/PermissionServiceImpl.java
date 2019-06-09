package com.pay.platform.modules.sysmgr.permission.service.impl;

import com.pay.platform.modules.sysmgr.permission.dao.PermissionDao;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User:
 * DateTime: 16/10/5 14:08
 */
@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Integer addPermission(PermissionModel model) {
        Integer nextSeq = permissionDao.queryNextSeq(model.getParentId());
        model.setSeq(nextSeq);
        return permissionDao.addPermission(model);
    }

    @Override
    public Integer deletePermission(String[] ids) {
        return permissionDao.deletePermission(ids);
    }

    @Override
    public Integer updatePermission(PermissionModel model) {
        return permissionDao.updatePermission(model);
    }

    @Override
    public Integer updatePosition(String id1,Integer seq1,String id2,Integer seq2) {

        Integer count = 0;

        PermissionModel permission1 = new PermissionModel();
        permission1.setId(id1);
        permission1.setSeq(seq2);

        PermissionModel permission2 = new PermissionModel();
        permission2.setId(id2);
        permission2.setSeq(seq1);

        count += permissionDao.updatePosition(permission1);
        count += permissionDao.updatePosition(permission2);

        return count;
    }

    @Override
    public List<PermissionModel> queryPermissionList() {
        return permissionDao.queryPermissionList();
    }

    @Override
    public Integer queryNextSeq(String parentId) {
        return permissionDao.queryNextSeq(parentId);
    }

    @Override
    public PermissionModel queryPermissionById(String id) {
        return permissionDao.queryPermissionById(id);
    }

}