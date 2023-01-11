package icu.chenz.commentx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author : Chenz
 * @date : 2023-01-08 21:35
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends Entity {
    // 评论所在的页面
    String context;
    @NotNull(message = "评论不能为空")
    @Size(min = 1, max = 65534,message = "评论内容过长！")
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
}
