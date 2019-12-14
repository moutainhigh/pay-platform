package com.pay.platform.common.enums;

/**
 * User:
 * DateTime: 2019/3/2 22:22
 */
public enum PayChannelEnum {

    hcZfb("话冲支付宝"),
    hcWechat("话冲微信"),
    lzyhZfb("柳行-支付宝"),
    lzyhWechat("柳行-微信"),
    ds_hj_zfb_wap("电商汇聚支付宝"),
    ds_hj_wx_wap("电商汇聚微信"),
    ds_zl_wx_wap("电商直连微信");

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

    PayChannelEnum(String msg) {
        this.msg = msg;
    }

}