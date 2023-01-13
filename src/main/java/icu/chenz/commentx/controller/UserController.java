package icu.chenz.commentx.controller;

import icu.chenz.commentx.entity.UserEntity;
import icu.chenz.commentx.service.UserService;
import icu.chenz.commentx.utils.HandleErrors;
import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.annotation.NoPermission;
import icu.chenz.commentx.utils.cryption.JWTEncryption;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @NoPermission
    @PostMapping("/login")
    public R login(@Valid @RequestBody UserEntity user, Errors errors, HttpServletResponse response) throws BadRequest {
        HandleErrors.handle(errors);
        UserEntity userEntity = userService.login(user);
        String token = JWTEncryption.createToken(userEntity.getId());
        response.addHeader("Authorization", token);
        return R.ok(userEntity);
    }

    @NoPermission
    @PostMapping("/register")
    public R register(@Valid @RequestBody UserEntity user, Errors errors, HttpServletResponse response) throws BadRequest {
        HandleErrors.handle(errors);
        UserEntity userEntity = userService.register(user);
        String token = JWTEncryption.createToken(userEntity.getId());
        response.addHeader("Authorization", token);
        return R.ok(userEntity);
    }

    @PostMapping("/updateNickname")
    public R updateNickname(@RequestAttribute("user") Long id, String nickname) {
        return R.ok(userService.updateNickname(id, nickname));
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestAttribute("user") Long id, String password) {
        return R.ok(userService.updatePassword(id, password));
    }

    @PostMapping("/t")
    public R c(@RequestAttribute("user") Long id) {
        return R.ok(userService.c(id));
    }
}
