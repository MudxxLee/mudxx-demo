package com.mudxx.demo.boot.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laiw
 * @date 2023/4/6 10:21
 */
@SpringBootApplication(scanBasePackages = "com.mudxx")
public class BootMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootMessageApplication.class, args);
    }
}
