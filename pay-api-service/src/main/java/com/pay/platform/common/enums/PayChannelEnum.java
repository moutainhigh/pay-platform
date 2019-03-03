package com.pay.platform.common.enums;

/**
 * User: zjt
 * DateTime: 2019/3/2 22:22
 */
public enum  PayChannelEnum {

    JU_FU_BAO_CHARGE("聚福宝话冲");

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