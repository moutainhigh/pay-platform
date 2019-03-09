package com.pay.platform.common.enums;

/**
 * User: zjt
 * DateTime: 2019/3/9 15:48
 */
public enum CheckStatusEnum {

    waitCheck("待审核"),
    checkSuccess("审核成功"),
    checkFail("审核失败");

    /**
     * 获取编码
     *
     * @return
     */
    public String getCode() {
        return this.toString();
    }

    /**
     * 获取备注
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 构造函数
     */
    private String msg;

    CheckStatusEnum(String msg) {
        this.msg = msg;
    }

}