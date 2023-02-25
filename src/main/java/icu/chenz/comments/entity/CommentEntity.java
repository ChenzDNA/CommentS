package icu.chenz.comments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author : Chenz
 * @date : 2023-01-08 21:35
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends BaseEntity {
    // 评论所在的页面
    Long contextId;
    String context;
    @NotNull(message = "评论不能为空")
    @Size(min = 1, max = 65534, message = "没有评论内容或内容过长！")
    String content;
    // 是否可见
    @JsonIgnore
    Integer visible;
    // 回复的评论id
    Long reply;
    // 楼中楼的父评论id
    Long parent;
    // 发评论的用户
    Long user;
    List<CommentEntity> subComments;
    Integer likes = 0;
    Integer dislikes = 0;

    public CommentEntity(String content, String context) {
        this.content = content;
        this.context = context;
    }
}
