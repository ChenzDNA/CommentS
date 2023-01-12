package icu.chenz.commentx.utils;

import icu.chenz.commentx.utils.exception.BadRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

/**
 * @author : Chenz
 * @date : 2023-01-11 23:01
 */

public class HandleErrors {
    public static void handle(Errors errors) throws BadRequest {
        if (!errors.hasErrors()) {
            return;
        }
        String s = errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        throw new BadRequest(s);
    }
}
