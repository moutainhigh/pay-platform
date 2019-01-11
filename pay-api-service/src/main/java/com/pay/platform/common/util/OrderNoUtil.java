package com.pay.platform.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: zjt
 * DateTime: 2018/8/15 17:06
 *
 * 订单号生成工具
 */
public class OrderNoUtil {

    private static OrderNoUtil OrderNoUtil = null;

    private OrderNoUtil() {

    }

    /**
     * 取得OrderNoUtil的单例实现
     *
     * @return
     */
    public static OrderNoUtil getInstance() {
        if (OrderNoUtil == null) {
            synchronized (OrderNoUtil.class) {
                if (OrderNoUtil == null) {
                    OrderNoUtil = new OrderNoUtil();
                }
            }
        }
        return OrderNoUtil;
    }

    /**
     * 生成唯一单号
     * <p>
     * 规则: 订单前缀 + 年月日 + 时间戳后十位
     */
    public synchronized String generaterUniqueOrderNo(String orderHeader) {

        StringBuilder uniqueCode = new StringBuilder("");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        uniqueCode.append(orderHeader);
        uniqueCode.append(formatter.format(date));
        String suffix = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        uniqueCode.append(suffix);

        //休眠二毫米
        try {
            Thread.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uniqueCode.toString();

    }


}