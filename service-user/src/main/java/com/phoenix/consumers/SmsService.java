package com.phoenix.consumers;

import com.alibaba.fastjson.JSON;
import com.phoenix.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * description:
 * date: 2020/5/12 2:44 下午
 * author: phoenix
 * version: 1.0.0
 */

@Slf4j
@Service
@ConditionalOnProperty(prefix = "consumer", name = "enabled", havingValue = "true")
@RocketMQMessageListener(
        consumerGroup = "service-user",
        topic = "order-topic",
        consumeMode = ConsumeMode.CONCURRENTLY, // 消费模式，无序和有序，默认是无序
        messageModel = MessageModel.CLUSTERING // 消息模式，广播和集群，默认是集群
)
public class SmsService implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
          log.info("收到消息：{}", JSON.toJSONString(order));
    }
}
