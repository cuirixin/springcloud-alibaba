package com.phoenix;

import com.phoenix.anno.ExcludeCompScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * description:
 * date: 2020/5/9 6:25 下午
 * author: phoenix
 * version: 1.0.0
 */
@MapperScan("com.phoenix.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeCompScan.class)})
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }

    @Bean
    @LoadBalanced // 启用Ribbon负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
