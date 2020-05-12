package com.phoenix.feign.fallback;

import com.phoenix.feign.ProductService;
import com.phoenix.model.Product;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * description: 容错工厂类，
 * date: 2020/5/11 11:52 上午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {

    @Override
    public ProductService create(Throwable throwable) {
        return new ProductService() {
            @Override
            public Product findById(Integer id) {
                log.error("触发Feign Fallback，method={}", "findById");
                throwable.printStackTrace();
                return null;
            }

            @Override
            public boolean reduceInventory(Integer pid, Integer num) {
                log.error("触发Feign Fallback，method={}", "reduceInventory");
                throwable.printStackTrace();
                return false;
            }
        };
    }
}
