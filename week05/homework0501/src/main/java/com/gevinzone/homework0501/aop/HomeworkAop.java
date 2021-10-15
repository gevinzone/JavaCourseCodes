package com.gevinzone.homework0501.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HomeworkAop {
    @Pointcut(value="execution(* com.gevinzone.homework0501.service.IHomeworkService.*(..))")
//    @Pointcut(value="execution(* com.gevinzone.homework0501.service.IHomeworkService.assignHomework(..))")
    public void point() {

    }

    @Before(value="point()")
    public void before(){
        log.info("========>begin method... ");
    }

    @AfterReturning(value = "point()")
    public void afterReturn(){
        log.info("========>after method return... ");
    }

    @After(value = "point()")
    public void after(){
        log.info("========>after method... ");
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("========>around begin method");
        Object res = joinPoint.proceed();
        log.info("========>around after method");
        return res;

    }
}
