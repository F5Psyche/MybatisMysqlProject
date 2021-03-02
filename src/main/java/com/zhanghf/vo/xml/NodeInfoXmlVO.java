package com.zhanghf.vo.xml;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhanghf
 * @version 1.0
 * @date 10:53 2021/1/25
 */
@Data
public class NodeInfoXmlVO implements Serializable {

    private String NodeName;
    private String Author;
    private String Type;
    private String Body;
    private String Modified;
}
