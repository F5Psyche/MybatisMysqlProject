package com.zhanghf.enums;

import lombok.Getter;

/**
 * @author zhanghf
 * @version 1.0
 * @date 17:04 2021/1/19
 */
@Getter
public enum PeaceInfoEnum {

    /**
     * 环节信息enum
     */
    NODE_NAME("NodeName", "环节名称"),
    AUTHOR("Author", "经办人"),
    TYPE("Type", "意见类型"),
    BODY("Body", "意见内容"),
    MODIFIED("Modified", "办理时间"),

    DOCUMENT_NUMBER("DocumentNumber", "电子文件号"),
    RETENTION_PERIOD("RetentionPeriod", "保管期限"),
    ARCHIVE_TIME("ArchiveTime", "归档时间"),
    DEPARTMENT_NAME("DepartmentName", "立档单位名称"),
    LEAD_DEPARTMENT("LeadDepartment", "责任处科室"),
    APPLY_NAME("ApplyName", "行政相对人名称"),
    MOBILE("Mobile", "行政相对人手机"),
    PHONE("Phone", "行政相对人电话"),
    ADDRESS("Address", "行政相对人地址"),
    CARD_TYPE("CardType", "证件类型"),
    CONTACTED_CARD("ContactedCard", "证件号码"),
    LEGAL_MAN("LegalMan", "法定代表人"),
    PROJECT_NAME("ProjectName", "办件名称"),
    RECEIVE_DEPARTMENT("ReceiveDepartment", "承办单位"),
    RECEIVE_TIME("ReceiveTime", "受理（立案）时间"),
    TRANSACT_TIME("TransactTime", "办结时间"),
    PROJECT_ID("Projectid", "业务流水号"),
    SERVICE_TYPE("ServiceType", "办件类型"),
    RESULT("Result", "办理结果"),
    RESULT_CODE("ResultCode", "证件编号"),
    TASK_CODE("TaskCode", "事项编码"),
    TASK_VERSION("TaskVersion", "事项版本号"),
    TASK_TYPE("TaskType", "事项类型"),
    ;

    private String nodeId;
    private String nodeName;

    PeaceInfoEnum(String nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public static String getNodeName(String nodeId) {
        if (nodeId != null && nodeId.length() > 0) {
            for (PeaceInfoEnum type : PeaceInfoEnum.values()) {
                if (type.getNodeId().equals(nodeId)) {
                    return type.getNodeName();
                }
            }
        }
        return null;
    }

}
