package io.tchepannou.www.academy.classroom.config;

import io.tchepannou.www.academy.classroom.servlet.LoginFilter;
import io.tchepannou.www.academy.classroom.servlet.RequiresUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class SecurityConfiguration {
    //-- Filters
    @Bean
    public FilterRegistrationBean loginFilterBean(){
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(loginFilter());
        filter.addUrlPatterns("/classroom/*");
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public Filter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean requiresUserFilterBean(){
        final FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(requiresUserFilter());
        filter.addUrlPatterns("/classroom/*");
        filter.setOrder(2);
        return filter;
    }

    @Bean
    public Filter requiresUserFilter(){
        return new RequiresUserFilter();
    }
}
