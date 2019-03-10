package com.pay.platform.modules.finance.withdraw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.plugins.redis.RedisLock;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.encrypt.Md5Util;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.security.CommonRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

        UserModel userModel = AppContext.getCurrentUser();
        String userId = userModel.getId();
        String merchantId = userModel.getMerchantId();

        try {

            withdraw.setMerchantId(merchantId);
            Integer count = withdrawService.addWithdraw(userId, withdraw);

            if (count > 0) {
                json.put("success", true);
                json.put("msg", "申请提现成功");
            } else {
                json.put("success", false);
                json.put("msg", "申请提现失败");
            }

        } catch (Exception e) {
            json.put("success", false);
            json.put("msg",  e.getMessage());
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

    /**
     * 查询是否设置过提现密码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/queryIsInitWithdrawPassword", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "查询是否设置过提现密码", operation = "查询是否设置过提现密码")
    public void queryIsInitWithdrawPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        UserModel userModel = AppContext.getCurrentUser();
        String withdrawPassword = userModel.getWithdrawPassword();
        if (StringUtil.isNotEmpty(withdrawPassword)) {
            json.put("success", true);
            json.put("msg", "已设置提现密码。");
        } else {
            json.put("success", false);
            json.put("msg", "未设置提现密码。");
        }

        writeJson(response, json.toString());

    }

    /**
     * 初始化提现密码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/initWithdrawPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "提现申请", operation = "初始化提现密码")
    public void initWithdrawPassword(HttpServletRequest request, HttpServletResponse response, String withdrawPassword) throws Exception {

        JSONObject json = new JSONObject();

        UserModel currentUser = AppContext.getCurrentUser();
        String userId = currentUser.getId();

        Integer count = withdrawService.initWithdrawPassword(userId, withdrawPassword);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "修改成功");

            //刷新session实体数据
            currentUser.setWithdrawPassword(Md5Util.md5_32(withdrawPassword));

        } else {
            json.put("success", false);
            json.put("msg", "修改失败!");
        }

        writeJson(response, json.toString());

    }

    /**
     * 校验提现密码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/checkWithdrawPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "提现申请", operation = "校验提现密码")
    public void checkWithdrawPassword(HttpServletRequest request, HttpServletResponse response, String withdrawPassword) throws Exception {

        JSONObject json = new JSONObject();

        UserModel currentUser = AppContext.getCurrentUser();
        String userId = currentUser.getId();

        if (currentUser.getWithdrawPassword().equalsIgnoreCase(Md5Util.md5_32(withdrawPassword))) {
            json.put("success", true);
            json.put("msg", "校验成功");
        } else {
            json.put("success", false);
            json.put("msg", "校验失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 查询账户资金
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/queryAccountAmount", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "提现申请", operation = "查询账户资金")
    public void queryAccountAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        UserModel currentUser = AppContext.getCurrentUser();
        String userId = currentUser.getId();

        Map<String, Object> accountAmountInfo = withdrawService.queryAccountAmountInfo(userId);
        double accountAmount = Double.parseDouble(accountAmountInfo.get("account_amount").toString());
        double freezeAmount = Double.parseDouble(accountAmountInfo.get("freeze_amount").toString());
        double withdrawableAmount = DecimalCalculateUtil.sub(accountAmount, freezeAmount);

        Map<String, Object> data = new HashMap<>();
        data.put("accountAmount", accountAmount);
        data.put("freezeAmount", freezeAmount);
        data.put("withdrawableAmount", withdrawableAmount);

        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("data", data);
        writeJson(response, json.toString());

    }


}