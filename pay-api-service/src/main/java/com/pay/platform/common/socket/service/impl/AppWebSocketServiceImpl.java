package com.pay.platform.common.socket.service.impl;

import com.pay.platform.api.pay.lzyh.service.impl.LzyhPayServiceImpl;
import com.pay.platform.common.socket.handler.AppWebSocketHandler;
import com.pay.platform.common.socket.service.AppWebSocketService;
import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.AppSignUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/6/4 15:30
 */
@Service
public class AppWebSocketServiceImpl implements AppWebSocketService{

    private static final Logger logger = LoggerFactory.getLogger(AppWebSocketServiceImpl.class);

    @Autowired
    private AppWebSocketHandler appWebSocketHandler;

    /**
     * 从列表中获取登录在线的号；
     *
     * @return
     */
    @Override
    public Map<String, Object> getOnLineSocket(List<Map<String, Object>> list) {

        for (Map<String, Object> map : list) {
            String codeNum = map.get("code_num").toString();

            //遍历在线的号
            for (WebSocketSession user : AppWebSocketHandler.users) {
                if (codeNum.equals(user.getAttributes().get(AppWebSocketHandler.LOGIN_ID))) {
                    if (user.isOpen()) {
                        return map;
                    }
                }
            }

        }

        return null;

    }

    /**
     * 发送获取收款码消息
     *
     * @param codeNum
     * @param
     */
    @Override
    public void sendGetQrCodeSocket(String codeNum, String secret, String amount) {

        try {

            JSONObject reqJson = new JSONObject();
            reqJson.put("messageType", AppWebSocketHandler.MESSAGE_GET_QR_CODE);
            reqJson.put("amount", amount);
            reqJson.put("remarks", "");

            String sign = AppSignUtil.buildAppSign(JsonUtil.parseToMapString(reqJson.toString()), secret);
            reqJson.put("sign", sign);

            appWebSocketHandler.sendMessageToUser(codeNum, new TextMessage(reqJson.toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取登录在线的设备；
     *
     * @return
     */
    @Override
    public List<String> getOnLineSocketDevice() {

        List<String> list = new ArrayList<>();

        //遍历在线的号
        for (WebSocketSession user : AppWebSocketHandler.users) {
            if (user.isOpen()) {
                if(user.getAttributes().containsKey(AppWebSocketHandler.LOGIN_ID)){
                    String loginId = user.getAttributes().get(AppWebSocketHandler.LOGIN_ID).toString();
                    list.add(loginId);
                }
            }
        }

        return list;

    }

}