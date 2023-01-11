package icu.chenz.commentx.utils;

import icu.chenz.commentx.utils.exception.BadRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author : Chenz
 * @date : 2023-01-11 23:01
 */

public class HandleErrors {
    public static void handle(Errors errors, HttpServletResponse response) throws BadRequest {
        if (!errors.hasErrors()) {
            return;
        }
        String s = errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        throw new BadRequest(s);
    }
}
