package icu.chenz.commentx.dao;

import icu.chenz.commentx.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    List<UserEntity> getByIds(List<Long> ids);

    UserEntity getByUsername(String username);

    int updateNickname(Long id, String nickname);

    int updatePassword(Long id, String password);

    int create(UserEntity user);
}
