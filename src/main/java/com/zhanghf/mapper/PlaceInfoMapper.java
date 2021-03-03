package com.zhanghf.mapper;

import com.zhanghf.vo.archives.BaseInfoVO;
import com.zhanghf.vo.xml.BaseInfoXmlVO;
import com.zhanghf.vo.xml.NodeInfoXmlVO;

import java.util.List;

/**
 * @author zhanghf
 * @version 1.0
 * @date 16:27 2021/1/19
 */
public interface PlaceInfoMapper {

    /**
     * 获取环节信息xml
     *
     * @return
     */
    List<NodeInfoXmlVO> getNodeInfo();

    /**
     * 获取基本信息xml
     *
     * @return
     */
    List<BaseInfoXmlVO> getBaseInfo();

    /**
     * 基本信息
     * @return
     */
    BaseInfoVO baseInfoQuery();
}
