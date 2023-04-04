package com.mudxx.demo.boot.quartz.database.service.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/4 14:52
 */
@Data
@NoArgsConstructor
public class QrtzTriggersBo implements Serializable {
    private static final long serialVersionUID = 6758450570156276918L;
    private String schedName;
    private String triggerName;
    private String triggerGroup;
    private String jobName;
    private String jobGroup;
    private String description;
    private Integer nextFireTime;
    private Integer prevFireTime;
    private Integer priority;
    private String triggerState;
    private String triggerType;
    private Integer startTime;
    private Integer endTime;
    private String calendarName;
    private int misfireInstr;
    private String jobData;
}
