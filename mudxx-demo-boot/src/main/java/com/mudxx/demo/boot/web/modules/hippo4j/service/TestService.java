package com.mudxx.demo.boot.web.modules.hippo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/5/4 13:41
 */
@Service
public class TestService {

    @Autowired
    private ThreadPoolExecutor bizCommonDynamicExecutor;

    @Autowired
    private ThreadPoolExecutor bizClearDataDynamicExecutor;

    public void aVoid() {
        bizClearDataDynamicExecutor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        });
    }


}
