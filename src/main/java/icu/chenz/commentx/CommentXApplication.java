package icu.chenz.commentx;

import icu.chenz.commentx.adapter.UserAdapter;
import icu.chenz.commentx.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WebConfig.class, UserAdapter.class})
public class CommentXApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentXApplication.class, args);
    }

}
