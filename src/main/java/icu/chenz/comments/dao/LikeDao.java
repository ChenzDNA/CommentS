package icu.chenz.comments.dao;

import icu.chenz.comments.entity.LikeEntity;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : Chenz
 * @date : 2023-02-24 16:58
 */

@Mapper
public interface LikeDao {
    int create(LikeEntity likesEntity);

    @MapKey("cid")
    Map<Long, Map<String, Object>> getCommentsLike(List<Long> commentIDList);

    int delete(Long uid, Long cid);

    LikeEntity getByUIDAndCID(Long uid, Long cid);

    int updateType(Long uid, Long cid, Byte type);

    List<Long> getUserLike(Long uid, List<Long> commentIDList);
}
