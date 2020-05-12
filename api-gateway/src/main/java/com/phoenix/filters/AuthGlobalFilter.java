package com.phoenix.filters;

import com.phoenix.config.GatewayContext;
import com.phoenix.properties.AuthProperties;
import com.phoenix.result.ResultEnum;
import com.phoenix.result.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

/**
 * 全局网关过滤器
 *  白名单
 *  Auth权限
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private AuthProperties authProperties;

    private final static String AUTHORIZATION = "Authorization";


    /**
     * 失败的请求会格式 response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
     * 成功的请求会通过各个微服务自行转发的json格式
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayContext context = new GatewayContext();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String urlPath = exchange.getRequest().getURI().getPath();
        // 黑名单校验
        if (blackServersCheck(context, urlPath)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.BLACK_SERVER_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return response.writeWith(Flux.just(buffer));
        }
        // 白名单校验
        if (whiteListCheck(context, urlPath)) {
            // 登录校验
            if (!loginIgnoreListCheck(context, urlPath)) {
                log.debug("登录认证 Required: " + urlPath);
                authToken(context, request);
            } else {
                log.debug("登录认证 Ignore: " + urlPath);
            }
            if (!context.isDoNext()) {
                byte[] failureInfo = ResultJson.failure(ResultEnum.LOGIN_ERROR_GATEWAY, context.getRedirectUrl()).toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                return response.writeWith(Flux.just(buffer));
            }
            ServerHttpRequest mutateReq = exchange.getRequest().mutate().header(AUTHORIZATION, context.getUserToken()).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutateReq).build();
            return chain.filter(mutableExchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            byte[] failureInfo = ResultJson.failure(ResultEnum.WHITE_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(failureInfo);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return response.writeWith(Flux.just(buffer));
        }
    }


    private void authToken(GatewayContext context, ServerHttpRequest request) {
        try {
            // boolean isLogin = "success";
            boolean isLogin = true;
            if (isLogin) {
                // String token = getToken;
                String token = "";
                context.setUserToken(token);
            } else {
                unLogin(context);
            }
        } catch (Exception e) {
            log.error("Invoke SSO Exception :{}", e.getMessage());
            context.setDoNext(false);
        }
    }

    /**
     * + "?returnUrl=" + request.getURI()
     */
    private void unLogin(GatewayContext context) {
        context.setRedirectUrl(getSsoUrl());
        context.setDoNext(false);
        log.info("Check Current Session is No Login, Return New Url Page : {} ", getSsoUrl());
    }


    /**
     * Check white
     *
     * @param context
     * @param urlPath
     * @return
     */
    private boolean whiteListCheck(GatewayContext context, String urlPath) {
        Assert.notNull(authProperties, () -> "AuthProperties '" + authProperties + "' is null");
        boolean white = authProperties.getUrlPatterns().stream()
                .map(pattern -> pattern.matcher(urlPath))
                .anyMatch(Matcher::find);
        if (white) {
            context.setPath(urlPath);
            return true;
        }
        return false;
    }

    /**
     * Check loginIgnore
     *
     * @param context
     * @param urlPath
     * @return
     */
    private boolean loginIgnoreListCheck(GatewayContext context, String urlPath) {
        boolean ignore = authProperties.getLoginIgnoreUrlPatterns().stream()
                .map(pattern -> pattern.matcher(urlPath))
                .anyMatch(Matcher::find);
        if (ignore) {
            return true;
        }
        return false;
    }

    /**
     * 黑名单，禁止采用ServiceID的方式访问，http://[GATEWAY_HOST]/CONSUMER-USER/buyTicket
     * @param context
     * @param urlPath
     * @return
     */
    private boolean blackServersCheck(GatewayContext context, String urlPath) {
        //See whiteListCheck() is Check
        String instanceId = urlPath.substring(1, urlPath.indexOf('/', 1));
        if (!CollectionUtils.isEmpty(authProperties.getApiServers())) {
            boolean black = authProperties.getServerPatterns().stream()
                    .map(pattern -> pattern.matcher(instanceId))
                    .anyMatch(Matcher::find);
            if (black) {
                context.setBlack(true);
                return true;
            }
        }
        return false;
    }


    @Deprecated
    private String getSsoUrl() {
        // String env = "test";
        String env = "test/prod/dev/pre";
        switch (env) {
            // delete StringPool.TEST
            case "test":
                return "/test";
            case "prod":
                return "/prod";
            default:
                return "pre";
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
