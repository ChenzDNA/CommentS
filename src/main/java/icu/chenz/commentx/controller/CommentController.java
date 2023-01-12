package icu.chenz.commentx.controller;

import icu.chenz.commentx.entity.CommentEntity;
import icu.chenz.commentx.service.CommentService;
import icu.chenz.commentx.utils.HandleErrors;
import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.exception.BadRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
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
    public R create(@RequestAttribute("user") Long user, @Valid @RequestBody CommentEntity comment, Errors errors, HttpServletResponse response) throws BadRequest {
        HandleErrors.handle(errors, response);
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
