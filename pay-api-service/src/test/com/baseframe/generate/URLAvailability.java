package com.baseframe.generate;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class URLAvailability {

    /**
     * 功能：检测当前URL是否可连接或是否有效,
     *
     * @param urlStr 指定URL网络地址
     * @return URL
     */
    public synchronized static boolean isConnect(String urlStr) {

        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }

        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000); //连接超时 单位毫秒
            conn.setReadTimeout(30000);    //读取超时 单位毫秒
            conn.setDoOutput(true);         //是否输入参数
            conn.setDoInput(true);        //是否读取参数

            int state = conn.getResponseCode();
            if (state == 200) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return false;
    }

    @Test
    public void testUrl(){


        long begimTime = System.currentTimeMillis();
        String url = "http://192.168.0.107:8080/openApi/ping";
        System.out.println("url是否有效：" + isConnect(url));
        long endTime = System.currentTimeMillis();

        System.out.println("耗时：" + (endTime - begimTime));
    }

}