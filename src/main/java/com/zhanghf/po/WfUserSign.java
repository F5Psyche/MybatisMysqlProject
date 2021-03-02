package com.zhanghf.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhanghf
 * @version 1.0
 * @date 14:45 2021/1/21
 */
@Data
public class WfUserSign implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ou_guid")
    private String ouGuid;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "out_time")
    private Date outTime;
}
