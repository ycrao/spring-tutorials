package com.douyasi.tutorial.blog.config;

import com.douyasi.tutorial.blog.annotation.AuthGatewayUserArgumentResolver;
import com.douyasi.tutorial.blog.annotation.AuthTokenUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * BlogWebMvcConfigurer
 *
 * @author raoyc
 */
@Configuration
public class BlogWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AuthGatewayUserArgumentResolver authGatewayUserArgumentResolver;

    @Autowired
    private AuthTokenUserArgumentResolver authTokenUserArgumentResolver;

    /**
     * addArgumentResolvers
     *
     * @param argumentResolvers argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authGatewayUserArgumentResolver);
        argumentResolvers.add(authTokenUserArgumentResolver);
    }

    /**
     * addCorsMappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/t-blog/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
}
