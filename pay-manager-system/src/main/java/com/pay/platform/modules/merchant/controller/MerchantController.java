package com.pay.platform.modules.merchant.controller;

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

}