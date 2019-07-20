package com.pay.platform.common.enums;

/**
 * User:
 * DateTime: 2019/2/11 14:44
 *
 * 冻结资金 - 操作类型
 */
public enum FreezeAmountType {

    withdrawApply("提现申请"),                         //提现申请-增加冻结资金
    withdrawFail("提现失败"),                          //提现失败-扣减冻结资金
    withdrawSuccess("提现成功");                       //提现成功,扣减冻结资金和账户余额

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
    FreezeAmountType(String msg) {
        this.msg = msg;
    }

}