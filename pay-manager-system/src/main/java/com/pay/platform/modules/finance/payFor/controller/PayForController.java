package com.pay.platform.modules.finance.payFor.controller;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.enums.CheckStatusEnum;
import com.pay.platform.common.enums.WithdrawStatusEnum;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.finance.payFor.service.PayForService;
import com.pay.platform.modules.finance.withdraw.controller.WithdrawController;
import com.pay.platform.modules.finance.withdraw.dao.WithdrawDao;
import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.finance.withdraw.service.WithdrawService;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private WithdrawService withdrawService;

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

    /**
     * 审核
     *
     * @param request
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/review", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "代付管理", operation = "提现审核")
    public void review(HttpServletRequest request, HttpServletResponse response, String id, String checkStatus, String checkDesc) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = payForService.payForReview(id, checkStatus, checkDesc);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "修改成功");
        } else {
            json.put("success", false);
            json.put("msg", "修改失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 设置为提现成功
     *
     * @param request
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/updateWithdrawStatusToSuccess", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "代付管理", operation = "更新为提现成功")
    public void updateWithdrawStatusToSuccess(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        try {
            boolean result = payForService.updateWithdrawStatusToSuccess(id, WithdrawStatusEnum.withdrawSuccess.getCode());

            if (result) {
                json.put("success", true);
                json.put("msg", "修改成功");
            } else {
                json.put("success", false);
                json.put("msg", "修改失败");
            }
            writeJson(response, json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage());
            json.put("success", false);
            json.put("msg", e.getMessage());

            writeJson(response, json.toString());
        }


    }


    /**
     * 设置为提现失败
     *
     * @param request
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/updateWithdrawStatusToFail", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "代付管理", operation = "更新为提现失败")
    public void updateWithdrawStatusToFail(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        try {

            boolean result = payForService.updateWithdrawStatusToFail(id, WithdrawStatusEnum.withdrawFail.getCode());

            if (result) {
                json.put("success", true);
                json.put("msg", "修改成功");
            } else {
                json.put("success", false);
                json.put("msg", "修改失败");
            }

            writeJson(response, json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            getCurrentLogger().error(e.getMessage());
            json.put("success", false);
            json.put("msg", e.getMessage());
            writeJson(response, json.toString());
        }

    }


}