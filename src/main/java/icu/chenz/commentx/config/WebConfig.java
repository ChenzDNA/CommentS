package icu.chenz.commentx.config;

import icu.chenz.commentx.interceptor.JWTInterceptor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Chenz
 * @date : 2023-01-11 20:09
 */

@Configuration
@ConfigurationProperties(prefix = "webconfig")
public class WebConfig implements WebMvcConfigurer {
    @Setter
    private String secret;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(secret)).addPathPatterns("/**");
    }
}
