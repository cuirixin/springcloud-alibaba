package com.phoenix.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import com.phoenix.domain.SimpleResponseData;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 自定义Sentinel流控异常
 * date: 2020/5/11 10:41 上午
 * author: phoenix
 * version: 1.0.0
 */

@Component
public class ExceptionHandlerDefination implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse responsee, BlockException e) throws IOException {
        responsee.setContentType("application/json;charset=utf-8");
        SimpleResponseData data = new SimpleResponseData(-1, e.toString());
        if (e instanceof FlowException) {
            data = new SimpleResponseData(-101, "接口被限流");
        } else if (e instanceof DegradeException) {
            data = new SimpleResponseData(-102, "接口被降级");
        } else if (e instanceof AuthorityException) {
            data = new SimpleResponseData(-103, "接口授权认证失败");
        }
        responsee.getWriter().write(JSON.toJSONString(data));
    }
}
