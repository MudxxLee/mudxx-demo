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
public class QrtzJobDetailsBo implements Serializable {
    private static final long serialVersionUID = -8029315581327588816L;
    private String schedName;
    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClassName;
    private String isDurable;
    private String isNonconcurrent;
    private String isUpdateData;
    private String requestsRecovery;
    private String jobData;
}


