package icu.chenz.comments;

import icu.chenz.comments.config.AuthorConfig;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentSApplicationTests {

    @Resource
    private AuthorConfig authorConfig;

    @Test
    public void s() {
        System.out.println(authorConfig.getName());
    }

}
