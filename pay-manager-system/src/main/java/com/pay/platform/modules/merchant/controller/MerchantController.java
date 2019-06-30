package com.pay.platform.modules.merchant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.modules.agent.model.AgentModel;
import com.pay.platform.modules.agent.service.AgentService;
import com.pay.platform.modules.codeTrader.service.CodeTraderService;
import com.pay.platform.modules.merchant.model.MerchantRateListModel;
import com.pay.platform.modules.merchant.model.MerchantRateModel;
import com.pay.platform.modules.merchant.service.MerchantNotifyService;
import com.pay.platform.modules.merchant.service.MerchantRateService;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.security.CommonRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;

import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantNotifyService merchantNotifyService;

    @Autowired
    private MerchantRateService service;

    @Autowired
    private CodeTraderService codeTraderService;

    @Autowired
    private AgentService agentService;

    /**
     * 分页查询商家列表
     *
     * @param request
     * @param response
     * @param merchant
     * @return
     * @throws Exception
     */
    @ResponseBody
    @CommonRequest
    @RequestMapping(value = "/queryMerchantList", produces = "application/json")
    public PageInfo<MerchantModel> queryMerchantList(HttpServletRequest request, HttpServletResponse response, MerchantModel merchant) throws Exception {

        UserModel user = AppContext.getCurrentUser();
        String[] merchantIds = null;

        //代理管理员
        if (SysUserUtil.isAgentRole(user)) {
            AgentModel agentModel = agentService.queryAgentById(user.getAgentId());
            //一级代理,查询自身的商家以及下级代理的商家
            if ("1".equalsIgnoreCase(agentModel.getLevel())) {
                List<String> merchantIdList = merchantService.queryMerchantIdByAgentId(user.getAgentId(), user.getAgentId());
                if (merchantIdList != null && merchantIdList.size() > 0) {
                    merchantIds = merchantIdList.toArray(new String[merchantIdList.size()]);
                }
            } else {
                merchant.setAgentId(user.getAgentId());                 //二级代理,只查询自身
            }
        }

        //码商管理员：默认可查看到绑定的商家
        if (SysUserUtil.isCodeTraderRole(user)) {
            List<String> merchantIdList = codeTraderService.queryMerchantIdCodeTraderId(user.getCodeTraderId());
            if (merchantIdList != null && merchantIdList.size() > 0) {
                merchantIds = merchantIdList.toArray(new String[merchantIdList.size()]);
            }
        }

        setPageInfo(request);
        return merchantService.queryMerchantList(merchant, merchantIds);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryMerchantById", produces = "application/json")
    public void queryMerchantById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        MerchantModel merchant = merchantService.queryMerchantById(id);
        json.put("success", true);
        json.put("data", merchant);

        writeJson(response, json.toString());

    }

    /**
     * 新增商家
     *
     * @param response
     * @param merchant
     * @throws Exception
     */
    @RequestMapping(value = "/addMerchant", produces = "application/json")
    @SystemControllerLog(module = "商家管理", operation = "新增商家")
    public void addMerchant(HttpServletResponse response, MerchantModel merchant) throws Exception {

        JSONObject json = new JSONObject();

        UserModel userModel = AppContext.getCurrentUser();
        if (!SysUserUtil.isAdminRole(userModel)) {
            json.put("success", false);
            json.put("msg", "只有平台才能新增商户!");
            writeJson(response, json.toString());
            return;
        }

        Integer count = merchantService.addMerchant(merchant);

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
     * 删除商家
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMerchant", produces = "application/json")
    @SystemControllerLog(module = "商家管理", operation = "删除商家")
    public void deleteMerchant(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        UserModel userModel = AppContext.getCurrentUser();
        if (!SysUserUtil.isAdminRole(userModel)) {
            json.put("success", false);
            json.put("msg", "只有平台才能删除商户!");
            writeJson(response, json.toString());
            return;
        }

        Integer count = merchantService.deleteMerchant(ids.split(","));

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
     * 逻辑删除商家 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMerchantByLogic", produces = "application/json")
    @SystemControllerLog(module = "商家管理", operation = "删除商家")
    public void deleteMerchantByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = merchantService.deleteMerchantByLogic(ids.split(","));

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
     * 修改商家信息
     *
     * @param request
     * @param response
     * @param merchant
     * @throws Exception
     */
    @RequestMapping(value = "/updateMerchant", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "商家管理", operation = "修改商家")
    public void updateMerchant(HttpServletRequest request, HttpServletResponse response, MerchantModel merchant) throws Exception {

        JSONObject json = new JSONObject();

        UserModel userModel = AppContext.getCurrentUser();
        if (!SysUserUtil.isAdminRole(userModel)) {
            json.put("success", false);
            json.put("msg", "只有平台才能修改商户!");
            writeJson(response, json.toString());
            return;
        }
        Integer count = merchantService.updateMerchant(merchant);

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
     * 分页查询商家列表
     *
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception CommonRequest 标识为通用请求,任何用户可访问,不进行权限过滤
     */
    @ResponseBody
    @CommonRequest
    @RequestMapping(value = "/queryMerchantIdAndNameList", produces = "application/json")
    public void queryMerchantIdAndNameList(HttpServletRequest request, HttpServletResponse response, String agentId) throws Exception {

        JSONObject json = new JSONObject();

        UserModel user = AppContext.getCurrentUser();

        List<Map<String, Object>> merchantIdList = null;

        //mybatis分页排序此处有bug,会导致数据库没有该排序字段,拼接sql语句出错; 此处直接去除排序字段
        PageHelper.offsetPage(0, Integer.MAX_VALUE);

        //超级管理员可查询所有商家
        if (SysUserUtil.isAdminRole(user)) {
            merchantIdList = merchantService.queryMerchantIdAndNameList(null, agentId, null);
        }
        //码商管理员：默认可查看到绑定的商家
        else if (SysUserUtil.isCodeTraderRole(user)) {
            String codeTraderId = user.getCodeTraderId();
            List<String> list = codeTraderService.queryMerchantIdCodeTraderId(codeTraderId);
            merchantIdList = merchantService.queryMerchantIdAndNameList(null, agentId, list.toArray(new String[list.size()]));
        }
        //代理管理员可查询下级商家
        else if (SysUserUtil.isAgentRole(user)) {

            AgentModel agentModel = agentService.queryAgentById(user.getAgentId());

            //一级代理,查询自身的商家以及下级代理的商家
            if ("1".equalsIgnoreCase(agentModel.getLevel())) {
                String[] merchantIds = null;
                List<String> list = merchantService.queryMerchantIdByAgentId(user.getAgentId(), user.getAgentId());
                if (list != null && list.size() > 0) {
                    merchantIds = list.toArray(new String[list.size()]);
                }
                merchantIdList = merchantService.queryMerchantIdAndNameList(null, agentId, merchantIds);
            }
            //二级代理,只查询自身的商家
            else {
                merchantIdList = merchantService.queryMerchantIdAndNameList(null, user.getAgentId(), null);
            }

        }
        //商家管理员可以查看当前信息
        else if (SysUserUtil.isMerchantRole(user)) {
            merchantIdList = merchantService.queryMerchantIdAndNameList(user.getMerchantId(), null, null);
        }

        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("merchantIdList", merchantIdList);

        writeJson(response, json.toString());

    }


    /**
     * 审核
     *
     * @param request
     * @param response
     * @param merchant
     * @throws Exception
     */
    @RequestMapping(value = "/review", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "商家管理", operation = "审核商家")
    public void review(HttpServletRequest request, HttpServletResponse response, MerchantModel merchant) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = merchantService.review(merchant);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "修改成功");
        } else {
            json.put("success", false);
            json.put("msg", "修改失败");
        }

        writeJson(response, json.toString());

    }

    @RequestMapping(value = "/queryMerchantRateList", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "查詢商家費率列表")
    public void queryMerchantRateList(HttpServletResponse response, String merchantId) throws Exception {
        JSONObject json = new JSONObject();
        List<MerchantRateListModel> merchantRateListModels = service.queryMerchantRateList(merchantId);
        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("data", merchantRateListModels);
        writeJson(response, json.toString());
    }

    @RequestMapping(value = "/addMerchantRate", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "新增費率")
    public void addMerchantRate(HttpServletResponse response, MerchantRateModel model) throws Exception {

        JSONObject json = new JSONObject();

        //去除百分号,再除以100存储
        String rate = model.getRate().replace("%", "");
        double doubleRate = DecimalCalculateUtil.divForNotRounding(Double.parseDouble(rate), 100);
        model.setRate(String.valueOf(doubleRate));

        Integer count = service.addMerchantRate(model);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "保存成功");
        } else {
            json.put("success", false);
            json.put("msg", "保存失败");
        }

        writeJson(response, json.toString());

    }

    @RequestMapping(value = "/deleteMerchantRate", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "删除费率")
    public void deleteMerchantRate(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        Integer delete = service.deleteMerchantRate(id);

        if (delete == 1) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失敗");
        }

        writeJson(response, json.toString());
    }

    @RequestMapping(value = "/updateMerchantChannelEnabledStatus", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "更新通道启用状态")
    public void updateMerchantChannelEnabledStatus(HttpServletResponse response, String id, String enabled) throws Exception {

        JSONObject json = new JSONObject();

        Integer delete = service.updateMerchantChannelEnabledStatus(id, enabled);

        if (delete == 1) {
            json.put("success", true);
            json.put("msg", "更新成功");
        } else {
            json.put("success", false);
            json.put("msg", "更新失敗");
        }

        writeJson(response, json.toString());
    }

    /**
     * 查询商家金额,用于提醒商家提现
     *
     * @param response
     * @param merchantId
     * @throws Exception
     */
    @CommonRequest
    @RequestMapping(value = "/queryMerchantAmountOfNotifyWithdraw", produces = "application/json")
    @SystemControllerLog(module = "查询商家金额", operation = "查询商家金额提醒商家提现")
    public void queryMerchantAmountOfNotifyWithdraw(HttpServletResponse response, String merchantId) throws Exception {

        JSONObject json = new JSONObject();

        MerchantModel merchantModel = merchantService.queryMerchantById(merchantId);
        if (1 == merchantModel.getNeedNotifyWithdraw()) {

            //1,查询商家累计收款金额
            Map<String, Object> map = merchantService.queryMerchantAmountInfo(merchantId);
            double totalAmount = Double.parseDouble(map.get("totalAmount").toString());

            //2,查询上一次提醒商家提现时的金额
            double lastNotifyAmount = Double.parseDouble(map.get("lastNotifyAmount").toString());

            //每达到10万提醒一次商家提现
            double difference = DecimalCalculateUtil.sub(totalAmount, lastNotifyAmount);
            if (difference > 100000) {
                json.put("success", true);
                json.put("msg", "已达到可提现金额,为了您的资金安全,请及时联系平台财务提现！");

                //记录最后一次提醒的时间及金额
                merchantService.saveMerchantNotifyWithdrawAmount(merchantId, totalAmount);
            }

            writeJson(response, json.toString());

        }

    }

}