package com.pay.platform.modules.payChannel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.util.DecimalCalculateUtil;
import com.pay.platform.security.CommonRequest;
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

import com.pay.platform.modules.payChannel.model.PayChannelModel;
import com.pay.platform.modules.payChannel.service.PayChannelService;


/**
 * User:
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/payChannel")
public class PayChannelController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PayChannelController.class);

    @Autowired
    private PayChannelService payChannelService;

    /**
     * 分页查询通道列表
     *
     * @param request
     * @param response
     * @param payChannel
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPayChannelList", produces = "application/json")
    public PageInfo<PayChannelModel> queryPayChannelList(HttpServletRequest request, HttpServletResponse response, PayChannelModel payChannel) throws Exception {
        setPageInfo(request);
        return payChannelService.queryPayChannelList(payChannel);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryPayChannelById", produces = "application/json")
    public void queryPayChannelById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        PayChannelModel payChannel = payChannelService.queryPayChannelById(id);
        json.put("success", true);
        json.put("data", payChannel);

        writeJson(response, json.toString());

    }


    /**
     * 新增通道
     *
     * @param response
     * @param payChannel
     * @throws Exception
     */
    @RequestMapping(value = "/addPayChannel", produces = "application/json")
    @SystemControllerLog(module = "通道管理", operation = "新增通道")
    public void addPayChannel(HttpServletResponse response, PayChannelModel payChannel) throws Exception {

        JSONObject json = new JSONObject();

        if (null != payChannelService.queryInfoByChannelCode(payChannel.getChannelCode())) {
            json.put("success", false);
            json.put("msg", "通道编号已经存在");
            writeJson(response, json.toString());
            return;
        }

        //去除百分号,再除以100存储
        String costRate = payChannel.getCostRate().replace("%","");
        double doubleCostRate = DecimalCalculateUtil.divForNotRounding(Double.parseDouble(costRate) , 100);
        payChannel.setCostRate(String.valueOf(doubleCostRate));

        Integer count = payChannelService.addPayChannel(payChannel);

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
     * 删除通道
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deletePayChannel", produces = "application/json")
    @SystemControllerLog(module = "通道管理", operation = "删除通道")
    public void deletePayChannel(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = payChannelService.deletePayChannel(ids.split(","));

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
     * 逻辑删除通道 - 字段为isDel
     * addPayChannel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deletePayChannelByLogic", produces = "application/json")
    @SystemControllerLog(module = "通道管理", operation = "删除通道")
    public void deletePayChannelByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = payChannelService.deletePayChannelByLogic(ids.split(","));

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
     * 修改通道信息
     *
     * @param request
     * @param response
     * @param payChannel
     * @throws Exception
     */
    @RequestMapping(value = "/updatePayChannel", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "通道管理", operation = "修改通道")
    public void updatePayChannel(HttpServletRequest request, HttpServletResponse response, PayChannelModel payChannel) throws Exception {

        JSONObject json = new JSONObject();

        //去除百分号,再除以100存储
        String costRate = payChannel.getCostRate().replace("%","");
        double doubleCostRate = DecimalCalculateUtil.divForNotRounding(Double.parseDouble(costRate) , 100);
        payChannel.setCostRate(String.valueOf(doubleCostRate));

        Integer count = payChannelService.updatePayChannel(payChannel);

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
     * 读取所有的通道费率信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllPayChannelList", produces = "application/json", method = RequestMethod.POST)
    @CommonRequest
    public void queryAllPayChannelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        final List<PayChannelModel> list = payChannelService.queryAllPayChannelList();
        json.put("success", true);
        json.put("data", list);
        writeJson(response, json.toString());
    }

    /**
     * 读取所有的通道费率信息,以及代理费率信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllPayChannelListAndAgentRate", produces = "application/json", method = RequestMethod.POST)
    @CommonRequest
    public void queryAllPayChannelListAndAgentRate(HttpServletRequest request, HttpServletResponse response, String agentId) throws Exception {
        JSONObject json = new JSONObject();
        List<Map<String, Object>> list = payChannelService.queryAllPayChannelListAndAgentRate(agentId);
        json.put("success", true);
        json.put("data", list);
        writeJson(response, json.toString());
    }

}