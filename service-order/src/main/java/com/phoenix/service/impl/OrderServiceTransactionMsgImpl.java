package com.phoenix.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.phoenix.feign.ProductService;
import com.phoenix.mapper.OrderMapper;
import com.phoenix.mapper.TxLogMapper;
import com.phoenix.model.Order;
import com.phoenix.model.Product;
import com.phoenix.model.TxLog;
import com.phoenix.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * description: 可靠消息的服务
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
public class OrderServiceTransactionMsgImpl {
    @Resource
    OrderMapper orderMapper;

    @Resource
    ProductService productService;

    @Resource
    TxLogMapper txLogMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    // 基于可靠消息的事务处理
    public void createOrderBefore(Order order) {
        // 事务ID
        String txId = UUID.randomUUID().toString();

        // 1 发送半事务消息
        log.info("1 开始发送半事务消息");
        rocketMQTemplate.sendMessageInTransaction(
                "tx_producer_group_1",
                "tx_topic",
                MessageBuilder.withPayload(order).setHeader("txId", txId).build(),
                order
                );
    }

    @Transactional // 开启本地事务
    public Order createOrder(String txId, Order order) {
        orderMapper.createOrder(order);
        txLogMapper.createLog(new TxLog(txId, new Date()));
        order.setId(order.getId());
        return order;
    }

}
