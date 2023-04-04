package com.mudxx.demo.boot.quartz.database.dao.upk;

import lombok.Data;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/4 14:43
 */
@Data
public class QrtzJobDetailsUPK implements Serializable {
    private static final long serialVersionUID = 7827335773534203742L;
    private String schedName;
    private String jobName;
    private String jobGroup;
}
