package com.baseframe.generate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.InputStream;

/**
 * User: zjt
 * DateTime: 2019/6/1 10:29
 */
public class TestByLzyhApi {


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