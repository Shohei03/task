package com.example.task.domain.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    // private final String successMessage = "ログインに成功しました！";

    @Autowired
    private MessageSource messageSource;

    public CustomAuthenticationSuccessHandler() {

        super.setDefaultTargetUrl("/");
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {
        // ここに任意の処理を記述する
        String successMessage = messageSource.getMessage("login.success", null, null);

        // 成功メッセージをセッションに設定
        request.getSession().setAttribute("loginMessage", successMessage);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}