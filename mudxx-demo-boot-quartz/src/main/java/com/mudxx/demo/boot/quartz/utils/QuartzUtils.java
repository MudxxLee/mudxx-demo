package com.mudxx.demo.boot.quartz.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mudxx.demo.boot.quartz.entity.QuartzBean;
import com.mudxx.demo.boot.quartz.entity.QuartzExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author laiw
 * @date 2023/4/4 10:48
 */
@Slf4j
public class QuartzUtils {

    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     *
     * @param scheduler  调度器
     * @param quartzBean 定时任务信息类
     */
    public static QuartzExecuteResult createJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            // 如果存在这个任务，则删除
            JobKey jobKey = new JobKey(quartzBean.getJobName(), quartzBean.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobKey)
                    .withDescription(quartzBean.getJobDescription())
                    .build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器trigger
            TriggerKey triggerKey = new TriggerKey(quartzBean.getTriggerName(), quartzBean.getTriggerGroup());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withDescription(quartzBean.getTriggerDescription())
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            return QuartzExecuteResult.succeed();
        } catch (ClassNotFoundException e) {
            log.error(StrUtil.format("创建定时任务出错-定时任务类路径出错：{}", quartzBean.getJobClass()), e);
            return QuartzExecuteResult.failed(StrUtil.format("创建定时任务出错-定时任务类路径出错：{}", quartzBean.getJobClass()));
        } catch (Exception e) {
            log.error(StrUtil.format("创建定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("创建定时任务出错");
        }
    }

    /**
     * 判断定时任务是否存在
     *
     * @param scheduler 调度器
     * @param jobKey    定时任务
     */
    public static boolean existsJob(Scheduler scheduler, JobKey jobKey) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            return ObjectUtil.isNotEmpty(jobDetail);
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 判断触发器是否存在
     *
     * @param scheduler  调度器
     * @param triggerKey 触发器
     */
    public static boolean existsTrigger(Scheduler scheduler, TriggerKey triggerKey) {
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            return ObjectUtil.isNotEmpty(trigger);
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 暂停定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @param jobGroup  定时任务组
     */
    public static QuartzExecuteResult pauseJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (!existsJob(scheduler, jobKey)) {
                return QuartzExecuteResult.failedNotExistJob();
            }
            scheduler.pauseJob(jobKey);
            return QuartzExecuteResult.succeed();
        } catch (Exception e) {
            log.error(StrUtil.format("暂停定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("暂停定时任务出错");
        }
    }

    /**
     * 恢复定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @param jobGroup  定时任务组
     */
    public static QuartzExecuteResult resumeJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (!existsJob(scheduler, jobKey)) {
                return QuartzExecuteResult.failedNotExistJob();
            }
            scheduler.resumeJob(jobKey);
            return QuartzExecuteResult.succeed();
        } catch (Exception e) {
            log.error(StrUtil.format("启动定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("启动定时任务出错");
        }
    }

    /**
     * 立即运行一次定时任务
     *
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     * @param jobGroup  定时任务组
     */
    public static QuartzExecuteResult runOnce(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (!existsJob(scheduler, jobKey)) {
                return QuartzExecuteResult.failedNotExistJob();
            }
            scheduler.triggerJob(jobKey);
            return QuartzExecuteResult.succeed();
        } catch (Exception e) {
            log.error(StrUtil.format("运行定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("运行定时任务出错");
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler  调度器
     * @param quartzBean 定时任务信息类
     */
    public static QuartzExecuteResult updateJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //获取触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getTriggerName(), quartzBean.getTriggerGroup());
            if (!existsTrigger(scheduler, triggerKey)) {
                return QuartzExecuteResult.failedNotExistTrigger();
            }
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
            return QuartzExecuteResult.succeed();
        } catch (Exception e) {
            log.error(StrUtil.format("更新定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("更新定时任务出错");
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler  调度器
     * @param quartzBean 定时任务信息类
     */
    public static QuartzExecuteResult deleteJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //获取触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getTriggerName(), quartzBean.getTriggerGroup());
            if (!existsTrigger(scheduler, triggerKey)) {
                return QuartzExecuteResult.failedNotExistTrigger();
            }
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            JobKey jobKey = JobKey.jobKey(quartzBean.getJobName(), quartzBean.getJobGroup());
            scheduler.deleteJob(jobKey);
            return QuartzExecuteResult.succeed();
        } catch (Exception e) {
            log.error(StrUtil.format("删除定时任务出错：{}", e.getMessage()), e);
            return QuartzExecuteResult.failed("删除定时任务出错");
        }
    }
}

