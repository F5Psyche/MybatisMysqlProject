package com.zhanghf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhanghf.init.CommonInitParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author zhanghf
 * @version 1.0
 * @date 18:50 2020/8/6
 */
@Slf4j
public class OssUtils {

    private OssUtils() {
        throw new IllegalStateException("OssUtils");
    }

    public static boolean fileDownWithUrl(String uuid, File file, String httpUrl) {
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            return fileDownWithUrl(uuid, conn, file);
        } catch (IOException e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            return false;
        }
    }

    private static boolean fileDownWithUrl(String uuid, HttpURLConnection conn, File file) {
        try (
                InputStream inputStream = conn.getInputStream()
        ) {
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] getData = bos.toByteArray();
            return fileDownWithUrl(uuid, file, getData);
        } catch (Exception e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            return false;
        }
    }

    private static boolean fileDownWithUrl(String uuid, File file, byte[] getData) {
        try (
                FileOutputStream fos = new FileOutputStream(file)
        ) {
            fos.write(getData);
            return true;
        } catch (IOException e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            return false;
        }
    }

    public static void fileDelete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        fileDelete(f);
                    } else if (f.isFile()) {
                        f.delete();
                    }
                }
                file.delete();
            }
        } else if (file.isFile()) {
            file.delete();
        }

    }

    public static String upload(String uuid, File file) {
        PostMethod filePost = new PostMethod("http://10.85.159.203:10540/fileUpload/upload");
        HttpClient client = new HttpClient();
        try {
            Part[] parts = {new FilePart("file", file), new StringPart("isPublic", "0")};
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String response = new String(filePost.getResponseBodyAsString().getBytes(StandardCharsets.UTF_8));
                JSONObject responseJson = JSON.parseObject(response);
                String key = responseJson.getJSONObject("result").getString("key");
                return CommonInitParam.OSS_FILE_URL + key;
            } else {
                log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, "上传失败");
            }
        } catch (Exception e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
        } finally {
            filePost.releaseConnection();
            fileDelete(file);
        }
        return "";
    }
}
