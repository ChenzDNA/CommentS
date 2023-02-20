package icu.chenz.comments.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : Chenz
 * @date : 2023-01-10 23:43
 */

@Getter
public class R<T> {
    int code;

    String msg;

    T data;

    private R(HttpStatus code, String msg, T data) {
        this.code = code.value();
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok(T data) {
        return ok(null, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(HttpStatus.OK, msg, data);
    }

    public static R<String> fail(HttpStatus code, String msg) {
        return new R<>(code, msg, null);
    }

    @Override
    public String toString() {
        return String.format("""
                {"code":%d,"msg":"%s"}
                """, code, msg);
    }
}
