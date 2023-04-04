package com.mudxx.demo.boot.quartz.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DisallowConcurrentExecution
 *  禁止并发执行多个相同定义的JobDetail, 这个注解是加在Job类上的,
 *  但意思并不是不能同时执行多个Job,而是不能并发执行同一个Job Definition(由JobDetail定义), 但是可以同时执行多个不同的JobDetail。
 * @PersistJobDataAfterExecution
 *  加在Job上,表示当正常执行完Job后, JobDataMap中的数据应该被改动, 下次执行相同的job时, 会接受到已经更新的数据。
 * @author laiw
 * @date 2023/4/4 11:21
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyDemoJob extends QuartzJobBean {

    public static final String EXECUTION_COUNT = "count";


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //TODO 这里写定时任务的执行逻辑
        log.info("动态的定时任务执行时间：{}", DateUtil.now());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobDataMap data = context.getJobDetail().getJobDataMap();
        if( ! data.containsKey(EXECUTION_COUNT)) {
            data.put(EXECUTION_COUNT, "1");
        } else {
            int count = data.getInt(EXECUTION_COUNT);
            log.info("count={}", count);
            count++;
            data.put(EXECUTION_COUNT, String.valueOf(count));
        }

        log.info("动态的定时任务完成时间：{}", DateUtil.now());
    }
}
