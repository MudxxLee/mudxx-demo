package com.mudxx.demo.boot.quartz.database.dao.upk;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/4 14:47
 */
public class QrtzTriggersUPK implements Serializable {
    private static final long serialVersionUID = 7827335773534203742L;
    private String schedName;
    private String triggerName;
    private String triggerGroup;
}
