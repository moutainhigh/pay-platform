package com.pay.platform.modules.sysmgr.log.service.impl;

import com.pay.platform.modules.sysmgr.log.dao.OperationLogDao;
import com.pay.platform.modules.sysmgr.log.model.OperationLogModel;
import com.pay.platform.modules.sysmgr.log.service.OperationLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/22 16:26
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public Integer addOperationLog(OperationLogModel operationLogModel) {
        return operationLogDao.addOperationLog(operationLogModel);
    }

    @Override
    public PageInfo<OperationLogModel> queryOperationLogList(String userName,String ip,String module,String operation,String startTime,String endTime) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userName",userName);
        params.put("ip",ip);
        params.put("module",module);
        params.put("operation",operation);
        params.put("startTime",startTime);
        params.put("endTime",endTime);

        return new PageInfo(operationLogDao.queryOperationLogList(params));
    }

    @Override
    public OperationLogModel queryOperationLogById(String id) {
        return operationLogDao.queryOperationLogById(id);
    }

}