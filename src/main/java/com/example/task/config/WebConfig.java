package com.example.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // /以下の全エンドポイントに適用
                .allowedOrigins("https://various11project.com")  // 本番用
                //.allowedOrigins("http://localhost:3000") // ReactのURLを指定
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 必要なHTTPメソッドを許可
                .allowCredentials(true); // クッキー情報のやり取りが必要な場合
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                //.addResourceLocations("file:///C:/task_work/")  // ローカル用
                .addResourceLocations("file:/var/www/images/")  // AWS環境用
                .setCachePeriod(0); // キャッシュを無効化
    }

    public DelegatingFilterProxy springSessionRepositoryFilter() {
        return new DelegatingFilterProxy("springSessionRepositoryFilter");
    }
}