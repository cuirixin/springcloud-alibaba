package com.phoenix;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * description:
 * date: 2020/5/12 12:36 下午
 * author: phoenix
 * version: 1.0.0
 */

@SpringBootTest
public class RocketMQTests {

    @Test
    public void sendMessage() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        // 1 创建生产者，并设置组名
        DefaultMQProducer producer = new DefaultMQProducer("myproducer-group-1");
        // 2 设置NameServer
        producer.setNamesrvAddr("192.168.33.10:9876");
        // 3 启动
        producer.start();
        // 4 创建发送
        Message message = new Message("myTopic", "myTag", ("TestMessage").getBytes());
        SendResult result = producer.send(message, 3000);
        System.out.println(result);
        // 5 关闭
        producer.shutdown();
    }

}