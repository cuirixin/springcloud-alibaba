package com.phoenix.service.impl;

import com.phoenix.mapper.TxLogMapper;
import com.phoenix.model.Order;
import com.phoenix.model.TxLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * description: 可靠消息的服务
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
@RocketMQTransactionListener(txProducerGroup = "tx_producer_group_1")
public class OrderServiceTransactionMsgImplListener implements RocketMQLocalTransactionListener {

    @Resource
    OrderServiceTransactionMsgImpl orderServiceTransactionMsg;

    @Resource
    TxLogMapper txLogMapper;

    // 2 消息服务端半事务消息发送成功回调
    // 3 执行本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("2 消息服务端半事务消息发送成功回调");
        String txId = (String) message.getHeaders().get("txId");
        try{
            log.info("3 执行本地事务");
            Order order = (Order)o;
            orderServiceTransactionMsg.createOrder(txId, order);
            // 4.1 执行Commit
            log.info("4 执行Commit");
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e) {
            log.error("Transaction fail: {}", e);
            log.info("4 执行Rollback");
            // 4.1 执行Rollback
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    // 5 异常消息回查（消息服务端长时间未收到消息确认Commit或Rollback）
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("5 执行回查");
        String txId = (String) message.getHeaders().get("txId");
        TxLog log = txLogMapper.getByTxId(txId);
        if (log != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
