package com.mudxx.demo.boot.redis;

import java.util.concurrent.CountDownLatch;

/**
 * @author laiw
 * @date 2023/9/26 14:59
 */
public class ThreadTest {
    private static final int threadNum = 10;
    private static final CountDownLatch cdl = new CountDownLatch(threadNum);

    static class DelayMessage implements Runnable {
        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            AppTest appTest = new AppTest();
            appTest.consumerDelayMessage();
        }
    }

    public static void main(String[] args) {
        AppTest appTest = new AppTest();
        appTest.productionDelayMessage();

        for (int i = 0; i < threadNum; i++) {
            new Thread(new DelayMessage()).start();
            cdl.countDown();
        }
    }
}
