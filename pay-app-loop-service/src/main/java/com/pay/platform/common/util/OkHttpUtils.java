package com.pay.platform.common.util;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * User: zjt
 * DateTime: 2019/6/2 22:46
 */
public class OkHttpUtils {

    private static OkHttpUtils okHttpUtil;
    private final OkHttpClient okHttpClient;

    public OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 单例
     *
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (null == okHttpUtil) {
            synchronized (OkHttpUtils.class) {
                if (null == okHttpUtil) {
                    okHttpUtil = new OkHttpUtils();
                }
            }
        }
        return okHttpUtil;
    }

    /**
     * get请求：带token认证
     *
     * @param urlString
     * @param callback
     */
    public void sendGetByTohenAuth(String urlString, String token, Callback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .addHeader("Content-type", "application/json; charset=utf-8")
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "close")
                .addHeader("X-Authorization", token)
                .get().build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    /**
     * post请求
     *
     * @param urlString
     * @param jsonStr
     * @param callback
     */
    public void sendPost(String urlString, String jsonStr, Callback callback) throws IOException {

        //封装请求参数
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);

        Request request = new Request.Builder()
                .url(urlString)
                .addHeader("Content-type", "application/json; charset=utf-8")
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "close")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);

    }

}