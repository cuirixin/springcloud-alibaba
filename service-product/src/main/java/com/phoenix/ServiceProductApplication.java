package com.phoenix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description:
 * date: 2020/5/9 6:25 下午
 * author: phoenix
 * version: 1.0.0
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.phoenix.mapper")
@EnableDiscoveryClient
public class ServiceProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class, args);
    }
}
