package com.pay.platform.common.util;

public class BuildNumberUtils {

    /**
     * 生成商户号
     *
     * @return
     */
    public static String getMerchantNo() {
        String sequence = SnowSequenceUtils.getSequence();
        return "1" + sequence.substring(9);
    }

    /**
     * 生成代理号
     *
     * @return
     */
    public static String getAgentNo() {
        String sequence = SnowSequenceUtils.getSequence();
        return "2" + sequence.substring(9);
    }

    public static void main(String[] args) {
        System.out.println(getMerchantNo());
    }

}
