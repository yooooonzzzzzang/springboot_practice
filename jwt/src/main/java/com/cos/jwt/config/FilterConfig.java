package com.cos.jwt.config;

import com.cos.jwt.filter.MyFilter1;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
// 시큐리티 필터 먼저 실행 후 실행
    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");  // 모든 요청에서 다해라
        bean.setOrder(0);  // 낮은 번호가 필터 중에서 가장 먼저 실행됨
        return bean;
    }
}
