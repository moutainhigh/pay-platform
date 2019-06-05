package com.baseframe.generate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pay.platform.common.util.*;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/6/2 21:48
 */
public class PayNotifyUtil {

    /**
     * 发送支付回调请求
     *
     * @return
     */
    public static void sendLzyhPayNotifyApi(String amount, String payCode, String payTime) {

        try {

            //从SharedPreferences获取
            String codeNum = "";
            String secret = "";
            String notifyServerURL = "";

            Map<String, String> params = new HashMap();
            params.put("codeNum", codeNum);
            params.put("amount", amount);
            params.put("payCode", payCode);
            params.put("payTime", payTime);
            params.put("timestamp", System.currentTimeMillis() + "");
            params.put("sign", AppSignUtil.buildAppSign(params, secret).trim());

            String jsonStr = JsonUtil.parseToJsonStr(params);
            String result = HttpClientUtil.doPost(notifyServerURL + "/app/payNotifyByLzyh", jsonStr);
            if (StringUtil.isEmpty(result)) {
                //输出日志
                return;
            }

            JSONObject responseJson = new JSONObject(result);

            //回调成功后,更新本地sqlite订单支付状态
            if (1 == responseJson.getInt("status")) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}