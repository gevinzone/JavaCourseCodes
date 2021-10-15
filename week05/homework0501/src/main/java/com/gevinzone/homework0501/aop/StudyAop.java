package com.gevinzone.homework0501.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class StudyAop {
    @Pointcut(value="execution(* com.gevinzone.homework0501.service.StudyService.learn(..))")
    public void point() {}

    @Before(value="point()")
    public void before(){
        log.info("========>begin method... ");
    }

    @After(value="point()")
    public void after(){
        log.info("========>after method... ");
    }
}
