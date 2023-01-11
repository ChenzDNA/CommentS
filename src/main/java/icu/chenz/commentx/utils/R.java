package icu.chenz.commentx.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : Chenz
 * @date : 2023-01-10 23:43
 */

@Getter
public class R {
    HttpStatus code;

    String msg;

    Object data;

    private R(HttpStatus code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R ok(Object data) {
        return ok(null, data);
    }

    public static R ok(String msg, Object data) {
        return new R(HttpStatus.OK, msg, data);
    }

    public static R fail(HttpStatus code, String msg) {
        return new R(code, msg, null);
    }

    @Override
    public String toString() {
        return String.format("""
                {"code":%d,"msg":%s}
                """, code.value(), msg);
    }
}
