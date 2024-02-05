package com.mudxx.demo.boot.page.modules.services.impl;

import cn.hutool.core.date.DateUtil;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.demo.boot.page.modules.services.IRetryTestService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author laiw
 * @date 2023/9/8 10:14
 */
@Service
public class RetryTestServiceImpl implements IRetryTestService {

    @Override
    @Retryable(value = BizException.class, maxAttempts = 6, backoff = @Backoff(delay = 2000, multiplier = 3))
    public void retryServiceOne(int code) {
        System.out.println("retryService被调用,时间：" + DateUtil.now());
        System.out.println("执行当前业务逻辑的线程名：" + Thread.currentThread().getName());
        if (code == 0) {
            throw new BizException("业务执行失败情况！");
        }
        System.out.println("retryServiceOne执行成功！");
    }

}
