package com.zhanghf.util;

import com.zhanghf.dto.CommonDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Slf4j
public class EncryptUtils {

    /**
     * sha-256加密
     *
     * @param inputData 需加密数据
     * @return 加密数据
     */
    public static String SHA256(final String inputData) {
        String encryptionType = "SHA-256";
        return SHA(inputData, encryptionType);
    }

    /**
     * sha-512加密
     *
     * @param inputData 需加密数据
     * @return 加密数据
     */
    public static String SHA512(final String inputData) {
        String encryptionType = "SHA-512";
        return SHA(inputData, encryptionType);
    }

    /**
     * MD5加密
     *
     * @param inputData 需加密数据
     * @return 加密数据
     */
    public static String MD5(final String inputData) {
        String encryptionType = "MD5";
        return SHA(inputData, encryptionType);
    }

    /**
     * 通用加密方法
     *
     * @param inputData      需加密的数据
     * @param encryptionType 加密类型（SHA-256,SHA-512,MD5）
     * @return 加密数据
     */
    private static String SHA(final String inputData, final String encryptionType) {
        String encryptionData = null;
        if (inputData != null && inputData.length() > 0) {
            try {
                //SHA 加密开始
                //创建加密对象,传入加密类型
                MessageDigest messageDigest = MessageDigest.getInstance(encryptionType);
                //传入要加密的字符串
                messageDigest.update(inputData.getBytes(CommonDTO.CHARSET_NAME));
                //得到byte数组
                byte byteBuffer[] = messageDigest.digest();
                //将byte数组转成String类型
                StringBuffer buffer = new StringBuffer();
                //遍历byte数组
                int size = byteBuffer.length;
                for (int i = 0; i < size; i++) {
                    //转换成16进制并存储在字符串中
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    //如果结果小于16,则在前面加上一个0填满两位的十六进制
                    if (hex.length() == 1) {
                        buffer.append("0");
                    }
                    //System.out.println("hex = " + hex + "值：" + (0xff & byteBuffer[i]));
                    buffer.append(hex);
                }
                encryptionData = buffer.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return encryptionData;
    }
}
