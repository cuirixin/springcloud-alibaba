package com.phoenix;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * description:
 * date: 2020/5/12 1:24 下午
 * author: phoenix
 * version: 1.0.0
 */
public class RocketMQReceive {
    public static void main(String[] args) throws MQClientException {
        // 1 创建消费者，并指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myconsumer-group-1");
        // 2 为消费者设置NameServer
        consumer.setNamesrvAddr("192.168.33.10:9876");
        // 3 指定消费者订阅的主体和标签
        consumer.subscribe("myTopic", "*");
        // 4 设置回调函数，处理接收
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 5 启动消费者
        consumer.start();
        System.out.println("Consumer started....");
    }
}
