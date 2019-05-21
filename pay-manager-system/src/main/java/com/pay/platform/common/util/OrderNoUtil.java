package com.pay.platform.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * User: zjt
 * DateTime: 2018/8/15 17:06
 * <p>
 * 订单号生成工具
 */
public class OrderNoUtil {

    /**
     * 根据hashCode 生成唯一单号
     */
    public static String getOrderNoByUUId() {

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);

        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }

        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return time + String.format("%011d", hashCodeV);

    }

}