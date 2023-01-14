package icu.chenz.commentx.service;

import icu.chenz.commentx.adapter.UserAdapter;
import icu.chenz.commentx.dao.UserDao;
import icu.chenz.commentx.entity.UserEntity;
import icu.chenz.commentx.utils.exception.BadRequest;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


/**
 * @author : Chenz
 * @date : 2023-01-09 18:19
 */

@Service
public class UserService {
    @Resource
    UserDao userDao;

    @Resource
    UserAdapter userAdapter;

    public UserEntity login(UserEntity user) throws Exception {
        UserEntity userEntity = userDao.getByUsername(user.getUsername());
        if (userEntity == null) {
            throw new BadRequest("用户不存在");
        }
        if (userAdapter.isEnable()) {
            if (userAdapter.verifyPassword(user.getUsername(), user.getPassword())) {
                return userEntity;
            } else {
                throw new BadRequest("密码错误");
            }
        }
        if (!BCrypt.checkpw(user.getPassword(), userEntity.getPassword())) {
            throw new BadRequest("密码错误");
        }
        return userEntity;
    }

    public UserEntity register(UserEntity user) throws BadRequest {
        if (userDao.getByUsername(user.getUsername()) != null) {
            throw new BadRequest("用户已存在");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setNickname("新用户" + System.currentTimeMillis());
        userDao.create(user);
        return user;
    }

    public int updateNickname(Long id, String nickname) {
        return userDao.updateNickname(id, nickname);
    }

    public int updatePassword(Long id, String newPassword) {
        return userDao.updatePassword(id, newPassword);
    }

    public UserEntity c(Long id) {
        return userDao.getById(id);
    }

    public int r(UserEntity user) {
        return userDao.create(user);
    }
}
