package com.pay.platform.common.enums;

/**
 * User: zjt
 * DateTime: 2019/1/14 14:16
 *
 * 支付状态：枚举
 */
public enum PayStatusEnum {

    waitPay("待支付"),
    payed("已支付"),
    payFail("支付失败");

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
    PayStatusEnum(String msg) {
        this.msg = msg;
    }

}