package com.mudxx.demo.boot.quartz.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/4 11:29
 */
@Data
public class QuartzExecuteResult implements Serializable {
    private static final long serialVersionUID = 7437359960624550307L;
    /**
     * 执行结果
     */
    private Boolean execute;
    /**
     * 结果描述
     */
    private String description;

    private QuartzExecuteResult(Boolean execute, String description) {
        this.execute = execute;
        this.description = description;
    }

    public static QuartzExecuteResult succeed() {
        return new QuartzExecuteResult(true, "操作成功");
    }

    public static QuartzExecuteResult succeed(String description) {
        return new QuartzExecuteResult(true, description);
    }

    public static QuartzExecuteResult failed() {
        return new QuartzExecuteResult(false, "操作失败");
    }

    public static QuartzExecuteResult failed(String description) {
        return new QuartzExecuteResult(false, description);
    }

    public static QuartzExecuteResult failedNotExistJob() {
        return failed("定时任务不存在");
    }

    public static QuartzExecuteResult failedNotExistTrigger() {
        return failed("触发器不存在");
    }

}
