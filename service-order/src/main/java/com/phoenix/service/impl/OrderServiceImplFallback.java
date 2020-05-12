package com.phoenix.service.impl;

import com.phoenix.model.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * description:
 * date: 2020/5/11 11:23 上午
 * author: phoenix
 * version: 1.0.0
 */

@Slf4j
public class OrderServiceImplFallback {
    // fallback
    // 要求：返回值和参数跟原方法一致；允许最后添加一个Throwable参数; 需要时static
    public static Order submitByProductUserFallback(Integer pid, Integer uid, Throwable e) {
        log.error("触发了Throwable异常,内容为:{}", e);
        return null;
    }
}
