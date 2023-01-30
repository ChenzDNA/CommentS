package icu.chenz.comments.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Chenz
 * @date : 2023-01-26 20:21
 */

@Data
@Component
@ConfigurationProperties(prefix = "comments.author")
public class AuthorConfig {
    private String name = null;
}
