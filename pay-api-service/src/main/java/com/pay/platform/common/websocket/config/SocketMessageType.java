package com.pay.platform.common.websocket.config;

/**
 *
 * DateTime: 2019/7/17 15:42
 */
public class SocketMessageType {

    /**
     * 消息类型配置
     */
    public static final String MESSAGE_LOGIN = "MESSAG_LOGIN";                           //登录
    public static final String MESSAGE_UPLOAD_QR_CODE = "MESSAG_UPLOAD_QR_CODE";         //上传收款码
    public static final String MESSAGE_GET_QR_CODE = "MESSAG_GET_QR_CODE";               //获取收款码

    //心跳检测包
    public static final String MESSAGE_HEARTBEAT = "MESSAGE_HEARTBEAT";

}