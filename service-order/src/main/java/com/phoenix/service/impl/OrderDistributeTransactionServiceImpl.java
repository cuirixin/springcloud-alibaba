package com.phoenix.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.phoenix.feign.ProductService;
import com.phoenix.mapper.OrderMapper;
import com.phoenix.model.Order;
import com.phoenix.model.Product;
import com.phoenix.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 分布式事务Service
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
public class OrderDistributeTransactionServiceImpl {
    @Resource
    OrderMapper orderMapper;

    @Resource
    ProductService productService;

    public Order submitByProductUser(Integer pid, Integer uid) {

        log.info("开启分布式事务: submitByProductUser");

        Product product = productService.findById(pid);
        Order order = new Order();
        order.setUid(uid);
        order.setUsername("victor");
        order.setNum(1);
        order.setPid(product.getId());
        order.setPprice(product.getPrice());
        order.setPtitle(product.getTitle());

        // 创建订单
        orderMapper.createOrder(order);
        order.setId(order.getId());
        // 修改库存
        productService.reduceInventory(pid, 1);

        return order;
    }

}
