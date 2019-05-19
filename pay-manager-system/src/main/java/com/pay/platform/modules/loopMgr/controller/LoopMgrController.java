package com.pay.platform.modules.loopMgr.controller;

import com.github.pagehelper.PageInfo;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.loopMgr.model.TradeCodeModel;
import com.pay.platform.modules.loopMgr.service.LoopMgrService;
import com.pay.platform.modules.payChannel.model.PayChannelModel;
import com.pay.platform.modules.payChannel.service.PayChannelService;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
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


/**
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("/loopMgr/tradeCode")
public class LoopMgrController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoopMgrController.class);

    @Autowired
    private LoopMgrService tradeCodeService;

    @Autowired
    private PayChannelService payChannelService;

    /**
     * 分页查询交易码列表
     *
     * @param request
     * @param response
     * @param tradeCode
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryTradeCodeList", produces = "application/json")
    public PageInfo<TradeCodeModel> queryTradeCodeList(HttpServletRequest request, HttpServletResponse response, TradeCodeModel tradeCode) throws Exception {
        setPageInfo(request);
        return tradeCodeService.queryTradeCodeList(tradeCode);
    }

    /**
     * 根据id查询
     *
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryTradeCodeById", produces = "application/json")
    public void queryTradeCodeById(HttpServletResponse response, String id) throws Exception {

        JSONObject json = new JSONObject();

        TradeCodeModel tradeCode = tradeCodeService.queryTradeCodeById(id);
        json.put("success", true);
        json.put("data", tradeCode);

        writeJson(response, json.toString());

    }

    /**
     * 新增交易码
     *
     * @param response
     * @param tradeCode
     * @throws Exception
     */
    @RequestMapping(value = "/addTradeCode", produces = "application/json")
    @SystemControllerLog(module = "交易码管理", operation = "新增交易码")
    public void addTradeCode(HttpServletResponse response, TradeCodeModel tradeCode) throws Exception {

        JSONObject json = new JSONObject();

        PayChannelModel payChannelModel = payChannelService.queryInfoByChannelCode(tradeCode.getChannelCode());
        tradeCode.setChannelId(payChannelModel.getId());

        TradeCodeModel existsModel = tradeCodeService.queryExistsByCode(tradeCode);
        if(existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())){
            json.put("success", false);
            json.put("msg", "新增失败: 编号不可重复");
            writeJson(response, json.toString());
            return;
        }
        if(existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())){
            json.put("success", false);
            json.put("msg", "新增失败: 设备回调密钥不可重复");
            writeJson(response, json.toString());
            return;
        }
        //同一个支付通道,不可出现同一个登录账号重复的码
        if(existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())){
            if(existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())){
                json.put("success", false);
                json.put("msg", "新增失败: 登录账号不可重复");
                writeJson(response, json.toString());
                return;
            }
        }
        if(existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())){
            json.put("success", false);
            json.put("msg", "新增失败: 收款码链接不可重复");
            writeJson(response, json.toString());
            return;
        }

        Integer count = tradeCodeService.addTradeCode(tradeCode);

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
     * 删除交易码
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteTradeCode", produces = "application/json")
    @SystemControllerLog(module = "交易码管理", operation = "删除交易码")
    public void deleteTradeCode(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = tradeCodeService.deleteTradeCode(ids.split(","));

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
     * 逻辑删除交易码 - 字段为isDel
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteTradeCodeByLogic", produces = "application/json")
    @SystemControllerLog(module = "交易码管理", operation = "删除交易码")
    public void deleteTradeCodeByLogic(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = tradeCodeService.deleteTradeCodeByLogic(ids.split(","));

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
     * 修改交易码信息
     *
     * @param request
     * @param response
     * @param tradeCode
     * @throws Exception
     */
    @RequestMapping(value = "/updateTradeCode", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "交易码管理", operation = "修改交易码")
    public void updateTradeCode(HttpServletRequest request, HttpServletResponse response, TradeCodeModel tradeCode) throws Exception {

        JSONObject json = new JSONObject();

        TradeCodeModel existsModel = tradeCodeService.queryExistsByCode(tradeCode);
        if(existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())){
            json.put("success", false);
            json.put("msg", "新增失败: 编号不可重复");
            writeJson(response, json.toString());
            return;
        }
        if(existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())){
            json.put("success", false);
            json.put("msg", "新增失败: 设备回调密钥不可重复");
            writeJson(response, json.toString());
            return;
        }
        //同一个支付通道,不可出现同一个登录账号重复的码
        if(existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())){
            if(existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())){
                json.put("success", false);
                json.put("msg", "新增失败: 登录账号不可重复");
                writeJson(response, json.toString());
                return;
            }
        }
        if(existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())){
            json.put("success", false);
            json.put("msg", "新增失败: 收款码链接不可重复");
            writeJson(response, json.toString());
            return;
        }

        Integer count = tradeCodeService.updateTradeCode(tradeCode);

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