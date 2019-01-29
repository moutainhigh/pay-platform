package com.pay.platform.common.enums;

/**
 * User: zjt
 * DateTime: 2019/1/18 21:40
 */
public enum RoleCodeEnum {

    ROLE_ADMIN("超级管理员"),
    ROLE_AGENT("代理管理员"),
    ROLE_MERCHANT("商户管理员");

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

    RoleCodeEnum(String msg) {
        this.msg = msg;
    }

}