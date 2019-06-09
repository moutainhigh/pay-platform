package com.pay.platform.modules.codeTrader.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.merchant.model.MerchantModel;
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

import com.pay.platform.modules.codeTrader.model.CodeTraderModel;
import com.pay.platform.modules.codeTrader.service.CodeTraderService;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/codeTrader")
public class CodeTraderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CodeTraderController.class);

    @Autowired
    private CodeTraderService codeTraderService;

    /**
     * 分页查询码商列表
     *
     * @param request
     * @param response
     * @param codeTrader
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryCodeTraderList", produces = "application/json")
    public PageInfo<CodeTraderModel> queryCodeTraderList(HttpServletRequest request, HttpServletResponse response, CodeTraderModel codeTrader) throws Exception {
        setPageInfo(request);
        return codeTraderService.queryCodeTraderList(codeTrader);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryCodeTraderById", produces = "application/json")
    public void queryCodeTraderById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        CodeTraderModel codeTrader = codeTraderService.queryCodeTraderById(id);
        json.put("success", true);
        json.put("data", codeTrader);

        writeJson(response, json.toString());

    }

    /**
     * 新增码商
     *
     * @param response
     * @param codeTrader
     * @throws Exception
     */
    @RequestMapping(value = "/addCodeTrader", produces = "application/json")
    @SystemControllerLog(module = "码商管理", operation = "新增码商")
    public void addCodeTrader(HttpServletResponse response, String account, String password, CodeTraderModel codeTrader) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = codeTraderService.addCodeTrader(account, password, codeTrader);

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
     * 删除码商
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCodeTrader", produces = "application/json")
    @SystemControllerLog(module = "码商管理", operation = "删除码商")
    public void deleteCodeTrader(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = codeTraderService.deleteCodeTrader(ids.split(","));

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
     * 逻辑删除码商 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCodeTraderByLogic", produces = "application/json")
    @SystemControllerLog(module = "码商管理", operation = "删除码商")
    public void deleteCodeTraderByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = codeTraderService.deleteCodeTraderByLogic(ids.split(","));

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
     * 修改码商信息
     *
     * @param request
     * @param response
     * @param codeTrader
     * @throws Exception
     */
    @RequestMapping(value = "/updateCodeTrader", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "码商管理", operation = "修改码商")
    public void updateCodeTrader(HttpServletRequest request, HttpServletResponse response, CodeTraderModel codeTrader) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = codeTraderService.updateCodeTrader(codeTrader);

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
     * 查询所有码商
     *
     * @param request
     * @param response
     * @param merchantId
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllCodeTraderByMerchantId", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "码商管理", operation = "查询所有码商")
    public PageInfo<Map> queryAllCodeTraderByMerchantId(HttpServletRequest request, HttpServletResponse response, String merchantId) throws Exception {
        setPageInfo(request);
        return codeTraderService.queryAllCodeTraderByMerchantId(merchantId);
    }

    /**
     * 商家绑定码商
     *
     * @param request
     * @param response
     * @param merchantId
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/addMerchantCodeTrader", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "码商管理", operation = "绑定码商")
    public void addMerchantCodeTrader(HttpServletRequest request, HttpServletResponse response, String codeTraderId , String merchantId) throws Exception {

        JSONObject json = new JSONObject();

        int count = codeTraderService.addMerchantCodeTrader(codeTraderId , merchantId);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "绑定成功");
        } else {
            json.put("success", false);
            json.put("msg", "绑定失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 商家取消绑定码商
     *
     * @param request
     * @param response
     * @param merchantId
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMerchantCodeTrader", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "码商管理", operation = "商家取消绑定码商")
    public void deleteMerchantCodeTrader(HttpServletRequest request, HttpServletResponse response, String codeTraderId , String merchantId) throws Exception {

        JSONObject json = new JSONObject();

        int count = codeTraderService.deleteMerchantCodeTrader(codeTraderId , merchantId);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "取消绑定成功");
        } else {
            json.put("success", false);
            json.put("msg", "取消绑定失败");
        }

        writeJson(response, json.toString());

    }

}