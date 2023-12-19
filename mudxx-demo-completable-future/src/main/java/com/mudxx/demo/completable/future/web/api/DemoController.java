package com.mudxx.demo.completable.future.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author laiw
 * @date 2023/9/25 12:00
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/1")
    public void demo1() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future1开始...");
            return "Hello";
        }, threadPoolExecutor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future2开始...");
            return "World";
        }, threadPoolExecutor);
        future1.thenCombine(future2, (result1, result2) -> {
            String result = result1 + " " + result2;
            System.out.println("获取到future1、future2聚合结果：" + result);
            return result;
        }).thenAccept(System.out::println);

    }

    @GetMapping("/2")
    public void demo2() {

        System.out.println("main..." + Thread.currentThread().getName());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future1开始-计算订单手续费..." + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "do1";
        }, threadPoolExecutor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future2开始-获取订单商品明细..." + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int i = 1 / 0;
            return "do2";
        }, threadPoolExecutor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future3开始-获取订单扩展信息..." + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "do3";
        }, threadPoolExecutor);

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2, future3);
        CompletableFuture<String> resultFuture = allOfFuture.thenApply(v -> {
            try {
                String s1 = future1.join();
                String s2 = future2.join();
                String s3 = future3.join();
                System.out.println("allOf..." + Thread.currentThread().getName());
                return s1 + s2 + s3;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        System.out.println(resultFuture.join());

    }

    @GetMapping("/3")
    public void demo3() {

        System.out.println("main..." + Thread.currentThread().getName());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future1开始-计算订单手续费..." + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "do1";
        }, threadPoolExecutor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future2开始-获取订单商品明细..." + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int i = 1 / 0;
            String s = null;
            return s;
        }, threadPoolExecutor).thenApply(r -> {
            System.out.println(r.toString());
            return r;
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行future3开始-获取订单扩展信息..." + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "do3";
        }, threadPoolExecutor);


        CompletableFuture<Integer> combine = future1.thenCombine((future2), (result1, result2) -> {
            String result = result1 + " " + result2;
            System.out.println("获取到future1、future2聚合结果：" + result);
            return result;
        }).thenCombine(future3, (result1, result2) -> {
            String result = result1 + " " + result2;
            System.out.println("获取到future1、future3聚合结果：" + result);
            return result.length();
        });

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2, future3);
        CompletableFuture<String> resultFuture = allOfFuture.thenApply(v -> {
            try {
                String s1 = future1.get();
                String s2 = future2.get();
                String s3 = future3.get();
                System.out.println("allOf..." + Thread.currentThread().getName());
                return s1 + s2 + s3;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        System.out.println(resultFuture.join());

    }


}
