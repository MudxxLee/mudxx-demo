package com.mudxx.demo.boot.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laiw
 * @date 2023/4/6 16:47
 */
@SpringBootApplication(scanBasePackages = "com.mudxx")
public class BootGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootGeneratorApplication.class, args);
    }
}
