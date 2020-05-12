package com.phoenix.feign;

import com.phoenix.feign.fallback.ProductServiceFallback;
import com.phoenix.feign.fallback.ProductServiceFallbackFactory;
import com.phoenix.model.Product;
import org.omg.CORBA.INTERNAL;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * description:
 * date: 2020/5/10 8:48 下午
 * author: phoenix
 * version: 1.0.0
 */

@FeignClient(
        value = "service-product",
        // fallback = ProductServiceFallback.class,
        fallbackFactory = ProductServiceFallbackFactory.class
)
public interface ProductService {

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    Product findById(@PathVariable Integer id);

    @RequestMapping(value = "/product/sku/{pid}/num{num}", method = RequestMethod.GET)
    boolean reduceInventory(@PathVariable Integer pid, @PathVariable Integer num);
}
