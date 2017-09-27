package io.tchepannou.www.academy.classroom.config;

import io.tchepannou.www.academy.classroom.servlet.LoggerFilter;
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
    public FilterRegistrationBean loggerFilterBean(){
        return createFilterRegistrationBean(loggerFilter(), 1);
    }

    @Bean
    public Filter loggerFilter(){
        return new LoggerFilter();
    }


    @Bean
    public FilterRegistrationBean loginFilterBean(){
        return createFilterRegistrationBean(loginFilter(), 2);
    }

    @Bean
    public Filter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean requiresUserFilterBean(){
        return createFilterRegistrationBean(requiresUserFilter(), 3);
    }

    @Bean
    public Filter requiresUserFilter(){
        return new RequiresUserFilter();
    }

    private FilterRegistrationBean createFilterRegistrationBean(final Filter filter, final int order) {
        final FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        bean.addUrlPatterns("/classroom/*");
        bean.setOrder(order);
        return bean;
    }

}
