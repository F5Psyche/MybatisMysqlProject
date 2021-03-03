package com.zhanghf.vo.archives;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhanghf
 * @version 1.0
 * @date 10:29 2021/3/3
 */
@Data
public class BaseInfoVO implements Serializable {

    private String localInnerCode;
    private String projectId;
}
