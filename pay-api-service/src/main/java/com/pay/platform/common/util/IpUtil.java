package com.pay.platform.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * User:
 * DateTime: 2016/10/22 16:41
 * <p>
 * ip相关工具类
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {

        if (request == null) {
            return null;
        }

        String s = request.getHeader("X-Forwarded-For");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getHeader("Proxy-Client-IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getHeader("WL-Proxy-Client-IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getHeader("HTTP_CLIENT_IP");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
            s = request.getRemoteAddr();
        }
        return s;
    }

    /**
     * 获取baseURL
     *
     * @param request
     * @return
     */
    public static String getBaseURL(HttpServletRequest request) {

        String path = request.getContextPath();
        String getBaseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        return getBaseURL;

    }


}