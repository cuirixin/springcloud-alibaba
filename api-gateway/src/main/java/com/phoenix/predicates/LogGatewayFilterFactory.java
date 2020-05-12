package com.phoenix.predicates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * description:
 * date: 2020/5/11 3:18 下午
 * author: phoenix
 * version: 1.0.0
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {
    public static final Boolean CONSOLE_LOG = true;
    public static final Boolean CACHE_LOG = false;

    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.isConsoleLog()) {
                    System.out.println("开启了Consolelog");
                }
                if (config.isCacheLog()) {
                    System.out.println("开启了Cachelog");
                }
                return chain.filter(exchange);
            }
        };
    }


    @Data
    @NoArgsConstructor
    public static class Config {
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
