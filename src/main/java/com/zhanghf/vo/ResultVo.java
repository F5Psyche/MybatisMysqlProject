package com.zhanghf.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhanghf
 * @version 1.0
 * @date 10:11 2020/3/10
 */
@Data
@NoArgsConstructor
public class ResultVo<T> implements Serializable {

    private T result;

    private boolean isSuccess = false;

    private String code;

    private String resultDes;

    private String requestId;

    public ResultVo(String requestId) {
        this.requestId = requestId;
    }

    public ResultVo(String code, String resultDes, String requestId) {
        this.code = code;
        this.resultDes = resultDes;
        this.requestId = requestId;
    }
}