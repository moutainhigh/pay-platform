package com.pay.platform.common.websocket.service;

import java.util.List;
import java.util.Map;

/**
 *
 * DateTime: 2019/6/4 15:30
 */
public interface AppWebSocketService {

    /**
     * 从列表中获取登录在线的号；
     *
     * @return
     */
    Map<String, Object> getOnLineSocket(List<Map<String, Object>> list);

    /**
     * 发送消息
     *
     * @param codeNum
     * @param
     */
    void sendMessageToUser(String codeNum , String message);

    /**
     * 获取登录在线的设备
     * @return
     */
    List<String> getOnLineSocketDevice();
}