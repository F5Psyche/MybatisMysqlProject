package com.zhanghf.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.zhanghf.modues.PlaceInfoService;
import com.zhanghf.util.HttpConnectionUtils;
import com.zhanghf.util.XmlUtils;
import com.zhanghf.vo.ResultVo;
import com.zhanghf.vo.archives.BaseInfoVO;
import com.zhanghf.vo.xml.BaseInfoXmlVO;
import com.zhanghf.vo.xml.NodeInfoXmlVO;
import com.zhanghf.vo.xml.XmlVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("")
public class ExternalDoorController {

    @Resource
    PlaceInfoService placeInfoService;

    @RequestMapping("/col/rule")
    public ResultVo<List<String>> getPowerMatterInfo() {
        String uuid = UUID.randomUUID().toString();
        ResultVo<List<String>> resultVo = new ResultVo<>(uuid);
        List<JSONObject> result = Lists.newArrayList();

        List<BaseInfoXmlVO> baseInfos = placeInfoService.getBaseInfo();
        XmlVO baseInfosXml = new XmlVO();
        baseInfosXml.setNodeName("description");
        baseInfosXml.setNodeTitleName("title");
        baseInfosXml.setNodeTitleValue("基本信息描述");
        for (BaseInfoXmlVO baseInfo : baseInfos) {
            List<XmlVO> baseInfoVos = XmlUtils.xmlInfoListGet(uuid, baseInfo);
            baseInfosXml.setNodeChild(baseInfoVos);
            String baseInfosFilePath = XmlUtils.xmlGenerate(uuid, Lists.newArrayList(baseInfosXml));
            result.add(new JSONObject(ImmutableMap.of(
                    "fileName", "基本信息.xml",
                    "kind", "基本信息",
                    "filelocation", baseInfosFilePath,
                    "fileMD5", XmlUtils.fileToMd5(uuid, baseInfosFilePath, ".xml"))));
        }

        XmlVO xml = new XmlVO();
        xml.setNodeName("description");
        xml.setNodeTitleName("title");
        xml.setNodeTitleValue("办理流程信息");
        List<XmlVO> vos = Lists.newArrayList();
        List<NodeInfoXmlVO> nodeInfos = placeInfoService.getNodeInfo();
        for (NodeInfoXmlVO nodeInfo : nodeInfos) {
            XmlVO vo = new XmlVO();
            vo.setNodeName("Opinion");
            List<XmlVO> list = XmlUtils.xmlInfoListGet(uuid, nodeInfo);
            vo.setNodeChild(list);
            vos.add(vo);
        }
        xml.setNodeChild(vos);
        String nodeFilePath = XmlUtils.xmlGenerate(uuid, Lists.newArrayList(xml));
        result.add(new JSONObject(ImmutableMap.of(
                "fileName", "办理流程信息.xml",
                "kind", "办理流程信息",
                "filelocation", nodeFilePath,
                "fileMD5", XmlUtils.fileToMd5(uuid, nodeFilePath, ".xml"))));

        result.add(new JSONObject(ImmutableMap.of(
                "fileName", "报告.jpg",
                "kind", "行政确认申报材料/《基本医疗保险参保人员享受规定（特殊慢性）病种待遇备案表》或确诊的病理切片报告或出院记录或病历一份",
                "filelocation", "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400437204FgX.jpg",
                "fileMD5", XmlUtils.fileToMd5(uuid, "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400437204FgX.jpg", ".jpg"))));
        result.add(new JSONObject(ImmutableMap.of(
                "fileName", "身份证.jpg",
                "kind", "行政确认申报材料/本人社会保障卡或有效身份证件（现场办理需提供）",
                "filelocation", "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400437204FgX.jpg",
                "fileMD5", XmlUtils.fileToMd5(uuid, "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400437204FgX.jpg", ".jpg"))));

        result.add(new JSONObject(ImmutableMap.of(
                "fileName", "照片.jpg",
                "kind", "行政确认申报材料/一寸免冠近照一张",
                "filelocation", "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400442503s4F.jpg",
                "fileMD5", XmlUtils.fileToMd5(uuid, "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400442503s4F.jpg", ".jpg"))));

        result.add(new JSONObject(ImmutableMap.of(
                "fileName", "病历.jpg",
                "kind", "行政确认申报材料/确诊的病理切片报告或出院记录或病历一份",
                "filelocation", "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400442503s4F.jpg",
                "fileMD5", XmlUtils.fileToMd5(uuid, "http://ybj.zjzwfw.gov.cn:10540/openapiApp/download?key=bizimg/rdm/1607400442503s4F.jpg", ".jpg"))));


        List<String> params = Lists.newArrayList();


        BaseInfoVO info = placeInfoService.baseInfoQuery();
        //事项流水号
        params.add(info.getProjectId());

        //事项唯一码
        params.add(info.getLocalInnerCode());
        params.add("2020");
        params.add(result.toString());
        params.add(Lists.newArrayList("行政确认申报材料").toString());
        params.add(Lists.newArrayList("《基本医疗保险参保人员享受规定（特殊慢性）病种待遇备案表》或确诊的病理切片报告或出院记录或病历一份", "本人社会保障卡或有效身份证件（现场办理需提供）", "一寸免冠近照一张", "确诊的病理切片报告或出院记录或病历一份").toString());

        JSONObject condition = new JSONObject();
        condition.put("params", params);

        String data = HttpConnectionUtils.httpPostUsing(uuid, "http://10.85.159.203:10420/placeOnFile/counterSystem", condition);
        log.info("uuid={}, data={}", uuid, data);
        resultVo.setResult(params);
        return resultVo;
    }


    @PostMapping("/signUserInfo")
    public ResultVo<List<Long>> getSignUserInfo(@RequestParam(value = "userId") List<Long> userIds) {
        ResultVo<List<Long>> resultVo = new ResultVo<>();
        userIds = placeInfoService.getSignUserInfo(userIds, "001003148", 580L);
        log.info("userIds={}, flag={}", userIds, CollectionUtils.isEmpty(userIds));
        resultVo.setResult(userIds);
        return resultVo;
    }


    @PostMapping("/dataTest")
    public ResultVo<Map<String, String[]>> dataTest(HttpServletRequest request) {
        ResultVo<Map<String, String[]>> resultVo = new ResultVo<>();
        Map<String, String[]> map = request.getParameterMap();
        map.keySet().forEach(key -> {
            for (String s : map.get(key)) {
                log.info("key={}, s={}", key, s);
            }
        });
        log.info("map={}", map);
        resultVo.setResult(map);
        return resultVo;
    }
}
