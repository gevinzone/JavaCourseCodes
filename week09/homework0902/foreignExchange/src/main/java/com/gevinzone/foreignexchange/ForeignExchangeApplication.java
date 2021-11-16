package com.gevinzone.foreignexchange;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
@MapperScan("com.gevinzone.foreignexchange.mapper")
public class ForeignExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForeignExchangeApplication.class, args);
    }
}
