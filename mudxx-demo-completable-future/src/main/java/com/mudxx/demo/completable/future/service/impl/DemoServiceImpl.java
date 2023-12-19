package com.mudxx.demo.completable.future.service.impl;

import com.mudxx.demo.completable.future.service.IDemoService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author laiw
 * @date 2023/9/25 15:03
 */
@Service
public class DemoServiceImpl implements IDemoService {

    public String aVoid() {
        System.out.println("执行future2开始-获取订单商品明细..." + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1 / 0;
        return "do2";
    }

}
