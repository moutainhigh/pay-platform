package com.pay.platform.common.websocket.service.impl;

import com.pay.platform.common.websocket.config.SocketMessageType;
import com.pay.platform.common.websocket.handler.AppWebSocketHandler;
import com.pay.platform.common.websocket.service.AppWebSocketService;
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
 *
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
                if(user != null){
                    if (codeNum.equals(user.getAttributes().get(AppWebSocketHandler.LOGIN_ID))) {
                        if (user.isOpen()) {
                            return map;
                        }
                    }
                }
            }

        }

        return null;

    }

    /**
     * 发送获取收款码消息
     *
     * @param
     */
    @Override
    public void sendMessageToUser(String codeNum , String message) {

        try {
            appWebSocketHandler.sendMessageToUser(codeNum, new TextMessage(message));
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
            if (user != null && user.isOpen()) {
                if(user.getAttributes().containsKey(AppWebSocketHandler.LOGIN_ID)){
                    String loginId = user.getAttributes().get(AppWebSocketHandler.LOGIN_ID).toString();
                    list.add(loginId);
                }
            }
        }

        return list;

    }

}