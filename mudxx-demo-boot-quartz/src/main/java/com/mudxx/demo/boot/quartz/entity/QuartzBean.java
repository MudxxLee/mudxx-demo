package com.mudxx.demo.boot.quartz.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 定时任务信息类
 *
 * @author laiw
 * @date 2023/4/4 10:47
 */
@Data
public class QuartzBean implements Serializable {
    private static final long serialVersionUID = 6110290165651506078L;
    /**
     * 任务id
     */
    private String id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     * 任务执行类
     */
    private String jobClass;
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 触发器组
     */
    private String triggerGroup;
    /**
     * 任务描述
     */
    private String triggerDescription;
    /**
     * 任务状态 启动还是暂停
     */
    private Integer status;
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
}
