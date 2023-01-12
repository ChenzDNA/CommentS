package icu.chenz.commentx.dao;

import icu.chenz.commentx.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserDao {
    List<UserEntity> getByIds(Set<Long> ids);

    UserEntity getByUsername(String username);

    int updateNickname(Long id, String nickname);

    int updatePassword(Long id, String password);

    int create(UserEntity user);
}
