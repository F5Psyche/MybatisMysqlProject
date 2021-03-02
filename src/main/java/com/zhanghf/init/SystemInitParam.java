package com.zhanghf.init;

import org.apache.http.client.config.RequestConfig;

/**
 * @author zhanghf
 * @version 1.0
 * @date 16:07 2021/1/19
 */
public class SystemInitParam {

    public static RequestConfig REQUEST_TIMEOUT_CONFIG = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
            .setConnectionRequestTimeout(3000000).build();

    public static final String HEADER_NAME = "Content-Type";
    public static final String HEADER_VALUE = "application/json;charset=utf-8";
}
