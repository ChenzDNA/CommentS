package icu.chenz.securitydemo.controller;

import icu.chenz.securitydemo.client.CommentSClient;
import icu.chenz.securitydemo.constant.CommentSConstant;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author : Chenz
 * @date : 2023-01-27 16:55
 */

@RestController
@RequestMapping("/user")
public class SecurityController implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        request.getSession().setAttribute("username", name);
        response.setContentType("application/json;charset=utf-8");
        Map<String, String> token = CommentSClient.getToken(name);
        response.setHeader(CommentSConstant.COMMENTS_TOKEN_HEADER_KEY, token.get("token"));
        PrintWriter writer = response.getWriter();
        // nickname 是你定义的 nickname，不从 CommentS 里获取。
        writer.write(String.format("""
                {"username":"%s","nickname":"%s","id":%d}
                """, name, name, Long.valueOf(token.get("id"))));
        writer.close();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
    }

    @PostMapping("/register")
    private void register(String username, String password, HttpServletResponse response) throws Exception {
        String nickname = "新用户" + System.currentTimeMillis();
        // 先做一些处理
        // ...
        // 再把数据同步到 CommentS
        Map<String, String> token = CommentSClient.register(username, nickname);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader(CommentSConstant.COMMENTS_TOKEN_HEADER_KEY, token.get("token"));
        PrintWriter writer = response.getWriter();
        writer.write(String.format("""
                {"username":"%s","nickname":"%s","id":%d}
                """, username, nickname, Long.valueOf(token.get("id"))));
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        String s = String.valueOf(CommentSClient.getToken("chenz"));
        System.out.println("token: " + s);
    }
}
