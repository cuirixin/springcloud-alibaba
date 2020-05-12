package com.phoenix.model;

import lombok.Data;

/**
 * description:
 * date: 2020/5/9 6:10 下午
 * author: phoenix
 * version: 1.0.0
 */

@Data
public class Order {
    private Long id;
    // 用户
    private Integer uid;
    private String username;
    private Integer pid;
    // 商品
    private String ptitle;
    private Double pprice;
    private Integer num;
}
