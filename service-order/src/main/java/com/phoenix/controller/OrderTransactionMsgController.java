package com.phoenix.controller;

import com.alibaba.fastjson.JSON;
import com.phoenix.domain.SimpleResponseData;
import com.phoenix.feign.ProductService;
import com.phoenix.model.Order;
import com.phoenix.model.Product;
import com.phoenix.service.OrderService;
import com.phoenix.service.impl.OrderServiceTransactionMsgImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: 基于可靠消息的服务
 * date: 2020/5/9 7:01 下午
 * author: phoenix
 * version: 1.0.0
 */

@Slf4j
@RestController
public class OrderTransactionMsgController {

    @Resource
    private OrderServiceTransactionMsgImpl orderService;

    @Resource
    ProductService productService;

    @RequestMapping("/order/txmsg/submit/product/{pid}/user/{uid}")
    public SimpleResponseData submitByProductUser(@PathVariable Integer pid, @PathVariable Integer uid) {

        Product product = productService.findById(pid);

        log.info("查询到商品信息={}", JSON.toJSONString(product));
        if (product == null) {
            return null;
        }

        Order order = new Order();
        order.setUid(1);
        order.setUsername("victor");
        order.setNum(1);
        order.setPid(product.getId());
        order.setPprice(product.getPrice());
        order.setPtitle(product.getTitle());
        orderService.createOrderBefore(order);
        return new SimpleResponseData(0, "", order);
    }
}
