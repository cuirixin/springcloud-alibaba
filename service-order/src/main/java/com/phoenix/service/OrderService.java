package com.phoenix.service;

import com.phoenix.model.Order;

/**
 * description:
 * date: 2020/5/9 7:01 下午
 * author: phoenix
 * version: 1.0.0
 */
public interface OrderService {
    Order submitByProductUser(Integer pid, Integer uid);
}
