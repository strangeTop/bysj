package com.sjc.bysj.filter;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

@Configuration
public class AllFilter implements Filter {

    @Bean
    public FilterRegistrationBean<AllFilter> getAllFilter(){
        FilterRegistrationBean<AllFilter> f=new FilterRegistrationBean<>();
        f.setFilter(new AllFilter());
        f.addUrlPatterns("/*");
        return f;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
