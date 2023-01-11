package icu.chenz.commentx;

import icu.chenz.commentx.dao.CommentDao;
import icu.chenz.commentx.dao.UserDao;
import icu.chenz.commentx.entity.CommentEntity;
import icu.chenz.commentx.entity.UserEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CommentXApplicationTests {
    @Resource
    UserDao userDao;

    @Resource
    CommentDao commentDao;

    @Test
    void contextLoads() {
        List<UserEntity> byIds = userDao.getByIds(new ArrayList<>() {
            {
                add(1L);
                add(2L);
            }
        });
        System.out.println(byIds);
    }

    @Test
    void comments() {
        List<CommentEntity> byContext = commentDao.getByContext("123");
        System.out.println(byContext);
    }

}
