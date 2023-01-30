package icu.chenz.comments.adapter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Chenz
 * @date : 2023-01-13 1:00
 */

@Data
@Component
@ConfigurationProperties(prefix = "comments.adapter")
public class UserAdapter {
    boolean enable = false;

    String key = null;

    String value = "";

    String tokenHeaderKey = "Authorization";

    String tokenGenerationKey = null;

    String tokenGenerationValue = "";
}
