package com.zhanghf.util;

import com.alibaba.fastjson.JSONObject;
import com.zhanghf.dto.CommonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

@Slf4j
public class HttpServletRequestUtils {

    /**
     * 获取参数
     *
     * @param uuid    唯一码
     * @param request 请求内容
     * @return 请求参数
     */
    public static JSONObject getParameter(String uuid, HttpServletRequest request) {
        String httpUrl = request.getRequestURL() + (StringUtils.isEmpty(request.getQueryString()) ? "" : ("?" + request.getQueryString()));
        JSONObject result = new JSONObject();
        String inputLine;
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), CommonDTO.CHARSET_NAME));
            while ((inputLine = bufferedReader.readLine()) != null) {
                buffer.append(inputLine);
            }
            String data = buffer.toString();
            if (!StringUtils.isEmpty(data)) {
                result = JSONObject.parseObject(data);
            }
            log.info("httpUrl={}, ip={}", httpUrl, getIpAddress(request));
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                result.put(paramName, request.getParameter(paramName));
            }
        } catch (Exception e) {
            log.error("<getParameter.Exception>uuid={}, buffer={}, flag={}, errMsg={}", uuid, buffer, StringUtils.isEmpty(buffer.toString()), e.toString());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                log.error("<getParameter.IOException>uuid={}, errMsg={}", uuid, e.toString());
            }
        }
        return result;
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
