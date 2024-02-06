package com.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer{


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/**")
          .addResourceLocations("classpath:/static/**","classpath:/web/**")
          .resourceChain(true)
          .addResolver(new ResourceHandler());
    }
    
    @Bean
    public ViewResolver internalResourceViewResolver() {
        UrlBasedViewResolver bean = new UrlBasedViewResolver();
        bean.setViewClass(ViewHandler.class);
        return bean;
    }
}
