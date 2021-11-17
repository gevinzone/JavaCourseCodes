package com.gevinzone.cnbank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan({"com.gevinzone.cnbank.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class CnBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(CnBankApplication.class, args);
    }
}
