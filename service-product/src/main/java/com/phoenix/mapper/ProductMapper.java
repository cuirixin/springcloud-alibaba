package com.phoenix.mapper;

import com.phoenix.model.Product;
import org.springframework.stereotype.Repository;

/**
 * description:
 * date: 2020/5/9 6:05 下午
 * author: phoenix
 * version: 1.0.0
 */
@Repository
public interface ProductMapper {
    Product getById(Integer id);
    void reduceInventory(Product p);
}
