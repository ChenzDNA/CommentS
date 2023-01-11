package icu.chenz.commentx.dao;

import icu.chenz.commentx.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    List<CommentEntity> getByContext(String context);

    List<CommentEntity> getSubCommentsByParentId(Long parent);

    int delete(Long id);

    int create(CommentEntity commentEntity);

    String getContextString(String context);
}
