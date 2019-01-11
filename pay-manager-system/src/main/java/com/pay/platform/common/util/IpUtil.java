package com.pay.platform.common.util;

import com.pay.platform.modules.sysmgr.log.annotation.SystemLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * User: zjt
 * DateTime: 2016/10/22 16:41
 * <p>
 * ip相关工具类
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
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