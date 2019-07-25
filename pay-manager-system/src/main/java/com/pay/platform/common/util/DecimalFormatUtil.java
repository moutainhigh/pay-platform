package com.pay.platform.common.util;

import org.junit.Test;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    public static String format2Point(String amonut){
        return new DecimalFormat("0.00").format(Double.parseDouble(amonut));
    }

    public static String format2Point(double amonut){
        return new DecimalFormat("0.00").format(amonut);
    }

    @Test
    public void testFormat(){

        double amount = 594.00000;
        System.out.println(" ---> " + format2Point(amount));

    }

}