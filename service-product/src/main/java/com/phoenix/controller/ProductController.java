package com.phoenix.controller;

import com.alibaba.fastjson.JSON;
import com.phoenix.mapper.ProductMapper;
import com.phoenix.model.Product;
import com.phoenix.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description:
 * date: 2020/5/9 7:01 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/product/{id}")
    public Product getProduct(@PathVariable Integer id) {
        log.info("获取商品信息ID={}", id);
        Product p = productService.getById(id);
        log.info("获取到商品内容为={}", JSON.toJSONString(p));
        p.setTitle(p.getTitle() + ":" + port);
        return p;
    }


    @RequestMapping(value = "/product/sku/{pid}/num{num}", method = RequestMethod.GET)
    public boolean reduceInventory(@PathVariable Integer pid, @PathVariable Integer num) {
        log.info("开始扣减库存pid={} num={}", pid, num);
        productService.reduceInventory(pid, num);
        return true;
    }
}
