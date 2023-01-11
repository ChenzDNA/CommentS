package icu.chenz.commentx.service;

import icu.chenz.commentx.dao.UserDao;
import icu.chenz.commentx.entity.UserEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author : Chenz
 * @date : 2023-01-09 18:19
 */

@Service
public class UserService {
    @Resource
    UserDao userDao;

    public UserEntity login(UserEntity user) {

        return null;
    }
}
