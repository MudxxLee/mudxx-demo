package com.mudxx.demo.boot.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author laiw
 * @date 2023/4/3 11:45
 */
@SpringBootApplication(scanBasePackages = "com.mudxx")
@EnableRetry
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
