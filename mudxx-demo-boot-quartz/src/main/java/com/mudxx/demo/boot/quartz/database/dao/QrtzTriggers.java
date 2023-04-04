package com.mudxx.demo.boot.quartz.database.dao;

import com.mudxx.demo.boot.quartz.database.dao.upk.QrtzTriggersUPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@IdClass(QrtzTriggersUPK.class)
@Table(name = "QRTZ_TRIGGERS")
public class QrtzTriggers implements Serializable {
    private static final long serialVersionUID = -2344818779145893025L;
    @Id
    @Column(name = "SCHED_NAME", length = 120, nullable = false)
    private String schedName;

    @Id
    @Column(name = "TRIGGER_NAME", length = 190, nullable = false)
    private String triggerName;

    @Id
    @Column(name = "TRIGGER_GROUP", length = 190, nullable = false)
    private String triggerGroup;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_GROUP")
    private String jobGroup;

    @Column(length = 250)
    private String description;

    @Column(name = "NEXT_FIRE_TIME")
    private Integer nextFireTime;

    @Column(name = "PREV_FIRE_TIME")
    private Integer prevFireTime;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "TRIGGER_STATE", length = 16, nullable = false)
    private String triggerState;

    @Column(name = "TRIGGER_TYPE", length = 8, nullable = false)
    private String triggerType;

    @Column(name = "START_TIME", nullable = false)
    private Integer startTime;

    @Column(name = "END_TIME")
    private Integer endTime;

    @Column(name = "CALENDAR_NAME", length = 190)
    private String calendarName;

    @Column(name = "MISFIRE_INSTR")
    private int misfireInstr;

    @Column(name = "JOB_DATA")
    private String jobData;

}
