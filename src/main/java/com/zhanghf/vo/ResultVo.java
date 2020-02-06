package com.zhanghf.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    private T result;
    private boolean isSuccess = false;
    private String code;
    private String resultDes;
}
