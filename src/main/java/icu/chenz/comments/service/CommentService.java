package icu.chenz.comments.service;

import icu.chenz.comments.config.AuthorConfig;
import icu.chenz.comments.dao.CommentDao;
import icu.chenz.comments.dao.ContextDao;
import icu.chenz.comments.dao.LikeDao;
import icu.chenz.comments.dao.UserDao;
import icu.chenz.comments.entity.CommentEntity;
import icu.chenz.comments.entity.ContextEntity;
import icu.chenz.comments.entity.IDEntity;
import icu.chenz.comments.entity.UserEntity;
import icu.chenz.comments.utils.exception.BadRequest;
import icu.chenz.comments.utils.exception.ForbiddenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private final LikeDao likeDao;

    public Map<String, Object> getByContext(Long user, String context) {
        HashMap<String, Object> res = new HashMap<>(2);
        res.put("author", authorConfig.getName());
        List<CommentEntity> comments = commentDao.getByContext(context);
        if (needContext(comments, user)) {
            contextDao.createContext(context);
        }
        res.put("comments", comments);

        List<CommentEntity> commentEntityFlatList = new ArrayList<>();
        if (comments != null && comments.size() != 0) {
            commentEntityFlatList.addAll(comments);
            comments.stream().map(CommentEntity::getSubComments).forEach(commentEntityFlatList::addAll);
            commentEntityFlatList.sort(Comparator.comparingLong(IDEntity::getId));
        }

        // todo 异步处理 评论涉及的用户
        if (commentEntityFlatList.size() != 0) {
            // 提取顶层评论的 user
            HashSet<Long> query = new HashSet<>(commentEntityFlatList.stream().map(CommentEntity::getUser).toList());
            res.put("users", userDao.getByIds(query));
        } else {
            res.put("users", new ArrayList<>());
        }

        // todo 异步处理 置顶的评论
        ContextEntity contextByName = contextDao.getContextByName(context);
        if (contextByName != null && contextByName.getTop() != null) {
            res.put("top", contextByName.getTop());
        } else {
            res.put("top", -1);
        }

        // todo 异步处理 like数据
        if (commentEntityFlatList.size() != 0) {
            List<Long> commentIDList = commentEntityFlatList.stream().map(CommentEntity::getId).toList();
            Map<Long, Map<String, Object>> likeMap = likeDao.getCommentsLike(commentIDList);
            for (CommentEntity c : commentEntityFlatList) {
                Map<String, Object> likes = likeMap.get(c.getId());
                if (likes != null) {
                    c.setLikes(((BigDecimal) likes.get("like")).intValue());
                    c.setDislikes(((BigDecimal) likes.get("dislike")).intValue());
                }
            }
            res.put("userLike", likeDao.getUserLike(user, commentIDList));
        } else {
            res.put("userLike", new ArrayList<>());
        }

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

    public int top(Long user, Long commentId) throws ForbiddenRequest, BadRequest {
        String username = userDao.getById(user).getUsername();
        if (!username.equals(authorConfig.getName())) {
            throw new ForbiddenRequest("?");
        }
        CommentEntity comment = commentDao.getById(commentId);
        if (comment == null) {
            throw new BadRequest("虚空评论。");
        }
        if (comment.getParent() != null) {
            throw new BadRequest("回复评论不能置顶。");
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
