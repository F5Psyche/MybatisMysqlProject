package com.zhanghf.vo.xml;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhanghf
 * @version 1.0
 * @date 10:33 2021/1/25
 */
@Data
public class BaseInfoXmlVO implements Serializable {

    private String DocumentNumber;
    private String RetentionPeriod;
    private String ArchiveTime;
    private String DepartmentName;
    private String LeadDepartment;
    private String ApplyName;
    private String Mobile;
    private String Phone;
    private String Address;
    private String CardType;
    private String ContactedCard;
    private String LegalMan;
    private String ProjectName;
    private String ReceiveDepartment;
    private String ReceiveTime;
    private String TransactTime;
    private String ProjectId;
    private String ServiceType;
    private String Result;
    private String ResultCode;
    private String TaskCode;
    private String TaskVersion;
    private String TaskType;

}
