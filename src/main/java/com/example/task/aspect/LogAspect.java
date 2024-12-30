package com.example.task.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("@within(org.springframework.stereotype.Controller)")
    public void startLog(JoinPoint jp) {
        log.info("メソッド開始：" + jp.getSignature());
    }

    @After("@within(org.springframework.stereotype.Controller)")
    public void endLog(JoinPoint jp) {
        log.info("メソッド終了：" + jp.getSignature());
    }


}
