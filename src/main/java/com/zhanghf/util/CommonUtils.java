package com.zhanghf.util;

import com.google.common.collect.Maps;
import com.zhanghf.init.CommonInitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CommonUtils {

    /**
     * 将异常信息转换为字符串(不换行。如果要换行，将\t换成\n)
     * e.toString() 获取异常名称
     * stackTraceElements获取出现异常的行数、类名、方法名
     *
     * @param e 异常信息
     * @return 字符串
     */
    public static String getStackTraceString(Throwable e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(e.toString());
        if (stackTraceElements != null && stackTraceElements.length > 0) {
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                builder.append("\t at ").append(stackTraceElement.toString());
            }
        }
        return builder.toString();
    }

    public static String inputStreamToString(String uuid, InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            int num;
            while ((num = bufferedReader.read()) != -1) {
                char ch = (char) num;
                builder.append(ch);
            }
            return builder.toString();
        } catch (Exception e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, getStackTraceString(e));
        }
        return null;
    }
}
