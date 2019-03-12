package com.pay.platform.modules.finance.payFor.controller;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.finance.payFor.service.PayForService;
import com.pay.platform.modules.finance.withdraw.controller.WithdrawController;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.finance.withdraw.service.WithdrawService;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: zjt
 * DateTime: 2019/3/12 20:38
 */
@Controller
@RequestMapping("/finance/payFor")
public class PayForController extends BaseController {

    @Autowired
    private PayForService payForService;

    /**
     * 分页查询提现申请列表
     *
     * @param request
     * @param response
     * @param withdraw
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPayForList", produces = "application/json")
    public PageInfo<WithdrawModel> queryPayForList(HttpServletRequest request, HttpServletResponse response, WithdrawModel withdraw
            , String agentId, String merchantId, String beginTime, String endTime) throws Exception {
        setPageInfo(request);
        return payForService.queryPayForList(withdraw, agentId, merchantId, beginTime, endTime);
    }


}