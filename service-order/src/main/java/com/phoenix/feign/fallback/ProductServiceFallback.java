package com.phoenix.feign.fallback;

import com.phoenix.feign.ProductService;
import com.phoenix.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * description: 容错类，需要实现Feign中的所有接口
 * date: 2020/5/11 11:52 上午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
public class ProductServiceFallback implements ProductService {
    @Override
    public Product findById(Integer id) {
        log.error("触发Feign Fallback，method={}", "findById");
        return null;
    }

    @Override
    public boolean reduceInventory(Integer pid, Integer num) {
        log.error("触发Feign Fallback，method={}", "reduceInventory");
        return false;
    }
}
