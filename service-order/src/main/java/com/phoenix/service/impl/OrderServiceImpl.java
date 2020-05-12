package com.phoenix.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.phoenix.feign.ProductService;
import com.phoenix.mapper.OrderMapper;
import com.phoenix.model.Order;
import com.phoenix.model.Product;
import com.phoenix.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * description:
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;

    // @Resource
    // RestTemplate restTemplate;

    // @Resource
    // DiscoveryClient discoveryClient;

    @Resource
    ProductService productService;

    // 定义一个资源
    // 定义当资源内部发生异常的时候的处理逻辑
    // blockHandler 捕获BlockException
    // fallback 捕获throwable业务异常「开发人员关注」
    @SentinelResource(
            value = "submitByProductUser",
            blockHandler = "submitByProductUserBlockHandler",
            blockHandlerClass = OrderServiceImplBlockHandler.class,
            fallback = "submitByProductUserFallback",
            fallbackClass = OrderServiceImplFallback.class
    )
    @Override
    public Order submitByProductUser(Integer pid, Integer uid) {

        // 方式一
        // List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        // ServiceInstance instance = instances.get(new Random().nextInt(instances.size()));
        // String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + pid;
        // Product product = restTemplate.getForObject(url, Product.class);

        // 方式二：Feign
        Product product = productService.findById(pid);

        log.info("查询到商品信息={}", JSON.toJSONString(product));
        if (product == null) {
            return null;
        }

        Order order = new Order();
        order.setUid(1);
        order.setUsername("victor");
        order.setNum(1);
        order.setPid(product.getId());
        order.setPprice(product.getPrice());
        order.setPtitle(product.getTitle());

        orderMapper.createOrder(order);
        order.setId(order.getId());
        return order;
    }

}
