package com.zhanghf.modues;

import com.google.common.collect.Lists;
import com.zhanghf.mapper.PlaceInfoMapper;
import com.zhanghf.mapper.WfUserSignMapper;
import com.zhanghf.po.WfUserSign;
import com.zhanghf.vo.archives.BaseInfoVO;
import com.zhanghf.vo.xml.BaseInfoXmlVO;
import com.zhanghf.vo.xml.NodeInfoXmlVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghf
 * @version 1.0
 * @date 17:17 2021/1/19
 */
@Slf4j
@Service
public class PlaceInfoService {

    @Resource
    PlaceInfoMapper placeInfoMapper;

    @Resource
    WfUserSignMapper wfUserSignMapper;

    public List<NodeInfoXmlVO> getNodeInfo() {
        return placeInfoMapper.getNodeInfo();
    }

    public List<BaseInfoXmlVO> getBaseInfo() {
        return placeInfoMapper.getBaseInfo();
    }

    public List<Long> getSignUserInfo(List<Long> userIds, String ouGuid, Long userId) {
        Example example = new Example(WfUserSign.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("outTime");
        criteria.andNotEqualTo("userId", userId);
        criteria.andEqualTo("ouGuid", ouGuid);
        criteria.andIn("userId", userIds);
        List<WfUserSign> infos = wfUserSignMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(infos)) {
            return Collections.emptyList();
        } else {
            List<Long> ids = Lists.newArrayList();
            infos.forEach(info -> {
                ids.add(info.getUserId());
            });
            return ids;
        }
    }

    public BaseInfoVO baseInfoQuery() {
        return placeInfoMapper.baseInfoQuery();
    }
}
