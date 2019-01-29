package com.pay.platform.modules.agent.controller;

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

import com.pay.platform.modules.agent.model.AgentModel;
import com.pay.platform.modules.agent.service.AgentService;


/**
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

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

}