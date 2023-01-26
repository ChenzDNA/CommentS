package icu.chenz.comments.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Chenz
 * @date : 2023-01-26 20:21
 */

@Component
@ConfigurationProperties(prefix = "comments.author")
public class AuthorConfig {
    @Getter
    @Setter
    private String name = null;
}
