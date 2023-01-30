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
import org.springframework.http.HttpStatus;
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
        if (userAdapter.isEnable()) {
            throw new ForbiddenRequest("已禁用");
        }
        HandleErrors.handle(errors);
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
        if (nickname == null || nickname.length() == 0) {
            return R.fail(HttpStatus.BAD_REQUEST, "昵称不可为空");
        }
        return R.ok(userService.updateNickname(id, nickname));
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestAttribute("user") Long id, String password) {
        if (password == null || password.length() == 0) {
            return R.fail(HttpStatus.BAD_REQUEST, "密码不可为空");
        }
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
     * 对接的系统调用此接口，不校验数据。
     */
    @NoPermission
    @PostMapping("/r")
    public R r(String username, String nickname, HttpServletRequest request, HttpServletResponse response) throws ForbiddenRequest {
        if (!userAdapter.isEnable() || !userAdapter.getValue().equals(request.getHeader(userAdapter.getKey()))) {
            throw new ForbiddenRequest("已禁用");
        }
        UserEntity user = new UserEntity(username, "adapted", nickname);
        userService.r(user);
        response.setHeader("Authorization", JWTEncryption.createToken(user.getId()));
        return R.ok(user.getId());
    }

    @NoPermission
    @PostMapping("/g")
    public R t(String username, HttpServletRequest request, HttpServletResponse response) throws ForbiddenRequest {
        if (!userAdapter.isEnable() ||
                !userAdapter.getTokenGenerationValue().equals(request.getHeader(userAdapter.getTokenGenerationKey()))) {
            throw new ForbiddenRequest("已禁用");
        }
        UserEntity user = userService.c(username);
        response.setHeader("Authorization", JWTEncryption.createToken(user.getId()));
        return R.ok(user.getId());
    }
}
