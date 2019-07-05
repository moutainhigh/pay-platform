package com.pay.platform.modules.agent.controller;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.common.util.SysUserUtil;
import com.pay.platform.modules.agent.model.AgentModel;
import com.pay.platform.modules.agent.model.AgentRateListModel;
import com.pay.platform.modules.agent.model.AgentRateModel;
import com.pay.platform.modules.agent.service.AgentRateService;
import com.pay.platform.modules.agent.service.AgentService;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.merchant.model.MerchantModel;
import com.pay.platform.modules.merchant.service.MerchantService;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRateService agentRateService;

    @Autowired
    private MerchantService merchantService;

    /**
     * 分页查询代理列表
     *
     * @param request
     * @param response
     * @param agent
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryAgentList", produces = "application/json")
    public PageInfo<AgentModel> queryAgentList(HttpServletRequest request, HttpServletResponse response, AgentModel agent) throws Exception {
        setPageInfo(request);
        return agentService.queryAgentList(agent);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryAgentById", produces = "application/json")
    public void queryAgentById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        AgentModel agent = agentService.queryAgentById(id);
        json.put("success", true);
        json.put("data", agent);

        writeJson(response, json.toString());

    }

    /**
     * 新增代理
     *
     * @param response
     * @param agent
     * @throws Exception
     */
    @RequestMapping(value = "/addAgent", produces = "application/json")
    @SystemControllerLog(module = "代理管理", operation = "新增代理")
    public void addAgent(HttpServletResponse response, AgentModel agent) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = agentService.addAgent(agent);

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
     * 删除代理
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAgent", produces = "application/json")
    @SystemControllerLog(module = "代理管理", operation = "删除代理")
    public void deleteAgent(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = agentService.deleteAgent(ids.split(","));

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
     * 逻辑删除代理 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAgentByLogic", produces = "application/json")
    @SystemControllerLog(module = "代理管理", operation = "删除代理")
    public void deleteAgentByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = agentService.deleteAgentByLogic(ids.split(","));

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
     * 修改代理信息
     *
     * @param request
     * @param response
     * @param agent
     * @throws Exception
     */
    @RequestMapping(value = "/updateAgent", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "代理管理", operation = "修改代理")
    public void updateAgent(HttpServletRequest request, HttpServletResponse response, AgentModel agent) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = agentService.updateAgent(agent);

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
     * 分页查询代理列表
     *
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception CommonRequest 标识为通用请求,任何用户可访问,不进行权限过滤
     */
    @ResponseBody
    @CommonRequest
    @RequestMapping(value = "/queryAgentIdAndNameList", produces = "application/json")
    public void queryAgentIdAndNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        UserModel user = AppContext.getCurrentUser();

        List<Map<String, Object>> agentIdList = null;

        //超级管理员可查询所有代理信息
        if (SysUserUtil.isAdminRole(user)) {
            agentIdList = agentService.queryAgentIdAndNameList(null);
        }
        //代理管理员
        else if (SysUserUtil.isAgentRole(user)) {

            agentIdList = agentService.queryAgentIdAndNameList(user.getAgentId());

            //一级代理除了自身,还可查询下级的代理信息
            AgentModel agentModel = agentService.queryAgentById(user.getAgentId());
            if ("1".equalsIgnoreCase(agentModel.getLevel())) {
                List<Map<String,Object>> childAgentList = agentService.queryAgentIdAndNameByParentId(user.getAgentId());
                agentIdList.addAll(childAgentList);
            }

        }
        //商家管理员：只能查询上级的代理
        else if (SysUserUtil.isMerchantRole(user)) {
            MerchantModel merchantModel = merchantService.queryMerchantById(user.getMerchantId());
            agentIdList = agentService.queryAgentIdAndNameList(merchantModel.getAgentId());
        }

        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("agentIdList", agentIdList);

        writeJson(response, json.toString());

    }

    /**
     * 查询代理费率列表
     *
     * @param response
     * @param agentId
     * @throws Exception
     */
    @RequestMapping(value = "/queryAgentRateList", produces = "application/json")
    @SystemControllerLog(module = "代理費率", operation = "查询代理费率列表")
    public void queryAgentRateList(HttpServletResponse response, String agentId) throws Exception {
        JSONObject json = new JSONObject();
        List<AgentRateListModel> AgentRateListModels = agentRateService.queryAgentRateList(agentId);
        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("data", AgentRateListModels);
        writeJson(response, json.toString());
    }

    /**
     * 新增代理费率
     *
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "/addAgentRate", produces = "application/json")
    @SystemControllerLog(module = "代理費率", operation = "新增費率")
    public void addAgentRate(HttpServletResponse response, AgentRateModel model) throws Exception {

        JSONObject json = new JSONObject();

        //去除百分号,再除以100存储
        String rate = model.getRate().replace("%", "");
        double doubleRate = DecimalCalculateUtil.divForNotRounding(Double.parseDouble(rate), 100);
        model.setRate(String.valueOf(doubleRate));

        //二级代理费率校验
        AgentModel agentModel = agentService.queryAgentById(model.getAgentId());
        if ("2".equalsIgnoreCase(agentModel.getLevel())) {
            double parentAgentRate = agentRateService.queryParentAgentRate(agentModel.getParentId(), model.getChannelId());
            if (doubleRate < parentAgentRate) {
                json.put("success", false);
                json.put("msg", "不得低于上级费率：" + DecimalCalculateUtil.mul(parentAgentRate, 100) + "%");
                writeJson(response, json.toString());
                return;
            }
        }

        Integer count = agentRateService.addAgentRate(model);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "保存成功");
        } else {
            json.put("success", false);
            json.put("msg", "保存失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 删除代理费率
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAgentRate", produces = "application/json")
    @SystemControllerLog(module = "代理費率", operation = "删除费率")
    public void deleteAgentRate(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        Integer delete = agentRateService.deletAgentRate(id);
        if (delete == 1) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失敗");
        }

        writeJson(response, json.toString());
    }


    /**
     * 查询一级代理信息
     *
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception CommonRequest 标识为通用请求,任何用户可访问,不进行权限过滤
     */
    @ResponseBody
    @CommonRequest
    @RequestMapping(value = "/queryOneLevelAgent", produces = "application/json")
    public void queryOneLevelAgent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        List<Map<String, Object>> agentIdList = agentService.queryOneLevelAgent();
        json.put("success", true);
        json.put("msg", "查询成功");
        json.put("data", agentIdList);
        writeJson(response, json.toString());

    }

}