package com.zhanghf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhanghf.dto.CommonDTO;
import com.zhanghf.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpUtils {

    /**
     * http POST 请求
     *
     * @param uuid    唯一识别码
     * @param httpUrl 请求地址
     * @param params  请求参数
     * @return 请求结果
     */
    public static ResultVo<Object> getHttpResponse(String uuid, String httpUrl, JSONObject params) {
        ResultVo<Object> resultVo = new ResultVo<>();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(CommonDTO.HEADER_NAME, CommonDTO.HEADER_VALUE);
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(JSON.toJSONString(params).getBytes(CommonDTO.CHARSET_NAME));
            int status = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            String data = CommonUtils.inputStreamToString(uuid, inputStream).getResult();
            if (status == 200) {
                resultVo.setSuccess(true);
            } else {
                log.info("<getHttpResponse.status>uuid={}, httpUrl={}, status={}, data={}", uuid, httpUrl, status, data);
            }
            try {
                resultVo.setResult(JSON.parse(data));
            } catch (Exception e) {
                resultVo.setResult(data);
            }
            connection.disconnect();
        } catch (IOException e) {
            resultVo.setResult("");
            resultVo.setCode("8099");
            resultVo.setResultDes(e.toString());
            log.error("<getHttpResponse.IOException>uuid={}, httpUrl={}, errMsg={}", uuid, httpUrl, CommonUtils.exceptionToString(e));
        }
        return resultVo;
    }
}
