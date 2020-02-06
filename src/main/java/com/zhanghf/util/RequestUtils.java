package com.zhanghf.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanghf
 * @since 2020/1/17
 */
public class RequestUtils {

    /**
     * 获取真实IP
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] arr = ip.split(",");
        String[] var3 = arr;
        int var4 = arr.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            String i = var3[var5];
            ip = i.trim();
        }
        if (ip.length() > 15) {
            ip = StringUtils.substring(ip, 0, 15);
        }
        return ip;
    }
}
