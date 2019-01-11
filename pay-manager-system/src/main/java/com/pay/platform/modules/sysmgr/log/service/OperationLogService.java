package com.pay.platform.modules.sysmgr.log.service;
import com.pay.platform.modules.sysmgr.log.model.OperationLogModel;
import com.github.pagehelper.PageInfo;

/**
 * UserModel: zjt
 * DateTime: 16/9/25 15:53
 */
public interface OperationLogService {

    /**
     * 添加操作日志
     * @param operationLogModel
     * @return
     */
    Integer addOperationLog(OperationLogModel operationLogModel);

    /**
     * 分页查询日志信息
     * @param userName
     * @param ip
     * @param module
     * @param operation
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<OperationLogModel> queryOperationLogList(String userName,String ip,String module,String operation,String startTime,String endTime);

    /**
     * 根据id查询日志信息
     * @param id
     * @return
     */
    OperationLogModel queryOperationLogById(String id);

}