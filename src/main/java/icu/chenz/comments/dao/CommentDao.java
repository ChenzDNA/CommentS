package icu.chenz.comments.dao;

import icu.chenz.comments.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    CommentEntity getById(Long id);

    List<CommentEntity> getByContext(String context);

    List<CommentEntity> getSubCommentsByParentId(Long parent);

    int delete(Long id);

    int create(CommentEntity commentEntity);
}
