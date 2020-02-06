package com.zhanghf.modues;

import com.zhanghf.mapper.RuleInfoTabMapper;
import com.zhanghf.po.RuleInfoTab;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ExternalDoorService {

    @Resource
    RuleInfoTabMapper ruleInfoTabMapper;

    public List<RuleInfoTab> ruleInfoList(String uuid) {
        List<RuleInfoTab> list = ruleInfoTabMapper.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            log.info("uuid={}, list={}", uuid, list);
            return Collections.emptyList();
        }
        return list;
    }
}
