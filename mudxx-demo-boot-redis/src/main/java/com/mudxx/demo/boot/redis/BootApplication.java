package com.mudxx.demo.boot.redis;

import com.mudxx.demo.boot.redis.utils.SpringContextStaticUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author laiw
 * @date 2023/4/3 11:45
 */
@SpringBootApplication(scanBasePackages = "com.mudxx")
public class BootApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BootApplication.class, args);
        SpringContextStaticUtils.setApplicationContext(applicationContext);
    }
}
