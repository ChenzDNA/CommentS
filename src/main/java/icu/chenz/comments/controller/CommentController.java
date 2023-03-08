package icu.chenz.comments.controller;

import icu.chenz.comments.entity.CommentEntity;
import icu.chenz.comments.entity.IDEntity;
import icu.chenz.comments.service.CommentService;
import icu.chenz.comments.utils.HandleErrors;
import icu.chenz.comments.utils.R;
import icu.chenz.comments.utils.annotation.OptionalToken;
import icu.chenz.comments.utils.exception.BadRequest;
import icu.chenz.comments.utils.exception.ForbiddenRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : Chenz
 * @date : 2023-01-09 18:18
 */

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    public R<CommentEntity> create(@RequestAttribute("user") Long user, @Valid @RequestBody CommentEntity comment, Errors errors) throws BadRequest {
        HandleErrors.handle(errors);
        comment.setUser(user);
        if (comment.getReply() != null && comment.getReply() == 0) {
            comment.setReply(null);
        }
        if (comment.getParent() != null && comment.getParent() == 0) {
            comment.setParent(null);
        }
        if (comment.getReply() != null && comment.getParent() == null) {
            comment.setParent(comment.getReply());
        }
        return R.ok(commentService.comment(comment));
    }

    @PostMapping("/delete")
    public R<Integer> delete(@RequestAttribute("user") Long user, @RequestBody IDEntity commentId) throws BadRequest {
        return R.ok(commentService.delete(user, commentId.getId()));
    }

    @OptionalToken
    @GetMapping("/getByContext")
    public R<Map<String, Object>> getByContext(@RequestAttribute(value = "user", required = false) Long user, @RequestParam("context") String context) {
        return R.ok(commentService.getByContext(user, context));
    }

    @PostMapping("/top")
    public R<Integer> top(@RequestAttribute("user") Long user, @RequestBody IDEntity commentId) throws ForbiddenRequest, BadRequest {
        return R.ok(commentService.top(user, commentId.getId()));
    }
}
