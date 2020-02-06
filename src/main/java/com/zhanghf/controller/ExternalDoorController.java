package com.zhanghf.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhanghf.modues.ExternalDoorService;
import com.zhanghf.po.RuleInfoTab;
import com.zhanghf.util.HttpServletRequestUtils;
import com.zhanghf.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("")
public class ExternalDoorController {

    @Resource
    ExternalDoorService externalDoorService;

    @RequestMapping("/col/rule")
    public ResultVo getPowerMatterInfo(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        JSONObject paramter = HttpServletRequestUtils.getParameter(uuid, request);
        List<RuleInfoTab> list = externalDoorService.ruleInfoList(uuid);
        log.info("uuid={}, paramter={}, list={}", uuid, paramter, list);
        return null;
    }
}
