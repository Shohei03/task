package com.example.task.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllAdvice {

    private static final Logger logger = LoggerFactory.getLogger("appLogger");


    //データベース関連の例外処理
    @ExceptionHandler(DataAccessException.class)
    public String dataAccessExceptionHandler(DataAccessException e, Model model){
        // 例外情報をログに出力
        logger.error("Exception caught: ", e);


        model.addAttribute("error", "");
        model.addAttribute("message", "DataAccessExceptionが発生しました。");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

    // 権限エラーの例外処理 (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedExceptionHandler(AccessDeniedException e, Model model) {
        logger.warn("AccessDeniedException caught: ", e);

        model.addAttribute("error", "アクセス拒否");
        model.addAttribute("message", "アクセス権限がありません。");
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());

        return "error/403"; // 403用エラーページ
    }

    /* その他の例外処理*/
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        logger.error("Exception caught: ", e);

        model.addAttribute("error", "");
        model.addAttribute("message", "Exceptionが発生しました。");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }
}
