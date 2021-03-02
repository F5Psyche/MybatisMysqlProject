package com.zhanghf.vo.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanghf
 * @version 1.0
 * @date 9:36 2021/1/13
 */
@Data
@NoArgsConstructor
public class XmlVO implements Serializable {

    private String nodeName;
    private String nodeValue;
    private String nodeTitleName;
    private String nodeTitleValue;
    private List<XmlVO> nodeChild;

    public XmlVO(String nodeName, String nodeValue, String nodeTitleName, String nodeTitleValue) {
        this.nodeName = nodeName;
        this.nodeValue = nodeValue;
        this.nodeTitleName = nodeTitleName;
        this.nodeTitleValue = nodeTitleValue;
    }
}
