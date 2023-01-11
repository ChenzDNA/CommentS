package icu.chenz.commentx.handler;

import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.exception.BadRequest;
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
}
