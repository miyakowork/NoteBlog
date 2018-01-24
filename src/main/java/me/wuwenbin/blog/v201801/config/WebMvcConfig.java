package me.wuwenbin.blog.v201801.config;

import me.wuwenbin.blog.v201801.config.interceptor.AdminInterceptor;
import me.wuwenbin.blog.v201801.config.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by Wuwenbin on 2018/1/17 at 19:37
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private final Environment environment;

    @Autowired
    public WebMvcConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 配置静态资源路径以及上传文件的路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations(environment.getProperty("spring.resources.static-locations"));
    }

    /**
     * 配置后台以及前台的一些拦截
     *
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/management/**", "/token/**");
//        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/management/**");
//    }
}