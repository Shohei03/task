package com.example.task.aspect;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
public class ControllerAspect {

    @Autowired
    private HttpSession session;

    @Autowired
    private MessageSource messageSource;

    @Before("@within(org.springframework.stereotype.Controller)")
    public void addLoginSuccessMessage(JoinPoint joinPoint){
        String loginMessage = (String) session.getAttribute("loginMessage");

        if(loginMessage != null){
            Object[] args = joinPoint.getArgs();
            for(Object arg : args){
                if(arg instanceof Model) {
                    Model model = (Model) arg;
                    model.addAttribute("loginMessage", loginMessage);
                    session.removeAttribute("loginMessage");
                    break;
                }
            }
        }
    }

}
