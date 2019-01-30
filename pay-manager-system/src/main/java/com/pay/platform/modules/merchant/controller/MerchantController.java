package com.pay.platform.modules.merchant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.modules.merchant.model.MerchantRateListModel;
import com.pay.platform.modules.merchant.model.MerchantRateModel;
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
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantRateService service;

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
    @RequestMapping(value = "/queryMerchantList", produces = "application/json")
    public PageInfo<MerchantModel> queryMerchantList(HttpServletRequest request, HttpServletResponse response, MerchantModel merchant) throws Exception {
        setPageInfo(request);
        return merchantService.queryMerchantList(merchant);
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
        if (!SysUserUtil.isAgentRole(userModel)) {
            json.put("success", false);
            json.put("msg", "只有代理才能新增商户!");
            writeJson(response, json.toString());
            return;
        }

        merchant.setAgentId(userModel.getAgentId());
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
    public void queryMerchantIdAndNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        UserModel user = AppContext.getCurrentUser();

        List<Map<String, Object>> merchantIdList = null;

        //超级管理员可查询所有商家
        if (SysUserUtil.isAdminRole(user)) {
            merchantIdList = merchantService.queryMerchantIdAndNameList(null, null);
        }
        //代理管理员可查询下级商家
        else if (SysUserUtil.isAgentRole(user)) {
            merchantIdList = merchantService.queryMerchantIdAndNameList(null, user.getAgentId());
        }
        //商家管理员可以查看当前信息
        else if (SysUserUtil.isMerchantRole(user)) {
            merchantIdList = merchantService.queryMerchantIdAndNameList(user.getMerchantId(), null);
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


    @RequestMapping(value = "/add", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "新增費率")
    public void addPayChannel(HttpServletResponse response, MerchantRateModel model) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = service.add(model);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "新增成功");
        } else {
            json.put("success", false);
            json.put("msg", "新增失败");
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

    @RequestMapping(value = "/delete", produces = "application/json")
    @SystemControllerLog(module = "商家費率", operation = "删除费率")
    public void delete(HttpServletResponse response, String id) throws Exception {
        JSONObject json = new JSONObject();
        final Integer delete = service.delete(id);
        if (delete == 1) {
            json.put("success", true);
            json.put("msg", "查询成功");
        } else {
            json.put("success", false);
            json.put("msg", "查询失敗");
        }
        writeJson(response, json.toString());
    }

}