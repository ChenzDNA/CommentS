package icu.chenz.commentx.controller;

import icu.chenz.commentx.entity.UserEntity;
import icu.chenz.commentx.service.UserService;
import icu.chenz.commentx.utils.HandleErrors;
import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.annotation.NoPermission;
import icu.chenz.commentx.utils.exception.BadRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Chenz
 * @date : 2023-01-09 18:18
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @NoPermission
    @PostMapping("/login")
    public R login(@Valid @RequestBody UserEntity user, Errors errors, HttpServletResponse response) throws BadRequest {
        HandleErrors.handle(errors, response);
        return R.ok(userService.login(user));
    }
}
