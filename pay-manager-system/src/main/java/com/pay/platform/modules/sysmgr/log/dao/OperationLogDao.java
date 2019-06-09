package com.pay.platform.modules.sysmgr.log.dao;

import com.pay.platform.modules.sysmgr.log.model.OperationLogModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/22 16:26
 */
@MapperScan
public interface OperationLogDao {

    /**
     * 添加日志信息
     * @param operationLogModel
     * @return
     */
    Integer addOperationLog(OperationLogModel operationLogModel);

    /**
     * 分页查询日志列表
     * @param params
     * @return
     */
    List<OperationLogModel> queryOperationLogList(Map<String,Object> params);

    /**
     * 根据id查询日志信息
     * @param id
     * @return
     */
    OperationLogModel queryOperationLogById(String id);

}