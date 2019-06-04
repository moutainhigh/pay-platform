package com.pay.platform.common.socket.service;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
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
     * 发送获取收款码消息
     *
     * @param codeNum
     * @param
     */
    void sendGetQrCodeSocket(String codeNum, String secret, String amount);

}