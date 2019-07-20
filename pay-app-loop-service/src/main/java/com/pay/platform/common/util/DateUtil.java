package com.pay.platform.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User:
 * DateTime: 2016/12/10 15:24
 */
public class DateUtil {

    /**
     * 相关日期格式
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";                        //日期
    private static final String TIME_PATTERN = "HH:mm:ss";                          //时间
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";          //日期时间

    /**
     * 格式化方法
     *
     * @param date:    日期
     * @param pattern: 格式
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 获取当前日期,格式年-月-日
     *
     * @return
     */
    public static final String getCurrentDate() {
        return format(new Date(), DATE_PATTERN);
    }

    /**
     * 获取当前日期和时间
     *
     * @return
     */
    public static final String getCurrentDateTime() {
        return format(new Date(), DATE_TIME_PATTERN);
    }


    /**
     * 获取当前年月
     *
     * @return
     */
    public static final String getCurrentYearAndMonth() {
        return format(new Date(), "yyyy-MM");
    }

    /**
     * 获取时间戳：格式秒
     * @param datetime
     * @return
     */
    public static long getTimeStamp(String datetime) {

        try {
            SimpleDateFormat foramt = new SimpleDateFormat(DATE_TIME_PATTERN);
            Date date = foramt.parse(datetime);
            long mills = date.getTime();
            return Long.parseLong(String.valueOf(mills).substring(0, 10));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取时间戳：格式秒
     * @param date
     * @return
     */
    public static long getTimeStamp(Date date) {

        try {
            long mills = date.getTime();
            return Long.parseLong(String.valueOf(mills).substring(0, 10));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}