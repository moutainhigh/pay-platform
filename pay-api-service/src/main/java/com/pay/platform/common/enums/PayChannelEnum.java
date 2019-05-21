package com.pay.platform.common.enums;

/**
 * User:
 * DateTime: 2019/3/2 22:22
 */
public enum  PayChannelEnum {

    hcZfb("话冲支付宝"),
    hcWechat("话冲微信"),
    lklZfbFixed("lkl支付宝"),
    lklWeChatFixed("lkl微信");

    /**
     * 获取编码
     * @return
     */
    public String getCode() {
        return this.toString();
    }

    /**
     * 获取备注
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 构造函数
     */
    private String msg;
    PayChannelEnum(String msg) {
        this.msg = msg;
    }

}