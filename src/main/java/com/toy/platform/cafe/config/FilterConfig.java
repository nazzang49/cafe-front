package com.toy.platform.cafe.config;

import com.toy.platform.cafe.filter.RequestInitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 필터 설정
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestInitFilter> RequestInitFilter() {
        FilterRegistrationBean<RequestInitFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RequestInitFilter());
        return filterRegistrationBean;
    }
}
