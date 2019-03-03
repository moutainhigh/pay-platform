package com.pay.platform.common.util.payCharge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class HttpClientPost {
    /**
     * 请求连接固定参数
     *
     * @param s 传参地址
     * @return 返回正常连接
     * @throws IOException
     */
    public static URLConnection getConnection(String s) throws IOException {
        URL url = new URL(s);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Accept", "application/json");// 设置接收数据的格式
        conn.setRequestProperty("Content-Type", "application/json");// 设置发送数据的格式
        return conn;
    }

    /**
     * post请求
     *
     * @param url   地址加接口
     * @param param 请求参数
     * @return 返回响应结果
     * @throws IOException 抛异常
     */
    public static String reqPost(String url, String param) throws IOException {
        System.out.println("请求地址:" + url);
        System.out.println("请求参数:" + param);
        BufferedReader reader = null;
        String sb = null;
        OutputStreamWriter wr = null;
        try {
            URLConnection conn = getConnection(url);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            if (param != null && param.toString().trim().length() > 0) {
                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                wr.write(param.toString());
                wr.flush();

            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new String();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb += (line + "\n");
            }

        } catch (Exception e) {
            System.out.println("Get Error Occured!");
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    public static String reqPostTime(String url, int connectTimeOut, int timeOut, String param) throws IOException {
        System.out.println("请求地址:" + url);
        System.out.println("请求参数:" + param);
        BufferedReader reader = null;
        String sb = null;
        OutputStreamWriter wr = null;
        try {
            URLConnection conn = getConnection(url);
            conn.setDoOutput(true);
            conn.setConnectTimeout(connectTimeOut);
            conn.setReadTimeout(timeOut);
            conn.setConnectTimeout(1000 * 5);
            if (param != null && param.toString().trim().length() > 0) {
                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                wr.write(param.toString());
                wr.flush();

            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new String();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb += (line + "\n");
            }

        } catch (Exception e) {
            System.out.println("Get Error Occured!");
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    //顺序组装请求参数，用于签名/校验
    public static String assembelSignaturingData(Map data) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> treeMap = new TreeMap(data);
        for (Map.Entry<String, Object> ent : treeMap.entrySet()) {
            String name = ent.getKey();
            if (/* !"signType".equals(name) &&*/ !"sign".equals(name)) {
                sb.append(name).append('=').append(ent.getValue()).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("日期对象不允许为null!");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

}
