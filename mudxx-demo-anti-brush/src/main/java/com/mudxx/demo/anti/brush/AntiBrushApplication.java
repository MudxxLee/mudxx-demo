package com.mudxx.demo.anti.brush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laiw
 * @date 2023/3/31 14:10
 */
@SpringBootApplication(scanBasePackages = {"com.mudxx"})
public class AntiBrushApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntiBrushApplication.class, args);
    }

}
