package com.pay.platform.modules.sysmgr.log.controller;

import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.model.OperationLogModel;
import com.pay.platform.modules.sysmgr.log.service.OperationLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User:
 * DateTime: 2016/10/22 16:11
 */
@Controller
@RequestMapping("/sysmgr/log")
public class OperationLogController extends BaseController{

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 分页查询日志信息
     * @param request
     * @param response
     * @param userName
     * @param ip
     * @param module
     * @param operation
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryOperationLogList" , produces = "application/json;charset=UTF-8")
    public PageInfo<OperationLogModel> queryOperationLogList(HttpServletRequest request, HttpServletResponse response, String userName, String ip, String module, String operation, String startTime, String endTime) throws Exception{
        setPageInfo(request);
        return operationLogService.queryOperationLogList(userName, ip, module, operation, startTime, endTime);
    }

}