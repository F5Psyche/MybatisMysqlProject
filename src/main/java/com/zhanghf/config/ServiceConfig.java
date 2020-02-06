package com.zhanghf.config;

import com.zhanghf.util.HttpServletRequestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean(name = "httpServletRequestUtils")
    HttpServletRequestUtils httpServletRequestUtils() {
        HttpServletRequestUtils httpServletRequestUtils = new HttpServletRequestUtils();
        return httpServletRequestUtils;
    }

}
