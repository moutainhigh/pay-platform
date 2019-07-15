package com.pay.platform.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.push.model.v20160801.*;
import com.aliyuncs.utils.ParameterHelper;
import com.pay.platform.common.socket.handler.AppWebSocketHandler;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;

/**
 * User: zjt
 * DateTime: 2019/7/15 17:41
 */
public class AliPushUtil {

    public static final DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", "LTAIUtjarh3d9lYE", "gU8hW4eJ4AOM22OdHR4jLxJzmdKtIp");
    public static final IAcsClient client = new DefaultAcsClient(profile);

    public static final Long appKey = 27669759L;

    /**
     * 发送获取收款码的消息
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48085.html
     */
    public static void sendGetQrCodeMessage(String nonce , String alias, String secret, String amount) {

        try {

            PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();

            //安全性比较高的内容建议使用HTTPS
            androidRequest.setProtocol(ProtocolType.HTTPS);

            //内容较大的请求，使用POST请求
            androidRequest.setMethod(MethodType.POST);
            androidRequest.setAppKey(appKey);
            androidRequest.setTarget("ACCOUNT");              //指定账号推送; 一台设备只能有一个账号;
            androidRequest.setTargetValue(alias);
            androidRequest.setTitle("消息通知");

            //json内容
            JSONObject reqJson = new JSONObject();
            reqJson.put("nonce" , nonce);
            reqJson.put("messageType", AppWebSocketHandler.MESSAGE_GET_QR_CODE);
            reqJson.put("amount", amount);
            reqJson.put("remarks", "");
            String sign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(reqJson.toString()), secret);
            reqJson.put("sign", sign);
            androidRequest.setBody(reqJson.toString());

            PushMessageToAndroidResponse pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
            System.out.printf("RequestId: %s, MessageId: %s\n", pushMessageToAndroidResponse.getRequestId(), pushMessageToAndroidResponse.getMessageId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}