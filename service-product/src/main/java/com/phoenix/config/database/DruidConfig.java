package com.phoenix.config.database;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * 这种自定义数据源写法跟配置文件中只配置druid效果一样，prefix可以自定义
     * @return
    @ConfigurationProperties(prefix = "druid")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }
     */

    /**
     * druid监控台配置
     */
    @Bean
    public ServletRegistrationBean druidServlet() {

        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        // IP白名单
        reg.addInitParameter("allow", "localhost,127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        reg.addInitParameter("deny", "/deny");
        //控制台管理用户
        reg.addInitParameter("loginUsername", "admin");
        reg.addInitParameter("loginPassword", "admin888");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        reg.addInitParameter("resetEnable", "false");
        logger.info(" druid console manager init : ", reg);
        return reg;
    }

    /**
     * filter配置
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico, /druid/*");
        logger.info(" druid filter register : {} ", filterRegistrationBean);
        return filterRegistrationBean;
    }

}
