package icu.chenz.comments.service;

import icu.chenz.comments.dao.LikeDao;
import icu.chenz.comments.entity.LikeEntity;
import icu.chenz.comments.utils.exception.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : Chenz
 * @date : 2023-02-25 16:00
 */

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public int delete(Long uid, Long cid) {
        LikeEntity like = likeDao.getByUIDAndCID(uid, cid);
        if (like == null) {
            return 0;
        }
        return likeDao.delete(uid, cid);
    }

    public int create(Long uid, Long cid, Byte type) throws BadRequest {
        if (type == null || (type != 0 && type != 1)) {
            throw new BadRequest("错误");
        }
        LikeEntity like = likeDao.getByUIDAndCID(uid, cid);
        if (like != null) {
            if (type.equals(like.getType())) {
                return 0;
            }
            return likeDao.updateType(uid, cid, type);
        }
        return likeDao.create(new LikeEntity(uid, cid, type));
    }
}
