package com.pay.platform.modules.merchantRate.controller;


import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.merchantRate.model.MerchantRateListModel;
import com.pay.platform.modules.merchantRate.model.MerchantRateModel;
import com.pay.platform.modules.merchantRate.service.MerchantRateService;
import com.pay.platform.modules.payChannel.model.PayChannelModel;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/merchantRate")
public class MerchantRateController extends BaseController {
    @Autowired
    private MerchantRateService service;


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
        json.put("msg", "新增成功");
        json.put("date",merchantRateListModels);
        writeJson(response, json.toString());
    }

}
