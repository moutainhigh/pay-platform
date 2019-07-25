package com.pay.platform.common.util;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    public static String format2Point(String amonut){
        return new DecimalFormat("0.00").format(Double.parseDouble(amonut));
    }

    public static String format2Point(double amonut){
        return new DecimalFormat("0.00").format(amonut);
    }

}