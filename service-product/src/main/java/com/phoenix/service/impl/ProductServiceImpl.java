package com.phoenix.service.impl;

import com.phoenix.mapper.ProductMapper;
import com.phoenix.model.Product;
import com.phoenix.service.ProductService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * description:
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public Product getById(Integer id) {
        return productMapper.getById(id);
    }

    @Override
    @Transactional
    public boolean reduceInventory(Integer pid, Integer num) {
        Product p = new Product();
        p.setId(pid);
        p.setSkuNum(num);
        productMapper.reduceInventory(p);
        return true;
    }
}
