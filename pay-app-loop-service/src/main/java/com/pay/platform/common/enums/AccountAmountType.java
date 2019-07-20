package com.pay.platform.common.enums;

/**
 * User:
 * DateTime: 2019/2/11 14:44
 *
 * 账户余额 - 操作类型
 */
public enum  AccountAmountType {

    paySuccess("收款成功"),                         //增加账户余额
    withdrawSuccess("提现成功");                    //扣减账户余额

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
    AccountAmountType(String msg) {
        this.msg = msg;
    }

}