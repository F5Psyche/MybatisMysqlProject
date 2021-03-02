package com.zhanghf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhanghf.enums.HTTPCodeEnum;
import com.zhanghf.init.CommonInitParam;
import com.zhanghf.init.SystemInitParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * http接口调用类
 *
 * @author zhanghf
 * @version 1.0
 * @date 9:07 2020/3/12
 */
@Slf4j
public class HttpConnectionUtils {

    /**
     * @param uuid 唯一识别码
     * @param uri  请求地址
     * @param json 请求参数
     * @return 返回参数
     */
    public static String httpConnectionPost(String uuid, String uri, JSONObject json) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(4000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(SystemInitParam.HEADER_NAME, SystemInitParam.HEADER_VALUE);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            String data = JSON.toJSONString(json == null ? "{}" : json);
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);
            InputStream inputStream = connection.getInputStream();
            String responseContent = CommonUtils.inputStreamToString(uuid, inputStream);
            int status = connection.getResponseCode();
            String message = connection.getResponseMessage();
            connection.disconnect();
            log.info("uuid={}, status={}, message={}, responseContent={}", uuid, status, message, responseContent);
            return responseContent;
        } catch (IOException e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            throw new IllegalArgumentException("httpPost请求异常");
        }
    }


    /**
     * @param uuid      唯一识别码
     * @param httpUrl   请求地址
     * @param condition 请求参数
     * @return 返回参数
     */
    public static String httpPostUsing(String uuid, String httpUrl, JSONObject condition) {
        HttpPost post = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            if (object instanceof List<?>) {
                List<?> list = (List<?>) object;
                for (Object value : list) {
                    nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(value)));
                }
            } else {
                nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(object)));
            }
        }
        log.info("uuid={}, httpUrl={}, condition={}, nameValuePairs={}", uuid, httpUrl, condition, nameValuePairs);
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
        return httpPostUsing(uuid, post);
    }


    /**
     * @param uuid    唯一识别码
     * @param httpUrl 请求地址
     * @param object  请求参数
     * @param header  请求头
     * @return 返回参数
     */
    public static String httpClientPost(String uuid, String httpUrl, Object object, JSONObject header) {
        HttpPost post = new HttpPost(httpUrl);
        post.setHeader(SystemInitParam.HEADER_NAME, SystemInitParam.HEADER_VALUE);
        if (!CollectionUtils.isEmpty(header)) {
            header.keySet().forEach(key -> {
                post.setHeader(key, header.getString(key));
            });
        }
        byte[] bytes = object.toString().getBytes(StandardCharsets.UTF_8);
        post.setEntity(new ByteArrayEntity(bytes));
        return httpPostUsing(uuid, post);
    }


    /**
     * @param uuid 唯一识别码
     * @param post httpPost
     * @return 返回参数
     */
    private static String httpPostUsing(String uuid, HttpPost post) {
        post.setConfig(SystemInitParam.REQUEST_TIMEOUT_CONFIG);
        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)
        ) {
            int status = response.getStatusLine().getStatusCode();
            if (status != HTTPCodeEnum.OK.getCode()) {
                log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, "httpStatus<" + status + ">请求错误");
                throw new IllegalArgumentException("httpPost请求异常");
            }
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("uuid={}, responseContent={}", uuid, responseContent);
            return responseContent;
        } catch (Exception e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            throw new IllegalArgumentException("httpPost请求异常");
        }
    }
}
