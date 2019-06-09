package com.pay.platform.common.enums;

/**
 * User:
 * DateTime: 2019/3/9 15:41
 */
public enum WithdrawStatusEnum {

    withdrawApply("提现申请处理中"),                         //提现申请-增加冻结资金
    withdrawSuccess("提现成功"),                       //提现成功,扣减冻结资金和账户余额
    withdrawFail("提现失败");                          //提现失败-扣减冻结资金

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
    WithdrawStatusEnum(String msg) {
        this.msg = msg;
    }

}