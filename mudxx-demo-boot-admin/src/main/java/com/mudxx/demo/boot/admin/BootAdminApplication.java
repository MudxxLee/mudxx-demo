package com.mudxx.demo.boot.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laiw
 * @date 2023/4/4 09:26
 */
@SpringBootApplication
@EnableAdminServer
public class BootAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootAdminApplication.class, args);
    }
}

