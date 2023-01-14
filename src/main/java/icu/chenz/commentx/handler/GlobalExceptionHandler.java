package icu.chenz.commentx.handler;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.exception.BadRequest;
import icu.chenz.commentx.utils.exception.ForbiddenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Chenz
 * @date : 2023-01-11 23:55
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequest.class)
    public R badRequest(BadRequest e) {
        return R.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ForbiddenRequest.class)
    public R forbiddenRequest(ForbiddenRequest e) {
        return R.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({TokenExpiredException.class, SignatureVerificationException.class})
    public R tokenExpired() {
        return R.fail(HttpStatus.UNAUTHORIZED, "请重新登录");
    }

    @ExceptionHandler(Exception.class)
    public R tokenError() {
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR, "未知错误，请联系开发者");
    }
}
