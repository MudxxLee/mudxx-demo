package com.mudxx.demo.boot.config;

import com.mudxx.demo.boot.encypt.Des3;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author laiw
 * @date 2023/4/3 16:25
 */
@Configuration
public class DataSourceConfiguration {

    @Autowired
    DataSourceProperties properties;

    @Bean
    public DataSource dataSource() throws Exception{
        String username = Des3.decryptThreeDESECB(properties.getUsername(), Des3.DES3_KEY);
        String password = Des3.decryptThreeDESECB(properties.getPassword(), Des3.DES3_KEY);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
