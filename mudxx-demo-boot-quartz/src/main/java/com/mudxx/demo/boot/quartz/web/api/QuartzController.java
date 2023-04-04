package com.mudxx.demo.boot.quartz.web.api;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.quartz.entity.QuartzBean;
import com.mudxx.demo.boot.quartz.utils.QuartzUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author laiw
 * @date 2023/4/4 11:24
 */
@Controller
@RequestMapping("/api/quartz/")
public class QuartzController {

    /**
     * 注入任务调度
     */
    @Autowired
    private Scheduler scheduler;

    @PostMapping("/createJob")
    @ResponseBody
    public CommonResult<?> createJob(@RequestBody QuartzBean quartzBean) {
        return CommonResult.success(QuartzUtils.createJob(scheduler, quartzBean));
    }

    @GetMapping("/pauseJob")
    @ResponseBody
    public CommonResult<?> pauseJob(String jobName, String jobGroup) {
        return CommonResult.success(QuartzUtils.pauseJob(scheduler, jobName, jobGroup));
    }

    @GetMapping("/resumeJob")
    @ResponseBody
    public CommonResult<?> resumeJob(String jobName, String jobGroup) {
        return CommonResult.success(QuartzUtils.resumeJob(scheduler, jobName, jobGroup));
    }

    @GetMapping("/runOnce")
    @ResponseBody
    public CommonResult<?> runOnce(String jobName, String jobGroup) {
        return CommonResult.success(QuartzUtils.runOnce(scheduler, jobName, jobGroup));
    }

    @PostMapping("/updateJob")
    @ResponseBody
    public CommonResult<?> updateJob(@RequestBody QuartzBean quartzBean) {
        return CommonResult.success(QuartzUtils.updateJob(scheduler, quartzBean));
    }

    @PostMapping("/deleteJob")
    @ResponseBody
    public CommonResult<?> deleteJob(@RequestBody QuartzBean quartzBean) {
        return CommonResult.success(QuartzUtils.deleteJob(scheduler, quartzBean));
    }

    @GetMapping("/startAll")
    @ResponseBody
    public CommonResult<?> startAll() {
        try {
            scheduler.start();
            return CommonResult.success();
        } catch (SchedulerException e) {
            return CommonResult.failed("开启所有任务失败");
        }
    }

    @GetMapping("/pauseAll")
    @ResponseBody
    public CommonResult<?> pauseAll() {
        try {
            scheduler.pauseAll();
            return CommonResult.success();
        } catch (SchedulerException e) {
            return CommonResult.failed("暂停所有任务失败");
        }
    }

    @GetMapping("/resumeAll")
    @ResponseBody
    public CommonResult<?> resumeAll() {
        try {
            scheduler.resumeAll();
            return CommonResult.success();
        } catch (SchedulerException e) {
            return CommonResult.failed("恢复所有任务失败");
        }
    }

    @GetMapping("/shutdown")
    @ResponseBody
    public CommonResult<?> shutdown() {
        try {
            if( ! scheduler.isShutdown()) {
                // 需谨慎操作关闭scheduler容器
                // scheduler生命周期结束，无法再 start() 启动scheduler
                scheduler.shutdown(true);
            }
            return CommonResult.success();
        } catch (SchedulerException e) {
            return CommonResult.failed("关闭所有任务失败");
        }
    }

}
