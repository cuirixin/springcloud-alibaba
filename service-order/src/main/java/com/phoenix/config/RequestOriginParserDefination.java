package com.phoenix.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * description: 通过Requet域获取到来源标识，配合Sentinel流控进行匹配做授权限制
 * date: 2020/5/11 10:29 上午
 * author: phoenix
 * version: 1.0.0
 */

@Component
public class RequestOriginParserDefination implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String serviceName = request.getParameter("serviceName");
        return StringUtils.isEmpty(serviceName) ? "unknown" : serviceName;
    }
}
