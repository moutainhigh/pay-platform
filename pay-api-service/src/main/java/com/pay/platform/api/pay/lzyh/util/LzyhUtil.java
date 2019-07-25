package com.pay.platform.api.pay.lzyh.util;

import com.pay.platform.common.util.DecimalCalculateUtil;

import java.util.ArrayList;
import java.util.List;

public class LzyhUtil {

    /**
     * 获取浮动金额池；
     * 1、向上浮动5块钱
     * 2、向下浮动15块钱
     *
     * @param orderAmount
     * @return
     */
    public static List<Double> getPayFloatAmountList(Double orderAmount) {

        List<Double> payFloatAmountList = new ArrayList<>();

        //向上浮动5块钱
        for (int i = 1; i <= 5; i++) {
            double amount = DecimalCalculateUtil.add(orderAmount, i);
            payFloatAmountList.add(amount);
        }

        //向下浮动15块钱
        for (int i = 1; i <= 15; i++) {
            double amount = DecimalCalculateUtil.sub(orderAmount, i);
            payFloatAmountList.add(amount);
        }

        return payFloatAmountList;

    }

}