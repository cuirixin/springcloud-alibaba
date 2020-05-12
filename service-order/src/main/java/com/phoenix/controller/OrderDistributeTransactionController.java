package com.phoenix.controller;

import com.phoenix.domain.SimpleResponseData;
import com.phoenix.model.Order;
import com.phoenix.service.OrderService;
import com.phoenix.service.impl.OrderDistributeTransactionServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: 分布式事务
 * date: 2020/5/9 7:01 下午
 * author: phoenix
 * version: 1.0.0
 */

@RestController
public class OrderDistributeTransactionController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private OrderDistributeTransactionServiceImpl orderService;

    @RequestMapping("/order/distrans/submit/product/{pid}/user/{uid}")
    public SimpleResponseData submitByProductUser(@PathVariable Integer pid, @PathVariable Integer uid) {
        Order order = orderService.submitByProductUser(pid, uid);

        // 写队列
        if (order != null) {
            rocketMQTemplate.convertAndSend("order-topic", order);
        }
        return new SimpleResponseData(0, "", order);
    }
}
