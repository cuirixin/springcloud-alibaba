package com.phoenix.model;

import lombok.Data;

/**
 * description:
 * date: 2020/5/9 6:07 下午
 * author: phoenix
 * version: 1.0.0
 */

@Data
public class Product {
    private Integer id;
    private String title;
    private Double price;
    private Integer skuNum;
}
