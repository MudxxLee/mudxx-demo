package com.mudxx.demo.boot.quartz.database.dao;

import com.mudxx.demo.boot.quartz.database.dao.upk.QrtzJobDetailsUPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author laiwen
 */
@Entity
@Data
@IdClass(QrtzJobDetailsUPK.class)
@Table(name = "QRTZ_JOB_DETAILS")
public class QrtzJobDetails implements Serializable {
    private static final long serialVersionUID = 4244263015847002137L;

    @Id
    @Column(name = "SCHED_NAME", length = 120)
    private String schedName;

    @Id
    @Column(name = "JOB_NAME", length = 190)
    private String jobName;

    @Id
    @Column(name = "JOB_GROUP", length = 190)
    private String jobGroup;

    @Column(length = 250)
    private String description;

    @Column(name = "JOB_CLASS_NAME", length = 250)
    private String jobClassName;

    @Column(name = "IS_DURABLE", length = 1)
    private String isDurable;

    @Column(name = "IS_NONCONCURRENT", length = 1)
    private String isNonconcurrent;

    @Column(name = "IS_UPDATE_DATA", length = 1)
    private String isUpdateData;

    @Column(name = "REQUESTS_RECOVERY", length = 1)
    private String requestsRecovery;

    @Column(name = "JOB_DATA")
    private String jobData;

}