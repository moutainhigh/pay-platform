package com.pay.platform.modules.phoneConfig.controller;

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

import com.pay.platform.modules.phoneConfig.model.PhoneConfigModel;
import com.pay.platform.modules.phoneConfig.service.PhoneConfigService;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/phoneConfig")
public class PhoneConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneConfigController.class);

    @Autowired
    private PhoneConfigService phoneConfigService;

    /**
     * 分页查询手机校验配置列表
     *
     * @param request
     * @param response
     * @param phoneConfig
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPhoneConfigList", produces = "application/json")
    public PageInfo<PhoneConfigModel> queryPhoneConfigList(HttpServletRequest request, HttpServletResponse response, PhoneConfigModel phoneConfig) throws Exception {
        setPageInfo(request);
        return phoneConfigService.queryPhoneConfigList(phoneConfig);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryPhoneConfigById", produces = "application/json")
    public void queryPhoneConfigById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        PhoneConfigModel phoneConfig = phoneConfigService.queryPhoneConfigById(id);
        json.put("success", true);
        json.put("data", phoneConfig);

        writeJson(response, json.toString());

    }

    /**
     * 新增手机校验配置
     *
     * @param response
     * @param phoneConfig
     * @throws Exception
     */
    @RequestMapping(value = "/addPhoneConfig", produces = "application/json")
    @SystemControllerLog(module = "手机校验配置管理", operation = "新增手机校验配置")
    public void addPhoneConfig(HttpServletResponse response, PhoneConfigModel phoneConfig) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = phoneConfigService.addPhoneConfig(phoneConfig);

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
     * 删除手机校验配置
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deletePhoneConfig", produces = "application/json")
    @SystemControllerLog(module = "手机校验配置管理", operation = "删除手机校验配置")
    public void deletePhoneConfig(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = phoneConfigService.deletePhoneConfig(ids.split(","));

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
     * 逻辑删除手机校验配置 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deletePhoneConfigByLogic", produces = "application/json")
    @SystemControllerLog(module = "手机校验配置管理", operation = "删除手机校验配置")
    public void deletePhoneConfigByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = phoneConfigService.deletePhoneConfigByLogic(ids.split(","));

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
     * 修改手机校验配置信息
     *
     * @param request
     * @param response
     * @param phoneConfig
     * @throws Exception
     */
    @RequestMapping(value = "/updatePhoneConfig", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "手机校验配置管理", operation = "修改手机校验配置")
    public void updatePhoneConfig(HttpServletRequest request, HttpServletResponse response, PhoneConfigModel phoneConfig) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = phoneConfigService.updatePhoneConfig(phoneConfig);

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