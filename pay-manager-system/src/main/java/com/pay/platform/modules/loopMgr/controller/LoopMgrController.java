package com.pay.platform.modules.loopMgr.controller;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.util.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    //接口服务地址
    @Value("${api.server.url}")
    private String apiServerUrl;

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
     * 分页查询交易码-成功率
     *
     * @param request
     * @param response
     * @param tradeCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryTradeCodeSuccessRateList", produces = "application/json")
    public void queryTradeCodeSuccessRateList(HttpServletRequest request, HttpServletResponse response, TradeCodeModel tradeCode, String beginTime, String endTime) throws Exception {

        JSONObject json = new JSONObject();

        //查询总成功率(根据商编、通道、时间)
        Map<String, Object> successRate = tradeCodeService.queryTradeSuccessRate(tradeCode.getMerchantId(), tradeCode.getChannelId(), beginTime, endTime);

        //查询每个号的成功率
        setPageInfo(request);
        PageInfo<Map<String, Object>> pageInfo = tradeCodeService.queryTradeCodeSuccessRateList(tradeCode, beginTime, endTime);


        Map<String, Object> data = new HashMap<>();
        data.put("pageInfo", pageInfo);
        data.put("successRate", successRate);

        json.put("success", true);
        json.put("data", data);
        writeJson(response, json.toString());

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
        if (existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())) {
            json.put("success", false);
            json.put("msg", "新增失败: 编号不可重复");
            writeJson(response, json.toString());
            return;
        }
        if (existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())) {
            json.put("success", false);
            json.put("msg", "新增失败: 设备回调密钥不可重复");
            writeJson(response, json.toString());
            return;
        }
        //同一个支付通道,不可出现同一个登录账号重复的码
        if (existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())) {
            if (existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())) {
                json.put("success", false);
                json.put("msg", "新增失败: 登录账号不可重复");
                writeJson(response, json.toString());
                return;
            }
        }
//        if (existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())) {
//            json.put("success", false);
//            json.put("msg", "新增失败: 收款码链接不可重复");
//            writeJson(response, json.toString());
//            return;
//        }

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
        if (existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())) {
            json.put("success", false);
            json.put("msg", "新增失败: 编号不可重复");
            writeJson(response, json.toString());
            return;
        }
        if (existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())) {
            json.put("success", false);
            json.put("msg", "新增失败: 设备回调密钥不可重复");
            writeJson(response, json.toString());
            return;
        }
        //同一个支付通道,不可出现同一个登录账号重复的码
        if (existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())) {
            if (existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())) {
                json.put("success", false);
                json.put("msg", "新增失败: 登录账号不可重复");
                writeJson(response, json.toString());
                return;
            }
        }
//        if (existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())) {
//            json.put("success", false);
//            json.put("msg", "新增失败: 收款码链接不可重复");
//            writeJson(response, json.toString());
//            return;
//        }

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

    /**
     * 获取上传excel内容：校验并给前端回显
     *
     * @param request
     * @param response
     * @param excelPath
     * @throws Exception
     */
    @RequestMapping(value = "/getImportTradeCodeExcelList", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "交易码管理", operation = "获取上传excel内容")
    public void getImportTradeCodeExcelList(HttpServletRequest request, HttpServletResponse response, String channelCode, String excelPath) throws Exception {

        JSONObject json = new JSONObject();

        if (StringUtil.isEmpty(excelPath)) {
            json.put("success", false);
            json.put("msg", "excelPath不可为空！");
            writeJson(response, json.toString());
            return;
        }

        //解析excel
        String filePath = request.getSession().getServletContext().getRealPath(excelPath);

        File file = new File(filePath);

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("编号");
        fieldNameList.add("回调密钥");
        fieldNameList.add("登录账号");
        fieldNameList.add("收款链接");

        List<Map<String, String>> list = ExcelUtil.parseExcel(file, fieldNameList);

        //校验是否在数据库重复
        for (Map<String, String> map : list) {
            String codeNum = map.get("编号");
            String secret = map.get("回调密钥");
            String loginAccount = map.get("登录账号");
            String codeLink = map.get("收款链接");

            TradeCodeModel tradeCode = new TradeCodeModel();
            tradeCode.setCodeNum(codeNum);
            tradeCode.setSecret(secret);
            tradeCode.setLoginAccount(loginAccount);
            tradeCode.setCodeLink(codeLink);
            tradeCode.setChannelCode(channelCode);
            TradeCodeModel existsModel = tradeCodeService.queryExistsByCode(tradeCode);

            map.put("exists", "false");        //默认情况初始化未存在
            map.put("msg", "字段有效,未重复！");

            if (existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())) {
                map.put("exists", "true");
                map.put("msg", "编号已存在数据库,请更换！");
            }

            if (existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())) {
                map.put("exists", "true");
                map.put("msg", "设备回调密钥已存在数据库,请更换！");
            }

            //同一个支付通道,不可出现同一个登录账号重复的码
            if (existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())) {
                if (existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())) {
                    map.put("exists", "true");
                    map.put("msg", "登录账号已存在数据库,请更换！");
                }
            }

//            if (existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())) {
//                map.put("exists", "true");
//                map.put("msg", "收款码链接已存在数据库,请更换！");
//            }

        }

        //校验List是否有重复字段
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);

            //遍历整个list,查询是否重复
            for (int j = 0; j < list.size(); j++) {

                //避免跟自身比较
                if (i != j) {
                    Map<String, String> compareMap = list.get(j);

                    if (map.get("编号").equalsIgnoreCase(compareMap.get("编号"))) {
                        map.put("exists", "true");
                        map.put("msg", "编号重复,请检查excel表格！");
                    }
                    if (map.get("回调密钥").equalsIgnoreCase(compareMap.get("回调密钥"))) {
                        map.put("exists", "true");
                        map.put("msg", "回调密钥重复,请检查excel表格！");
                    }
                    if (map.get("登录账号").equalsIgnoreCase(compareMap.get("登录账号"))) {
                        map.put("exists", "true");
                        map.put("msg", "登录账号重复,请检查excel表格！");
                    }
//                    if (map.get("收款链接").equalsIgnoreCase(compareMap.get("收款链接"))) {
//                        map.put("exists", "true");
//                        map.put("msg", "收款链接重复,请检查excel表格！");
//                    }

                }

            }

        }

        json.put("success", true);
        json.put("rows", list);
        json.put("total", list.size());

        writeJson(response, json.toString());

    }


    /**
     * 批量保存收款码
     *
     * @param request
     * @param response
     * @param excelPath
     * @throws Exception
     */
    @RequestMapping(value = "/batchImportTradeCode", produces = "application/json", method = RequestMethod.POST)
    @SystemControllerLog(module = "交易码管理", operation = "批量保存收款码")
    public void batchImportTradeCode(HttpServletRequest request, HttpServletResponse response, String merchantId, String channelCode, String excelPath) throws Exception {

        JSONObject json = new JSONObject();

        if (StringUtil.isEmpty(merchantId) || StringUtil.isEmpty(channelCode) || StringUtil.isEmpty(excelPath)) {
            json.put("success", false);
            json.put("msg", "参数不完整！");
            writeJson(response, json.toString());
            return;
        }

        //解析excel
        String filePath = request.getSession().getServletContext().getRealPath(excelPath);
        File file = new File(filePath);
        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("编号");
        fieldNameList.add("回调密钥");
        fieldNameList.add("登录账号");
        fieldNameList.add("收款链接");
        List<Map<String, String>> list = ExcelUtil.parseExcel(file, fieldNameList);

        //加到集合中,便于后续插入
        List<TradeCodeModel> tradeCodeList = new ArrayList<>();
        PayChannelModel payChannelModel = payChannelService.queryInfoByChannelCode(channelCode);
        for (Map<String, String> map : list) {
            String codeNum = map.get("编号");
            String secret = map.get("回调密钥");
            String loginAccount = map.get("登录账号");
            String codeLink = map.get("收款链接");

            TradeCodeModel tradeCode = new TradeCodeModel();
            tradeCode.setMerchantId(merchantId);
            tradeCode.setChannelId(payChannelModel.getId());
            tradeCode.setChannelCode(channelCode);
            tradeCode.setCodeNum(codeNum);
            tradeCode.setSecret(secret);
            tradeCode.setLoginAccount(loginAccount);
            tradeCode.setCodeLink(codeLink);

            tradeCodeList.add(tradeCode);
        }

        //校验是否在数据库重复
        for (TradeCodeModel tradeCode : tradeCodeList) {

            TradeCodeModel existsModel = tradeCodeService.queryExistsByCode(tradeCode);

            if (existsModel != null && existsModel.getCodeNum().equalsIgnoreCase(tradeCode.getCodeNum())) {
                json.put("success", false);
                json.put("msg", "编号已存在数据库,请更换！");
                writeJson(response, json.toString());
                return;
            }

            if (existsModel != null && existsModel.getSecret().equalsIgnoreCase(tradeCode.getSecret())) {
                json.put("success", false);
                json.put("msg", "设备回调密钥已存在数据库,请更换！");
                writeJson(response, json.toString());
                return;
            }

            //同一个支付通道,不可出现同一个登录账号重复的码
            if (existsModel != null && existsModel.getChannelCode().equalsIgnoreCase(tradeCode.getChannelCode())) {
                if (existsModel.getLoginAccount().equalsIgnoreCase(tradeCode.getLoginAccount())) {
                    json.put("success", false);
                    json.put("msg", "登录账号已存在数据库,请更换！");
                    writeJson(response, json.toString());
                    return;
                }
            }

//            if (existsModel != null && existsModel.getCodeLink().equalsIgnoreCase(tradeCode.getCodeLink())) {
//                json.put("success", false);
//                json.put("msg", "收款码链接已存在数据库,请更换！");
//                writeJson(response, json.toString());
//                return;
//            }

        }

        //校验List自身是否有重复字段
        for (int i = 0; i < tradeCodeList.size(); i++) {
            TradeCodeModel tradeCode = tradeCodeList.get(i);

            //遍历整个list,查询是否重复
            for (int j = 0; j < tradeCodeList.size(); j++) {

                //避免比较到自身
                if (i != j) {
                    TradeCodeModel compareTradeCode = tradeCodeList.get(j);

                    if (tradeCode.getCodeNum().equalsIgnoreCase(compareTradeCode.getCodeNum())) {
                        json.put("success", false);
                        json.put("msg", "编号重复,请检查excel表格！");
                        writeJson(response, json.toString());
                        return;
                    }
                    if (tradeCode.getSecret().equalsIgnoreCase(compareTradeCode.getSecret())) {
                        json.put("success", false);
                        json.put("msg", "回调密钥重复,请检查excel表格！");
                        writeJson(response, json.toString());
                        return;
                    }
                    if (tradeCode.getLoginAccount().equalsIgnoreCase(compareTradeCode.getLoginAccount())) {
                        json.put("success", false);
                        json.put("msg", "登录账号重复,请检查excel表格！");
                        writeJson(response, json.toString());
                        return;
                    }
//                    if (tradeCode.getCodeLink().equalsIgnoreCase(compareTradeCode.getCodeLink())) {
//                        json.put("success", false);
//                        json.put("msg", "收款链接重复,请检查excel表格！");
//                        writeJson(response, json.toString());
//                        return;
//                    }

                }

            }

        }

        Integer count = tradeCodeService.batchAddTradeCode(tradeCodeList);

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
     * 更新启用状态
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/updateTradeCodeEnabled", produces = "application/json")
    @SystemControllerLog(module = "交易码管理", operation = "更新启用状态")
    public void updateTradeCodeEnabled(HttpServletResponse response, String ids, String enabled) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = tradeCodeService.updateTradeCodeEnabled(ids.split(","), enabled);

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
     * 测试支付
     *
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/testPay", produces = "application/json")
    @SystemControllerLog(module = "交易码管理", operation = "测试支付")
    public void testPay(HttpServletResponse response, String codeNum, String channelCode, String amount) throws Exception {

        JSONObject json = new JSONObject();

        Map<String, Object> merchantInfo = tradeCodeService.queryMerchantInfoByTradeCode(codeNum);
        String merchantNo = merchantInfo.get("merchantNo").toString();
        String merchantSecret = merchantInfo.get("merchantSecret").toString();

        //1、完成下单请求
        Map<String, String> params = new HashMap();
        params.put("merchantNo", merchantNo);
        params.put("merchantOrderNo", "test" + OrderNoUtil.getOrderNoByUUId());
        params.put("orderAmount", amount);                      //支付金额
        params.put("payWay", channelCode);                      //支付类型
        params.put("notifyUrl", apiServerUrl + "/openApi/testMerchantNotify");
        params.put("returnUrl", "");
        params.put("clientIp", "");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));
        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(apiServerUrl + "/api/unifiedCreateOrder", jsonStr);
        if (StringUtil.isEmpty(result)) {
            json.put("success", false);
            json.put("msg", "下单失败");
            writeJson(response, json.toString());
            return;
        }
        JSONObject respJson = new JSONObject(result);
        if (!"1".equalsIgnoreCase(respJson.getString("status"))) {
            json.put("success", false);
            json.put("msg", respJson.getString("msg"));
            writeJson(response, json.toString());
            return;
        }

        //2、获取支付链接
        String tradeId = respJson.getString("data");
        params = new HashMap<>();
        params.put("tradeId", tradeId);
        params.put("merchantNo", merchantNo);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));
        jsonStr = JsonUtil.parseToJsonStr(params);
        result = HttpClientUtil.doPost(apiServerUrl + "/api/getPayLink", jsonStr);
        if (StringUtil.isEmpty(result)) {
            json.put("success", false);
            json.put("msg", "下单失败");
            writeJson(response, json.toString());
            return;
        }

        respJson = new JSONObject(result);
        if (!"1".equalsIgnoreCase(respJson.getString("status"))) {
            json.put("success", false);
            json.put("msg", respJson.getString("msg"));
            writeJson(response, json.toString());
            return;
        }

        json.put("success", true);
        json.put("msg", "下单成功");
        json.put("data", respJson.getString("data"));
        writeJson(response, json.toString());

    }

}