package com.pay.platform.common.util;

/**
 * User: zjt
 * DateTime: 2019/3/12 16:59
 */
public class ValidateResult {

    private boolean success;
    private String msg;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}