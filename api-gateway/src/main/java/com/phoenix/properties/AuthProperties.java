package com.phoenix.properties;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * description:
 * date: 2020/5/8 10:00 下午
 * author: phoenix
 * version: 1.0.0
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "gateway-auth")
public class AuthProperties implements InitializingBean {

    private static final String NORMAL = "(\\w|\\d|-)+";
    private List<Pattern> urlPatterns = new ArrayList<>(10);
    private List<Pattern> serverPatterns = new ArrayList<>(10);

    public List<Pattern> getLoginIgnoreUrlPatterns() {
        return loginIgnoreUrlPatterns;
    }

    public void setLoginIgnoreUrlPatterns(List<Pattern> loginIgnoreUrlPatterns) {
        this.loginIgnoreUrlPatterns = loginIgnoreUrlPatterns;
    }

    private List<Pattern> loginIgnoreUrlPatterns = new ArrayList<>(10);
    private List<String> apiServers;
    private List<String> apiUrls;

    private List<String> loginIgnoreUrls;

    public List<Pattern> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<Pattern> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public List<Pattern> getServerPatterns() {
        return serverPatterns;
    }

    public void setServerPatterns(List<Pattern> serverPatterns) {
        this.serverPatterns = serverPatterns;
    }

    public List<String> getApiUrls() {
        return apiUrls;
    }

    public void setApiUrls(List<String> apiUrls) {
        this.apiUrls = apiUrls;
    }

    public List<String> getApiServers() {
        return apiServers;
    }

    public void setApiServers(List<String> apiServers) {
        this.apiServers = apiServers;
    }

    public List<String> getLoginIgnoreUrls() {
        return loginIgnoreUrls;
    }

    public void setLoginIgnoreUrls(List<String> loginIgnoreUrls) {
        this.loginIgnoreUrls = loginIgnoreUrls;
    }

    /**
     * This {@link AuthProperties} init
     */
    @Override
    public void afterPropertiesSet() {
        apiServers.stream().map(d -> d.replace("*", NORMAL)).map(Pattern::compile).forEach(serverPatterns::add);
        apiUrls.stream().map(s -> s.replace("*", NORMAL)).map(Pattern::compile).forEach(urlPatterns::add);
        loginIgnoreUrls.stream().map(i -> i.replace("*", NORMAL)).map(Pattern::compile).forEach(loginIgnoreUrlPatterns::add);
        log.info("============> Configuration Instance IDs : {} , Whitelist Urls : {}, IgnoreLogin Urls : {}", serverPatterns, urlPatterns, loginIgnoreUrlPatterns);
    }
}