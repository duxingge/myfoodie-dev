package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        //1.设置Cors配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:8088");
        configuration.addAllowedOrigin("http://localhost");
        configuration.addAllowedOrigin("http://192.168.0.107:8088");
        configuration.addAllowedOrigin("http://192.168.0.107:");
        configuration.addAllowedOrigin("*");
        // 设置是否发送cookie信息
        configuration.setAllowCredentials(true);
        //设置允许的请求头
        configuration.addAllowedHeader("*");
        //设置允许的请求方法
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource corsConigSource = new UrlBasedCorsConfigurationSource();
        //2.为url添加映射路径
        corsConigSource.registerCorsConfiguration("/**",configuration);
        //返回重新定义好的configResource
        return new CorsFilter(corsConigSource);
    }

}
