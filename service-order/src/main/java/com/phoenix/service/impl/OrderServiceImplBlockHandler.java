package com.phoenix.service.impl;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.phoenix.model.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * description:
 * date: 2020/5/11 11:25 上午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
public class OrderServiceImplBlockHandler {
    // blockHandler
    // 要求：返回值和参数跟原方法一致；允许最后添加一个BlockException参数
    public static Order submitByProductUserBlockHandler(Integer pid, Integer uid, BlockException e) {
        log.error("触发了Block异常,内容为:{}", e);
        return null;
    }
}
