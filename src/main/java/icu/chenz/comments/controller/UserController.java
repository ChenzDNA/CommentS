package icu.chenz.comments.controller;

import icu.chenz.comments.adapter.UserAdapter;
import icu.chenz.comments.entity.UserEntity;
import icu.chenz.comments.service.UserService;
import icu.chenz.comments.utils.HandleErrors;
import icu.chenz.comments.utils.R;
import icu.chenz.comments.utils.annotation.NoPermission;
import icu.chenz.comments.utils.cryption.JWTEncryption;
import icu.chenz.comments.utils.exception.BadRequest;
import icu.chenz.comments.utils.exception.ForbiddenRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    @Resource
    private UserAdapter userAdapter;

    @NoPermission
    @PostMapping("/login")
    public R login(@Valid @RequestBody UserEntity user, Errors errors, HttpServletResponse response) throws Exception {
        if (!userAdapter.isEnable()) {
            HandleErrors.handle(errors);
        }
        UserEntity userEntity = userService.login(user);
        String token = JWTEncryption.createToken(userEntity.getId());
        response.addHeader("Authorization", token);
        return R.ok(userEntity);
    }

    @NoPermission
    @PostMapping("/register")
    public R register(@Valid @RequestBody UserEntity user, Errors errors, HttpServletResponse response) throws BadRequest, ForbiddenRequest {
        if (userAdapter.isEnable()) {
            throw new ForbiddenRequest("已禁用");
        }
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

    /**
     * 只携带 token 调用的接口
     */
    @PostMapping("/t")
    public R t(@RequestAttribute("user") Long id) {
        return R.ok(userService.c(id));
    }

    /**
     * 由第三方调用的注册接口，不校验数据。
     */
    @NoPermission
    @PostMapping("/r")
    public R r(@RequestBody UserEntity user, HttpServletRequest request) throws ForbiddenRequest {
        if (!userAdapter.isEnable() || !request.getHeader(userAdapter.getKey()).equals(userAdapter.getValue())) {
            throw new ForbiddenRequest("已禁用");
        }
        return R.ok(userService.r(user));
    }
}
