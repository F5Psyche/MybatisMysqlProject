package com.zhanghf.util;

import com.google.common.collect.Lists;
import com.zhanghf.enums.PeaceInfoEnum;
import com.zhanghf.init.CommonInitParam;
import com.zhanghf.vo.xml.XmlVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * xml解析工具类
 *
 * @author zhanghf
 * @version 1.0
 * @date 16:38 2020/2/10
 */
@Slf4j
public class XmlUtils {

    public static String xmlGenerate(String uuid, List<XmlVO> vos) {
        File file = new File(uuid + ".xml");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file)
        ) {
            Document document = xmlGenerate(DocumentHelper.createDocument(), null, vos);
            XMLWriter writer = new XMLWriter(fileOutputStream, xmlStyle(2));
            writer.write(document);
            return OssUtils.upload(uuid, file);
        } catch (IOException e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
        }
        return "";
    }

    public static String fileToMd5(String uuid, String httpUrl, String fileType) {
        File file = new File(uuid + fileType);
        OssUtils.fileDownWithUrl(uuid, file, httpUrl);
        try (
                InputStream inputStream = new FileInputStream(file)
        ) {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
        } finally {
            OssUtils.fileDelete(file);
        }
        return "";
    }

    private static Document xmlGenerate(Document document, Element element, List<XmlVO> vos) {
        vos.forEach(vo -> {
            String nodeName = vo.getNodeName();
            String nodeValue = vo.getNodeValue();
            String nodeTitleName = vo.getNodeTitleName();
            String nodeTitleValue = vo.getNodeTitleValue();
            Element ele = document == null ? element.addElement(nodeName) : document.addElement(nodeName);
            if (!StringUtils.isEmpty(nodeTitleName) && !StringUtils.isEmpty(nodeTitleValue)) {
                ele.addAttribute(nodeTitleName, nodeTitleValue);
            }
            if (!StringUtils.isEmpty(nodeValue)) {
                ele.addText(nodeValue);
            }
            List<XmlVO> list = vo.getNodeChild();
            if (!CollectionUtils.isEmpty(list)) {
                xmlGenerate(null, ele, list);
            }
        });
        return document;
    }

    /**
     * xml样式设置
     *
     * @param type 1.默认样式；2.自定义样式
     * @return xml样式
     */
    private static OutputFormat xmlStyle(Integer type) {
        if (type == 1) {
            return OutputFormat.createPrettyPrint();
        } else {
            OutputFormat outputFormat = new OutputFormat();
            outputFormat.setEncoding("UTF-8");
            //行缩进
            outputFormat.setIndentSize(2);
            //一个结点为一行
            outputFormat.setNewlines(true);
            //去重空格
            outputFormat.setTrimText(true);
            outputFormat.setPadText(true);
            //放置xml文件中第二行为空白行
            outputFormat.setNewLineAfterDeclaration(false);
            //设置内容为空时是否自闭和
            outputFormat.setExpandEmptyElements(true);
            return outputFormat;
        }
    }


    public static List<XmlVO> xmlInfoListGet(String uuid, Object object) {
        List<XmlVO> list = Lists.newArrayList();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            String fieldName = field.getName();
            try {
                list.add(new XmlVO(fieldName, String.valueOf(field.get(object)), "title", PeaceInfoEnum.getNodeName(fieldName)));
            } catch (IllegalAccessException e) {
                log.error(CommonInitParam.COMMON_LOGGER_ERROR_INFO_PARAM, uuid, CommonUtils.getStackTraceString(e));
            }
        }
        return list;
    }

}
