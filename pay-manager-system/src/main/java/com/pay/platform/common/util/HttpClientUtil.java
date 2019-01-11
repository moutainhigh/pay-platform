package com.pay.platform.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * User: zjt
 * DateTime: 2016/11/29 17:36
 */
public class HttpClientUtil {

    /**
     * 发送http get 请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url); // 设置响应头信息
        httpGet.addHeader("Content-type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(httpGet);

        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            return getJsonStrFromHttpResponse(response);
        }

        return null;

    }

    /**
     * 发送http post 请求 , 数据为json
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static String doPost(String url, String jsonBody) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpost = new HttpPost(url); // 设置响应头信息
        httpost.addHeader("Content-type", "application/json; charset=utf-8");
        httpost.setHeader("Accept", "application/json");
        httpost.setEntity(new StringEntity(jsonBody, "UTF-8"));

        HttpResponse response = httpClient.execute(httpost);

        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            return getJsonStrFromHttpResponse(response);
        }

        return null;

    }

    /**
     * 发送https - post请求 , 数据为json
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static String doPostByHttps(String url, String jsonBody) throws Exception {

        CloseableHttpClient httpClient = createSSLClientDefault();

        HttpPost httpost = new HttpPost(url);
        httpost.addHeader("Content-type", "application/json; charset=utf-8");
        httpost.setHeader("Accept", "application/json");
        httpost.setEntity(new StringEntity(jsonBody, "UTF-8"));

        HttpResponse response = httpClient.execute(httpost);

        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            return getJsonStrFromHttpResponse(response);
        }

        return null;

    }

    /**
     * 创建发送https请求的HttpClient
     *
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {

        try {

            //信任所有
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpClients.createDefault();
    }

    /**
     * 将httpResponse解析成json字符串
     *
     * @param response
     * @return
     */
    public static String getJsonStrFromHttpResponse(HttpResponse response) throws Exception {

        InputStream in = response.getEntity().getContent();

        StringBuilder resultJson = new StringBuilder();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            resultJson.append(new String(buf, 0, len));
        }
        return resultJson.toString();

    }

}