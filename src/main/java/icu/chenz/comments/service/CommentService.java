package icu.chenz.comments.service;

import icu.chenz.comments.config.AuthorConfig;
import icu.chenz.comments.dao.CommentDao;
import icu.chenz.comments.dao.ContextDao;
import icu.chenz.comments.dao.UserDao;
import icu.chenz.comments.entity.CommentEntity;
import icu.chenz.comments.entity.ContextEntity;
import icu.chenz.comments.entity.UserEntity;
import icu.chenz.comments.utils.exception.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author : Chenz
 * @date : 2023-01-09 18:19
 */

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;

    private final UserDao userDao;

    private final AuthorConfig authorConfig;

    private final ContextDao contextDao;

    public Map<String, Object> getByContext(Long user, String context) {
        HashMap<String, Object> res = new HashMap<>(2);
        res.put("author", authorConfig.getName());
        List<CommentEntity> comments = commentDao.getByContext(context);
        if (needContext(comments, user)) {
            contextDao.createContext(context);
        }
        res.put("comments", comments);
        if (comments == null || comments.size() == 0) {
            res.put("users", new ArrayList<>());
            return res;
        }
        HashSet<Long> query = new HashSet<>(comments.stream().map(CommentEntity::getUser).toList());
        comments.stream()
                .map(CommentEntity::getSubComments)
                .forEach(item -> query.addAll(item.stream().map(CommentEntity::getUser).toList()));
        res.put("users", userDao.getByIds(query));
        return res;
    }

    public int delete(Long user, Long id) throws BadRequest {
        CommentEntity comment = commentDao.getById(id);
        if (!comment.getUser().equals(user)) {
            throw new BadRequest("不能删除别人的评论哦");
        }
        return commentDao.delete(id);
    }

    public CommentEntity comment(CommentEntity entity) throws BadRequest {
        ContextEntity context = contextDao.getContextByName(entity.getContext());
        if (context == null) {
            throw new BadRequest("未知文章");
        }
        entity.setContextId(context.getId());
        commentDao.create(entity);
        entity.setCtime(System.currentTimeMillis());
        return entity;
    }

    public int top(Long user, Long commentId) throws BadRequest {
        String username = userDao.getById(user).getUsername();
        if (!username.equals(authorConfig.getName())) {
            throw new BadRequest("?");
        }
        return contextDao.updateTop(commentId);
    }

    private boolean needContext(List<CommentEntity> commentEntityList, Long user) {
        if (commentEntityList != null && commentEntityList.size() != 0 || user == null) {
            return false;
        }
        UserEntity u = userDao.getById(user);
        return u != null && u.getUsername().equals(authorConfig.getName());
    }
}
