package com.pay.platform.modules.bill.controller;


import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.bill.service.BillService;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/18 20:58
 * <p>
 * 流水管理模块
 */
@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    /**
     * 查询代理每日流水（暂时没用）
     * @param request
     * @param agentId
     * @param beginTime
     * @param endTime
     * @param statisticsWay
     * @return
     */
    @RequestMapping(value = "/agent/queryAgentEveryDayBill")
    @ResponseBody
    @SystemControllerLog(module = "代理流水", operation = "查看代理每日流水")
    public PageInfo<Map<String, Object>> queryAgentEveryDayBill(HttpServletRequest request, String agentId, String beginTime, String endTime, String statisticsWay) {

        setPageInfo(request);
        UserModel userModel = AppContext.getCurrentUser();

        //代理管理员：可查到自身流水
        if (SysUserUtil.isAgentRole(userModel)) {
            agentId = userModel.getAgentId();
        }

        if ("day".equalsIgnoreCase(statisticsWay)) {
            return billService.queryAgentEveryDayBill(agentId, beginTime, endTime);                      //按天统计
        } else if ("timeLine".equalsIgnoreCase(statisticsWay)) {
            return billService.queryBillByDateTime(agentId, null, beginTime, endTime);        //按时间段统计
        }

        return null;

    }

    /**
     * 查询商家每日流水（暂时没用）
     * @param request
     * @param agentId
     * @param beginTime
     * @param endTime
     * @param statisticsWay
     * @return
     */
    @RequestMapping(value = "/merchant/queryMerchantEveryDayBill")
    @ResponseBody
    @SystemControllerLog(module = "商家流水", operation = "查看商家每日流水")
    public PageInfo<Map<String, Object>> queryMerchantEveryDayBill(HttpServletRequest request, HttpServletResponse response
            , String merchantId, String beginTime, String endTime, String agentId, String statisticsWay) {

        setPageInfo(request);
        UserModel userModel = AppContext.getCurrentUser();

        //商家管理员：只能查看自身,不接受前端传递参数
        if (SysUserUtil.isMerchantRole(userModel)) {
            merchantId = userModel.getMerchantId();
        }

        if (StringUtil.isEmpty(merchantId)) {
            return null;
        }

        if ("day".equalsIgnoreCase(statisticsWay)) {
            return billService.queryMerchantEveryDayBill(merchantId, beginTime, endTime, agentId);       //按天统计
        } else if ("timeLine".equalsIgnoreCase(statisticsWay)) {
            return billService.queryBillByDateTime(agentId, merchantId, beginTime, endTime);             //按时间段统计
        }

        return null;

    }

    /**
     * 查询代理分润流水
     * @param request
     * @param agentId
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/queryAgentProfit")
    @ResponseBody
    @SystemControllerLog(module = "查询代理分润流水", operation = "查询代理分润流水")
    public PageInfo<Map<String, Object>> queryAgentProfit(HttpServletRequest request, String agentId, String beginTime, String endTime) {

        setPageInfo(request);
        UserModel userModel = AppContext.getCurrentUser();

        //代理管理员：可查到自身流水
        if (SysUserUtil.isAgentRole(userModel)) {
            agentId = userModel.getAgentId();
        }

        return billService.queryAgentEveryDayBill(agentId, beginTime, endTime);                      //按天统计

    }

}