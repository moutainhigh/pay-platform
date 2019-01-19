package com.pay.platform.security;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: zjt
 * DateTime: 2016/10/27 19:15
 *
 * 由于request.getInputStream()与request.getReader()只能调用一次
 * 因为是流，在filter中读取完就没了，后续的controller将获取不到提交数据
 * 因此需要自定义ServletRequest,缓存InputStream
 */
public class ApiSecuirtyRequest extends HttpServletRequestWrapper {

    private byte[] requestBody = null;          //请求内容

    public ApiSecuirtyRequest(HttpServletRequest request) {
        super(request);

        //缓存请求body
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 重写 getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(requestBody == null){
            requestBody= new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    /**
     * 重写 getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}