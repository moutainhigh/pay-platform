package com.pay.platform.modules.finance.withdraw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;

import com.pay.platform.modules.finance.withdraw.model.WithdrawModel;
import com.pay.platform.modules.finance.withdraw.service.WithdrawService;


/**
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/finance/withdraw")
public class WithdrawController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);

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
    @RequestMapping(value = "/queryWithdrawList", produces = "application/json")
    public PageInfo<WithdrawModel> queryWithdrawList(HttpServletRequest request, HttpServletResponse response, WithdrawModel withdraw) throws Exception {
        setPageInfo(request);
        return withdrawService.queryWithdrawList(withdraw);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryWithdrawById", produces = "application/json")
    public void queryWithdrawById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        WithdrawModel withdraw = withdrawService.queryWithdrawById(id);
        json.put("success", true);
        json.put("data", withdraw);

        writeJson(response, json.toString());

    }

    /**
     * 新增提现申请
     *
     * @param response
     * @param withdraw
     * @throws Exception
     */
    @RequestMapping(value = "/addWithdraw", produces = "application/json")
    @SystemControllerLog(module = "提现申请管理", operation = "新增提现申请")
    public void addWithdraw(HttpServletResponse response, WithdrawModel withdraw) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = withdrawService.addWithdraw(withdraw);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "新增成功");
        } else {
            json.put("success", false);
            json.put("msg", "新增失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 删除提现申请
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteWithdraw", produces = "application/json")
    @SystemControllerLog(module = "提现申请管理", operation = "删除提现申请")
    public void deleteWithdraw(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = withdrawService.deleteWithdraw(ids.split(","));

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 逻辑删除提现申请 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteWithdrawByLogic", produces = "application/json")
    @SystemControllerLog(module = "提现申请管理", operation = "删除提现申请")
    public void deleteWithdrawByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = withdrawService.deleteWithdrawByLogic(ids.split(","));

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 修改提现申请信息
     *
     * @param request
     * @param response
     * @param withdraw
     * @throws Exception
     */
    @RequestMapping(value = "/updateWithdraw", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "提现申请管理", operation = "修改提现申请")
    public void updateWithdraw(HttpServletRequest request, HttpServletResponse response, WithdrawModel withdraw) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = withdrawService.updateWithdraw(withdraw);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "修改成功");
        } else {
            json.put("success", false);
            json.put("msg", "修改失败");
        }

        writeJson(response, json.toString());

    }

}