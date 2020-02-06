package com.zhanghf.util;

import com.zhanghf.dto.CommonDTO;
import com.zhanghf.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;

@Slf4j
public class CommonUtils {



    /**
     * 将异常信息转换为字符串
     *
     * @param e 异常信息
     * @return 字符串
     */
    public static String exceptionToString(Exception e) {
        PrintWriter pw = null;
        try {
            StringWriter sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ex) {
            return "ExceptionToString is error";
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 将内容写入
     *
     * @param uuid     唯一识别码
     * @param content  内容
     * @param fileName 文件名
     * @return 结果
     */
    public static ResultVo<String> writeFile(String uuid, String content, String fileName) {
        ResultVo<String> resultVo = new ResultVo<>();
        String pathName = "\\BlockChain\\" + fileName;
        File file = new File(pathName);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(file);
            byte bytes[] = content.getBytes();
            outputStream.write(bytes);
            resultVo.setSuccess(true);
        } catch (IOException e) {
            resultVo.setResult("");
            resultVo.setCode("8099");
            resultVo.setResultDes(e.toString());
            log.error("<writeFile.IOException>uuid={}, errMsg={}", uuid, exceptionToString(e));
        }
        return resultVo;
    }

    /**
     * 读取文件内容
     *
     * @param uuid     唯一识别码
     * @param fileName 文件名
     * @return 结果
     */
    public static ResultVo<String> readFile(String uuid, String fileName) {
        ResultVo<String> resultVo = new ResultVo<>();
        String pathName = "\\BlockChain\\" + fileName;
        File file = new File(pathName);
        try {
            InputStream inputStream = new FileInputStream(file);
            resultVo = inputStreamToString(uuid, inputStream);
            if (StringUtils.isEmpty(resultVo.getResult())) {
                resultVo.setSuccess(false);
            }
        } catch (FileNotFoundException e) {
            resultVo.setResult("");
            resultVo.setSuccess(false);
            resultVo.setCode("8099");
            resultVo.setResultDes(e.toString());
            log.error("<readFile.FileNotFoundException>uuid={}, errMsg={}", uuid, exceptionToString(e));
        }
        return resultVo;
    }

    /**
     * @param uuid        唯一识别码
     * @param inputStream InputStream
     * @return ResultVo
     */
    public static ResultVo<String> inputStreamToString(String uuid, InputStream inputStream) {
        ResultVo<String> resultVo = new ResultVo<>();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            inputStreamReader = new InputStreamReader(inputStream, CommonDTO.CHARSET_NAME);
            bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                buffer.append(lines);
            }
            resultVo.setResult(buffer.toString());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            resultVo.setResult("");
            resultVo.setCode("8099");
            resultVo.setResultDes(e.toString());
            log.error("<inputStreamToString.Exception>uuid={}, errMsg={}", uuid, exceptionToString(e));
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                resultVo.setResult("");
                resultVo.setCode("8099");
                resultVo.setResultDes(e.toString());
                log.error("<inputStreamToString.IOException>uuid={}, errMsg={}", uuid, exceptionToString(e));
            }
        }
        return resultVo;
    }
}
