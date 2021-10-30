package com.gevinzone.homework0702.aop;

import com.gevinzone.homework0702.annotation.CurDataSource;
import com.gevinzone.homework0702.datasource.DataSourceEnum;
import com.gevinzone.homework0702.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DataSourceAspect {
    @Autowired
    DynamicDataSource dataSource;
    @Pointcut("@annotation(com.gevinzone.homework0702.annotation.CurDataSource)")
    public void dataSourcePointcut() {

    }

    @Around("dataSourcePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CurDataSource dataSourceAnnotation = method.getAnnotation(CurDataSource.class);
        DataSourceEnum dataSourceName = dataSourceAnnotation.value() == null ? DataSourceEnum.MASTER : dataSourceAnnotation.value();
        dataSource.setDataSource(dataSourceName);
        log.info("get datasource: {}", dataSourceName);
        try {
            return point.proceed();
        } finally {
            dataSource.clearDataSource();
        }
    }
}
