package icu.chenz.comments.config;

import icu.chenz.comments.interceptor.JWTInterceptor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Chenz
 * @date : 2023-01-11 20:09
 */

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "comments.jwt")
public class WebConfig implements WebMvcConfigurer {
    private String secret = null;

    private Long expireTime = 86400000L;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(secret, expireTime)).addPathPatterns("/**");
    }
}
