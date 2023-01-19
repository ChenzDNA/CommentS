package icu.chenz.comments.controller;

import icu.chenz.comments.entity.CommentEntity;
import icu.chenz.comments.service.CommentService;
import icu.chenz.comments.utils.HandleErrors;
import icu.chenz.comments.utils.R;
import icu.chenz.comments.utils.exception.BadRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    public R create(@RequestAttribute("user") Long user, @Valid @RequestBody CommentEntity comment, Errors errors) throws BadRequest {
        HandleErrors.handle(errors);
        comment.setUser(user);
        return R.ok(commentService.comment(comment));
    }

    @PostMapping("/delete")
    public R delete(@RequestAttribute("user") Long user, Long id) throws BadRequest {
        return R.ok(commentService.delete(user, id));
    }

    @GetMapping("/getByContext")
    public R getByContext(@RequestParam("context") String context) {
        return R.ok(commentService.getByContext(context));
    }
}
