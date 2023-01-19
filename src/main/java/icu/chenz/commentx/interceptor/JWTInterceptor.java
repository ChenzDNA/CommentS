package icu.chenz.commentx.interceptor;

import icu.chenz.commentx.utils.R;
import icu.chenz.commentx.utils.annotation.NoPermission;
import icu.chenz.commentx.utils.cryption.JWTEncryption;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * @author : Chenz
 * @date : 2023-01-11 20:12
 */

public class JWTInterceptor implements HandlerInterceptor {

    public JWTInterceptor(String secret, Long expireTime) {
        JWTEncryption.initAlgorithm(secret, expireTime);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod method) {
            response.setHeader("Content-Type","application/json;charset=utf8");
            if (method.hasMethodAnnotation(NoPermission.class)) {
                return true;
            }
            if (request.getMethod().equals("GET")) {
                return true;
            }
            String token = request.getHeader("Authentication");
            PrintWriter writer = response.getWriter();
            if (token == null) {
                writer.write(R.fail(HttpStatus.UNAUTHORIZED, "未登录").toString());
                return false;
            }
            request.setAttribute("user", JWTEncryption.verifyToken(token));
        }
        return true;
    }
}
