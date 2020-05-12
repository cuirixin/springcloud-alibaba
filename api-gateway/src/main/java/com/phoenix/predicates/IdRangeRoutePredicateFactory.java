package com.phoenix.predicates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * description: 自定义断言, IdRange
 * date: 2020/5/11 2:40 下午
 * author: phoenix
 * version: 1.0.0
 */
@Component
public class IdRangeRoutePredicateFactory extends AbstractRoutePredicateFactory<IdRangeRoutePredicateFactory.Config> {

    public IdRangeRoutePredicateFactory() {
        super(IdRangeRoutePredicateFactory.Config.class);
    }

    // 用于从配置文件中获取参数复制到配置类中的属性上
    @Override
    public List<String> shortcutFieldOrder() {
        // 这里的顺序要跟配置文件中的参数顺序一致
        return Arrays.asList("minId", "maxId");
    }

    @Override
    public Predicate<ServerWebExchange> apply(IdRangeRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String idStr = serverWebExchange.getRequest().getQueryParams().getFirst("id");
                if (StringUtils.isNotEmpty(idStr)) {
                    int id = Integer.parseInt(idStr);
                    return id >= config.getMinId() && id <= config.getMaxId();
                }
                return false;
            }
        };
    }

    // 用于接收配置文件中参数
    @Data
    @NoArgsConstructor
    public static class Config {
        private int minId;
        private int maxId;
    }
}

